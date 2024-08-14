package com.tienda.online.controlador;

import com.tienda.online.modelo.Cargo;
import com.tienda.online.modelo.CargoDAO;
import com.tienda.online.modelo.Empleado;
import com.tienda.online.modelo.EmpleadoDAO;
import com.tienda.online.modelo.TipoIdentificacion;
import com.tienda.online.modelo.TipoIdentificacionDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class srvEmpleado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "listar":
                    this.listarEmpleados(response);
                    break;
                case "agregar":
                    this.registrarEmpleados(request, response);
                    break;
                case "leer":
                    this.presentarEmpleados(request, response);
                    break;
                case "editar":
                    this.actualizarEmpleados(request, response);
                    break;
                case "eliminar":
                    this.EliminarEmpleados(request, response);
                    break;
                case "listarTipoIdentificacion":
                    this.listarTipoIdentificacion(request, response);
                    break;
                case "listarCargo":
                    this.listarCargo(request, response);
                    break;
                case "exportarReporteEmpleados":
                    this.exportarReporteEmpleados(request, response);
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

    private void listarEmpleados(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            EmpleadoDAO dao = new EmpleadoDAO();
            List<Empleado> emp = dao.listar();
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

    private void registrarEmpleados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("emp") != null) {
            Gson gson = new Gson();
            Empleado emp = gson.fromJson(request.getParameter("emp"), Empleado.class);
            String rpt;
            try {
                EmpleadoDAO dao = new EmpleadoDAO();
                dao.registrar(emp);
                rpt = "Registrado";
                this.printMessage("Empleado(a) " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void presentarEmpleados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EmpleadoDAO dao;
        Empleado alu;
        if (request.getParameter("id") != null) {
            alu = new Empleado();
            alu.setIdEmpleado(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new EmpleadoDAO();
                alu = dao.leer(alu);
                String json = new Gson().toJson(alu);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del empleado", false, response);
        }
    }

    private void actualizarEmpleados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("emp") != null) {
            Gson gson = new Gson();
            Empleado emp = gson.fromJson(request.getParameter("emp"), Empleado.class);
            String rpt;
            try {
                EmpleadoDAO dao = new EmpleadoDAO();
                dao.actualizar(emp);
                rpt = "Actualizado";
                this.printMessage("Empleado(a) " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void EliminarEmpleados(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Empleado emp;
        if (request.getParameter("id") != null) {
            emp = new Empleado();
            emp.setIdEmpleado(Integer.parseInt(request.getParameter("id")));
            try {
                EmpleadoDAO dao = new EmpleadoDAO();
                dao.eliminar(emp);
                this.printMessage("Empleado(a) desactivado correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del empleado", false, response);
        }
    }

    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");

    }

    private void listarTipoIdentificacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            TipoIdentificacionDAO dao = new TipoIdentificacionDAO();
            List<TipoIdentificacion> ti = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(ti);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void listarCargo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            CargoDAO dao = new CargoDAO();
            List<Cargo> carg = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(carg);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void exportarReporteEmpleados(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        try {
            InputStream logoEmpresa = this.getServletConfig()
                    .getServletContext()
                    .getResourceAsStream("reportesJasper/img/logoAlexanderStore.png"),
                    logoFooter = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/img/check.png"),
                    reporteEmpleado = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/ReporteEmpleados.jasper");
            if (logoEmpresa != null && logoFooter != null && reporteEmpleado != null) {
                String jsonEmpleados = request.getParameter("lista"); //OJO
                Gson gson = new Gson();
                List<Empleado> reportesEmpleados = new ArrayList<>();
                List<Empleado> reportesEmpleados2 = new ArrayList<>();

                reportesEmpleados.add(new Empleado());
                reportesEmpleados2 = gson.fromJson(jsonEmpleados, new TypeToken<List<Empleado>>() {
                }.getType());
                reportesEmpleados.addAll(reportesEmpleados2);

                JasperReport report = (JasperReport) JRLoader.loadObject(reporteEmpleado);
                JRBeanArrayDataSource ds = new JRBeanArrayDataSource(reportesEmpleados.toArray());

                Map<String, Object> parameters = new HashMap();
                parameters.put("ds", ds);
                parameters.put("logoEmpresa", logoEmpresa);
                parameters.put("imagenAlternativa", logoFooter);
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "inline; filename=ReportesEmpleados.pdf");
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
