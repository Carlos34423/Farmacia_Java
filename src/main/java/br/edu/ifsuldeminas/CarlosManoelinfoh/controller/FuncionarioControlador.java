package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CargoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CidadeDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.FuncionarioDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cargo;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cidade;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Funcionario;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(WebConstante.BASE_PATH + "/FuncionarioControlador")
public class FuncionarioControlador extends HttpServlet {

    Funcionario objFuncionario = new Funcionario();
    FuncionarioDao objFuncionarioDao = new FuncionarioDao();

    Integer codigoFuncionario;

    String nome = "";
    String cpf = "";
    String telefone = "";
    String email = "";

    Cargo cargo;
    Cidade cidade;

    String opcao = "";

    CargoDao cargoDao = new CargoDao();
    CidadeDao cidadeDao = new CidadeDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            opcao = request.getParameter("opcao");

            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            nome = request.getParameter("nome");
            cpf = request.getParameter("cpf");
            telefone = request.getParameter("telefone");
            email = request.getParameter("email");

            String codigoFuncionarioStr =
                    request.getParameter("codigoFuncionario");

            if (codigoFuncionarioStr != null
                    && !codigoFuncionarioStr.isBlank()) {

                codigoFuncionario =
                        Integer.valueOf(codigoFuncionarioStr);
            }

            String codigoCargoStr =
                    request.getParameter("cargoFuncionario");

            if (codigoCargoStr != null
                    && !codigoCargoStr.isBlank()) {

                cargo = cargoDao.buscarCargoPorId(
                        Integer.valueOf(codigoCargoStr));
            }

            String codigoCidadeStr =
                    request.getParameter("cidadeFuncionario");

            if (codigoCidadeStr != null
                    && !codigoCidadeStr.isBlank()) {

                cidade = cidadeDao.buscarCidadePorId(
                        Integer.valueOf(codigoCidadeStr));
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

        objFuncionario = new Funcionario();

        objFuncionario.setNome(nome);
        objFuncionario.setCpf(cpf);
        objFuncionario.setTelefone(telefone);
        objFuncionario.setEmail(email);
        objFuncionario.setCargo(cargo);
        objFuncionario.setCidade(cidade);

        objFuncionarioDao.salvar(objFuncionario);

        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Funcionario funcionario =
                objFuncionarioDao.buscarFuncionarioPorId(
                        codigoFuncionario);

        request.setAttribute("funcionarioEdicao", funcionario);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");

        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        objFuncionario = new Funcionario();

        objFuncionario.setCodigoFuncionario(
                codigoFuncionario);

        objFuncionario.setNome(nome);
        objFuncionario.setCpf(cpf);
        objFuncionario.setTelefone(telefone);
        objFuncionario.setEmail(email);
        objFuncionario.setCargo(cargo);
        objFuncionario.setCidade(cidade);

        objFuncionarioDao.alterar(objFuncionario);

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

        objFuncionarioDao.excluir(codigoFuncionario);

        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("funcionarioEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("funcionarios",
                objFuncionarioDao.buscarTodosFuncionarios());

        request.setAttribute("listaCargo",
                cargoDao.buscarTodosCargos());

        request.setAttribute("listaCidade",
                cidadeDao.buscarTodasCidades());

        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "/CadastroFuncionario.jsp");

        dispatcher.forward(request, response);
    }
}