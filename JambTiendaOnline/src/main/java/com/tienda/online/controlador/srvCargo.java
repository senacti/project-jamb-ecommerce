package com.tienda.online.controlador;

import com.tienda.online.modelo.Cargo;
import com.tienda.online.modelo.CargoDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "srvCargo", urlPatterns = {"/cargo"})
public class srvCargo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarCargos(response);
                    break;
                case "agregar":
                    this.registrarCargos(request, response);
                    break;
                case "leer":
                    this.presentarCargos(request, response);
                    break;
                case "editar":
                    this.actualizarCargos(request, response);
                    break;
                case "desactivar":
                    this.desactivarCargos(request, response);
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

    private void listarCargos(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            CargoDAO dao = new CargoDAO();
            List<Cargo> emp = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void registrarCargos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("car") != null) {
            Gson gson = new Gson();
            Cargo car = gson.fromJson(request.getParameter("car"), Cargo.class);
            String rpt;
            try {
                CargoDAO dao = new CargoDAO();
                if (!dao.validacion(car)) {
                    dao.registrar(car);
                    rpt = "Registrado";
                    this.printMessage("Cargo " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("Cargo ya registrado", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void presentarCargos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CargoDAO dao;
        Cargo car;
        if (request.getParameter("id") != null) {
            car = new Cargo();
            car.setId_cargo(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new CargoDAO();
                car = dao.leer(car);
                String json = new Gson().toJson(car);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del cliente", false, response);
        }
    }

    private void actualizarCargos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("car") != null) {
            Gson gson = new Gson();
            Cargo car = gson.fromJson(request.getParameter("car"), Cargo.class);
            String rpt;
            try {
                CargoDAO dao = new CargoDAO();
                if (!dao.validacion(car)) {
                    dao.actualizar(car);
                    rpt = "Actualizado";
                    this.printMessage("Cargo " + rpt + " correctamente", true, response);
                } else {
                    this.printMessage("Este cargo ya existe en la base de datos.", false, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void desactivarCargos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cargo car;
        if (request.getParameter("id") != null) {
            car = new Cargo();
            car.setId_cargo(Integer.parseInt(request.getParameter("id")));
            try {
                CargoDAO dao = new CargoDAO();
                dao.desactivarCargo(car);
                this.printMessage("Cargo desactivado correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del cargo", false, response);
        }
    }

    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");

    }

    private void printMessage(String msj, boolean rpt, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"rpt\": " + rpt + ", \"msj\": \"" + msj + "\"}");
    }

}
