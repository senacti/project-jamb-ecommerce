package com.tienda.online.controlador;

import com.tienda.online.modelo.Categoria;
import com.tienda.online.modelo.CategoriaDAO;
import com.tienda.online.modelo.Marca;
import com.tienda.online.modelo.MarcaDAO;
import com.tienda.online.modelo.Producto;
import com.tienda.online.modelo.ProductoDAO;
import com.tienda.online.modelo.Proveedor;
import com.tienda.online.modelo.ProveedorDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@MultipartConfig
public class srvProducto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarProductos(response);
                    break;
                case "cambiarVigencia":
                    this.cambiarVigencia(request, response);
                    break;
                case "listarProveedores":
                    this.listarProveedores(request, response);
                    break;
                case "listarCategorias":
                    this.listarCategorias(request, response);
                    break;
                case "listarMarcas":
                    this.listarMarcas(request, response);
                    break;
                case "guardarProducto":
                    this.guardarProducto2(request, response);
                    break;
                case "leer":
                    this.leerProducto(request, response);
                    break;
                case "actualizarProducto":
                    this.actualizarProducto(request, response);
                    break;
                case "exportarReporteProducto":
                    this.exportarReporteProducto(request, response);
                    break;
            }
        } else {
            this.printError("No se indico la operacion a realizar", response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");
    }

    private void listarProductos(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ProductoDAO dao = new ProductoDAO();
            List<Producto> pro = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(pro);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void cambiarVigencia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Producto pro;
        if (request.getParameter("id") != null) {
            pro = new Producto();
            pro.setId(Integer.parseInt(request.getParameter("id")));
            try {
                ProductoDAO dao = new ProductoDAO();
                dao.cambiarEstado(pro);
                this.printMessage("Producto desactivado correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del producto", false, response);
        }
    }

    private void printMessage(String msj, boolean rpt, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"rpt\": " + rpt + ", \"msj\": \"" + msj + "\"}");
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ProveedorDAO dao = new ProveedorDAO();
            List<Proveedor> pro = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(pro);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void listarCategorias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            CategoriaDAO dao = new CategoriaDAO();
            List<Categoria> cate = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(cate);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void listarMarcas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            MarcaDAO dao = new MarcaDAO();
            List<Marca> mar = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(mar);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void guardarProducto2(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Producto pro = null;
        ProductoDAO dao = new ProductoDAO();

        if (request.getParameter("nombreProd") != null) {
            pro = new Producto();
            pro.setNombres(request.getParameter("nombreProd"));
            pro.setPrecio(Double.parseDouble(request.getParameter("precioProd")));
            pro.setStock(Integer.parseInt(request.getParameter("stockProd")));
            pro.setDescripcion(request.getParameter("descripcionProd"));
            pro.setCategoria(new Categoria());
            pro.getCategoria().setCodigo(Integer.parseInt(request.getParameter("combo_categoria")));
            pro.setProveedor(new Proveedor());
            pro.getProveedor().setId(Integer.parseInt(request.getParameter("combo_proveedor")));
            pro.setMarca(new Marca());
            pro.getMarca().setCodigo(Integer.parseInt(request.getParameter("combo_marca")));
            //GUARDAR FOTO
            Part part = request.getPart("txtFoto");//Nombre de nuestro input de tipo file.
            String nombreArchivo = Paths.get(part.getSubmittedFileName()).getFileName().toString(); //Conseguir el nombre del archivo
            File file = new File("C:\\xampp\\htdocs\\TiendaJamb\\" + nombreArchivo);
            Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            pro.setImagen("http://localhost:80/TiendaJamb/" + nombreArchivo);

            if (request.getParameter("chkEstadoProducto") != null) {
                pro.setEstado(true);
            } else {
                pro.setEstado(false);
            }
            try {
                dao.registrarProducto(pro);
                response.sendRedirect("backend/productos.jsp");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el producto" + e.getMessage());
                request.setAttribute("marca", pro);
            }
        } else {
            request.setAttribute("msje", "Complete los campos");
        }
    }

    private void leerProducto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductoDAO dao;
        Producto pro;
        if (request.getParameter("id") != null) {
            pro = new Producto();
            pro.setId(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new ProductoDAO();
                pro = dao.leer(pro);
                String json = new Gson().toJson(pro);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del cliente", false, response);
        }
    }

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Producto pro = null;
        ProductoDAO dao = new ProductoDAO();

        if (request.getParameter("idProd") != null
                && request.getParameter("nombreProdAc") != null) {
            pro = new Producto();
            pro.setId(Integer.parseInt(request.getParameter("idProd")));
            pro.setNombres(request.getParameter("nombreProdAc"));
            pro.setPrecio(Double.parseDouble(request.getParameter("precioProdAc")));
            pro.setStock(Integer.parseInt(request.getParameter("stockProdAc")));
            pro.setDescripcion(request.getParameter("descripcionProdAc"));
            pro.setCategoria(new Categoria());
            pro.getCategoria().setCodigo(Integer.parseInt(request.getParameter("combo_categoriaAc")));
            pro.setProveedor(new Proveedor());
            pro.getProveedor().setId(Integer.parseInt(request.getParameter("combo_proveedorAc")));
            pro.setMarca(new Marca());
            pro.getMarca().setCodigo(Integer.parseInt(request.getParameter("combo_marcaAc")));
            //GUARDAR FOTO
            if (request.getParameter("chkConservarImagen") == null) {
                Part part = request.getPart("txtFotoAc");//Nombre de nuestro input de tipo file.
                String nombreArchivo = Paths.get(part.getSubmittedFileName()).getFileName().toString(); //Conseguir el nombre del archivo
                File file = new File("C:\\xampp\\htdocs\\TiendaJamb\\" + nombreArchivo);
                Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                pro.setImagen("http://localhost:80/TiendaJamb/" + nombreArchivo);
            } else {
                pro.setImagen(request.getParameter("txtConservarImagen")); //INPUT DE TIPO HIDDEN
            }

            if (request.getParameter("chkEstadoProductoAc") != null) {
                pro.setEstado(true);
            } else {
                pro.setEstado(false);
            }
            try {
                dao.actualizar(pro);
                response.sendRedirect("backend/productos.jsp");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo actualizar el producto" + e.getMessage());
                //request.setAttribute("marca", pro);
            }
        } else {
            request.setAttribute("msje", "Complete los campos");
        }
    }

    private void exportarReporteProducto(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        try {
            InputStream logoEmpresa = this.getServletConfig()
                    .getServletContext()
                    .getResourceAsStream("reportesJasper/img/logoAlexanderStore.png"),
                    logoFooter = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/img/check.png"),
                    reporteProducto = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/ReporteProductos.jasper");
            if (logoEmpresa != null && logoFooter != null && reporteProducto != null) {
                String jsonCompras = request.getParameter("lista");
                Gson gson = new Gson();
                List<Producto> reportesProducto = new ArrayList<>();
                List<Producto> reportesProducto2 = new ArrayList<>();

                reportesProducto.add(new Producto());
                reportesProducto2 = gson.fromJson(jsonCompras, new TypeToken<List<Producto>>() {
                }.getType());
                reportesProducto.addAll(reportesProducto2);

                JasperReport report = (JasperReport) JRLoader.loadObject(reporteProducto);
                JRBeanArrayDataSource ds = new JRBeanArrayDataSource(reportesProducto.toArray());

                Map<String, Object> parameters = new HashMap();
                parameters.put("ds", ds);
                parameters.put("logoEmpresa", logoEmpresa);
                parameters.put("ImagenAlternativa", logoFooter);
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "inline; filename=ReporteProductos.pdf");
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
                out.close();
            } else {
                response.setContentType("text/plain");
                out.println("no se pudo generar el reporte");
                out.println("esto puede debrse a que la lista de datos no fue recibida o el archivo plantilla del reporte no se ha encontrado");
                out.println("contacte a soporte");
            }
        } catch (Exception e) {
            response.setContentType("text/plain");
            out.print("ocurrió un error al intentar generar el reporte:" + e.getMessage());
            e.printStackTrace();
        }
    }

}
