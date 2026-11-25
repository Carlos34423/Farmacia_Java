package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ClienteDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.MedicamentoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.VendaDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cliente;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.ItemCarrinho;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Medicamento;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compra de remédios com carrinho: o usuário vai adicionando medicamentos e
 * quantidades e depois finaliza a compra de uma vez. O desconto do convênio do
 * cliente vale sobre o valor total dos produtos.
 */
@WebServlet(WebConstante.BASE_PATH + "/CompraRemedioControlador")
public class CompraRemedioControlador extends HttpServlet {

    private static final String SESSAO_CARRINHO = "carrinhoCompra";
    private static final String SESSAO_CLIENTE = "carrinhoCliente";

    private final ClienteDao objClienteDao = new ClienteDao();
    private final MedicamentoDao objMedicamentoDao = new MedicamentoDao();
    private final VendaDao objVendaDao = new VendaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = obterCarrinho(session);

        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "atualizar";
            }

            // Guarda o cliente escolhido (só quando o formulário enviou o campo)
            String clienteParam = request.getParameter("clienteCompra");
            if (clienteParam != null) {
                if (clienteParam.isBlank()) {
                    session.removeAttribute(SESSAO_CLIENTE);
                } else {
                    session.setAttribute(SESSAO_CLIENTE, Integer.valueOf(clienteParam.trim()));
                }
            }

            switch (opcao) {
                case "adicionar":
                    adicionar(request, response, carrinho);
                    break;
                case "remover":
                    remover(request, response, carrinho);
                    break;
                case "comprar":
                    comprar(request, response, session, carrinho);
                    break;
                case "cancelar":
                    carrinho.clear();
                    session.removeAttribute(SESSAO_CLIENTE);
                    abrirPagina(request, response, session, carrinho);
                    break;
                case "atualizar":
                default:
                    abrirPagina(request, response, session, carrinho);
                    break;
            }

        } catch (NumberFormatException e) {
            request.setAttribute("mensagemErro", "Um ou mais campos possuem valor inválido.");
            abrirPagina(request, response, session, carrinho);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterCarrinho(HttpSession session) {
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute(SESSAO_CARRINHO);
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute(SESSAO_CARRINHO, carrinho);
        }
        return carrinho;
    }

    private ItemCarrinho buscarNoCarrinho(List<ItemCarrinho> carrinho, Integer codigoMedicamento) {
        for (ItemCarrinho item : carrinho) {
            if (item.getCodigoMedicamento().equals(codigoMedicamento)) {
                return item;
            }
        }
        return null;
    }

    private void adicionar(HttpServletRequest request, HttpServletResponse response,
            List<ItemCarrinho> carrinho) throws ServletException, IOException {

        Map<String, String> erros = new HashMap<>();

        String medParam = request.getParameter("medicamentoCompra");
        String qtdParam = request.getParameter("quantidade");

        Medicamento medicamento = null;
        if (medParam == null || medParam.isBlank()) {
            erros.put("medicamento", "Informação obrigatória");
        } else {
            medicamento = objMedicamentoDao.buscarMedicamentoPorId(Integer.valueOf(medParam.trim()));
            if (medicamento == null) {
                erros.put("medicamento", "Medicamento não encontrado");
            }
        }

        Integer quantidade = null;
        if (qtdParam == null || qtdParam.isBlank()) {
            erros.put("quantidade", "Informação obrigatória");
        } else {
            try {
                quantidade = Integer.valueOf(qtdParam.trim());
            } catch (NumberFormatException e) {
                quantidade = null;
            }
            if (quantidade == null || quantidade <= 0) {
                erros.put("quantidade", "Informe uma quantidade válida");
            }
        }

        if (erros.isEmpty()) {
            ItemCarrinho existente = buscarNoCarrinho(carrinho, medicamento.getCodigoMedicamento());
            int jaNoCarrinho = existente != null ? existente.getQuantidade() : 0;
            int estoque = medicamento.getQuantidadeEstoque() != null ? medicamento.getQuantidadeEstoque() : 0;

            if (estoque < jaNoCarrinho + quantidade) {
                erros.put("quantidade", "Estoque insuficiente (disponível: " + estoque
                        + ", já no carrinho: " + jaNoCarrinho + ")");
            }
        }

        HttpSession session = request.getSession();

        if (!erros.isEmpty()) {
            request.setAttribute("errosCampo", erros);
            request.setAttribute("mensagemErro", "Não foi possível adicionar ao carrinho. Verifique os campos destacados abaixo.");
            abrirPagina(request, response, session, carrinho);
            return;
        }

        ItemCarrinho existente = buscarNoCarrinho(carrinho, medicamento.getCodigoMedicamento());
        if (existente != null) {
            existente.setQuantidade(existente.getQuantidade() + quantidade);
            existente.setPrecoUnitario(medicamento.getPrecoVenda());
        } else {
            carrinho.add(new ItemCarrinho(medicamento.getCodigoMedicamento(), medicamento.getNome(),
                    medicamento.getPrecoVenda(), quantidade));
        }

        request.setAttribute("mensagem", quantidade + "x " + medicamento.getNome() + " adicionado ao carrinho.");
        abrirPagina(request, response, session, carrinho);
    }

    private void remover(HttpServletRequest request, HttpServletResponse response,
            List<ItemCarrinho> carrinho) throws ServletException, IOException {

        String codigo = request.getParameter("medicamentoRemover");
        if (codigo != null && !codigo.isBlank()) {
            Integer codigoMedicamento = Integer.valueOf(codigo.trim());
            carrinho.removeIf(item -> item.getCodigoMedicamento().equals(codigoMedicamento));
        }
        abrirPagina(request, response, request.getSession(), carrinho);
    }

    private double percentualDoCliente(Cliente cliente) {
        if (cliente != null && cliente.getConvenio() != null && cliente.getConvenio().getPercentual() != null) {
            return cliente.getConvenio().getPercentual();
        }
        return 0.0;
    }

    private double somarBruto(List<ItemCarrinho> carrinho) {
        double bruto = 0;
        for (ItemCarrinho item : carrinho) {
            bruto += item.getSubtotal();
        }
        return bruto;
    }

    private double aplicarDesconto(double bruto, double percentual) {
        return Math.round(bruto * (1 - percentual / 100.0) * 100.0) / 100.0;
    }

    private void comprar(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, List<ItemCarrinho> carrinho) throws ServletException, IOException {

        Map<String, String> erros = new HashMap<>();

        Integer codigoCliente = (Integer) session.getAttribute(SESSAO_CLIENTE);
        Cliente cliente = codigoCliente != null ? objClienteDao.buscarClientePorId(codigoCliente) : null;

        if (codigoCliente == null) {
            erros.put("cliente", "Selecione o cliente");
        } else if (cliente == null) {
            erros.put("cliente", "Cliente não encontrado");
        }

        if (carrinho.isEmpty()) {
            erros.put("carrinho", "O carrinho está vazio. Adicione ao menos um medicamento.");
        }

        // Revalida estoque e preço atual de cada item
        Map<Integer, Medicamento> medicamentos = new HashMap<>();
        if (erros.isEmpty()) {
            for (ItemCarrinho item : carrinho) {
                Medicamento med = objMedicamentoDao.buscarMedicamentoPorId(item.getCodigoMedicamento());
                if (med == null) {
                    erros.put("carrinho", "Medicamento não encontrado: " + item.getNome());
                    break;
                }
                if (med.getQuantidadeEstoque() == null || med.getQuantidadeEstoque() < item.getQuantidade()) {
                    erros.put("carrinho", "Estoque insuficiente para " + item.getNome()
                            + " (disponível: " + (med.getQuantidadeEstoque() == null ? 0 : med.getQuantidadeEstoque()) + ")");
                    break;
                }
                item.setPrecoUnitario(med.getPrecoVenda());
                medicamentos.put(med.getCodigoMedicamento(), med);
            }
        }

        double valorBruto = 0;
        double percentual = 0;
        double valorFinal = 0;

        if (erros.isEmpty()) {
            valorBruto = somarBruto(carrinho);
            percentual = percentualDoCliente(cliente);
            valorFinal = aplicarDesconto(valorBruto, percentual);

            float saldoAtual = cliente.getCarteira() != null ? cliente.getCarteira() : 0f;
            if (saldoAtual < valorFinal) {
                erros.put("cliente", "Saldo insuficiente na carteira do cliente");
            }
        }

        if (!erros.isEmpty()) {
            request.setAttribute("errosCampo", erros);
            request.setAttribute("mensagemErro", "Não foi possível concluir a compra. Verifique os campos destacados abaixo.");
            abrirPagina(request, response, session, carrinho);
            return;
        }

        // Efetiva a compra
        float saldoAtual = cliente.getCarteira() != null ? cliente.getCarteira() : 0f;
        cliente.setCarteira(saldoAtual - (float) valorFinal);
        objClienteDao.alterar(cliente);

        int totalUnidades = 0;
        for (ItemCarrinho item : carrinho) {
            Medicamento med = medicamentos.get(item.getCodigoMedicamento());
            med.setQuantidadeEstoque(med.getQuantidadeEstoque() - item.getQuantidade());
            objMedicamentoDao.alterar(med);
            totalUnidades += item.getQuantidade();
        }

        // Funcionário/forma de pagamento ficam em branco porque a tela ainda não pergunta esses dados.
        objVendaDao.registrarItens(cliente.getCodigoCliente(), null, null, carrinho, percentual);

        String msg = "Compra realizada com sucesso para " + cliente.getNome() + "! "
                + carrinho.size() + " produto(s), " + totalUnidades + " unidade(s) — valor dos produtos: R$ "
                + String.format("%.2f", valorBruto)
                + (percentual > 0
                        ? " | desconto do convênio " + cliente.getConvenio().getNome()
                        + " (" + String.format("%.0f", percentual) + "%): -R$ "
                        + String.format("%.2f", valorBruto - valorFinal)
                        : " | sem desconto de convênio")
                + " | valor final: R$ " + String.format("%.2f", valorFinal)
                + ". Novo saldo da carteira: R$ " + String.format("%.2f", cliente.getCarteira());

        carrinho.clear();
        session.removeAttribute(SESSAO_CLIENTE);

        request.setAttribute("mensagem", msg);
        abrirPagina(request, response, session, carrinho);
    }

    private void abrirPagina(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, List<ItemCarrinho> carrinho) throws ServletException, IOException {

        List<Cliente> clientes = objClienteDao.buscarTodosClientes();

        Integer clienteSelecionado = (Integer) session.getAttribute(SESSAO_CLIENTE);
        Cliente cliente = null;
        if (clienteSelecionado != null) {
            for (Cliente c : clientes) {
                if (c.getCodigoCliente().equals(clienteSelecionado)) {
                    cliente = c;
                    break;
                }
            }
        }

        double bruto = somarBruto(carrinho);
        double percentual = percentualDoCliente(cliente);
        double valorFinal = aplicarDesconto(bruto, percentual);

        request.setAttribute("listaClientes", clientes);
        request.setAttribute("listaMedicamentos", objMedicamentoDao.buscarTodosMedicamentos());
        request.setAttribute("carrinho", carrinho);
        request.setAttribute("clienteSelecionado", clienteSelecionado);
        request.setAttribute("convenioNome", cliente != null && cliente.getConvenio() != null ? cliente.getConvenio().getNome() : null);
        request.setAttribute("valorBruto", bruto);
        request.setAttribute("percentualDesconto", percentual);
        request.setAttribute("valorDesconto", bruto - valorFinal);
        request.setAttribute("valorFinal", valorFinal);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CompraRemedio.jsp");
        dispatcher.forward(request, response);
    }
}
