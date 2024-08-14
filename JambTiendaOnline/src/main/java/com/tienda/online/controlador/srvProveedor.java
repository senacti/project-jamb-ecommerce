package com.tienda.online.controlador;

import com.tienda.online.modelo.Proveedor;
import com.tienda.online.modelo.ProveedorDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvProveedor", urlPatterns = {"/srvProveedor"})
public class srvProveedor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarProveedores(request, response);
                    break;
                case "registrar":
                    this.registrarProveedores(request, response);
                    break;
                case "actualizar":
                    this.actualizarProveedores(request, response);
                    break;
                case "leer":
                    this.presentarProveedores(request, response);
                    break;
                case "desactivar":
                    this.desactivarProveedor(request, response);
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

    private void registrarProveedores(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("prov") != null) {
            Gson gson = new Gson();
            Proveedor pro = gson.fromJson(request.getParameter("prov"), Proveedor.class);
            String rpt;
            try {
                ProveedorDAO dao = new ProveedorDAO();
                if (!dao.validacion(pro)) {
                    dao.registrar(pro);
                    rpt = "Registrado";
                    this.printMessage("Proveedor " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("El proveedor que intenta registrar ya existe en la base de datos", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void actualizarProveedores(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("prov") != null) {
            Gson gson = new Gson();
            Proveedor pro = gson.fromJson(request.getParameter("prov"), Proveedor.class);
            String rpt;
            try {
                ProveedorDAO dao = new ProveedorDAO();
                if (!dao.validacion(pro)) {
                    dao.actualizar(pro);
                    rpt = "actualizado";
                    this.printMessage("Proveedor " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("El proveedor que intenta actualizar ya existe en la base de datos", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void desactivarProveedor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Proveedor pro;
        if (request.getParameter("id") != null) {
            pro = new Proveedor();
            pro.setId(Integer.parseInt(request.getParameter("id")));
            try {
                ProveedorDAO dao = new ProveedorDAO();
                dao.desactivarProveedor(pro);
                this.printMessage("Proveedor desactivado correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del proveedor", false, response);
        }
    }

    private void presentarProveedores(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProveedorDAO dao;
        Proveedor pro;
        if (request.getParameter("id") != null) {
            pro = new Proveedor();
            pro.setId(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new ProveedorDAO();
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

}
