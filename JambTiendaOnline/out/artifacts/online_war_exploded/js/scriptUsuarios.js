var tabla = $("table#tablaUsuarios"),
        mdlUsu = $("#modal-lg"),
        frmUsu = $("#frmUsuario"),
        txtNombreUsuario = $("#nombreUsuario"),
        txtClave = $("#clave"),
        chkEstadoUsuario = $("#estadoUsu"),
        cboEmpleadoSinLogin = $("#combo_empleado_sin_login"),
        txtIdUsuario = $("#idU");//input de tipo hiden

$(document).ready(function () {

    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_usuarios').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Usuarios');

    mdlUsu.on("show.bs.modal", function () {
        frmUsu[0].reset();
    });

    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        eliminarUsuario(id);
    });

    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerUsuario(id);
        mdlUsu.modal({backdrop: 'static', keyboard: false});
    });
    frmUsu.on("submit", function (e) {
        e.preventDefault();
        var obj = {
            idLogin: txtIdUsuario.val(),
            usuario: txtNombreUsuario.val().toUpperCase(),
            clave: txtClave.val().toUpperCase(),
            empleado: {idEmpleado: parseInt(cboEmpleadoSinLogin.val())},
            vigencia: chkEstadoUsuario[0].checked
        };
        registrarUsuario(obj);
    });
    listarEmpleadoSinLogin();
    listarUsuarios();

});

function leerUsuario(idTemp) {
    $.ajax({
        url: "../session?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtIdUsuario.val(data.idLogin);
            txtNombreUsuario.val(data.usuario);
            txtClave.val(data.clave);
            var item = '<option value="' + data.empleado.idEmpleado + '" selected>' + (data.empleado.nombres) + '</option>';
            cboEmpleadoSinLogin.append(item);
            //cboEmpleadoSinLogin.val(data.empleado.idEmpleado);
            chkEstadoUsuario.prop('checked', data.vigencia);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Usuario');
    $('#titulo').html('Formulario de Actualización');
}

function registrarUsuario(json) {
    $.ajax({
        url: "../session?accion=" + (parseInt(json.idLogin) === 0 ? 'registrar' : 'editar'),
        //con parseInt lo convertimos a entero porque sino lo lee como cadena.
        type: 'POST',
        dataType: 'json',
        data: {usu: JSON.stringify(json)},
        success: function (data) {
            if (data.rpt) {
                swal("Mensaje del Sistema", data.msj, "success");
                listarUsuarios();
                listarEmpleadoSinLogin();
                frmUsu[0].reset();
                mdlUsu.modal("hide");
                txtIdUsuario.val(0);
                $('#btn-save').html('<i class="fas fa-save"></i> Registrar Usuario');
                $('#titulo').html('Formulario de Registro');
            } else {
                swal("Error", data.msj, "error");
            }
        }
    });
}

function eliminarUsuario(idTemp) {
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
                        url: "../session?accion=desactivarUsuario",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarUsuarios();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function listarEmpleadoSinLogin() {
    $.ajax({
        url: "../session?accion=listarEmpleadosSinLogin",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var combo_empleado_SL = '';
            for (var i = 0; i < data.length; i++) {
                combo_empleado_SL += '<option value="' + data[i].idEmpleado + '">' + data[i].nombres + '</option>';
            }
            cboEmpleadoSinLogin.html(combo_empleado_SL);
        }
    });
}

function listarUsuarios() {
    $.ajax({
        url: "../session?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr>"
                        + "<td>" + data[i].idLogin + "</td>"
                        + "<td>" + data[i].usuario + "</td>"
                        + "<td>" + data[i].clave + "</td>"
                        + "<td>" + data[i].nombres + "</td>"
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
function cancelarPeticion() {
    var frmUsu = $("#frmUsuario"),
            txtIdUsuario = $("#idU");
    frmUsu[0].reset();
    txtIdUsuario.val(0);
    listarEmpleadoSinLogin();
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Usuario');
    $('#titulo').html('Formulario de Registro');
}



