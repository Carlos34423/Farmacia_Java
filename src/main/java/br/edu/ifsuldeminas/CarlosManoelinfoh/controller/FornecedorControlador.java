package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CidadeDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cidade;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(WebConstante.BASE_PATH + "/FornecedorControlador")
public class FornecedorControlador extends HttpServlet {

    Fornecedor objFornecedor = new Fornecedor();
    FornecedorDao objFornecedorDao = new FornecedorDao();

    Integer codigoFornecedor;
    String razaoSocial = "";
    String nomeFantasia = "";
    String cnpj = "";
    String cnpjOriginal = "";
    String telefone = "";
    String email = "";
    Cidade cidade;
    String opcao = "";

    CidadeDao cidadeDao = new CidadeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");  try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";

                String codigoFornecedorStr =
                        request.getParameter("codigoFornecedor");

                if (codigoFornecedorStr != null && !codigoFornecedorStr.isEmpty()) {
                    codigoFornecedor = Integer.valueOf(codigoFornecedorStr);
                }
            }

            razaoSocial = request.getParameter("razaoSocial");
            nomeFantasia = request.getParameter("nomeFantasia");
            cnpj = request.getParameter("cnpj");
            cnpjOriginal = request.getParameter("cnpjOriginal");
            telefone = request.getParameter("telefone");
            email = request.getParameter("email");

            String codigoCidadeStr = request.getParameter("cidadeFuncionario");

            if (codigoCidadeStr != null && !codigoCidadeStr.isEmpty()) {
                Integer codigoCidade = Integer.valueOf(codigoCidadeStr);
                cidade = cidadeDao.buscarCidadePorId(codigoCidade);
            }

            String codigoFornecedorStr =
                    request.getParameter("codigoFornecedor");

            if (codigoFornecedorStr != null &&
                !codigoFornecedorStr.isBlank()) {

                codigoFornecedor =
                        Integer.valueOf(codigoFornecedorStr);
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
                default:
                    throw new IllegalArgumentException("Opcao invalida: " + opcao);
            }

        } catch (IllegalArgumentException ex) {
            response.getWriter().println("Erro: " + ex.getMessage());
        }
    }

    /**
     * Valida os campos obrigatórios e verifica se já existe outro
     * fornecedor cadastrado com o mesmo CNPJ. codigoAtual deve ser null
     * ao cadastrar, e o código do fornecedor em edição ao alterar.
     */
    private Map<String, String> validarFornecedor(Fornecedor fornecedor,
            Integer codigoAtual) {

        Map<String, String> erros = new HashMap<>();

        if (fornecedor.getRazaoSocial() == null
                || fornecedor.getRazaoSocial().isBlank()) {
            erros.put("razaoSocial", "Informação obrigatória");
        }

        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().isBlank()) {
            erros.put("cnpj", "Informação obrigatória");
        } else {

            Fornecedor existente = objFornecedorDao.buscarFornecedorPorCnpj(
                    fornecedor.getCnpj().trim());

            boolean pertenceAOutroFornecedor = existente != null &&
                    (codigoAtual == null ||
                     !existente.getCodigoFornecedor().equals(codigoAtual));

            if (pertenceAOutroFornecedor) {
                erros.put("cnpj", "CPF(ou CNPJ) já cadastrado");
            }
        }

        return erros;
    }

    private void reexibirComErros(HttpServletRequest request,
            HttpServletResponse response,
            Fornecedor fornecedor,
            Map<String, String> erros,
            String modoFormulario,
            String opcaoFormulario)
            throws ServletException, IOException {

        request.setAttribute("errosCampo", erros);
        request.setAttribute("mensagemErro",
                "Não foi possível salvar. Verifique os campos destacados abaixo.");

        request.setAttribute("codigoFornecedor", fornecedor.getCodigoFornecedor());
        request.setAttribute("razaoSocial", fornecedor.getRazaoSocial());
        request.setAttribute("nomeFantasia", fornecedor.getNomeFantasia());
        request.setAttribute("cnpj", fornecedor.getCnpj());
        request.setAttribute("telefone", fornecedor.getTelefone());
        request.setAttribute("email", fornecedor.getEmail());
        request.setAttribute("cidadeFuncionario",
                fornecedor.getCidade() != null ? fornecedor.getCidade().getCodigoCidade() : null);

        request.setAttribute("fornecedorEdicao", fornecedor);
        request.setAttribute("modoFormulario", modoFormulario);
        request.setAttribute("opcao", opcaoFormulario);

        encaminharParaPagina(request, response);
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objFornecedor = new Fornecedor();

        objFornecedor.setRazaoSocial(razaoSocial);
        objFornecedor.setNomeFantasia(nomeFantasia);
        objFornecedor.setCnpj(cnpj);
        objFornecedor.setTelefone(telefone);
        objFornecedor.setEmail(email);

        if (cidade != null) {
            objFornecedor.setCidade(cidade);
        }

        Map<String, String> erros = validarFornecedor(objFornecedor, null);

        if (!erros.isEmpty()) {
            reexibirComErros(request, response, objFornecedor, erros,
                    "cadastrar", "cadastrar");
            return;
        }

        objFornecedorDao.salvar(objFornecedor);

        cancelar(request, response);
    }

    private void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Fornecedor fornecedor = objFornecedorDao.buscarFornecedorPorId(codigoFornecedor);

        if (fornecedor != null) {
            fornecedor.setCnpjOriginal(fornecedor.getCnpj());
        }

        request.setAttribute("codigoFornecedor",
                fornecedor.getCodigoFornecedor());
        request.setAttribute("razaoSocial", fornecedor.getRazaoSocial());
        request.setAttribute("nomeFantasia", fornecedor.getNomeFantasia());
        request.setAttribute("cnpj", fornecedor.getCnpj());
        request.setAttribute("telefone", fornecedor.getTelefone());
        request.setAttribute("email", fornecedor.getEmail());
        request.setAttribute("cidadeFuncionario",
                fornecedor.getCidade().getCodigoCidade());

        request.setAttribute("fornecedorEdicao", fornecedor);
        request.setAttribute("modoFormulario", "editar");
        request.setAttribute("opcao", "confirmarAlterar");
        request.setAttribute("mensagem", "Edite os dados do fornecedor");
        encaminharParaPagina(request, response);
    }

    private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objFornecedor = new Fornecedor();

        objFornecedor.setCodigoFornecedor(codigoFornecedor);
        objFornecedor.setRazaoSocial(razaoSocial);
        objFornecedor.setNomeFantasia(nomeFantasia);
        objFornecedor.setCnpj(cnpj);
        objFornecedor.setTelefone(telefone);
        objFornecedor.setEmail(email);

        if (cidade != null) {
            objFornecedor.setCidade(cidade);
        }

        Map<String, String> erros =
                validarFornecedor(objFornecedor, codigoFornecedor);

        if (!erros.isEmpty()) {
            reexibirComErros(request, response, objFornecedor, erros,
                    "editar", "confirmarAlterar");
            return;
        }

        objFornecedorDao.alterar(objFornecedor);

        cancelar(request, response);
    }

    private void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        confirmarExcluir(request, response);
    }

    private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objFornecedorDao.excluir(codigoFornecedor);

        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("fornecedorEdicao", null);
        request.setAttribute("modoFormulario", "cadastrar");
        request.setAttribute("razaoSocial", "");
        request.setAttribute("nomeFantasia", "");
        request.setAttribute("cnpj", "");
        request.setAttribute("cnpjOriginal", "");
        request.setAttribute("telefone", "");
        request.setAttribute("email", "");
        request.setAttribute("cidadeFuncionario", null);
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Fornecedor> fornecedores =
                objFornecedorDao.buscarTodosFornecedores();

        List<Cidade> listaCidade =
                cidadeDao.buscarTodasCidades();

        request.setAttribute("fornecedores", fornecedores);
        request.setAttribute("listaCidade", listaCidade);

        if (request.getAttribute("modoFormulario") == null) {
            request.setAttribute("modoFormulario", "cadastrar");
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/CadastroFornecedor.jsp");

        dispatcher.forward(request, response);
    }
}