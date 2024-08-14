//Declaramos nuestras variables globlales
var tabla = $("table#tablaCargos"),
        frmCargo = $("#frmCargos"),
        mdlCate = $("#modal-lg"),
        txtNombreCargo = $("#nombreCar"),
        chkEstadoCargo = $("#chkEstadoCargo"),
        txtIdCargo = $("#idCargo");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_cargos').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Cargos');

    //Desactivar Categoria
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        desactivarCargo(id);
    });
    //Leer Categoría
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerCargo(id);
        mdlCate.modal({backdrop: 'static', keyboard: false});
    });

    //Atributos de html
    var requisitos = {
        required: true
    };

    $('#frmCargos').validate({
        submitHandler: function () {
            var json = {
                id_cargo: txtIdCargo.val(),
                nombre_cargo: txtNombreCargo.val().toUpperCase(),
                vigencia: chkEstadoCargo[0].checked
            };

            $.ajax({
                url: "../cargo?accion=" + (parseInt(json.id_cargo) === 0 ? 'agregar' : 'editar'),
                type: 'POST',
                dataType: 'json',
                data: {car: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarCargos();
                        frmCargo[0].reset();
                        mdlCate.modal("hide");
                        txtIdCargo.val(0);
                        chkEstadoCargo.attr('disabled', 'disabled');
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Cargo');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            nombreCar: requisitos
        },
        messages: {
            nombreCar: {
                required: "Por favor, ingresa un nombre para el cargo"
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
    listarCargos();
});

function listarCargos() {
    $.ajax({
        url: "../cargo?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].id_cargo + "</td>"
                        + "<td>" + data[i].nombre_cargo + "</td>"
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

function desactivarCargo(idTemp) {
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
                        url: "../cargo?accion=desactivar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarCargos();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerCargo(idTemp) {
    $.ajax({
        url: "../cargo?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtIdCargo.val(data.id_cargo);
            txtNombreCargo.val(data.nombre_cargo);
            chkEstadoCargo.prop('checked', data.vigencia);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Cargo');
    $('#titulo').html('Formulario de Actualización');
    chkEstadoCargo.removeAttr('disabled');
}

function cancelarPeticion() {
    frmCargo[0].reset();
    txtIdCargo.val(0);
    chkEstadoCargo.attr('disabled', 'disabled');
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Cargo');
    $('#titulo').html('Formulario de Registro');
    txtNombreCargo.removeClass('is-invalid');//Remueve la clase is-invalid
    $('#nombreCar-error').remove();//remueve la etiqueta span.

}





