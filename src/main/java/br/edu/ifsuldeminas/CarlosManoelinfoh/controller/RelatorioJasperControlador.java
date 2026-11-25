package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ConnectionFactory;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

/**
 * Abordagem 1: relatórios com JasperReports 7.
 * Uso: .../RelatorioJasperControlador?tipo=vendasMes|topMedicamentos|estoqueMinimo|vencimento|categoria
 *      &formato=pdf|xlsx|csv|html  [&dataInicial=yyyy-MM-dd&dataFinal=yyyy-MM-dd] [&dias=90]
 * Os layouts (.jrxml) ficam em WEB-INF/relatorios e são compilados na primeira chamada.
 */
@WebServlet(WebConstante.BASE_PATH + "/RelatorioJasperControlador")
public class RelatorioJasperControlador extends HttpServlet {

    private static final Map<String, JasperReport> CACHE = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String arquivo = arquivoDoTipo(request.getParameter("tipo"));
        if (arquivo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de relatório inválido.");
            return;
        }
        String formato = request.getParameter("formato");
        if (formato == null || formato.isBlank()) {
            formato = "pdf";
        }

        try (Connection con = ConnectionFactory.getIntance().getConnetion()) {

            JasperReport relatorio = compilar(arquivo);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put(JRParameter.REPORT_LOCALE, Locale.forLanguageTag("pt-BR"));
            LocalDate fim = lerData(request.getParameter("dataFinal"), LocalDate.now());
            LocalDate ini = lerData(request.getParameter("dataInicial"), fim.minusMonths(6));
            parametros.put("DATA_INICIAL", Timestamp.valueOf(ini.atStartOfDay()));
            parametros.put("DATA_FINAL", Timestamp.valueOf(fim.atTime(23, 59, 59)));
            parametros.put("DIAS", lerInteiro(request.getParameter("dias"), 90));

            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, con);
            String nome = arquivo.replace(".jrxml", "");

            switch (formato) {
                case "xlsx": {
                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + nome + ".xlsx\"");
                    JRXlsxExporter ex = new JRXlsxExporter();
                    ex.setExporterInput(new SimpleExporterInput(impressao));
                    ex.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
                    ex.exportReport();
                    break;
                }
                case "csv": {
                    response.setContentType("text/csv; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + nome + ".csv\"");
                    JRCsvExporter ex = new JRCsvExporter();
                    ex.setExporterInput(new SimpleExporterInput(impressao));
                    ex.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream(), "UTF-8"));
                    ex.exportReport();
                    break;
                }
                case "html": {
                    response.setContentType("text/html; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    HtmlExporter ex = new HtmlExporter();
                    ex.setExporterInput(new SimpleExporterInput(impressao));
                    ex.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
                    ex.exportReport();
                    break;
                }
                default: {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"" + nome + ".pdf\"");
                    JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());
                }
            }
        } catch (SQLException | JRException e) {
            throw new ServletException("Erro ao gerar o relatório", e);
        }
    }

    private JasperReport compilar(String arquivo) throws JRException, IOException {
        JasperReport cacheado = CACHE.get(arquivo);
        if (cacheado != null) {
            return cacheado;
        }
        try (InputStream in = getServletContext().getResourceAsStream("/WEB-INF/relatorios/" + arquivo)) {
            if (in == null) {
                throw new IOException("Layout não encontrado: " + arquivo);
            }
            JasperReport novo = JasperCompileManager.compileReport(in);
            CACHE.put(arquivo, novo);
            return novo;
        }
    }

    private static String arquivoDoTipo(String tipo) {
        if (tipo == null) {
            return null;
        }
        switch (tipo) {
            case "vendasMes":
                return "rel01_vendas_por_mes.jrxml";
            case "topMedicamentos":
                return "rel02_top10_medicamentos.jrxml";
            case "estoqueMinimo":
                return "rel03_estoque_abaixo_minimo.jrxml";
            case "vencimento":
                return "rel04_medicamentos_a_vencer.jrxml";
            case "categoria":
                return "rel05_vendas_por_categoria.jrxml";
            default:
                return null;
        }
    }

    private static LocalDate lerData(String s, LocalDate padrao) {
        try {
            return (s == null || s.isBlank()) ? padrao : LocalDate.parse(s.trim());
        } catch (RuntimeException e) {
            return padrao;
        }
    }

    private static Integer lerInteiro(String s, int padrao) {
        try {
            int v = Integer.parseInt(s.trim());
            return v < 0 ? padrao : v;
        } catch (RuntimeException e) {
            return padrao;
        }
    }
}
