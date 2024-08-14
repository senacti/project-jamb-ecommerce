$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_clientes').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Clientes');

    var tabla = $("table#tablaClientes"),
            mdlCli = $("#modal-lg"),
            frmCli = $("#frmClientes"),
            txtNombres = $("#nombresClientes"),
            txtEmail = $("#correoCliente"),
            txtNumIdentificacion = $("#numIdentificacion"),
            txtDireccionCliente = $("#direccionCliente"),
            cboTipoIdentificacion = $("#combo_TipoIdentificacion"),
            txtPassword = $("#passwordCliente"),
            txtIdCliente = $("#idC");//input de tipo hiden

    mdlCli.on("show.bs.modal", function () {
        frmCli[0].reset();
    });

    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerCliente(id);
        mdlCli.modal({backdrop: 'static', keyboard: false});
    });

    function leerCliente(idTemp) {
        $.ajax({
            url: "../srvCliente?accion=leer",
            type: 'POST',
            dataType: 'json',
            data: {id: idTemp},
            success: function (data) {
                txtIdCliente.val(data.id);
                txtNombres.val(data.nombres);
                txtDireccionCliente.val(data.direccion);
                txtEmail.val(data.email);
                txtPassword.val(data.pass);
                txtNumIdentificacion.val(data.numero_doc);
                cboTipoIdentificacion.val(data.tipoIdentificacion.idTipoIdentificacion);
            }
        });
        $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Cliente');
        $('#titulo').html('Formulario de Actualización');
    }

    frmCli.on("submit", function (e) {
        e.preventDefault();
        var obj = {
            id: txtIdCliente.val(),
            nombres: txtNombres.val().toUpperCase(),
            direccion: txtDireccionCliente.val().toUpperCase(),
            email: txtEmail.val().toUpperCase(),
            pass: txtPassword.val().toUpperCase(),
            tipoIdentificacion: {idTipoIdentificacion: parseInt(cboTipoIdentificacion.val())},
            numero_doc: txtNumIdentificacion.val().toUpperCase()
        };
        registrarCliente(obj);
    });

    function registrarCliente(json) {
        $.ajax({
            url: "../srvCliente?accion=" + (parseInt(json.id) === 0 ? 'agregar' : 'editar'),
            //con parseInt lo convertimos a entero porque sino lo lee como cadena.
            type: 'POST',
            dataType: 'json',
            data: {cli: JSON.stringify(json)},
            success: function (data) {
                if (data.rpt) {
                    swal("Mensaje del Sistema", data.msj, "success");
                    listarClientes();
                    frmCli[0].reset();
                    mdlCli.modal("hide");
                    txtIdCliente.val(0);
                    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Cliente');
                    $('#titulo').html('Formulario de Registro');
                } else {
                    swal("Error", data.msj, "error");
                }
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

    function listarClientes() {
        $.ajax({
            url: "../srvCliente?accion=listar",
            type: 'GET',
            dataType: 'JSON',
            success: function (data) {
                var tpl = "";
                for (var i = 0; i < data.length; i++) {
                    tpl += "<tr>"
                            + "<td>" + data[i].id + "</td>"
                            + "<td>" + data[i].nombres + "</td>"
                            + "<td class=\"text-center\">" + data[i].tipoIdentificacion.nombre_tipo_identificacion + "</td>"
                            + "<td>" + data[i].numero_doc + "</td>"
                            + "<td>" + data[i].direccion + "</td>"
                            + "<td>" + data[i].email + "</td>"
                            + "<td style=\"text-align: center\"><button title=\"Editar\" class=\"btn btn-warning\">"
                            + "<span class=\"fas fa-edit\"></span></button></td>"
                            + "</tr>";
                }
                tabla.find("tbody").html(tpl);
                tabla.dataTable();
            }
        });
    }
    listarClientes();
});

function cancelarPeticion() {
    var frmCli = $("#frmClientes"),
            txtIdCliente = $("#idC");
    frmCli[0].reset();
    txtIdCliente.val(0);
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


