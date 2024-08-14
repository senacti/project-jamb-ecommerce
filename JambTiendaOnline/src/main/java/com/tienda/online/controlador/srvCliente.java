package com.tienda.online.controlador;

import com.tienda.online.modelo.Cliente;
import com.tienda.online.modelo.ClienteDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class srvCliente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarClientes(response);
                    break;
                case "agregar":
                    this.registrarClientes(request, response);
                    break;
                case "leer":
                    this.presentarClientes(request, response);
                    break;
                case "editar":
                    this.actualizarClientes(request, response);
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

    private void listarClientes(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ClienteDAO dao = new ClienteDAO();
            List<Cliente> emp = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void printMessage(String msj, boolean rpt, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"rpt\": " + rpt + ", \"msj\": \"" + msj + "\"}");
    }

    private void registrarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("cli") != null) {
            Gson gson = new Gson();
            Cliente cli = gson.fromJson(request.getParameter("cli"), Cliente.class);
            String rpt;
            try {
                ClienteDAO dao = new ClienteDAO();
                dao.registrar(cli);
                rpt = "Registrado";
                this.printMessage("Cliente(a) " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void presentarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClienteDAO dao;
        Cliente cli;
        if (request.getParameter("id") != null) {
            cli = new Cliente();
            cli.setId(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new ClienteDAO();
                cli = dao.leer(cli);
                String json = new Gson().toJson(cli);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del cliente", false, response);
        }
    }

    private void actualizarClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("cli") != null) {
            Gson gson = new Gson();
            Cliente emp = gson.fromJson(request.getParameter("cli"), Cliente.class);
            String rpt;
            try {
                ClienteDAO dao = new ClienteDAO();
                dao.actualizar(emp);
                rpt = "Actualizado";
                this.printMessage("Cliente(a) " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

}
