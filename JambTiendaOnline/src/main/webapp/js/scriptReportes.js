$(document).ready(function () {
    let li_grupo_reportes = $('#li_grupo_reportes');//id de nuestra etiqueta </li>
    li_grupo_reportes.attr('class', 'nav-item has-treeview menu-open');//Hacemos que el menu se despliegue.
    let etiqueta_grupoReportes = $('#etiqueta_grupoReportes');
    etiqueta_grupoReportes.attr('class', 'nav-link active');
    let a = $('#li_reportes').find('a');
    a.attr('class', 'nav-link active');
    let tituloPag = $('#tituloPag');
    //Cambiamos el nombre del title de la pagina
    tituloPag.html('Reportes | Mostrar Reportes');
    /*------------------------------------------------------------------------------*/
    //Luego declaramos una variable para la etiqueta 'a' y le agregamos solo la clase nav-link
    let etiqueta_grupoR = $('#etiqueta_grupoR');
    etiqueta_grupoR.attr('class', 'nav-link');
    //Inicializar select
    $('.select2').select2();
    cargarCriterios();
});

function cargarCriterios() {
    let opcion = parseInt($('#cboTipoFiltro').val());
    var combo = '';
    switch (opcion) {
        case 1:
            $.get('../srvCliente?accion=listar', {}, function (r) {
                r.forEach(c => {
                    combo += '<option value="' + c.id + '">' + c.nombres + '</option>';
                });
                $('#cboFiltro').html(combo);
                cargarTabla();
            });
            break;
        case 2:
            combo += '<option value="0">Pedido No Despachado</option>';
            combo += '<option value="1">Pedido Despachado</option>';
            break;
        case 3:
            combo += '<option value="0">Compra No Anulada</option>';
            combo += '<option value="1">Compra Anulado</option>';
            break;
    }
    $('#cboFiltro').html(combo);
    //alert(opcion);
}
function cargarTabla() {
    var select = $('#cboFiltro').val();
    let fechaInicial = $('#fechaRangoInicial').val();
    let fechaFinal = $('#fechaRangoFinal').val();
    let peticion = {
        filtro: parseInt($('#cboTipoFiltro').val()),
        seleccion: select,
        fechaInicial: fechaInicial,
        fechaFinal: fechaFinal
    };
    debugger;
    //console.log(peticion);
    $.get('../srvReporte?accion=filtrarReporte', peticion, function (r) {
        console.log(r);
        var tabla = '';
        r.forEach(rpta => {
            tabla += '<tr>';
            tabla += '<td>' + rpta.id + '</td>';
            tabla += '<td>' + rpta.cliente.nombres + '</td>';
            tabla += '<td>' + rpta.compra.fecha + '</td>';
            tabla += '<td> S/. ' + rpta.compra.monto + '.00</td>';
            tabla += '<td style=\"text-align: center\">' + (rpta.compra.despachado === true ? '<h5><span class =\"badge badge-success\">Despachado</span></h5>' : '<h5><span class =\"badge badge-danger\">No Despachado</span></h5>') + '</td>';
            tabla += '<td style=\"text-align: center\">' + (rpta.compra.anularCompra === true ? '<h5 style=\"color: #ffffff\"><span style=\"background-color: #001f3f\" class =\"badge\">Anulado</span></h5>' : '<h5 style=\"color: #ffffff\"><span style=\"background-color: #ff851b\" class =\"badge\">No Anulado</span></h5>') + '</td>';
            tabla += '</tr>';
        });
        $('#TablaReportes').find('tbody').html(tabla);
        $('#TablaReportes').dataTable();
    });
}

function reporte(accion) {
    let select = $('#cboFiltro').val();
    let fechaInicial = $('#fechaRangoInicial').val();
    let fechaFinal = $('#fechaRangoFinal').val();
    let peticion = {
        filtro: parseInt($('#cboTipoFiltro').val()),
        seleccion: select,
        fechaInicial: fechaInicial,
        fechaFinal: fechaFinal
    };
    $.get('../srvReporte?accion=filtrarReporte', peticion, function (r) {
        if (r) {
            $('#accion').val(accion);
            $('#lista').val(JSON.stringify(r));
            $('#frmReporte').submit();
        } else {
            alert('el reporte no se ha generado debido a un error del servicio:' + r);
        }
    });
}

