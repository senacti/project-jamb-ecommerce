var tabla = $("table#tablaProductos"),
        mdlDetalleProducto = $("#modalDetalle"),
        imgProductoDetalle = $("#imgProductoDetalle"),
        proveedorProd = $("#proveedorProd"),
        marcaProd = $("#marcaProd"),
        frmProd = $("#frmProductos"),
        mdlProd = $("#modal-lg"),
        txtNombreProd = $("#nombreProd"),
        txtPrecioProd = $("#precioProd"),
        txtStockProd = $("#stockProd"),
        txtDescripcionProd = $("#descripcionProd"),
        cboProveedor = $("#combo_proveedor"),
        cboCategoria = $("#combo_categoria"),
        cboMarca = $("#combo_marca"),
        txtImagen = document.getElementById("txtFoto"),
        chkEstadoProd = $("#chkEstadoProducto"),
        txtIdProducto = $("#idP");

$(document).ready(function () {
    let li_grupo_registros = $('#li_grupo_registros');//id de nuestra etiqueta </li>
    li_grupo_registros.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let a = $('#li_productos').find('a');//id de nuestra etiqueta </li>, 
    a.attr('class', 'nav-link active');//Aplicamos la clase 'active' a la etiquea a
    let tituloPag = $('#tituloPag');
    tituloPag.html('Registros | Productos'); //Cambiamos el titulo de la página

    bsCustomFileInput.init();
    tabla.on("click", ".btn-danger", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        cambiarEstadoProducto(id);
    });

    tabla.on("click", ".btn-warning", function () {
        var id = $(this).parents("tr").children()[0].textContent;
        leerProducto(id);
        $('#modal-productos').modal({backdrop: 'static', keyboard: false});
    });

    listarProductos();
    listarProveedores();
    listarMarcas();
    listarCategorias();
});

function listarProductos() {
    $.ajax({
        url: "../srvProducto?accion=listar",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var tpl = "";
            for (var i = 0; i < data.length; i++) {
                tpl += "<tr>"
                        + "<td>" + data[i].id + "</td>"
                        + "<td>" + data[i].nombres + "</td>"
                        + "<td>" + data[i].descripcion + "</td>"
                        + "<td style=\"text-align: center\"><img src='" + data[i].imagen + "' width='50' height='50'></td>"
                        + "<td> S/" + data[i].precio + ".00</td>"
                        + "<td>" + data[i].stock + "</td>"
                        + "<td>" + data[i].categoria.nombrecate + "</td>"
                        + "<td style=\"text-align: center\">" + (data[i].estado === true ? '<h5><span class =\"badge badge-success\">Activo</span></h5>' : '<h5><span class =\"badge badge-danger\">Inactivo</span></h5>') + "</td>"
                        + "<td nowrap style=\"text-align: center\"><button title=\"Editar\" class=\"btn btn-warning\">"
                        + "<span class=\"fas fa-edit\"></span></button> "
                        + "<button title=\"Desactivar\" class=\"btn btn-danger\">"
                        + "<span class=\"fa fa-trash\"></span></button> "
                        + "<button onclick=\"verDetalle('" + data[i].imagen + "', '" + data[i].proveedor.razon_social + "', '" + data[i].marca.marca + "')\" title=\"Mas Información\" class=\"btn btn-info\">"
                        + "<span class=\"fas fa-info\"></span></button></td>"
                        + "</tr>";
            }
            tabla.find("tbody").html(tpl);
            tabla.dataTable();
        }
    });
}

function verDetalle(imagen, proveedor, marca) {
    proveedorProd.html(proveedor);
    marcaProd.html(marca);
    imgProductoDetalle.attr('src', imagen);
    mdlDetalleProducto.modal({backdrop: 'static', keyboard: false});
}

function cambiarEstadoProducto(idTemp) {
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
                        url: "../srvProducto?accion=cambiarVigencia",
                        type: 'POST',
                        dataType: 'json',
                        data: {id: idTemp},
                        success: function (data) {
                            swal("Desactivado!", data.msj, "success");
                            listarProductos();
                        }
                    });
                } else {
                    swal("Cancelado", "Petición cancelada!", "error");
                }
            });
}

function listarProveedores() {
    $.ajax({
        url: "../srvProducto?accion=listarProveedores",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var combo_proveedores = '';
            for (var i = 0; i < data.length; i++) {
                combo_proveedores += '<option value="' + data[i].id + '">' + data[i].razon_social + '</option>';
            }
            cboProveedor.html(combo_proveedores);
            $('#combo_proveedorAc').html(combo_proveedores);//For Update
        }
    });
}

function listarCategorias() {
    $.ajax({
        url: "../srvProducto?accion=listarCategorias",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var combo_categorias = '';
            for (var i = 0; i < data.length; i++) {
                combo_categorias += '<option value="' + data[i].codigo + '">' + data[i].nombrecate + '</option>';
            }
            cboCategoria.html(combo_categorias);
            $('#combo_categoriaAc').html(combo_categorias);//FOR UPDATE
        }
    });
}

function listarMarcas() {
    $.ajax({
        url: "../srvProducto?accion=listarMarcas",
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var combo_marcas = '';
            for (var i = 0; i < data.length; i++) {
                combo_marcas += '<option value="' + data[i].codigo + '">' + data[i].marca + '</option>';
            }
            cboMarca.html(combo_marcas);
            $('#combo_marcaAc').html(combo_marcas);//FOR UPDATE
        }
    });
}

function leerProducto(idTemp) {
    $.ajax({
        url: "../srvProducto?accion=leer",
        type: 'POST',
        dataType: 'json',
        data: {id: idTemp},
        success: function (data) {
            $('#idProd').val(data.id);
            $('#nombreProdAc').val(data.nombres);
            $('#precioProdAc').val(data.precio);
            $('#stockProdAc').val(data.stock);
            $('#descripcionProdAc').val(data.descripcion);
            $('#combo_proveedorAc').val(data.proveedor.id);
            $('#combo_categoriaAc').val(data.categoria.codigo);
            $('#combo_marcaAc').val(data.marca.codigo);
            $('#imagenProd').attr('src', data.imagen);
            $('#chkEstadoProductoAc').prop('checked', data.estado);
            $('#txtConservarImagen').val(data.imagen);
        }
    });
}

function reporte(accion) {
    $.get('../srvProducto?accion=listar', function (r) {
        if (r) {
            $('#accion').val(accion);
            $('#lista').val(JSON.stringify(r));
            $('#frmReporte').submit();
        } else {
            alert('el reporte no se ha generado debido a un error del servicio:' + r);
        }
    });
}

function cambiarPropiedad(){
    var uploadImg = $('#txtFotoAc'),
            chkConservarImagen = $('#chkConservarImagen');
    if((chkConservarImagen).is(':checked')){
        uploadImg.attr('disabled', '');
        uploadImg.removeAttr('required');
    }else{
        uploadImg.removeAttr('disabled');
        uploadImg.attr('required', '');
    }
}
