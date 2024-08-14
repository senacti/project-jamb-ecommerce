<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%
    if (request.getSession().getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <%@include file="plus/head.jsp" %>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <!-- Navbar -->
    <%@include file="plus/menuSuperior.jsp" %>
    <!-- /.navbar -->

    <!-- Main Sidebar Container -->
    <%@include  file="plus/menuLateral.jsp"%>
    <!-- /.main sidebar container -->

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
                        <h1 class="m-0 text-dark">Listado de Reportes</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="#">Reportes</a></li>
                            <li class="breadcrumb-item active">Listado de Reportes</li>
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        <!-- Main content -->
        <div class="content">
            <div class="container-fluid">
                <div class="card card-default">
                    <div class="card-header">
                        <h3 class="card-title">Filtros para generar reportes de compras</h3>
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i></button>
                            <button type="button" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times"></i></button>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-3 col-md-3 col-sm-3">
                                <label> Tipo Filtro</label>
                                <div class="form-inline">
                                    <select id="cboTipoFiltro" class="form-control select2 col-lg-12" data-minimum-results-for-search="Infinity" onchange="cargarCriterios()" data-placeholder="Seleccionar">
                                        <option value="1" selected="">Filtrar por Cliente</option>
                                        <option value="2">Filtrar por Despachado</option>
                                        <option value="3">Filtrar por Compra Anulada</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-3">
                                <label>Res. del Filtro</label>
                                <div class="form-inline">
                                    <select id="cboFiltro" class="select2 select2-orange col-lg-12" data-placeholder="Seleccionar" data-dropdown-css-class="select2-orange">
                                        <!-- Cargar desde la base de datos -->
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-6">
                                <label>Rango Fecha:</label>
                                <div class="form-inline">
                                    <input id="fechaRangoInicial" required="" type="date" name="fecha1" class="form-control col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <input id="fechaRangoFinal" required="" type="date" name="fecha2" class="form-control col-xl-4 col-lg-4 col-md-4 col-sm-4" style="margin-left: 5px;"> <!-- El margin left agrega 10px a cualquier elemento del bloque que tenga a su izquierda.-->
                                    <button onclick="cargarTabla()" type="button" id="reporte1" class="btn btn-outline-success" style="margin-left: 5px;"><i class="fa fa-chart-line"></i> Generar Reporte</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title">Listado De Reportes De Compras</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <div class="form-group">
                                    <form target="_blank" action="../srvReporte" id="frmReporte" method="post">
                                        <input type="hidden" name="accion" id="accion">
                                        <button onclick="reporte('exportarJasperReports')" type="button" class="btn btn-sm btn-outline-primary"><i class="fas fa-file-pdf"></i> Exportar PDF</button>
                                        <input type="hidden" name="lista" id="lista">
                                    </form>
                                </div>
                                <table id="TablaReportes" class="table table-bordered table-hover table-responsive-xl table-responsive-lg table-responsive-md table-responsive-sm">
                                    <thead>
                                    <tr>
                                        <th class="text-center">Id</th>
                                        <th class="text-center">Cliente</th>
                                        <th class="text-center">Fecha de Compras</th>
                                        <th class="text-center">Monto S/.</th>
                                        <th class="text-center">Despachado</th>
                                        <th class="text-center">Anulado</th>
                                    </tr>
                                    </thead>
                                    <tbody style="text-align: center">
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th class="text-center">Id</th>
                                        <th class="text-center">Cliente</th>
                                        <th class="text-center">Fecha de Compras</th>
                                        <th class="text-center">Monto S/.</th>
                                        <th class="text-center">Despachado</th>
                                        <th class="text-center">Anulado</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->
                        </div>
                        <!-- /.card -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
        </div>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="float-right d-none d-sm-inline">
            Alexander Store
        </div>
        <!-- Default to the left -->
        <strong>Copyright &copy; 2021-2030 <a href="https://adminlte.io">A-Developer</a>.</strong> Todos los derechos reservados.
    </footer>
</div>
<!-- ./wrapper -->

<!-- REQUIRED SCRIPTS -->

<!-- jQuery -->
<script src="../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="../plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<!-- jquery-validation -->
<script src="../plugins/jquery-validation/jquery.validate.min.js"></script>
<script src="../plugins/jquery-validation/additional-methods.min.js"></script>
<!-- bs-custom-file-input -->
<script src="../plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<!-- Datatables -->
<script src="../plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
<script src="../plugins/datatables-bs4/js/dataTables.bootstrap4.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="../dist/js/adminlte.min.js"></script>
<script src="../js/scriptReportes.js" type="text/javascript"></script>
<script src="../plugins/sweetAlert/sweetalert.js" type="text/javascript"></script>
</body>
</html>
<%
    } else {
        response.sendRedirect("../backend/loginAdministrator.jsp");
    }
%>