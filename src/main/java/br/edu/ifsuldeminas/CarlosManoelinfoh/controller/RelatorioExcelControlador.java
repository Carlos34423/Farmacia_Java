package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio.RelatorioEstoqueFornecedorExcel;
import br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio.RelatorioExcel;
import br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio.RelatorioInventarioExcel;
import br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio.RelatorioMargemExcel;
import br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio.RelatorioTopClientesExcel;
import br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio.RelatorioVendasFuncionarioExcel;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Abordagem 3: relatórios em Excel (Apache POI).
 * Uso: .../RelatorioExcelControlador?tipo=inventario|funcionarios|clientes|fornecedores|margem
 */
@WebServlet(WebConstante.BASE_PATH + "/RelatorioExcelControlador")
public class RelatorioExcelControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");
        RelatorioExcel relatorio = escolher(tipo);

        if (relatorio == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de relatório inválido: " + tipo);
            return;
        }

        try {
            List<Object[]> dados = relatorio.buscarDados();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + relatorio.nomeArquivo() + "\"");
            relatorio.gerar(dados, response.getOutputStream());
        } catch (SQLException e) {
            throw new ServletException("Erro ao consultar o banco para o relatório", e);
        }
    }

    private RelatorioExcel escolher(String tipo) {
        if (tipo == null) {
            return null;
        }
        switch (tipo) {
            case "inventario":
                return new RelatorioInventarioExcel();
            case "funcionarios":
                return new RelatorioVendasFuncionarioExcel();
            case "clientes":
                return new RelatorioTopClientesExcel();
            case "fornecedores":
                return new RelatorioEstoqueFornecedorExcel();
            case "margem":
                return new RelatorioMargemExcel();
            default:
                return null;
        }
    }
}
