package com.tienda.online.controlador;

import com.tienda.online.modelo.TipoIdentificacion;
import com.tienda.online.modelo.TipoIdentificacionDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvTipoIdentificacion", urlPatterns = {"/tipoIdentificacion"})
public class srvTipoIdentificacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarTipoIdentificacion(response);
                    break;
                case "agregar":
                    this.registrarTipoIden(request, response);
                    break;
                case "leer":
                    this.presentarTipoIden(request, response);
                    break;
                case "editar":
                    this.actualizarTipoIden(request, response);
                    break;
                case "desactivar":
                    this.desactivarTipoIden(request, response);
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

    private void listarTipoIdentificacion(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            TipoIdentificacionDAO dao = new TipoIdentificacionDAO();
            List<TipoIdentificacion> emp = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void registrarTipoIden(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("tp") != null) {
            Gson gson = new Gson();
            TipoIdentificacion tip = gson.fromJson(request.getParameter("tp"), TipoIdentificacion.class);
            String rpt;
            try {
                TipoIdentificacionDAO dao = new TipoIdentificacionDAO();
                if (!dao.validacion(tip)) {
                    dao.registrar(tip);
                    rpt = "Registrado";
                    this.printMessage("Tipo Identificación " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("Tipo Identificación ya registrado", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void actualizarTipoIden(HttpServletRequest request, HttpServletResponse response) throws IOException {
         if (request.getParameter("tp") != null) {
            Gson gson = new Gson();
            TipoIdentificacion car = gson.fromJson(request.getParameter("tp"), TipoIdentificacion.class);
            String rpt;
            try {
                TipoIdentificacionDAO dao = new TipoIdentificacionDAO();
                if (!dao.validacion(car)) {
                    dao.actualizar(car);
                    rpt = "Actualizado";
                    this.printMessage("Tipo Identificación " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("Este tipo identificación ya existe en la base de datos.", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void presentarTipoIden(HttpServletRequest request, HttpServletResponse response) throws IOException{
        TipoIdentificacionDAO dao;
        TipoIdentificacion tipo;
        if (request.getParameter("id") != null) {
            tipo = new TipoIdentificacion();
            tipo.setIdTipoIdentificacion(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new TipoIdentificacionDAO();
                tipo = dao.leer(tipo);
                String json = new Gson().toJson(tipo);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del cliente", false, response);
        }
    }

    private void desactivarTipoIden(HttpServletRequest request, HttpServletResponse response) throws IOException {
         TipoIdentificacion tp;
        if (request.getParameter("id") != null) {
            tp = new TipoIdentificacion();
            tp.setIdTipoIdentificacion(Integer.parseInt(request.getParameter("id")));
            try {
                TipoIdentificacionDAO dao = new TipoIdentificacionDAO();
                dao.desactivarTipoIdentificacion(tp);
                this.printMessage("Tipo identificación desactivado correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del cargo", false, response);
        }
    }

}
