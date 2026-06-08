package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.FabricanteDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Fabricante;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/FabricanteControlador")
public class FabricanteControlador extends HttpServlet {

    Fabricante objFabricante = new Fabricante();
    FabricanteDao objFabricanteDao = new FabricanteDao();

    String cnpj = "";
    String cnpjOriginal = "";
    String nome = "";
    String telefone = "";
    String email = "";
    String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            cnpj = request.getParameter("cnpj");
            cnpjOriginal = request.getParameter("cnpjOriginal");
            nome = request.getParameter("nome");
            telefone = request.getParameter("telefone");
            email = request.getParameter("email");

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
        objFabricante.setNome(nome);
        objFabricante.setCnpj(cnpj);
        objFabricante.setTelefone(telefone);
        objFabricante.setEmail(email);

        objFabricanteDao.salvar(objFabricante);
        request.setAttribute("mensagem", "Fabricante cadastrado com sucesso!");
        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Fabricante fabricante = objFabricanteDao.buscarFabricantePorCnpj(cnpj);

        if (fabricante != null) {
            fabricante.setCnpjOriginal(fabricante.getCnpj());
        }

        request.setAttribute("fabricanteEdicao", fabricante);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");
        request.setAttribute("mensagem", "Edite os dados do fabricante");
        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFabricante.setCnpjOriginal(cnpjOriginal);
        objFabricante.setNome(nome);
        objFabricante.setCnpj(cnpj);
        objFabricante.setTelefone(telefone);
        objFabricante.setEmail(email);

        objFabricanteDao.alterar(objFabricante);
        cancelar(request, response);
    }

    private void enviarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFabricante.setCnpj(cnpj);
        objFabricanteDao.excluir(objFabricante);
        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("fabricanteEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("cnpj", "");
        request.setAttribute("cnpjOriginal", "");
        request.setAttribute("nome", "");
        request.setAttribute("telefone", "");
        request.setAttribute("email", "");
        request.setAttribute("opcao", "cadastrar");
        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Fabricante> fabricantes = objFabricanteDao.buscarTodosFabricantes();
        request.setAttribute("fabricantes", fabricantes);

        if (request.getAttribute("modoFormulario") == null) {
            request.setAttribute("modoFormulario", "cadastrar");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroFabricante.jsp");
        dispatcher.forward(request, response);
    }
}
