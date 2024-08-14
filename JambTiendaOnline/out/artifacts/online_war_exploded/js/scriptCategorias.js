//Declaramos nuestras variables globlales
var tabla = $("table#tablaCategoria"),
        frmCate = $("#frmCategorias"),
        mdlCate = $("#modal-lg"),
        txtNombreCate = $("#nombreCate"),
        chkEstadoCate = $("#chkEstadoCategoria"),
        txtIdCategoria = $("#idCate");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_categorias').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    
    
    //Desactivar Categoria
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        desactivarCategoria(id);
    });
    //Leer Categoría
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerCategoria(id);
        mdlCate.modal({backdrop: 'static', keyboard: false});
    });
    //Formulario de envio de datos para la actualización y registro
    frmCate.on("submit", function (e) {
        /* e.preventDefault();
        var obj = {
            codigo: txtIdCategoria.val(),
            nombrecate: txtNombreCate.val().toUpperCase(),
            estado: chkEstadoCate[0].checked
        };
        registrarCategoria(obj); */
    });
    
    //Atributos de html
    var requisitos = {
        required: true
    };
    
    $('#frmCategorias').validate({
        submitHandler: function() {
            var json = {
                codigo: txtIdCategoria.val(),
                nombrecate: txtNombreCate.val().toUpperCase(),
                estado: chkEstadoCate[0].checked
            };
        
            $.ajax({
                url: "../categoria?accion=" + (parseInt(json.codigo) === 0 ? 'agregar' : 'editar'),
                type: 'POST',
                dataType: 'json',
                data: {cate: JSON.stringify(json)},
                success: function (data) {
                    if (data.rpt) {
                        swal("Mensaje del Sistema", data.msj, "success");
                        listarCategorias();
                        frmCate[0].reset();
                        mdlCate.modal("hide");
                        txtIdCategoria.val(0);
                        chkEstadoCate.attr('disabled', 'disabled');
                        $('#btn-save').html('<i class="fas fa-save"></i> Registrar Categoría');
                        $('#titulo').html('Formulario de Registro');
                    } else {
                        swal("Error", data.msj, "error");
                    }
                }
            });
        },
        rules: {
            nombreCate: requisitos
        },
        messages: {
            nombreCate: {
                required: "Por favor, ingresa un nombre para la categoría"
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
    listarCategorias();
});

/*function registrarCategoria(json) {
    $.ajax({
        url: "../categoria?accion=" + (parseInt(json.codigo) === 0 ? 'agregar' : 'editar'),
        //con parseInt lo convertimos a entero porque sino lo lee como cadena.
        type: 'POST',
        dataType: 'json',
        data: {cate: JSON.stringify(json)},
        success: function (data) {
            if (data.rpt) {
                swal("Mensaje del Sistema", data.msj, "success");
                listarCategorias();
                frmCate[0].reset();
                mdlCate.modal("hide");
                txtIdCategoria.val(0);
                $('#btn-save').html('<i class="fas fa-save"></i> Registrar Categoría');
                $('#titulo').html('Formulario de Registro');
            } else {
                swal("Error", data.msj, "error");
            }
        }
    });
}*/

function listarCategorias() {
    $.ajax({
        url: "../categoria?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].codigo + "</td>"
                        + "<td>" + data[i].nombrecate + "</td>"
                        + "<td>" + (data[i].estado === true ? '<h5><span class =\"badge badge-success\">Activo</span></h5>' : '<h5><span class =\"badge badge-danger\">Inactivo</span></h5>') + "</td>"
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

function desactivarCategoria(idTemp) {
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
                        url: "../categoria?accion=desactivar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarCategorias();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerCategoria(idTemp) {
    $.ajax({
        url: "../categoria?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtIdCategoria.val(data.codigo);
            txtNombreCate.val(data.nombrecate);
            chkEstadoCate.prop('checked', data.estado);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Categoria');
    $('#titulo').html('Formulario de Actualización');
    chkEstadoCate.removeAttr('disabled');
}

function cancelarPeticion() {
    frmCate[0].reset();
    txtIdCategoria.val(0);
    chkEstadoCate.attr('disabled', 'disabled');
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Categoria');
    $('#titulo').html('Formulario de Registro');
    txtNombreCate.removeClass('is-invalid');//Remueve la clase is-invalid
    $('#nombreCate-error').remove();//remueve la etiqueta span.
    
}



