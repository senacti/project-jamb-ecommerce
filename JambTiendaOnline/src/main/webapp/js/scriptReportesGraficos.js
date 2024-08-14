$(document).ready(function () {
    cargarTotalCliente();
    cargarTotalPedidosPorDespachar();
    cargarMontoTotalDeCaja();
    cargarTotalComprasOnline();
    cargarReporteAnual();
    cargarReporteSemanal();
    cargar10ProductosMasVendidos();
    reportes10ClientesQueMasCompran();
});

function cargarTotalCliente() {
    var totalCli = '';
    $.get('../srvReporte?accion=totalClientes', {}, function (r) {
        r.forEach(c => {
            totalCli += c.id;
        });
        $('#spanClientes').html(totalCli);
    });
}

function cargarTotalPedidosPorDespachar() {
    var totalPedidosPorDespachar = '';
    $.get('../srvReporte?accion=totalPedidosEntregar', {}, function (r) {
        r.forEach(c => {
            totalPedidosPorDespachar += c.id;
        });
        $('#spanPedidosPorEntregar').html(totalPedidosPorDespachar);
    });
}

function cargarMontoTotalDeCaja() {
    var totalCaja = '';
    $.get('../srvReporte?accion=totalCaja', {}, function (r) {
        r.forEach(c => {
            totalCaja += c.monto;
        });
        $('#spanTotalCaja').html("S/. " + totalCaja + ".00");
    });
}

function cargarTotalComprasOnline() {
    var totalComprasOnline = '';
    $.get('../srvReporte?accion=totalCompras', {}, function (r) {
        r.forEach(c => {
            totalComprasOnline += c.id;
        });
        $('#spanTotalComprasOnline').html(totalComprasOnline);
    });
}

/*-----------------REPORTES GRÁFICOS-------------------------------------*/

function cargarReporteAnual() {
    $(function () {
        'use strict';

        var ticksStyle = {
            fontColor: '#495057',
            fontStyle: 'bold'
        };

        var mode = 'index';
        var intersect = true;

        var $salesChart = $('#grafico4');
        $.get('../srvReporte?accion=generarReporteAnual', {}, function (r) {
            var salesChart = new Chart($salesChart, {
                type: 'bar',
                data: {
                    labels: ['ENE', 'FEB', 'MAR', 'ABR', 'MAY', 'JUN', 'JUL', 'AGO', 'SEP', 'OCT', 'NOV', 'DIC'],
                    datasets: [
                        {
                            backgroundColor: '#336B87',
                            borderColor: '#336B87',
                            data: r.esteAnio
                        },
                        {
                            backgroundColor: '#2a3132',
                            borderColor: '#2a3132',
                            data: r.anioPasado
                        }
                    ]
                },
                options: {
                    maintainAspectRatio: false,
                    tooltips: {
                        mode: mode,
                        intersect: intersect
                    },
                    hover: {
                        mode: mode,
                        intersect: intersect
                    },
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                                // display: false,
                                gridLines: {
                                    display: true,
                                    lineWidth: '4px',
                                    color: 'rgba(0, 0, 0, .2)',
                                    zeroLineColor: 'transparent'
                                },
                                ticks: $.extend({
                                    suggestedMax: 30,
                                    beginAtZero: true,

                                    // Include a dollar sign in the ticks
                                    callback: function (value, index, values) {
                                        if (value >= 1000) {
                                            value /= 1000
                                            value += 'k';//Si los trámites superan los 1000 se pone el valor de 'K' después de 1000 Ejemp: 1000k
                                        }
                                        return value
                                    }
                                }, ticksStyle)
                            }],
                        xAxes: [{
                                display: true,
                                gridLines: {
                                    display: false
                                },
                                ticks: ticksStyle
                            }]
                    }
                }
            });
        });
    });
}

function cargarReporteSemanal() {
    //GRÁFICO 3 - REPORTES DE COMPRAS REALIZADOS EN LA ÚLTIMA SEMANA
    'use strict';

    var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
    };

    var mode = 'index';
    var intersect = true;
    var $visitorsChart = $('#grafico3');
    $.get('../srvReporte?accion=generarReporteSemanal', {}, function (r) {
        var visitorsChart = new Chart($visitorsChart, {
            data: {
                labels: ['DOM', 'LUN', 'MAR', 'MIE', 'JUE', 'VIE', 'SAB'],
                datasets: [{
                        type: 'line',
                        data: r.estaSemana,
                        backgroundColor: 'transparent',
                        borderColor: '#2a3132',
                        pointBorderColor: '#2a3132',
                        pointBackgroundColor: '#2a3132',
                        fill: false
                                // pointHoverBackgroundColor: '#007bff',
                                // pointHoverBorderColor    : '#007bff'
                    },
                    {
                        type: 'line',
                        data: r.semanaPasada,
                        backgroundColor: 'tansparent',
                        borderColor: '#336B87',
                        pointBorderColor: '#336B87',
                        pointBackgroundColor: '#336B87',
                        fill: false
                                // pointHoverBackgroundColor: '#ced4da',
                                // pointHoverBorderColor    : '#ced4da'
                    }]
            },
            options: {
                maintainAspectRatio: false,
                tooltips: {
                    mode: mode,
                    intersect: intersect
                },
                hover: {
                    mode: mode,
                    intersect: intersect
                },
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                            // display: false,
                            gridLines: {
                                display: true,
                                lineWidth: '4px',
                                color: 'rgba(0, 0, 0, .2)',
                                zeroLineColor: 'transparent'
                            },
                            ticks: $.extend({
                                beginAtZero: true,
                                suggestedMax: 30
                            }, ticksStyle)
                        }],
                    xAxes: [{
                            display: true,
                            gridLines: {
                                display: false
                            },
                            ticks: ticksStyle
                        }]
                }
            }
        });
    });
}

function cargar10ProductosMasVendidos() {
    try {
        $(function () {
            $('.select2').select2();
            var reporteDenuncias = $('#graficoProductosMasVendidos').get(0).getContext('2d');
            $.get('../srvReporte?accion=top10ProductosMasVendidos', {}, function (r) {
                var tipoData = {
                    labels: r.nombresP,
                    datasets: [
                        {
                            data: r.contadores,
                            backgroundColor: ['#f56954', '#00a65a', '#3c8dbc', '#375E97', '#505160', '#68829E', '#AEBD38', '#07575B', '#FFBB00', '#F18D9E']
                        }
                    ]
                };
                var donutOptions = {
                    maintainAspectRatio: false,
                    responsive: true
                };
                var donutChart = new Chart(reporteDenuncias, {
                    type: 'pie',
                    data: tipoData,
                    options: donutOptions
                });
            });
        });
    } catch (e) {
        alert('no se pudo cargar el reporte:' + e);
    }
}

function reportes10ClientesQueMasCompran() {
    $(function () {
        $('.select2').select2();
        var reportesClientes = $('#grafico1').get(0).getContext('2d');
        $.get('../srvReporte?accion=top10ClientesQueMasCompran', {}, function (r) {
            var tipoData = {
                labels: r.nombresCli,
                datasets: [
                    {
                        data: r.contadores,
                        backgroundColor: ['#f56954', '#00a65a', '#3c8dbc', '#004445', '#505160', '#68829E', '#AEBD38', '#07575B', '#324851', '#34675C']
                    }
                ]
            };
            var donutOptions = {
                maintainAspectRatio: false,
                responsive: true
            };
            var donutChart = new Chart(reportesClientes, {
                type: 'doughnut',
                data: tipoData,
                options: donutOptions
            });
        });
    });
}


