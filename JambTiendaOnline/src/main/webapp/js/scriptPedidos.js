var tabla = $('#tablaPedidos'),
        tablaDetalle = $('#tabla_detalleC'),
        mdlDetalleCompra = $('#modal-dc'),
        mdlActualizacionP = $('#idPedido');
$(document).ready(function () {
    let li_grupo_pedidos = $('#li_grupo_pedidos');//id de nuestra etiqueta </li>
    li_grupo_pedidos.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let etiqueta_grupoP = $('#etiqueta_grupoP');
    etiqueta_grupoP.attr('class', 'nav-link active');
    let a = $('#li_verPedidos').find('a');
    a.attr('class', 'nav-link active');
    //Cambiamos el nombre del title de la pagina
    let tituloPag = $('#tituloPag');
    tituloPag.html('Pedidos | Ver Pedidos');
    /*------------------------------------------------------------------------------*/
    //Luego declaramos una variable para la etiqueta 'a' y le agregamos solo la clase nav-link
    let etiqueta_grupoR = $('#etiqueta_grupoR');
    etiqueta_grupoR.attr('class', 'nav-link');

    listarPedidos();

    tabla.on("click", ".btn-info", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        detalleCompra(id);
        mdlDetalleCompra.modal({backdrop: 'static', keyboard: false});
    });
});


function listarPedidos() {
    $.ajax({
        url: "../srvCompras?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr class=\"text-center\">"
                        + "<td>" + data[i].id + "</td>"
                        + "<td>" + data[i].nombresClienteDni + "</td>"
                        + "<td>" + data[i].fecha + "</td>"
                        + "<td nowrap> S/. " + data[i].monto + ".00</td>"
                        + "<td>" + (data[i].estado === "E" ? '<h5><span class =\"badge badge-secondary\">Enviado</span></h5>' : '<h5><span class =\"badge badge-primary\">Recepcionado</span></h5>') + "</td>"
                        + "<td>" + (data[i].despachado === true ? '<h5><span class =\"badge badge-success\">Despachado</span></h5>' : '<h5><span class =\"badge badge-danger\">No Despachado</span></h5>') + "</td>"
                        + "<td>" + (data[i].anularCompra === true ? '<h5 style=\"color: #ffffff\"><span style=\"background-color: #001f3f\" class =\"badge\">Anulado</span></h5>' : '<h5 style=\"color: #ffffff\"><span style=\"background-color: #ff851b\" class =\"badge\">No Anulado</span></h5>') + "</td>"
                        + "<td nowrap><button title=\"Ver Detalle\" class=\"btn btn-info\">"
                        + "<span class=\"fas fa-info\"></span></button> "
                        + "<button onclick=\"actualizarDespache('" + data[i].id + "', '" + !(data[i].despachado) + "')\" title=\"Despachar y Cambiar Estado\" class=\"btn btn-warning\">"
                        + "<span class=\"fas fa-check\"></span></button> "
                        + "<button onclick=\"anularCompra('" + data[i].id + "', '" + (!data[i].anularCompra) + "')\" title=\"Anular Compra\" class=\"btn btn-danger\">"
                        + "<span class=\"fa fa-times-circle\"></span></button></td>"
                        + "</tr>";
            }
            tabla.find("tbody").html(tpl);
            tabla.dataTable();
        }
    });
}

function actualizarDespache(id, despachado) {
    swal({
        title: "Está seguro que desea cambiar el estado del despache?",
        text: "Una vez cambiado, los datos se actualizarán, y no será necesario refrescar la página!",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "Si, confirmar!",
        cancelButtonText: "No, cancelar!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        url: "../srvCompras?accion=activarDesactivar",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: id, des: despachado},
                        success: function (data) {
                            swal("Buen Trabajo !", data.msj, "success");
                            listarPedidos();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function anularCompra(idC, anular) {
    swal({
        title: "Está seguro que desea cambiar la anulación de la compra?",
        text: "Una vez cambiado, los datos se actualizarán, y no será necesario refrescar la página!",
        type: "warning",
        showCancelButton: true,
        confirmButtonClass: "btn-danger",
        confirmButtonText: "Si, confirmar!",
        cancelButtonText: "No, cancelar!",
        closeOnConfirm: false,
        closeOnCancel: false
    },
            function (isConfirm) {
                if (isConfirm) {
                    $.ajax({
                        url: "../srvCompras?accion=anularCompra",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idC, anu: anular},
                        success: function (data) {
                            swal("Buen Trabajo !", data.msj, "success");
                            listarPedidos();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function detalleCompra(idTemp) {
    $.ajax({
        url: "../srvCompras?accion=verDetalle",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            var tablaA = '';
            data.forEach(ag => {
                tablaA += '<tr>';
                tablaA += '<td>' + ag.producto.nombres + '</td>';
                tablaA += '<td><img src="' + ag.producto.imagen + '" width="100" height="100"></td>';
                tablaA += '<td>' + ag.cantidad + '</td>';
                tablaA += '<td>' + ag.precioCompra + '</td>';
                tablaA += '<td>' + ag.montoTotal + '</td>';
                tablaA += '</tr>';
            });
            tablaDetalle.find("tbody").html(tablaA);
            tablaDetalle.dataTable();
        }
    });
}



