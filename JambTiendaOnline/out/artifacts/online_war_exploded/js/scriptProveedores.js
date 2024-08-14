var tabla = $('#tablaProveedores'),
        frmProveedor = $('#frmProveedor'),
        mdlProveedor = $('#modal-lg'),
        txtRazonSocial = $('#razonSocial'),
        txtDireccion = $('#direccionProve'),
        cboTipoIdentificacion = $('#combo_TipoIdentificacion'),
        txtNumeroDoc = $('#numDocPro'),
        txtEmail = $('#correoProveedor'),
        txtTelefono = $('#telefonoPro'),
        chkEstado = $('#estadoPro'),
        txtIdProveedor = $('#idPro');
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros'); //id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open'); //Hacemos que el menu se despliegue.
    let a = $('#li_proveedores').find('a'); //id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active'); //Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Proveedores');

    ///REGISTRAR PROVEEDOR VALIDADO CON JQUERY VALIDATION

    ///ATRIBUTOS DEL HTML
    var requisitos = {
        required: true
    };

    frmProveedor.validate({
        submitHandler: function () {
            var json = {
                id: txtIdProveedor.val(),
                razon_social: txtRazonSocial.val().toUpperCase(),
                direccion: txtDireccion.val().toUpperCase(),
                telefono: txtTelefono.val().toUpperCase(),
                email: txtEmail.val().toUpperCase(),
                tipoIdentificacion: {idTipoIdentificacion: parseInt(cboTipoIdentificacion.val())},
                numero_doc: txtNumeroDoc.val().toUpperCase(),
                estado: chkEstado[0].checked
            };

            $.ajax({
                url: "../srvProveedor?accion=" + (parseInt(json.id) === 0 ? 'registrar' : 'actualizar'),
                type: 'POST',
                dataType: 'json',
                data: {prov: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarProveedores();
                        frmProveedor[0].reset();
                        mdlProveedor.modal("hide");
                        txtIdProveedor.val(0);
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Proveedor');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            razonSocial: requisitos,
            combo_TipoIdentificacion: requisitos,
            correoProveedor: requisitos,
            direccionProve: requisitos,
            numDocPro: requisitos,
            telefonoPro: requisitos
        },
        messages: {
            razonSocial: {
                required: "Por favor, Ingrese el nombre de la empresa"
            },
            direccionProve: {
                required: "Por favor, Ingrese la dirección de la empresa"
            },
            combo_TipoIdentificacion: {
                required: "Por favor, Seleccione el tipo de identificación"
            },
            numDocPro: {
                required: "Por favor, Ingrese el numero de documento"
            },
            correoProveedor: {
                required: "Por favor, Ingrese un correo electrónico válido"
            },
            telefonoPro: {
                required: "Por favor, Ingrese el telefono de la empresa a registrar"
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
    listarProveedores();

    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        desactivarProveedor(id);
    });
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerProveedor(id);
        mdlProveedor.modal({backdrop: 'static', keyboard: false});
    });
});

function leerProveedor(idTemp) {
    $.ajax({
        url: "../srvProveedor?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtIdProveedor.val(data.id);
            txtRazonSocial.val(data.razon_social);
            txtDireccion.val(data.direccion);
            cboTipoIdentificacion.val(data.tipoIdentificacion.idTipoIdentificacion);
            txtNumeroDoc.val(data.numero_doc);
            txtEmail.val(data.email);
            txtTelefono.val(data.telefono);
            chkEstado.prop('checked', data.estado);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Proveedor');
    $('#titulo').html('Formulario de Actualización');
}

function listarProveedores() {
    $.ajax({
        url: "../srvProveedor?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].id + "</td>"
                        + "<td>" + data[i].razon_social + "</td>"
                        + "<td>" + data[i].direccion + "</td>"
                        + "<td>" + data[i].email + "</td>"
                        + "<td>" + data[i].telefono + "</td>"
                        + "<td>" + data[i].tipoIdentificacion.nombre_tipo_identificacion + "</td>"
                        + "<td>" + data[i].numero_doc + "</td>"
                        + "<td style=\"text-align: center\">" + (data[i].estado === true ? '<h5><span class =\"badge badge-success\">Activo</span></h5>' : '<h5><span class =\"badge badge-danger\">Inactivo</span></h5>') + "</td>"
                        + "<td nowrap style=\"text-align: center\"><button title=\"Editar\" class=\"btn btn-warning\">"
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
//CARGAMOS LA LISTA DE TIPO DE IDENTIFICACION DESDE EL SERVLET DE EMPLEADOS
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

function cancelarPeticion() {
    frmProveedor[0].reset();
    txtIdProveedor.val(0);
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Proveedor');
    $('#titulo').html('Formulario de Registro');
    txtRazonSocial.removeClass('is-invalid');//Remueve la clase is-invalid
    txtDireccion.removeClass('is-invalid');
    txtNumeroDoc.removeClass('is-invalid');
    cboTipoIdentificacion.removeClass('is-invalid');
    txtEmail.removeClass('is-invalid');
    txtTelefono.removeClass('is-invalid');

}

function desactivarProveedor(idTemp) {
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
                        url: "../srvProveedor?accion=desactivar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarProveedores();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

