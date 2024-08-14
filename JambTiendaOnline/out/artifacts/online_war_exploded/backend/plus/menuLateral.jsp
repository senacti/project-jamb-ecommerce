<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="index3.html" class="brand-link">
        <img src="../dist/img/logo3.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
             style="opacity: .8">
        <span class="brand-text font-weight-light">Jamb Ecommerce</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="../dist/img/user2.jpg" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">¡HOLA!, ${usuario.onlynames}</a>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <!-- Add icons to the links using the .nav-icon class
                     with font-awesome or any other icon font library -->
                <li class="nav-item has-treeview menu-close">
                    <a href="#" class="nav-link">
                        <i class="nav-icon fas fa-home"></i>
                        <p>
                            Página Principal
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li class="nav-item">
                            <a href="../backend/paginaPrincipalAdministrador.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Inicio</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="li_grupo_registros" class="nav-item has-treeview menu-close">
                    <a id="etiqueta_grupoR" href="#" class="nav-link active">
                        <i class="nav-icon fas fa-clipboard"></i>
                        <p>
                            Registros
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li id="li_categorias" class="nav-item">
                            <a href="../backend/categorias.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Categorías</p>
                            </a>
                        </li>
                        <li id="li_marcas" class="nav-item">
                            <a href="../backend/marcas.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Marcas</p>
                            </a>
                        </li>
                        <li id="li_productos" class="nav-item">
                            <a href="../backend/productos.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Productos</p>
                            </a>
                        </li>
                        <li id="li_empleados" class="nav-item">
                            <a href="../backend/empleados.jsp" class="nav-link">
                                <i class="fas fa-users nav-icon"></i>
                                <p>Empleados</p>
                            </a>
                        </li>
                        <li id="li_clientes" class="nav-item">
                            <a href="../backend/clientes.jsp" class="nav-link">
                                <i class="fas fa-users nav-icon"></i>
                                <p>Clientes</p>
                            </a>
                        </li>
                        <li id="li_usuarios" class="nav-item">
                            <a href="../backend/usuarios.jsp" class="nav-link">
                                <i class="fas fa-user nav-icon"></i>
                                <p>Usuarios</p>
                            </a>
                        </li>
                        <li id="li_cargos" class="nav-item">
                            <a href="../backend/cargos.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Cargos</p>
                            </a>
                        </li>
                        <li id="li_tipoIdentificacion" class="nav-item">
                            <a href="../backend/tipoIdentificacion.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Tipo Identificación</p>
                            </a>
                        </li>
                        <li id="li_proveedores" class="nav-item">
                            <a href="../backend/proveedores.jsp" class="nav-link">
                                <i class="fas fa-truck-moving nav-icon"></i>
                                <p>Proveedores</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="li_grupo_pedidos" class="nav-item has-treeview menu-close">
                    <a id="etiqueta_grupoP" href="#" class="nav-link">
                        <i class="nav-icon fas fa-cart-plus"></i>
                        <p>
                            Pedidos
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li id="li_verPedidos" class="nav-item">
                            <a href="../backend/verPedidos.jsp" class="nav-link">
                                <i class="far fa-circle nav-icon"></i>
                                <p>Ver Pedidos</p>
                            </a>
                        </li>
                    </ul>
                </li>
                <li id="li_grupo_reportes" class="nav-item has-treeview menu-close">
                    <a id="etiqueta_grupoReportes" href="#" class="nav-link">
                        <i class="nav-icon fas fa-chart-line"></i>
                        <p>
                            Reportes
                            <i class="right fas fa-angle-left"></i>
                        </p>
                    </a>
                    <ul class="nav nav-treeview">
                        <li id="li_reportes" class="nav-item">
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
