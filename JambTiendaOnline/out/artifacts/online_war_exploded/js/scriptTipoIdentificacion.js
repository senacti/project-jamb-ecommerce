//Declaramos nuestras variables globlales
var tabla = $("table#tablaTipoIdentifacion"),
        frmCargo = $("#frmTipoIdentifacion"),
        mdlTipoIden = $("#modal-lg"),
        txtTipoIdent = $("#nombreTipoIden"),
        chkEstadoTipoIden = $("#chkEstadoTipo"),
        txtIdTipoIden = $("#idTipoIden");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_tipoIdentificacion').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Tipo Identificación');

    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        desactivarTipoIdentificacion(id);
    });
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerTipoIdentificacion(id);
        mdlTipoIden.modal({backdrop: 'static', keyboard: false});
    });

    //Atributos de html
    var requisitos = {
        required: true
    };

    $('#frmTipoIdentifacion').validate({
        submitHandler: function () {
            var json = {
                idTipoIdentificacion: txtIdTipoIden.val(),
                nombre_tipo_identificacion: txtTipoIdent.val().toUpperCase(),
                vigencia: chkEstadoTipoIden[0].checked
            };

            $.ajax({
                url: "../tipoIdentificacion?accion=" + (parseInt(json.idTipoIdentificacion) === 0 ? 'agregar' : 'editar'),
                type: 'POST',
                dataType: 'json',
                data: {tp: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarTipoIdentificacion();
                        frmCargo[0].reset();
                        mdlTipoIden.modal("hide");
                        txtIdTipoIden.val(0);
                        chkEstadoTipoIden.attr('disabled', 'disabled');
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Tipo Identificación');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            nombreTipoIden: requisitos
        },
        messages: {
            nombreTipoIden: {
                required: "Por favor, ingresa un nombre para el tipo de identificación"
            }
        },
        errorElement: 'span',
        errorPlacement: function (error, element) {
            error.addClass('invalid-feedback');
            element.closest('.form-group').append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass('is-invalid');
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass('is-invalid');
        }
    });
    listarTipoIdentificacion();
});

function listarTipoIdentificacion() {
    $.ajax({
        url: "../tipoIdentificacion?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].idTipoIdentificacion + "</td>"
                        + "<td>" + data[i].nombre_tipo_identificacion + "</td>"
                        + "<td>" + (data[i].vigencia === true ? '<h5><span class =\"badge badge-success\">Activo</span></h5>' : '<h5><span class =\"badge badge-danger\">Inactivo</span></h5>') + "</td>"
                        + "<td nowrap><button title=\"Editar\" class=\"btn btn-warning\">"
                        + "<span class=\"fas fa-edit\"></span></button> "
                        + "<button title=\"Desactivar\" class=\"btn btn-danger\">"
                        + "<span class=\"fa fa-trash\"></span></button></td>"
                        + "</tr>";
            }
            tabla.find("tbody").html(tpl);
            tabla.dataTable();
        }
    });
}

function desactivarTipoIdentificacion(idTemp) {
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
                        url: "../tipoIdentificacion?accion=desactivar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarTipoIdentificacion();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerTipoIdentificacion(idTemp) {
    $.ajax({
        url: "../tipoIdentificacion?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtIdTipoIden.val(data.idTipoIdentificacion);
            txtTipoIdent.val(data.nombre_tipo_identificacion);
            chkEstadoTipoIden.prop('checked', data.vigencia);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Tipo de Identificación');
    $('#titulo').html('Formulario de Actualización');
    chkEstadoTipoIden.removeAttr('disabled');
}

function cancelarPeticion() {
    frmCargo[0].reset();
    txtIdTipoIden.val(0);
    chkEstadoTipoIden.attr('disabled', 'disabled');
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Tipo de Identificación');
    $('#titulo').html('Formulario de Registro');
    txtTipoIdent.removeClass('is-invalid');//Remueve la clase is-invalid
    $('#nombreTipoIden-error').remove();//remueve la etiqueta span.

}

