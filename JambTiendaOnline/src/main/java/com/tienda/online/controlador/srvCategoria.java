package com.tienda.online.controlador;

import com.tienda.online.modelo.Categoria;
import com.tienda.online.modelo.CategoriaDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvCategoria", urlPatterns = {"/categoria"})
public class srvCategoria extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarCategorias(response);
                    break;
                case "agregar":
                    this.registrarCategorias(request, response);
                    break;
                case "leer":
                    this.presentarCategorias(request, response);
                    break;
                case "editar":
                    this.actualizarCategorias(request, response);
                    break;
                case "desactivar":
                    this.desactivarCategoria(request, response);
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

    private void listarCategorias(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            CategoriaDAO dao = new CategoriaDAO();
            List<Categoria> emp = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void registrarCategorias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("cate") != null) {
            Gson gson = new Gson();
            Categoria cat = gson.fromJson(request.getParameter("cate"), Categoria.class);
            String rpt;
            try {
                CategoriaDAO dao = new CategoriaDAO();
                if (!dao.validacion(cat)) {
                    dao.registrar(cat);
                    rpt = "Registrada";
                    this.printMessage("Categoria " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("Categoría ya registrada", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void presentarCategorias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoriaDAO dao;
        Categoria cli;
        if (request.getParameter("id") != null) {
            cli = new Categoria();
            cli.setCodigo(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new CategoriaDAO();
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

    private void actualizarCategorias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("cate") != null) {
            Gson gson = new Gson();
            Categoria cat = gson.fromJson(request.getParameter("cate"), Categoria.class);
            String rpt;
            try {
                CategoriaDAO dao = new CategoriaDAO();
                if (!dao.validacion(cat)) {
                    dao.actualizar(cat);
                    rpt = "Actualizada";
                    this.printMessage("Categoria " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("Esta categoría ya existe en la base de datos.", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void desactivarCategoria(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Categoria cate;
        if (request.getParameter("id") != null) {
            cate = new Categoria();
            cate.setCodigo(Integer.parseInt(request.getParameter("id")));
            try {
                CategoriaDAO dao = new CategoriaDAO();
                dao.desactivarCategoria(cate);
                this.printMessage("Categoria desactivada correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro de la categoria", false, response);
        }
    }

}
