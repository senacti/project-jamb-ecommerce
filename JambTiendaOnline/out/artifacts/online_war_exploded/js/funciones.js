$(document).ready(function () {
    $("tr #deleteItem").click(function (e) {
        e.preventDefault();
        var idp = $(this).parent().find('#item2').val();        
        swal({
            title: "Esta Seguro de Eliminar?",
            text: "Una una Vez Eliminado, Debera Agregar de Nuevo!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                eliminar(idp);
                swal(" ¡Oh! ¡Registro Borrado! ", {
                    icon: "success",
                }).then((willDelete) => {
                    if (willDelete) {
                        parent.location.href = "Controlador?accion=carrito";
                    }
                });
            }
        });
    });
    function eliminar(idp) {
        var url = "Controlador?accion=deleteProducto&id=" + idp;
        console.log("hol");
        $.ajax({
            type: 'POST',
            url: url,
            async: true,
            success: function (r) {
            }
        });
    }

    $("tr #Cant").click(function (e) {
        var idp = $(this).parent().find('#item1').val();
        var cantidad = $(this).parent().find('#Cant').val();
        var url = "Controlador?accion=updateCantidad";
        console.log(idp, cantidad);
        $.ajax({
            type: 'POST',
            url: url,
            data: "id=" + idp + "&cantidad=" + cantidad,
            success: function (data, textStatus, jqXHR) {
               parent.location.href = "Controlador?accion=carrito";
            }
        });
    });    
   
});

$(document).ready(function () {
    $("tr #deleteMarca").click(function (e) {
        e.preventDefault();
        var idm = $(this).parent().find('#item3').val();        
        swal({
            title: "Esta Seguro de Eliminar?",
            text: "Una una Vez Eliminado, Debera Agregar de Nuevo!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                eliminar(idm);
                swal(" ¡Oh! ¡Registro Borrado! ", {
                    icon: "success",
                }).then((willDelete) => {
                    if (willDelete) {
                        parent.location.href = "Controlador?accion=NuevaMarca";
                    }
                });
            }
        });
    });
    
    function eliminar(idm) {
        var url = "Controlador?accion=EliminarMarca&id=" + idm;
        console.log("eliminado");
        $.ajax({
            type: 'POST',
            url: url,
            async: true,
            success: function (r) {
            }
        });
    } 
   
});

$(document).ready(function () {
    $("tr #deleteProd").click(function (e) {
        e.preventDefault();
        var codprod = $(this).parent().find('#item4').val();        
        swal({
            title: "Esta Seguro de Eliminar?",
            text: "Una una Vez Eliminado, Debera Agregar de Nuevo!",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((willDelete) => {
            if (willDelete) {
                eliminar(codprod);
                swal(" ¡Oh! ¡Producto Borrado! ", {
                    icon: "success",
                }).then((willDelete) => {
                    if (willDelete) {
                        parent.location.href = "Controlador?accion=NuevoProducto";
                    }
                });
            }
        });
    });
    
    function eliminar(codprod) {
        var url = "Controlador?accion=EliminarProducto&id=" + codprod;
        console.log("eliminado");
        $.ajax({
            type: 'POST',
            url: url,
            async: true,
            success: function (r) {
            }
        });
    } 
   
});



