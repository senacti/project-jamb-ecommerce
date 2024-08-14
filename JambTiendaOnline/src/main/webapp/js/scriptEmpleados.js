$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_empleados').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Empleados');

    var tabla = $("table#tablaEmpleados"),
            mdlEmp = $("#modal-lg"),
            frmEmp = $("#frmEmpleado"),
            txtNombres = $("#nombres"),
            txtEmail = $("#email"),
            cboGeneroEmp = $("#combo_genero_empleado"),
            txtNumIdentificacion = $("#numIdentificacion"),
            chkEstadoEmp = $("#estadoEmp"),
            txtApellidos = $("#apellidos"),
            cboCargo = $("#combo_cargo"),
            cboTipoIdentificacion = $("#combo_TipoIdentificacion"),
            txtTelefono = $("#telefonoEmpleado"),
            txtIdEmpleado = $("#idE");//input de tipo hiden

    mdlEmp.on("show.bs.modal", function () {
        frmEmp[0].reset();
    });

    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        eliminarEmpleado(id);
    });

    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerEmpleado(id);
        mdlEmp.modal({backdrop: 'static', keyboard: false});
    });

    function leerEmpleado(idTemp) {
        $.ajax({
            url: "../empleado?accion=leer",
            type: 'POST',
            dataType: 'json',
            data: {id: idTemp},
            success: function (data) {
                txtIdEmpleado.val(data.idEmpleado);
                txtNombres.val(data.nombres);
                txtApellidos.val(data.apellidos);
                cboGeneroEmp.val(data.sexo);
                txtEmail.val(data.email);
                txtTelefono.val(data.telefono);
                txtNumIdentificacion.val(data.numero_identificacion);
                cboCargo.val(data.cargo.id_cargo);
                cboTipoIdentificacion.val(data.tipoIdentifcacion.idTipoIdentificacion);
                chkEstadoEmp.prop('checked', data.vigencia);
            }
        });
        $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Empleado');
        $('#titulo').html('Formulario de Actualización');
    }

    frmEmp.on("submit", function (e) {
        e.preventDefault();
        var obj = {
            idEmpleado: txtIdEmpleado.val(),
            nombres: txtNombres.val().toUpperCase(),
            apellidos: txtApellidos.val().toUpperCase(),
            sexo: cboGeneroEmp.val(),
            email: txtEmail.val().toUpperCase(),
            telefono: txtTelefono.val(),
            tipoIdentifcacion: {idTipoIdentificacion: parseInt(cboTipoIdentificacion.val())},
            vigencia: chkEstadoEmp[0].checked,
            numero_identificacion: txtNumIdentificacion.val(),
            cargo: {id_cargo: parseInt(cboCargo.val())}
        };
        registrarEmpleado(obj);
    });

    function registrarEmpleado(json) {
        $.ajax({
            url: "../empleado?accion=" + (parseInt(json.idEmpleado) === 0 ? 'agregar' : 'editar'),
            //con parseInt lo convertimos a entero porque sino lo lee como cadena.
            type: 'POST',
            dataType: 'json',
            data: {emp: JSON.stringify(json)},
            success: function (data) {
                if (data.rpt) {
                    swal("Mensaje del Sistema", data.msj, "success");
                    listarEmpleado();
                    frmEmp[0].reset();
                    mdlEmp.modal("hide");
                    txtIdEmpleado.val(0);
                    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Empleado');
                    $('#titulo').html('Formulario de Registro');
                } else {
                    swal("Error", data.msj, "error");
                }
            }
        });
    }

    function eliminarEmpleado(idTemp) {
        swal({
            title: "Esta seguro de desactivar?",
            text: "Una vez desactivado ya no estará disponible!",
            type: "warning",
            showCancelButton: true,
            confirmButtonClass: "btn-danger",
            confirmButtonText: "Si, desactivar!",
            cancelButtonText: "No, cancelar!",
            closeOnConfirm: false,
            closeOnCancel: false
        },
                function (isConfirm) {
                    if (isConfirm) {
                        $.ajax({
                            url: "../empleado?accion=eliminar",
                            type: 'POST',
                            dataType: 'json',
                            data: {id: idTemp},
                            success: function (data) {
                                swal("Desactivado!", data.msj, "success");
                                listarEmpleado();
                            }
                        });
                    } else {
                        swal("Cancelado", "Peticion cancelada!", "error");
                    }
                });
    }

    function listarTipoIdentificacion() {
        $.ajax({
            url: "../empleado?accion=listarTipoIdentificacion",
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                var combo_tipoIdentificacion = '';
                for (var i = 0; i < data.length; i++) {
                    combo_tipoIdentificacion += '<option value="' + data[i].idTipoIdentificacion + '">' + data[i].nombre_tipo_identificacion + '</option>';
                }
                cboTipoIdentificacion.html(combo_tipoIdentificacion);
            }
        });
    }
    listarTipoIdentificacion();

    function listarCargo() {
        $.ajax({
            url: "../empleado?accion=listarCargo",
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                var combo_cargo = '';
                for (var i = 0; i < data.length; i++) {
                    combo_cargo += '<option value="' + data[i].id_cargo + '">' + data[i].nombre_cargo + '</option>';
                }
                cboCargo.html(combo_cargo);
            }
        });
    }
    listarCargo();

    function listarEmpleado() {
        $.ajax({
            url: "../empleado?accion=listar",
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                var tpl = "";
                for (var i = 0; i < data.length; i++) {
                    tpl += "<tr>"
                            + "<td>" + data[i].idEmpleado + "</td>"
                            + "<td>" + data[i].nombres + "</td>"
                            + "<td>" + data[i].telefono + "</td>"
                            + "<td>" + data[i].tipoIdentifcacion.nombre_tipo_identificacion + "</td>"
                            + "<td>" + data[i].numero_identificacion + "</td>"
                            + "<td>" + data[i].cargo.nombre_cargo + "</td>"
                            + "<td style=\"text-align: center\">" + (data[i].vigencia === true ? '<h5><span class =\"badge badge-success\">Activo</span></h5>' : '<h5><span class =\"badge badge-danger\">Inactivo</span></h5>') + "</td>"
                            + "<td style=\"text-align: center\"><button title=\"Editar\" class=\"btn btn-warning\">"
                            + "<span class=\"fas fa-edit\"></span></button> "
                            + "<button title=\"Eliminar\" class=\"btn btn-danger\">"
                            + "<span class=\"fa fa-trash\"></span></button></td>"
                            + "</tr>";
                }
                tabla.find("tbody").html(tpl);
                tabla.dataTable();
            }
        });
    }
    listarEmpleado();
});

function cancelarPeticion() {
    var frmEmp = $("#frmEmpleado"),
            txtIdEmpleado = $("#idE");
    frmEmp[0].reset();
    txtIdEmpleado.val(0);
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Empleado');
    $('#titulo').html('Formulario de Registro');
}

function cambiarmaxLength() {
    var cboTipoIdentificacion = $("#combo_TipoIdentificacion");
    var inputNumeroIdentificacion = $("#numIdentificacion");
    inputNumeroIdentificacion.val('');
    var seleccion = parseInt(cboTipoIdentificacion.val());
    switch (seleccion) {
        case 1:
            inputNumeroIdentificacion.attr("maxlength", 8);
            break;
        case 2:
            inputNumeroIdentificacion.attr("maxlength", 11);
            break;
    }
}

function reporte(accion) {
    $.get('../empleado?accion=listar', function (r) {
        if (r) {
            $('#accion').val(accion);
            $('#lista').val(JSON.stringify(r));
            $('#frmReporte').submit();
        } else {
            alert('El reporte no se pudo generar debido a un error : ' + r);
        }
    });
}

