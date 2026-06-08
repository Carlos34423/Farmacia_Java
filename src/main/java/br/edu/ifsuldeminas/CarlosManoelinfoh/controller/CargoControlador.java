package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CargoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cargo;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/CargoControlador")
public class CargoControlador extends HttpServlet {

    Cargo objCargo = new Cargo();
    CargoDao objCargoDao = new CargoDao();

    String codigoCargo = "";
    String nome = "";
    String salarioBase = "";
    String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            codigoCargo = request.getParameter("codigoCargo");
            nome = request.getParameter("nome");
            salarioBase = request.getParameter("salarioBase");

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
        objCargo.setNome(nome);
        objCargo.setSalarioBase(Double.valueOf(salarioBase.replace(",", ".")));

        objCargoDao.salvar(objCargo);
        request.setAttribute("mensagem", "Cargo cadastrado com sucesso!");
        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cargo cargo = objCargoDao.buscarCargoPorId(Integer.valueOf(codigoCargo));

        request.setAttribute("cargoEdicao", cargo);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");
        request.setAttribute("mensagem", "Edite os dados do cargo");
        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCargo.setCodigoCargo(Integer.valueOf(codigoCargo));
        objCargo.setNome(nome);
        objCargo.setSalarioBase(Double.valueOf(salarioBase.replace(",", ".")));

        objCargoDao.alterar(objCargo);
        cancelar(request, response);
    }

    private void enviarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCargo.setCodigoCargo(Integer.valueOf(codigoCargo));
        objCargoDao.excluir(objCargo);
        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cargoEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("codigoCargo", "0");
        request.setAttribute("nome", "");
        request.setAttribute("salarioBase", "");
        request.setAttribute("opcao", "cadastrar");
        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cargo> cargos = objCargoDao.buscarTodosCargos();
        request.setAttribute("cargos", cargos);

        if (request.getAttribute("modoFormulario") == null) {
            request.setAttribute("modoFormulario", "cadastrar");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroCargo.jsp");
        dispatcher.forward(request, response);
    }
}
