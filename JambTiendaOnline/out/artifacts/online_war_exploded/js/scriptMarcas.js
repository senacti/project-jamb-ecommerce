//Declaramos nuestras variables globlales
var tabla = $("table#tablaMarcas"),
        frmMar = $("#frmMarcas"),
        mdlMarcas = $("#modal-lg"),
        txtNombreM = $("#nombreMar"),
        chkEstadoMar = $("#chkEstadoMarca"),
        txtIdMar = $("#idMar");
$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_marcas').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Marcas');

    //Desactivar Categoria
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        desactivarMarca(id);
    });
    //Leer Categoría
    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerMarcas(id);
        mdlMarcas.modal({backdrop: 'static', keyboard: false});
    });
    //Formulario de envio de datos para la actualización y registro
    frmMar.on("submit", function (e) {
        e.preventDefault();
        var obj = {
            codigo: txtIdMar.val(),
            marca: txtNombreM.val().toUpperCase(),
            estado: chkEstadoMar[0].checked
        };
        registrarMarca(obj);
    });

    listarMarcas();
});

function registrarMarca(json) {
    $.ajax({
        url: "../marca?accion=" + (parseInt(json.codigo) === 0 ? 'agregar' : 'editar'),
        //con parseInt lo convertimos a entero porque sino lo lee como cadena.
        type: 'POST',
        dataType: 'json',
        data: {mar: JSON.stringify(json)},
        success: function (data) {
            if (data.rpt) {
                swal("Mensaje del Sistema", data.msj, "success");
                listarMarcas();
                frmMar[0].reset();
                mdlMarcas.modal("hide");
                txtIdMar.val(0);
                $('#btn-save').html('<i class="fas fa-save"></i> Registrar Marca');
                $('#titulo').html('Formulario de Registro');
            } else {
                swal("Error", data.msj, "error");
            }
        }
    });
}

function listarMarcas() {
    $.ajax({
        url: "../marca?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].codigo + "</td>"
                        + "<td>" + data[i].marca + "</td>"
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

function desactivarMarca(idTemp) {
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
                        url: "../marca?accion=desactivar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarMarcas();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function leerMarcas(idTemp) {
    $.ajax({
        url: "../marca?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            txtIdMar.val(data.codigo);
            txtNombreM.val(data.marca);
            chkEstadoMar.prop('checked', data.estado);
        }
    });
    $('#btn-save').html('<i class="fas fa-sync-alt"></i> Actualizar Marca');
    $('#titulo').html('Formulario de Actualización');
}

function cancelarPeticion() {
    frmMar[0].reset();
    txtIdMar.val(0);
    $('#btn-save').html('<i class="fas fa-save"></i> Registrar Categoria');
    $('#titulo').html('Formulario de Registro');
}


