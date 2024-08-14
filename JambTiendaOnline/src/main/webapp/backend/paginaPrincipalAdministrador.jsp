<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="true" %>
<%
    if (request.getSession().getAttribute("usuario") != null) {
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <link rel="icon" href="../img/perfil.png">
        <title>Pagina Principal | Inicio</title>
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="../plugins/fontawesome-free/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="../dist/css/adminlte.min.css">
        <!-- Google Font: Source Sans Pro -->
        <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">

            <!-- Navbar -->
            <nav class="main-header navbar navbar-expand navbar-white navbar-light">
                <!-- Left navbar links -->
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
                    </li>
                    <li class="nav-item d-none d-sm-inline-block">
                        <a href="../backend/paginaPrincipalAdministrador.jsp" class="nav-link">Inicio</a>
                    </li>
                    <li class="nav-item d-none d-sm-inline-block">
                        <a href="#" class="nav-link">Contactanos</a>
                    </li>
                </ul>

                <!-- Right navbar links -->
                <ul class="navbar-nav ml-auto">
                    <!-- Messages Dropdown Menu -->
                    <li class="nav-item dropdown">
                        <a class="nav-link" data-toggle="dropdown" href="#">Salir 
                            <i class="fas fa-sign-out-alt"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-xl dropdown-menu-right">
                            <a href="#" class="dropdown-item">
                                <!-- Message Start -->
                                <div class="media">
                                    <img src="../dist/img/user1.jpg" alt="User Avatar" class="img-size-50 mr-3 img-circle">
                                    <div class="media-body">
                                        <h3 class="dropdown-item-title">
                                            ${usuario.nombres}                                            
                                        </h3>
                                        <p class="text-sm">USTED ES, ${usuario.cargo} </p>
                                        <p class="text-sm text-muted"><i class="fas fa-envelope-square"></i> ${usuario.correo}</p>
                                    </div>
                                </div>
                                <!-- Message End -->
                            </a>
                            <div class="dropdown-divider"></div>
                            <a href="../session?accion=cerrar" class="dropdown-item dropdown-footer" style="background-color: #BD362F; color: white">Cerrar Sesion <span class="fas fa-sign-out-alt"></span></a>
                        </div>
                    </li>
                </ul>
            </nav>
            <!-- /.navbar -->

            <!-- Main Sidebar Container -->
            <aside class="main-sidebar sidebar-dark-primary elevation-4">
                <!-- Brand Logo -->
                <a href="index3.html" class="brand-link">
                    <img src="../dist/img/logo3.png" alt=" Logo" class="brand-image img-circle elevation-3"
                         style="opacity: .8">
                    <span class="brand-text font-weight-light">Jamb Ecommerce</span>
                </a>

                <!-- Sidebar -->
                <div class="sidebar">
                    <!-- Sidebar user panel (optional) -->
                    <div class="user-panel mt-3 pb-3 mb-3 d-flex">
                        <div class="image">
                            <img src="../dist/img/user1.jpg" class="img-circle elevation-2" alt="User Image">
                        </div>
                        <div class="info">
                            <a href="#" class="d-block">¡HOLA!, ${usuario.onlynames} </a>
                        </div>
                    </div>

                    <!-- Sidebar Menu -->
                    <nav class="mt-2">
                        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                            <!-- Add icons to the links using the .nav-icon class
                                 with font-awesome or any other icon font library -->
                            <li class="nav-item has-treeview menu-open">
                                <a href="#" class="nav-link active">
                                    <i class="nav-icon fas fa-home"></i>
                                    <p>
                                        Pagina Principal
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="../backend/paginaPrincipalAdministrador.jsp" class="nav-link active">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Inicio</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview menu-close">
                                <a href="#" class="nav-link">
                                    <i class="nav-icon fas fa-clipboard"></i>
                                    <p>
                                        Registros
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="../backend/categorias.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Categorias</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/marcas.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Marcas</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/productos.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Productos</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/empleados.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Empleados</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/clientes.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Clientes</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/usuarios.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Usuarios</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/cargos.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Cargos</p>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../backend/tipoIdentificacion.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Tipo Identificacion</p>
                                        </a>
                                    </li>
                                    <li id="li_tipoIdentificacion" class="nav-item">
                                        <a href="../backend/proveedores.jsp" class="nav-link">
                                            <i class="fas fa-truck-moving nav-icon"></i>
                                            <p>Proveedores</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview menu-close">
                                <a href="#" class="nav-link">
                                    <i class="nav-icon fas fa-cart-plus"></i>
                                    <p>
                                        Pedidos
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="../backend/verPedidos.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Ver Pedidos</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item has-treeview menu-close">
                                <a href="#" class="nav-link">
                                    <i class="nav-icon fas fa-chart-line"></i>
                                    <p>
                                        Reportes
                                        <i class="right fas fa-angle-left"></i>
                                    </p>
                                </a>
                                <ul class="nav nav-treeview">
                                    <li class="nav-item">
                                        <a href="../backend/reportes.jsp" class="nav-link">
                                            <i class="far fa-circle nav-icon"></i>
                                            <p>Mostrar Reportes</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                    <!-- /.sidebar-menu -->
                </div>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <div class="content-header">
                    <div class="container-fluid">
                        <div class="row mb-2">
                            <div class="col-sm-6">
                                <h1 class="m-0 text-dark">Inicio</h1>
                            </div><!-- /.col -->
                            <div class="col-sm-6">
                                <ol class="breadcrumb float-sm-right">
                                    <li class="breadcrumb-item"><a href="#">Página Principal</a></li>
                                    <li class="breadcrumb-item active">Inicio</li>
                                </ol>
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.container-fluid -->
                </div>
                <!-- /.content-header -->

                <!-- Main content -->
                <div class="content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12 col-sm-6 col-md-3">
                                <div class="info-box">
                                    <span class="info-box-icon bg-info elevation-1"><i class="fas fa-users"></i></span>

                                    <div class="info-box-content">
                                        <span class="info-box-text">Clientes Registrados</span>
                                        <span id="spanClientes" class="info-box-number">
                                            20
                                        </span>
                                    </div>
                                    <!-- /.info-box-content -->
                                </div>
                                <!-- /.info-box -->
                            </div>
                            <!-- /.col -->
                            <div class="col-12 col-sm-6 col-md-3">
                                <div class="info-box mb-3">
                                    <span class="info-box-icon bg-danger elevation-1"><i class="fas fa-truck-moving"></i></span>

                                    <div class="info-box-content">
                                        <span class="info-box-text">Total de Pedidos por entregar</span>
                                        <span id="spanPedidosPorEntregar" class="info-box-number">12</span>
                                    </div>
                                    <!-- /.info-box-content -->
                                </div>
                                <!-- /.info-box -->
                            </div>
                            <!-- /.col -->

                            <!-- fix for small devices only -->
                            <div class="clearfix hidden-md-up"></div>

                            <div class="col-12 col-sm-6 col-md-3">
                                <div class="info-box mb-3">
                                    <span class="info-box-icon bg-success elevation-1"><i class="fas fa-cart-plus"></i></span>

                                    <div class="info-box-content">
                                        <span class="info-box-text">Total de Ventas Online</span>
                                        <span id="spanTotalComprasOnline" class="info-box-number">100</span>
                                    </div>
                                    <!-- /.info-box-content -->
                                </div>
                                <!-- /.info-box -->
                            </div>
                            <!-- /.col -->
                            <div class="col-12 col-sm-6 col-md-3">
                                <div class="info-box mb-3">
                                    <span class="info-box-icon bg-warning elevation-1"><i class="fas fa-money-bill-alt"></i></span>

                                    <div class="info-box-content">
                                        <span class="info-box-text">Total de Caja</span>
                                        <span id="spanTotalCaja" class="info-box-number">S/. 500.00</span>
                                    </div>
                                    <!-- /.info-box-content -->
                                </div>
                                <!-- /.info-box -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <div class="card">
                                        <div class="card-header" style="background-color: #336B87; color: white">
                                            <h3 class="card-title" style="font-size: 16px; font-style: inherit;">REPORTE GRÁFICO DE COMPRAS EN LOS ÚLTIMOS AÑOS</h3>
                                            <div class="card-tools">
                                                <button type="button" disabled="" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times" style="color: #336B87"></i></button>
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus" style="color: white"></i></button>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <div class="position-relative mb-4">
                                                <canvas id="grafico4" height="200"></canvas>
                                            </div>
                                            <div class="d-flex flex-row justify-content-end">
                                                <span class="mr-2">
                                                    <i class="fas fa-square" style="color: #336B87;"></i> Este Año
                                                </span>
                                                <span>
                                                    <i class="fas fa-square" style="color: #2a3132"></i> Año Pasado
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="card">
                                        <div class="card-header" style="background-color: #505160; color: white">
                                            <h3 class="card-title" style="font-size: 16px; font-style: inherit;">REPORTE GRÁFICO DE LOS 10 PRODUCTOS MÁS COMPRADOS</h3>
                                            <div class="card-tools">
                                                <button type="button" disabled="" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times" style="color: #505160"></i></button>
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus" style="color: white"></i></button>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <canvas id="graficoProductosMasVendidos" style="min-height: 350px; height: 350px; max-height: 700px; max-width: 700px;"></canvas>
                                        </div>
                                        <!-- /.card-body -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <div class="card">
                                        <div class="card-header" style="background-color: #2a3132; color: white">
                                            <h3 class="card-title" style="font-size: 16px; font-style: inherit;">REPORTE GRÁFICO DE LAS COMPRAS REALIZADAS EN LAS ÚLTIMAS SEMANAS</h3>
                                            <div class="card-tools">
                                                <button type="button" disabled="" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times" style="color: #2a3132"></i></button>
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus" style="color: white"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <div class="position-relative mb-4">
                                                <canvas id="grafico3" height="200"></canvas>
                                            </div>

                                            <div class="d-flex flex-row justify-content-end">
                                                <span class="mr-2">
                                                    <i class="fas fa-square" style="color: #2a3132;"></i> Esta Semana
                                                </span>

                                                <span>
                                                    <i class="fas fa-square" style="color: #336B87"></i> Semana Pasada
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="card">
                                        <div class="card-header" style="background-color: #68829e; color: white">
                                            <h3 class="card-title" style="font-size: 16px; font-style: inherit;">REPORTE GRÁFICO DE LOS 10 CLIENTES QUE MAS COMPRAN</h3>
                                            <div class="card-tools">
                                                <button type="button" disabled="" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times" style="color: #68829e"></i></button>
                                                <button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus" style="color: white"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <canvas id="grafico1" style="min-height: 350px; height: 350px; max-height: 700px; max-width: 700px;"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">

                            </div>
                        </div>
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
        <!-- Select 2 -->
        <script src="../plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <!-- Pie Chart -->
        <script src="../plugins/chart.js/Chart.min.js" type="text/javascript"></script>
        <!-- AdminLTE App -->
        <script src="../dist/js/adminlte.min.js"></script>
        <script src="../js/scriptReportesGraficos.js" type="text/javascript"></script>
    </body>
</html>
<%
    } else {
        response.sendRedirect("../backend/loginAdministrator.jsp");
    }
%>