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
                        <h1 class="m-0 text-dark">Cargos</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="#">Registros</a></li>
                            <li class="breadcrumb-item active">Cargos</li>
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        <div class="modal fade" data-backdrop="static" data-keyboard="false" id="modal-lg">
            <input type="hidden" id="idCargo" value="0">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 id="titulo" class="modal-title">Formulario De Registro</h4>
                        <button onclick="cancelarPeticion();" type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form class="form" id="frmCargos">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Nombre del Cargo</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-copyright"></i></span>
                                            </div>
                                            <input type="text" name="nombreCar" id="nombreCar" class="form-control" placeholder="Ingresar Nombre Cargo">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Estado del Cargo</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                        <span class="input-group-text">
                                                            <input type="checkbox" checked="" disabled="" name="chkEstadoCargo" id="chkEstadoCargo">
                                                        </span>
                                            </div>
                                            <label type="text" class="form-control">Activo / Inactivo</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer clearfix">
                            <button id="btn-save" type="submit" class="btn btn-outline-primary float-right">Registrar Cargo <span class="fas fa-save"></span></button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <!-- Main content -->
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <div class="card card-blue card-outline">
                            <div class="card-header">
                                <h3 class="card-title">Registrar Cargos</h3>
                            </div>
                            <div class="card-body">
                                <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modal-lg"><i class="fas fa-file-signature"></i> Nuevo Registro</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <div class="card card-yellow card-outline">
                            <div class="card-header">
                                <h3 class="card-title">Listado De Cargos</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="tablaCargos" class="table table-responsive-lg table-bordered table-hover">
                                    <thead>
                                    <tr class="text-center">
                                        <th>Id</th>
                                        <th>Cargo</th>
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                    <tfoot>
                                    <tr class="text-center">
                                        <th>Id</th>
                                        <th>Cargo</th>
                                        <th>Estado</th>
                                        <th>Acciones</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->
                        </div>
                        <!-- /.card -->
                    </div>
                </div>
                <!-- /.row -->
            </div><!-- /.container-fluid -->
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
<script src="../js/scriptCargos.js" type="text/javascript"></script>
<script src="../plugins/sweetAlert/sweetalert.js" type="text/javascript"></script>
</body>
</html>
<%
    } else {
        response.sendRedirect("../backend/loginAdministrator.jsp");
    }
%>


