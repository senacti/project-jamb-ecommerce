<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="img/shopping-cart.png">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
        <link href="css/estilos.css" rel="stylesheet" type="text/css"/>        
        <title>Carrito de Compras</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg" style="background-color: #262626">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand"><img src="img/logo3.png" width="40px"></a>
            <div class="site-header collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item">
                        <a style="color: white" class="nav-link" href="./Controlador?accion=Nuevo"><i class="fas fa-home"></i> Inicio<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="./Controlador?accion=home"><i class="fas fa-plus-circle"></i> Ofertas del Dia</a>
                    </li>                   
                    <li class="nav-item">
                        <a class="nav-link" href="./Controlador?accion=carrito"><i class="fas fa-cart-plus">(<label style="color: darkorange">${cont}</label>)</i> Carrito</a>
                    </li>
                </ul>
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <form class="form-inline my-2 my-lg-0">
                        <input type="search" name="txtbuscar" style="width:450px" class="form-control mr-sm-2" placeholder="Busca un Producto">
                        <input type="submit" name="accion" value="Buscar" class="btn btn-secondary my-2 my-sm-0">
                    </form>                       
                </ul>                                
                <ul class="navbar-nav btn-group my-2 my-lg-0" role="group">
                    <a style="color: white; cursor: pointer" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fas fa-user-tie"></i> ${logueo} </a>
                    <div class="dropdown-menu text-center dropdown-menu-right">
                        <a class="dropdown-item" href="#"><img src="img/user1.jpg" alt="60" height="60"/></a>
                        <a style="color: black;" class="dropdown-item" href="#">${user}</a>
                        <a style="color: black;" class="dropdown-item" href="#" data-toggle="modal" data-target="#myModal">${correo}</a>
                        <div class="dropdown-divider"></div>
                        <a style="color: black;" class="dropdown-item" href="Controlador?accion=MisCompras">Mis Compras</a>
                        <div class="dropdown-divider"></div>
                        <a style="color: black;" class="dropdown-item" href="./Controlador?accion=Salir"> <i class="fas fa-arrow-right"> Salir</i></a>                        
                    </div>
                </ul>     
            </div>
        </nav>
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img class="d-block w-100" src="img/banner8.gif" alt="Banner 1">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="img/banner7.gif" alt="Banner 2">
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100" src="img/banner6.gif.gif" alt="Banner 3">
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div><br>
        <div>
            <h2 class="display-4 text-center">No lo pienses más y compra ahora !</h2>
        </div><br>
        <div class="contenedor-producto">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <div class="col-xl-3 col-lg-3 col-md-4 col-sm-6 col-xs-1">
                        <div class="form-group">
                            <div class="card" style="width: 18rem;">
                                <img class="card-img-top imgProduct img" src="${p.getImagen()}" width="270" height="270">
                                <div class="card-body">
                                    <h5 class="card-title text-center"><b>${p.getNombres()}</b></h5>
                                    <h6 class="card-subtitle mb-2 text-center" style="color: #FB6542"><b>COP/${p.getPrecio()}0</b></h6>
                                    <p class="card-text text-justify">${p.getDescripcion()}</p>
                                </div>
                                <div class="card-footer">
                                    <a href="Controlador?accion=AgregarCarrito&id=${p.getId()}" class="btn btn-primary">Agregar <i class="fas fa-cart-plus"></i></a>
                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar <i class="far fa-money-bill-alt"></i></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div><br>

        <footer class="container py-5">
            <div class="row">
                <div class="col-12 col-md">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="d-block mb-2"><circle cx="12" cy="12" r="10"></circle><line x1="14.31" y1="8" x2="20.05" y2="17.94"></line><line x1="9.69" y1="8" x2="21.17" y2="8"></line><line x1="7.38" y1="12" x2="13.12" y2="2.06"></line><line x1="9.69" y1="16" x2="3.95" y2="6.06"></line><line x1="14.31" y1="16" x2="2.83" y2="16"></line><line x1="16.62" y1="12" x2="10.88" y2="21.94"></line></svg>
                    <small class="d-block mb-3 text-muted">&copy; 2024-2030</small>
                </div>
                <div class="col-6 col-md">
                    <h5>Contactanos</h5>
                    <ul class="list-unstyled text-small">
                        <li><a class="text-muted" href="#">info@jamb.com</a></li>
                        <li><a class="text-muted" href="#">3003119581</a></li>
                        <li><a class="text-muted" href="#">Bosa,Bogota</a></li>
                        
                    </ul>
                </div>
                <div class="col-6 col-md">
                    <h5>Productos</h5>
                    <ul class="list-unstyled text-small">
                        <li><a class="text-muted" href="#">Negocios</a></li>
                        <li><a class="text-muted" href="#">Accesorios</a></li>
                        <li><a class="text-muted" href="#">Calzado</a></li>
                        
                    </ul>
                </div>
                <div class="col-6 col-md">
                    <h5>Sobre Nosotros</h5>
                    <ul class="list-unstyled text-small">
                        <li><a class="text-muted" href="#">Equipo</a></li>
                        <li><a class="text-muted" href="#">Localizacion</a></li>
                        <li><a class="text-muted" href="#">Privacidad</a></li>
                        <li><a class="text-muted" href="#">Terminos</a></li>
                    </ul>
                </div>
            </div>
        </footer>
        <!-- Modal Iniciar Session o Registrarse -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="container col-lg-9">                   
                    <div class="modal-content">                   
                        <div class="pr-2 pt-1">                         
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span>
                            </button>                    
                        </div>
                        <div class="text-center">                         
                            <img src="img/login1.gif" width="100" height="100">
                        </div>
                        <div class="modal-header">                      
                            <ul class="nav nav-pills" style="margin-left: 30px">                           
                                <li class="nav-item">
                                    <a class="nav-link active" data-toggle="pill" href="#pills-iniciar">Iniciar Sesion</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" data-toggle="pill" href="#pills-registrar">Registrarse</a>
                                </li>                          
                            </ul>  
                        </div>
                        <div class="modal-body"> 
                            <div class="tab-content" id="pills-tabContent">
                                <!-- Iniciar Session -->
                                <div class="tab-pane fade show active" id="pills-iniciar" role="tabpanel">
                                    <form action="Controlador">
                                        <div class="form-group">
                                            <label>Email address</label>
                                            <input required="" type="email" name="txtemail" class="form-control" placeholder="email@example.com">
                                        </div>
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input required="" type="password" name="txtpass" class="form-control" placeholder="Password">
                                        </div>                                   
                                        <button type="submit" name="accion" value="Validar" class="btn btn-outline-danger btn-block">Iniciar</button>
                                    </form>
                                </div>
                                <!-- Registrarse -->
                                <div class="tab-pane fade" id="pills-registrar" role="tabpanel">
                                    <form action="Controlador"> 
                                        <div>
                                            <h6>${msje}</h6>
                                        </div>
                                        <div class="form-group">
                                            <label>Nombres</label>
                                            <input required="" type="text" name="txtnom" class="form-control" placeholder="Leo Perez">
                                        </div>
                                        <div class="form-group">
                                            <label>Tipo Identificación</label>
                                            <select onchange="cambiarmaxLength();" id="combo_tipo_doc" class="form-control">
                                                <option value="1">CC</option>
												<option value="2">DNI</option>
                                                <option value="3">RUC</option>
                                                <option value="4">CARNET DE EXTRAJERÍA</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Num. Doc</label>
                                            <input required="" id="num_doc" type="text" maxlength="8" name="txtdni" class="form-control" placeholder="01245678">
                                        </div>
                                        <div class="form-group">
                                            <label>Direccion</label>
                                            <input required="" type="text" name="txtdire" class="form-control" placeholder="CARRERA 7 #89-12, BARRIO CHAPINERO, BOGOTA, CUNDIN...">
                                        </div>
                                        <div class="form-group">
                                            <label>Email address</label>
                                            <input required="" type="email" name="txtemail" class="form-control" placeholder="email@example.com">
                                        </div>
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input required="" type="password" name="txtpass" class="form-control" placeholder="Password">
                                        </div>                                  
                                        <button type="submit" name="accion" value="Registrar" class="btn btn-outline-danger btn-block">Crear Cuenta</button>
                                    </form>
                                </div>                          
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script>
                                                function cambiarmaxLength() {
                                                    var cboTipoIdentificacion = $("#combo_tipo_doc");
                                                    var inputNumeroIdentificacion = $("#num_doc");
                                                    inputNumeroIdentificacion.val('');
                                                    var seleccion = parseInt(cboTipoIdentificacion.val());
                                                    switch (seleccion) {
                                                        case 1:
                                                            inputNumeroIdentificacion.attr("maxlength", 8);
                                                            break;
                                                        case 2:
                                                            inputNumeroIdentificacion.attr("maxlength", 11);
                                                            break;
                                                        case 3:
                                                            inputNumeroIdentificacion.attr("maxlength", 8);
                                                            break;
                                                    }
                                                }
        </script>
    </body>
</html>
