package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.FornecedorDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Fornecedor;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/FornecedorControlador")
public class FornecedorControlador extends HttpServlet {

    Fornecedor objFornecedor = new Fornecedor();
    FornecedorDao objFornecedorDao = new FornecedorDao();

    String razaoSocial = "";
    String nomeFantasia = "";
    String cnpj = "";
    String cnpjOriginal = "";
    String telefone = "";
    String email = "";
    String cidade = "";
    String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            razaoSocial = request.getParameter("razaoSocial");
            nomeFantasia = request.getParameter("nomeFantasia");
            cnpj = request.getParameter("cnpj");
            cnpjOriginal = request.getParameter("cnpjOriginal");
            telefone = request.getParameter("telefone");
            email = request.getParameter("email");
            cidade = request.getParameter("cidade");

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
                default:
                    throw new IllegalArgumentException("Opcao invalida: " + opcao);
            }

        } catch (IllegalArgumentException ex) {
            response.getWriter().println("Erro: " + ex.getMessage());
        }
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFornecedor.setRazaoSocial(razaoSocial);
        objFornecedor.setNomeFantasia(nomeFantasia);
        objFornecedor.setCnpj(cnpj);
        objFornecedor.setTelefone(telefone);
        objFornecedor.setEmail(email);
        objFornecedor.setCidade(cidade);

        objFornecedorDao.salvar(objFornecedor);
        request.setAttribute("mensagem", "Fornecedor cadastrado com sucesso!");
        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Fornecedor fornecedor = objFornecedorDao.buscarFornecedorPorCnpj(cnpj);

        if (fornecedor != null) {
            fornecedor.setCnpjOriginal(fornecedor.getCnpj());
        }

        request.setAttribute("fornecedorEdicao", fornecedor);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");
        request.setAttribute("mensagem", "Edite os dados do fornecedor");
        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFornecedor.setRazaoSocial(razaoSocial);
        objFornecedor.setNomeFantasia(nomeFantasia);
        objFornecedor.setCnpj(cnpj);
        objFornecedor.setCnpjOriginal(cnpjOriginal);
        objFornecedor.setTelefone(telefone);
        objFornecedor.setEmail(email);
        objFornecedor.setCidade(cidade);

        objFornecedorDao.alterar(objFornecedor);
        cancelar(request, response);
    }

    private void enviarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFornecedor.setCnpj(cnpj);
        objFornecedorDao.excluir(objFornecedor);
        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("fornecedorEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("razaoSocial", "");
        request.setAttribute("nomeFantasia", "");
        request.setAttribute("cnpj", "");
        request.setAttribute("cnpjOriginal", "");
        request.setAttribute("telefone", "");
        request.setAttribute("email", "");
        request.setAttribute("cidade", "");
        request.setAttribute("opcao", "cadastrar");
        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Fornecedor> fornecedores = objFornecedorDao.buscarTodosFornecedores();
        request.setAttribute("fornecedores", fornecedores);

        if (request.getAttribute("modoFormulario") == null) {
            request.setAttribute("modoFormulario", "cadastrar");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroFornecedor.jsp");
        dispatcher.forward(request, response);
    }
}
