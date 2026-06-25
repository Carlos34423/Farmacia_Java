/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

/**
 *
 * @author hatak
 */


import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.FabricanteDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.FornecedorDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.MedicamentoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Medicamento;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CategoriaMedicamentoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.CategoriaMedicamento;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Fabricante;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Fornecedor;

@WebServlet(WebConstante.BASE_PATH + "/MedicamentoControlador")
public class MedicamentoControlador extends HttpServlet {

    private Medicamento objMedicamento = new Medicamento();
    private MedicamentoDao objMedicamentoDao = new MedicamentoDao();

    private CategoriaMedicamentoDao categoriaDao =
            new CategoriaMedicamentoDao();

    private FabricanteDao fabricanteDao =
            new FabricanteDao();

    private FornecedorDao fornecedorDao =
            new FornecedorDao();

    private String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            opcao = request.getParameter("opcao");

            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            switch (opcao) {

                case "cadastrar":
                    cadastrar(request, response);
                    break;

                case "listar":
                    encaminharParaPagina(request, response);
                    break;

                case "novo":
                case "cancelar":
                    cancelar(request, response);
                    break;

                case "editar":
                case "enviarAlterar":
                    enviarAlterar(request, response);
                    break;

                case "confirmarAlterar":
                    confirmarAlterar(request, response);
                    break;

                case "excluir":
                case "enviarExcluir":
                    enviarExcluir(request, response);
                    break;

                case "confirmarExcluir":
                    confirmarExcluir(request, response);
                    break;
            }

        } catch (Exception e) {
            response.getWriter().println(e.getMessage());
        }
    }

private void cadastrar(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    Medicamento medicamento = new Medicamento();

    medicamento.setNome(request.getParameter("nome"));
    medicamento.setDescricao(request.getParameter("descricao"));
    medicamento.setPrecoVenda(
            Double.valueOf(request.getParameter("precoVenda")));
    medicamento.setQuantidadeEstoque(
            Integer.valueOf(request.getParameter("quantidadeEstoque")));

    CategoriaMedicamento categoria =
            categoriaDao.buscarCategoriaMedicamentoPorId(
                    Integer.valueOf(
                            request.getParameter("codigoCategoriaMedicamento")));

    medicamento.setCategoriaMedicamento(categoria);

Fabricante fabricante =
        fabricanteDao.buscarFabricantePorCnpj(
                request.getParameter("cnpjFabricante"));

    medicamento.setFabricante(fabricante);

    Fornecedor fornecedor =
            fornecedorDao.buscarFornecedorPorId(
                    Integer.valueOf(
                            request.getParameter("codigoFornecedor")));

    medicamento.setFornecedor(fornecedor);

    objMedicamentoDao.salvar(medicamento);

    cancelar(request, response);
}

    private void enviarAlterar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer codigoMedicamento =
                Integer.valueOf(request.getParameter("codigoMedicamento"));

        Medicamento medicamento =
                objMedicamentoDao.buscarMedicamentoPorId(codigoMedicamento);

        request.setAttribute("medicamentoEdicao", medicamento);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");

        encaminharParaPagina(request, response);
    }

private void confirmarAlterar(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

    Medicamento medicamento = new Medicamento();

    medicamento.setCodigoMedicamento(
            Integer.valueOf(
                    request.getParameter("codigoMedicamento")));

    medicamento.setNome(
            request.getParameter("nome"));

    medicamento.setDescricao(
            request.getParameter("descricao"));

    medicamento.setPrecoVenda(
            Double.valueOf(
                    request.getParameter("precoVenda")));

    medicamento.setQuantidadeEstoque(
            Integer.valueOf(
                    request.getParameter("quantidadeEstoque")));

    CategoriaMedicamento categoria =
            categoriaDao.buscarCategoriaMedicamentoPorId(
                    Integer.valueOf(
                            request.getParameter("codigoCategoriaMedicamento")));

    medicamento.setCategoriaMedicamento(categoria);

    Fabricante fabricante =
            fabricanteDao.buscarFabricantePorCnpj(
                    request.getParameter("cnpjFabricante"));

    medicamento.setFabricante(fabricante);

    Fornecedor fornecedor =
            fornecedorDao.buscarFornecedorPorId(
                    Integer.valueOf(
                            request.getParameter("codigoFornecedor")));

    medicamento.setFornecedor(fornecedor);

    objMedicamentoDao.alterar(medicamento);

    cancelar(request, response);
}

    private void enviarExcluir(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer codigoMedicamento =
                Integer.valueOf(request.getParameter("codigoMedicamento"));

        objMedicamentoDao.excluir(codigoMedicamento);

        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("medicamentoEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("medicamentos",
                objMedicamentoDao.buscarTodosMedicamentos());

        request.setAttribute("listaCategorias",
                categoriaDao.buscarTodasCategoriasMedicamentos());

        request.setAttribute("listaFabricantes",
                fabricanteDao.buscarTodosFabricantes());

        request.setAttribute("listaFornecedores",
                fornecedorDao.buscarTodosFornecedores());

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/CadastroMedicamento.jsp");

        dispatcher.forward(request, response);
    }
}
