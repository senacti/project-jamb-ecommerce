package com.tienda.online.controlador;

import com.tienda.online.modelo.Compra;
import com.tienda.online.modelo.ComprasDAO;
import com.tienda.online.modelo.DetalleCompra;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvCompras", urlPatterns = {"/srvCompras"})
public class srvCompras extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarCompras(response);
                    break;
                case "verDetalle":
                    this.verDetalle(request, response);
                    break;
                case "activarDesactivar":
                    this.activarDesactivarDespacho(request, response);
                    break;
                case "anularCompra":
                    this.anularCompra(request, response);
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

    private void listarCompras(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ComprasDAO dao = new ComprasDAO();
            List<Compra> com = dao.listarPedidos();
            Gson gson = new Gson();
            String json = gson.toJson(com);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void verDetalle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ComprasDAO dao;
        Compra comp;
        List<DetalleCompra> detalles;
        if (request.getParameter("id") != null) {
            comp = new Compra();
            comp.setId(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new ComprasDAO();
                detalles = dao.listarDetalles(comp);
                String json = new Gson().toJson(detalles);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro de la compra", false, response);
        }
    }

    private void activarDesactivarDespacho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCompra;
        boolean despachado;
        if (request.getParameter("id") != null && request.getParameter("des") != null) {
            idCompra = Integer.parseInt(request.getParameter("id"));
            despachado = Boolean.parseBoolean(request.getParameter("des"));
            try {
                ComprasDAO dao = new ComprasDAO();
                dao.cambiarEstado(idCompra, despachado);
                this.printMessage("Datos actualizados correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se obtuvo los parámetros", false, response);
        }
    }

    private void anularCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCompra;
        boolean anularC;
        if (request.getParameter("id") != null
                && request.getParameter("anu") != null) {
            idCompra = Integer.parseInt(request.getParameter("id"));
            anularC = Boolean.parseBoolean(request.getParameter("anu"));
            try {
                ComprasDAO dao = new ComprasDAO();
                dao.anularVenta(idCompra, anularC);
                this.printMessage("Datos actualizados correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se obtuvo los parámetros", false, response);
        }
    }
}
