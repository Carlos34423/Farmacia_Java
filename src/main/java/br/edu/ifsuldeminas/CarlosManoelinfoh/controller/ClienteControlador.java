
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
import java.util.HashMap;
import java.util.Map;

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
    Float carteira;
    String erroFormatoCarteira;

    String opcao = "";

    CidadeDao cidadeDao = new CidadeDao();
    ConvenioDao convenioDao = new ConvenioDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
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

            String carteiraStr = request.getParameter("carteira");
            erroFormatoCarteira = null;

            if (carteiraStr != null && !carteiraStr.isBlank()) {
                try {
                    carteira = Float.valueOf(
                            carteiraStr.trim().replace(",", "."));
                } catch (NumberFormatException e) {
                    carteira = null;
                    erroFormatoCarteira = "Informe um valor numérico válido";
                }
            } else {
                carteira = 0f;
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

    private Map<String, String> validarCliente(Cliente cliente,
            Integer codigoAtual) {

        Map<String, String> erros = new HashMap<>();

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            erros.put("nome", "Informação obrigatória");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            erros.put("cpf", "Informação obrigatória");
        } else {

            Cliente existente =
                    objClienteDao.buscarClientePorCpf(cliente.getCpf().trim());

            boolean pertenceAOutroCliente = existente != null &&
                    (codigoAtual == null ||
                     !existente.getCodigoCliente().equals(codigoAtual));

            if (pertenceAOutroCliente) {
                erros.put("cpf", "CPF(ou CNPJ) já cadastrado");
            }
        }

        if (erroFormatoCarteira != null) {
            erros.put("carteira", erroFormatoCarteira);
        }

        return erros;
    }

    private void reexibirComErros(HttpServletRequest request,
            HttpServletResponse response,
            Cliente cliente,
            Map<String, String> erros,
            String modoFormulario,
            String opcaoFormulario)
            throws ServletException, IOException {

        request.setAttribute("errosCampo", erros);
        request.setAttribute("mensagemErro",
                "Não foi possível salvar. Verifique os campos destacados abaixo.");
        request.setAttribute("clienteEdicao", cliente);
        request.setAttribute("modoFormulario", modoFormulario);
        request.setAttribute("opcao", opcaoFormulario);

        encaminharParaPagina(request, response);
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
        objCliente.setCarteira(carteira);

        Map<String, String> erros = validarCliente(objCliente, null);

        if (!erros.isEmpty()) {
            reexibirComErros(request, response, objCliente, erros,
                    "cadastrar", "cadastrar");
            return;
        }

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
        objCliente.setCarteira(carteira);

        Map<String, String> erros = validarCliente(objCliente, codigoCliente);

        if (!erros.isEmpty()) {
            reexibirComErros(request, response, objCliente, erros,
                    "editar", "confirmarAlterar");
            return;
        }

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