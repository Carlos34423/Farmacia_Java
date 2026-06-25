/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CidadeDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ClienteDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ClienteDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ConvenioDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cidade;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cliente;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Convenio;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author hatak
 */


@WebServlet(WebConstante.BASE_PATH + "/ClienteControlador")
public class ClienteControlador extends HttpServlet {

    Cliente objCliente = new Cliente();
    ClienteDao objClienteDao = new ClienteDao();

    Integer codigoCliente;
    String nome = "";
    String cpf = "";
    String telefone = "";
    String email = "";

    Cidade cidade;
    Convenio convenio;

    String opcao = "";

    CidadeDao cidadeDao = new CidadeDao();
    ConvenioDao convenioDao = new ConvenioDao();

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

            String codigoClienteStr =
                    request.getParameter("codigoCliente");

            if (codigoClienteStr != null &&
                    !codigoClienteStr.isBlank()) {

                codigoCliente =
                        Integer.valueOf(codigoClienteStr);
            }

            String codigoCidadeStr =
                    request.getParameter("cidadeCliente");

            if (codigoCidadeStr != null &&
                    !codigoCidadeStr.isBlank()) {

                cidade =
                        cidadeDao.buscarCidadePorId(
                                Integer.valueOf(codigoCidadeStr));
            }

            String codigoConvenioStr =
                    request.getParameter("convenioCliente");

            if (codigoConvenioStr != null &&
                    !codigoConvenioStr.isBlank()) {

                convenio =
                        convenioDao.buscarConvenioPorId(
                                Integer.valueOf(codigoConvenioStr));
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

        objCliente = new Cliente();

        objCliente.setNome(nome);
        objCliente.setCpf(cpf);
        objCliente.setTelefone(telefone);
        objCliente.setEmail(email);
        objCliente.setCidade(cidade);
        objCliente.setConvenio(convenio);

        objClienteDao.salvar(objCliente);

        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Cliente cliente =
                objClienteDao.buscarClientePorId(codigoCliente);

        request.setAttribute("clienteEdicao", cliente);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");

        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        objCliente = new Cliente();

        objCliente.setCodigoCliente(codigoCliente);
        objCliente.setNome(nome);
        objCliente.setCpf(cpf);
        objCliente.setTelefone(telefone);
        objCliente.setEmail(email);
        objCliente.setCidade(cidade);
        objCliente.setConvenio(convenio);

        objClienteDao.alterar(objCliente);

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

        objClienteDao.excluir(codigoCliente);

        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("clienteEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("clientes",
                objClienteDao.buscarTodosClientes());

        request.setAttribute("listaCidade",
                cidadeDao.buscarTodasCidades());

        request.setAttribute("listaConvenio",
                convenioDao.buscarTodosConvenios());

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/CadastroCliente.jsp");

        dispatcher.forward(request, response);
    }
}