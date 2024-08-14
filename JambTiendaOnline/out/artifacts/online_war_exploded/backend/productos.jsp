<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                        <h1 class="m-0 text-dark">Productos</h1>
                    </div><!-- /.col -->
                    <div class="col-sm-6">
                        <ol class="breadcrumb float-sm-right">
                            <li class="breadcrumb-item"><a href="#">Registros</a></li>
                            <li class="breadcrumb-item active">Productos</li>
                        </ol>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>
        <!-- /.content-header -->

        <div class="modal fade" id="modalDetalle">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Detalle del Producto</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                <div style="text-align: center">
                                    <img id="imgProductoDetalle" src="../img/lgOled.jpg" width="200px">
                                </div>
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                <div class="form-group">
                                    <label style="font-family: sans-serif">Proveedor: </label>
                                    <p id="proveedorProd" class="form-control">TecnoAlexander S.A.C </p>
                                </div>
                                <div class="form-group">
                                    <label style="font-family: sans-serif">Marca: </label>
                                    <p id="marcaProd" class="form-control">LG</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <div class="modal fade" id="modal-lg">
            <input type="hidden" id="idP" value="0">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 id="titulo" class="modal-title">Formulario De Registro</h4>
                        <button onclick="cancelarPeticion();" type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="../srvProducto?accion=guardarProducto" method="post" class="form" id="frmProductos" enctype="multipart/form-data">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Nombre del producto</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fab fa-product-hunt"></i></span>
                                            </div>
                                            <input type="text" required="" name="nombreProd" id="nombreProd" class="form-control" placeholder="Ingresar Nombre Producto">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Precio</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-dollar-sign"></i></span>
                                            </div>
                                            <input type="text" required="" name="precioProd" id="precioProd" class="form-control" placeholder="Ingresar Precio">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Stock</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-box-open"></i></span>
                                            </div>
                                            <input type="text" required="" name="stockProd" id="stockProd" class="form-control" placeholder="Ingresar Stock">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Descripción del Producto</label>
                                        <textarea class="form-control" required="" name="descripcionProd" id="descripcionProd" rows="3" placeholder="Ingresar Descripción del Producto"></textarea>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Proveedor</label>
                                        <select id="combo_proveedor" required="" name="combo_proveedor" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">
                                            <!-- Cargar desde la base de datos -->
                                            <option>Cargando . . .</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Categoria</label>
                                        <select id="combo_categoria" required="" name="combo_categoria" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">
                                            <!-- Cargar desde la base de datos -->
                                            <option>Cargando . . .</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Marca</label>
                                        <select id="combo_marca" required="" name="combo_marca" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">
                                            <!-- Cargar desde la base de datos -->
                                            <option>Cargando . . .</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif" for="txtFoto">Subir Imagen</label>
                                        <div class="input-group">
                                            <div class="custom-file">
                                                <input required="" type="file" name="txtFoto" class="custom-file-input" id="txtFoto">
                                                <label class="custom-file-label" for="txtFoto">Escoger Foto</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Estado del Producto</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                        <span class="input-group-text">
                                                            <input required="" type="checkbox" checked="" name="chkEstadoProducto" id="chkEstadoProducto">
                                                        </span>
                                            </div>
                                            <label type="text" class="form-control">Activo / Inactivo</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer clearfix">
                            <button id="btn-save" type="submit" class="btn btn-outline-info float-right"><span class="fas fa-save"></span> Registrar Producto</button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <!-- Modal para actualizar datos del producto-->
        <div class="modal fade" id="modal-productos">

            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 id="titulo" class="modal-title">Formulario De Actualización</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="false"><i style="margin-top: 8px;" class="far fa-times-circle"></i></span>
                        </button>
                    </div>
                    <form action="../srvProducto?accion=actualizarProducto" method="post" class="form" id="frmProductosActualizar" enctype="multipart/form-data">
                        <input type="hidden" name="idProd" id="idProd" value="0">
                        <div class="modal-body">
                            <span style="color: #a71d2a">${msje}</span>
                            <div class="row">
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Nombre del producto</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fab fa-product-hunt"></i></span>
                                            </div>
                                            <input type="text" name="nombreProdAc" id="nombreProdAc" class="form-control" placeholder="Ingresar Nombre Producto">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Precio</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-dollar-sign"></i></span>
                                            </div>
                                            <input type="text" name="precioProdAc" id="precioProdAc" class="form-control" placeholder="Ingresar Precio">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-lg-3 col-md-3 col-sm-3">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Stock</label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="fas fa-box-open"></i></span>
                                            </div>
                                            <input type="text" name="stockProdAc" id="stockProdAc" class="form-control" placeholder="Ingresar Stock">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Descripción del Producto</label>
                                        <textarea class="form-control" name="descripcionProdAc" id="descripcionProdAc" rows="3" placeholder="Ingresar Descripción del Producto"></textarea>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Proveedor</label>
                                        <select id="combo_proveedorAc" name="combo_proveedorAc" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">
                                            <!-- Cargar desde la base de datos -->
                                            <option>Cargando . . .</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Categoria</label>
                                        <select id="combo_categoriaAc" name="combo_categoriaAc" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">
                                            <!-- Cargar desde la base de datos -->
                                            <option>Cargando . . .</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Marca</label>
                                        <select id="combo_marcaAc" name="combo_marcaAc" style="width: 100%" class="select2 form-control" data-placeholder="Seleccionar">
                                            <!-- Cargar desde la base de datos -->
                                            <option>Cargando . . .</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif" for="txtFoto">Subir Imagen</label>
                                        <div class="input-group">
                                            <div class="custom-file">
                                                <input required="" type="file" name="txtFotoAc" class="custom-file-input" id="txtFotoAc">
                                                <label class="custom-file-label" for="txtFotoAc">Escoger Foto</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">Estado del Producto</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                        <span class="input-group-text">
                                                            <input type="checkbox" name="chkEstadoProductoAc" id="chkEstadoProductoAc">
                                                        </span>
                                            </div>
                                            <label type="text" class="form-control">Activo / Inactivo</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                    <div class="form-group">
                                        <label style="font-family: sans-serif">¿Desea conservar la misma imagen?</label>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                        <span class="input-group-text">
                                                            <input onchange="cambiarPropiedad()" type="checkbox" name="chkConservarImagen" value="true" id="chkConservarImagen">
                                                        </span>
                                            </div>
                                            <label type="text" class="form-control">Sí / No</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                                    <div style="text-align: center;" class="form-group">
                                        <img id="imagenProd" width="250px"/>
                                        <input type="hidden" name="txtConservarImagen" id="txtConservarImagen">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer clearfix">
                            <button id="btn-update" type="submit" class="btn btn-outline-info float-right"><span class="fas fa-sync-alt"></span> Actualizar Producto</button>
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
                        <div class="card card-yellow card-outline">
                            <div class="card-header">
                                <h3 class="card-title">Registrar Productos</h3>
                            </div>
                            <div class="card-body">
                                <button type="button" class="btn btn-outline-info" data-toggle="modal" data-target="#modal-lg"><i class="fas fa-file-signature"></i> Nuevo Registro</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
                        <div class="card card-orange card-outline">
                            <div class="card-header">
                                <h3 class="card-title">Listado De Productos</h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <div class="form-group">
                                    <div class="form-group">
                                        <form target="_blank" action="../srvProducto" id="frmReporte" method="post">
                                            <input type="hidden" name="accion" id="accion">
                                            <button onclick="reporte('exportarReporteProducto')" type="button" class="btn btn-sm btn-outline-primary"><i class="fas fa-file-pdf"></i> Exportar PDF</button>
                                            <input type="hidden" name="lista" id="lista">
                                        </form>
                                    </div>
                                    <table id="tablaProductos" class="table table-responsive-lg table-bordered table-hover">
                                        <thead>
                                        <tr class="text-center">
                                            <th>Id</th>
                                            <th>Producto</th>
                                            <th>Descripcion</th>
                                            <th>Imagen</th>
                                            <th>Precio</th>
                                            <th>Stock</th>
                                            <th>Categoria</th>
                                            <th>Estado</th>
                                            <th>Acciones</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                        <tfoot>
                                        <tr class="text-center">
                                            <th>Id</th>
                                            <th>Producto</th>
                                            <th>Descripcion</th>
                                            <th>Imagen</th>
                                            <th>Precio</th>
                                            <th>Stock</th>
                                            <th>Categoria</th>
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
    <!-- bs-custom-file-input -->
    <script src="../plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
    <!-- Datatables -->
    <script src="../plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
    <script src="../plugins/datatables-bs4/js/dataTables.bootstrap4.js" type="text/javascript"></script>
    <!-- AdminLTE App -->
    <script src="../dist/js/adminlte.min.js"></script>
    <script src="../js/scriptProductos.js" type="text/javascript"></script>
    <script src="../plugins/sweetAlert/sweetalert.js" type="text/javascript"></script>
</body>
</html>
<%
    } else {
        response.sendRedirect("../backend/loginAdministrator.jsp");
    }
%>

