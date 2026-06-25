package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CategoriaMedicamentoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.CategoriaMedicamento;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/CategoriaMedicamentoControlador")
public class CategoriaMedicamentoControlador extends HttpServlet {

    CategoriaMedicamento objCategoriaMedicamento = new CategoriaMedicamento();
    CategoriaMedicamentoDao objCategoriaMedicamentoDao = new CategoriaMedicamentoDao();

    String codigoCategoriaMedicamento = "";
    String nome = "";
    String descricao = "";
    String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            codigoCategoriaMedicamento = request.getParameter("codigoCategoriaMedicamento");
            nome = request.getParameter("nome");
            descricao = request.getParameter("descricao");

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

        } catch (NumberFormatException e) {
            response.getWriter().println("Erro: Parametro numerico invalido - " + e.getMessage());
        } catch (IllegalArgumentException ex) {
            response.getWriter().println("Erro: " + ex.getMessage());
        }
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCategoriaMedicamento.setNome(nome);
        objCategoriaMedicamento.setDescricao(descricao);

        objCategoriaMedicamentoDao.salvar(objCategoriaMedicamento);
        request.setAttribute("mensagem", "Categoria cadastrada com sucesso!");
        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaMedicamento categoria = objCategoriaMedicamentoDao.buscarCategoriaMedicamentoPorId(
                Integer.valueOf(codigoCategoriaMedicamento));

        request.setAttribute("categoriaEdicao", categoria);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");
        request.setAttribute("mensagem", "Edite os dados da categoria");
        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCategoriaMedicamento.setCodigoCategoriaMedicamento(Integer.valueOf(codigoCategoriaMedicamento));
        objCategoriaMedicamento.setNome(nome);
        objCategoriaMedicamento.setDescricao(descricao);

        objCategoriaMedicamentoDao.alterar(objCategoriaMedicamento);
        cancelar(request, response);
    }

    private void enviarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCategoriaMedicamento.setCodigoCategoriaMedicamento(Integer.valueOf(codigoCategoriaMedicamento));
        objCategoriaMedicamentoDao.excluir(
        Integer.valueOf(codigoCategoriaMedicamento));
        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categoriaEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("codigoCategoriaMedicamento", "0");
        request.setAttribute("nome", "");
        request.setAttribute("descricao", "");
        request.setAttribute("opcao", "cadastrar");
        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoriaMedicamento> categorias = objCategoriaMedicamentoDao.buscarTodasCategoriasMedicamentos();
        request.setAttribute("categorias", categorias);

        if (request.getAttribute("modoFormulario") == null) {
            request.setAttribute("modoFormulario", "cadastrar");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroCategoriaMedicamento.jsp");
        dispatcher.forward(request, response);
    }
}
