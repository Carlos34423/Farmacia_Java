package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ConvenioDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Convenio;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/ConvenioControlador")
public class ConvenioControlador extends HttpServlet {

    Convenio objConvenio = new Convenio();
    ConvenioDao objConvenioDao = new ConvenioDao();

    String codigoConvenio = "";
    String nome = "";
    String percentual = "";
    String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            codigoConvenio = request.getParameter("codigoConvenio");
            nome = request.getParameter("nome");
            percentual = request.getParameter("percentual");

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
        objConvenio.setNome(nome);
        objConvenio.setPercentual(Double.valueOf(percentual.replace(",", ".")));

        objConvenioDao.salvar(objConvenio);
        request.setAttribute("mensagem", "Convenio cadastrado com sucesso!");
        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Convenio convenio = objConvenioDao.buscarConvenioPorId(Integer.valueOf(codigoConvenio));

        request.setAttribute("convenioEdicao", convenio);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");
        request.setAttribute("mensagem", "Edite os dados do convenio");
        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objConvenio.setCodigoConvenio(Integer.valueOf(codigoConvenio));
        objConvenio.setNome(nome);
        objConvenio.setPercentual(Double.valueOf(percentual.replace(",", ".")));

        objConvenioDao.alterar(objConvenio);
        cancelar(request, response);
    }

    private void enviarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objConvenio.setCodigoConvenio(Integer.valueOf(codigoConvenio));
        objConvenioDao.excluir(objConvenio);
        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("convenioEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("codigoConvenio", "0");
        request.setAttribute("nome", "");
        request.setAttribute("percentual", "");
        request.setAttribute("opcao", "cadastrar");
        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Convenio> convenios = objConvenioDao.buscarTodosConvenios();
        request.setAttribute("convenios", convenios);

        if (request.getAttribute("modoFormulario") == null) {
            request.setAttribute("modoFormulario", "cadastrar");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroConvenio.jsp");
        dispatcher.forward(request, response);
    }
}
