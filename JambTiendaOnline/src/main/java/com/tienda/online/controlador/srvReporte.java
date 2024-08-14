package com.tienda.online.controlador;

import com.tienda.online.modelo.Cliente;
import com.tienda.online.modelo.Compra;
import com.tienda.online.modelo.Reportes;
import com.tienda.online.modelo.ReportesDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet(name = "srvReporte", urlPatterns = {"/srvReporte"})
public class srvReporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        if (request.getParameter("accion") != null) {
            String accion = request.getParameter("accion");
            switch (accion) {
                case "filtrarReporte":
                    this.mostrarReporte(request, response);
                    break;
                case "totalClientes":
                    this.totalClientes(request, response);
                    break;
                case "totalCaja":
                    this.totalCaja(request, response);
                    break;
                case "totalPedidosEntregar":
                    this.totalPedidosEntregar(request, response);
                    break;
                case "totalCompras":
                    this.totalComprasOnline(request, response);
                    break;
                case "generarReporteSemanal":
                    this.generarReporteSemanal(request, response);
                    break;
                case "generarReporteAnual":
                    this.generarReporteAnual(request, response);
                    break;
                case "top10ProductosMasVendidos":
                    this.top10ProductosMasVendidos(request, response);
                    break;
                case "top10ClientesQueMasCompran":
                    this.top10ClientesMasCompran(request, response);
                    break;
                case "exportarJasperReports":
                    this.exportarJasperReports(request, response);
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

    private void mostrarReporte(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ReportesDAO dao;
        List<Reportes> lista = new ArrayList<>();
        List<Reportes> pedidosFiltrados = new ArrayList<>();
        Gson gson = new Gson();
        if (request.getParameter("filtro") != null
                && request.getParameter("seleccion") != null
                && request.getParameter("fechaInicial") != null
                && request.getParameter("fechaFinal") != null) {
            dao = new ReportesDAO();
            int filtro = Integer.parseInt(request.getParameter("filtro"));
            String fechaRangoInicial = request.getParameter("fechaInicial");
            String fechaRangoFinal = request.getParameter("fechaFinal");
            PrintWriter out = response.getWriter();
            try {
                switch (filtro) {
                    case 1:
                        lista = dao.listarReportesPorCliente(Integer.parseInt(request.getParameter("seleccion")));
                        break;
                    case 2:
                        lista = dao.listarReportesByDespacho(request.getParameter("seleccion").equals("1"));
                        break;
                    case 3:
                        lista = dao.listarReportesByAnulacion(request.getParameter("seleccion").equals("1"));
                        break;
                }
                if (!fechaRangoInicial.equals("") && !fechaRangoFinal.equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaInicial = sdf.parse(fechaRangoInicial),
                            fechaFinal = sdf.parse(fechaRangoFinal);

                    for (Reportes r : lista) {
                        fechaFinal.before(fechaInicial);
                        Date fechaConvertida = sdf.parse(r.getCompra().getFecha());
                        if (!fechaConvertida.before(fechaInicial) && !fechaConvertida.after(fechaFinal)) {
                            pedidosFiltrados.add(r);
                        }
                    }
                } else {
                    pedidosFiltrados.addAll(lista);
                }
                response.setContentType("application/json");
                String json = gson.toJson(pedidosFiltrados);
                out.print(json);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } else {
            printError("No se obtuvieron los datos", response);
        }
    }

    private void totalClientes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ReportesDAO dao = new ReportesDAO();
            List<Cliente> cli = dao.totalClientes();
            Gson gson = new Gson();
            String json = gson.toJson(cli);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void totalCaja(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ReportesDAO dao = new ReportesDAO();
            List<Compra> comp = dao.montoTotal();
            Gson gson = new Gson();
            String json = gson.toJson(comp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void totalPedidosEntregar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ReportesDAO dao = new ReportesDAO();
            List<Compra> comp = dao.contarTotalPedidosPorEntregar();
            Gson gson = new Gson();
            String json = gson.toJson(comp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void totalComprasOnline(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            ReportesDAO dao = new ReportesDAO();
            List<Compra> comp = dao.contarTotalCompras();
            Gson gson = new Gson();
            String json = gson.toJson(comp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private List<Integer> reporteSemanal(boolean semanaPasada) {
        String fechaInicialS = "", fechaFinalS = "";
        LocalDateTime ldt = LocalDateTime.now();
        final Calendar actualCalendar = GregorianCalendar.from(ZonedDateTime.of(ldt, ZoneId.systemDefault()));
        if (semanaPasada) {
            Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.add(Calendar.DAY_OF_YEAR, -7);
            ldt = c.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        final int diaSemana = actualCalendar.get(Calendar.DAY_OF_WEEK);
        final Calendar fi = GregorianCalendar.from(ZonedDateTime.of(ldt, ZoneId.systemDefault()));
        final Calendar ff = GregorianCalendar.from(ZonedDateTime.of(ldt, ZoneId.systemDefault()));
        switch (diaSemana) {
            case 1:
                asignarCalendar(fi, ff, 0, 6);
                break;
            case 2:
                asignarCalendar(fi, ff, -1, 5);
                break;
            case 3:
                asignarCalendar(fi, ff, -2, 4);
                break;
            case 4:
                asignarCalendar(fi, ff, -3, 3);
                break;
            case 5:
                asignarCalendar(fi, ff, -4, 2);
                break;
            case 6:
                asignarCalendar(fi, ff, -5, 1);
                break;
            case 7:
                asignarCalendar(fi, ff, -6, 0);
                break;
        }
        fechaInicialS = armarFechaDesdeCalendar(fi);
        fechaFinalS = armarFechaDesdeCalendar(ff);
        ReportesDAO dao = new ReportesDAO();
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            data.add(dao.obtenerContadoPorDiaSemana(fechaInicialS, fechaFinalS, i));
        }
        return data;
    }

    private void generarReporteSemanal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("estaSemana", reporteSemanal(false));
        data.put("semanaPasada", reporteSemanal(true));
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(data);
        PrintWriter out = response.getWriter();
        out.print(json);
    }

    private String armarFechaDesdeCalendar(Calendar c) {
        String fecha = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
        return fecha;
    }

    private void asignarCalendar(Calendar fi, Calendar ff, int addI, int addF) {
        fi.add(Calendar.DAY_OF_YEAR, addI);
        ff.add(Calendar.DAY_OF_YEAR, addF);
    }

    private void generarReporteAnual(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("esteAnio", reporteAnual(false));
        data.put("anioPasado", reporteAnual(true));
        response.setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(data);
        PrintWriter out = response.getWriter();
        out.print(json);
    }

    private List<Integer> reporteAnual(boolean anioPasado) {
        LocalDateTime ldt = LocalDateTime.now();
        final Calendar actualCalendar = GregorianCalendar.from(ZonedDateTime.of(ldt, ZoneId.systemDefault()));
        if (anioPasado) {
            Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.add(Calendar.YEAR, -1);
            ldt = c.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        ReportesDAO dao = new ReportesDAO();
        List<Integer> data = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            data.add(dao.obtenerContadorPorMesesAnio(i, ldt.getYear()));
        }
        return data;
    }

    private void top10ProductosMasVendidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReportesDAO dao = new ReportesDAO();
        Map<String, Object> top = new HashMap<>();
        try {
            top = dao.top10ProductoMasVendidos();
            response.setContentType("application/json");
            Gson gson = new Gson();
            String json = gson.toJson(top);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void top10ClientesMasCompran(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReportesDAO dao = new ReportesDAO();
        Map<String, Object> top = new HashMap<>();
        try {
            top = dao.top10ClientesQueMasCompran();
            response.setContentType("application/json");
            Gson gson = new Gson();
            String json = gson.toJson(top);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void exportarJasperReports(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        try {
            InputStream logoEmpresa = this.getServletConfig()
                    .getServletContext()
                    .getResourceAsStream("reportesJasper/img/logoAlexanderStore.png"),
                    logoFooter = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/img/check.png"),
                    reporteCarritoCompras = this.getServletConfig()
                            .getServletContext()
                            .getResourceAsStream("reportesJasper/reporteCarritoCompras.jasper");
            if (logoEmpresa != null && logoFooter != null && reporteCarritoCompras != null) {
                String jsonCompras = request.getParameter("lista");
                Gson gson = new Gson();
                List<Reportes> reportesCompras = new ArrayList<>();
                List<Reportes> reportesCompras2 = new ArrayList<>();

                reportesCompras.add(new Reportes());
                reportesCompras2 = gson.fromJson(jsonCompras, new TypeToken<List<Reportes>>() {
                }.getType());
                reportesCompras.addAll(reportesCompras2);

                JasperReport report = (JasperReport) JRLoader.loadObject(reporteCarritoCompras);
                JRBeanArrayDataSource ds = new JRBeanArrayDataSource(reportesCompras.toArray());

                Map<String, Object> parameters = new HashMap();
                parameters.put("ds", ds);
                parameters.put("logoEmpresa", logoEmpresa);
                parameters.put("Check", logoFooter);
                response.setContentType("application/pdf");
                response.addHeader("Content-disposition", "inline; filename=RCarritoCompras.pdf");
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
