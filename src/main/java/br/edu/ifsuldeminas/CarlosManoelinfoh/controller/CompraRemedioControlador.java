/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ClienteDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.MedicamentoDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cliente;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Medicamento;
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
 * Registra a compra de um medicamento por um cliente.
 *
 * A cada compra confirmada, dois registros já existentes no banco são
 * atualizados no mesmo processo: o campo "carteira" do cliente (é
 * descontado o valor da compra) e o campo "quantidadeEstoque" do
 * medicamento (é descontada a quantidade comprada). Não são criadas
 * tabelas novas — usa somente ClienteDao e MedicamentoDao, que já
 * existem no projeto.
 */
@WebServlet(WebConstante.BASE_PATH + "/CompraRemedioControlador")
public class CompraRemedioControlador extends HttpServlet {

    ClienteDao objClienteDao = new ClienteDao();
    MedicamentoDao objMedicamentoDao = new MedicamentoDao();

    Integer codigoCliente;
    Integer codigoMedicamento;
    String quantidadeStr = "";

    String opcao = "";

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {

            opcao = request.getParameter("opcao");

            if (opcao == null || opcao.isEmpty()) {
                opcao = "cancelar";
            }

            String codigoClienteStr =
                    request.getParameter("clienteCompra");

            if (codigoClienteStr != null && !codigoClienteStr.isBlank()) {
                codigoCliente = Integer.valueOf(codigoClienteStr);
            } else {
                codigoCliente = null;
            }

            String codigoMedicamentoStr =
                    request.getParameter("medicamentoCompra");

            if (codigoMedicamentoStr != null && !codigoMedicamentoStr.isBlank()) {
                codigoMedicamento = Integer.valueOf(codigoMedicamentoStr);
            } else {
                codigoMedicamento = null;
            }

            quantidadeStr = request.getParameter("quantidade");

            switch (opcao) {

                case "comprar":
                    comprar(request, response);
                    break;

                case "cancelar":
                default:
                    cancelar(request, response);
                    break;
            }

        } catch (NumberFormatException e) {

            request.setAttribute("mensagemErro",
                    "Um ou mais campos possuem valor inválido.");

            abrirPagina(request, response);
        }
    }

    /**
     * Valida os campos obrigatórios da compra (cliente, medicamento e
     * quantidade) e devolve os erros encontrados. A validação de saldo
     * e de estoque é feita à parte, em comprar(), pois depende dos
     * registros já terem sido localizados no banco.
     */
    private Map<String, String> validarCompra(Cliente cliente,
            Medicamento medicamento, Integer quantidade) {

        Map<String, String> erros = new HashMap<>();

        if (codigoCliente == null) {
            erros.put("cliente", "Informação obrigatória");
        } else if (cliente == null) {
            erros.put("cliente", "Cliente não encontrado");
        }

        if (codigoMedicamento == null) {
            erros.put("medicamento", "Informação obrigatória");
        } else if (medicamento == null) {
            erros.put("medicamento", "Medicamento não encontrado");
        }

        if (quantidadeStr == null || quantidadeStr.isBlank()) {
            erros.put("quantidade", "Informação obrigatória");
        } else if (quantidade == null || quantidade <= 0) {
            erros.put("quantidade", "Informe uma quantidade válida");
        }

        return erros;
    }

    private void comprar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Cliente cliente = codigoCliente != null
                ? objClienteDao.buscarClientePorId(codigoCliente)
                : null;

        Medicamento medicamento = codigoMedicamento != null
                ? objMedicamentoDao.buscarMedicamentoPorId(codigoMedicamento)
                : null;

        Integer quantidade = null;

        if (quantidadeStr != null && !quantidadeStr.isBlank()) {
            try {
                quantidade = Integer.valueOf(quantidadeStr.trim());
            } catch (NumberFormatException e) {
                quantidade = null;
            }
        }

        Map<String, String> erros =
                validarCompra(cliente, medicamento, quantidade);

        if (erros.isEmpty()) {

            if (medicamento.getQuantidadeEstoque() == null
                    || medicamento.getQuantidadeEstoque() < quantidade) {

                erros.put("quantidade", "Estoque insuficiente para essa quantidade");
            }
        }

        Double valorTotal = null;

        if (erros.isEmpty()) {

            valorTotal = medicamento.getPrecoVenda() * quantidade;

            Float saldoAtual = cliente.getCarteira() != null
                    ? cliente.getCarteira() : 0f;

            if (saldoAtual < valorTotal) {
                erros.put("cliente", "Saldo insuficiente na carteira do cliente");
            }
        }

        if (!erros.isEmpty()) {

            request.setAttribute("errosCampo", erros);
            request.setAttribute("mensagemErro",
                    "Não foi possível concluir a compra. Verifique os campos destacados abaixo.");
            request.setAttribute("opcao", "comprar");

            abrirPagina(request, response);
            return;
        }

        //==============================
        // ATUALIZA A CARTEIRA DO CLIENTE
        //==============================
        Float saldoAtual = cliente.getCarteira() != null
                ? cliente.getCarteira() : 0f;

        cliente.setCarteira(saldoAtual - valorTotal.floatValue());

        objClienteDao.alterar(cliente);

        //==============================
        // ATUALIZA O ESTOQUE DO MEDICAMENTO
        //==============================
        medicamento.setQuantidadeEstoque(
                medicamento.getQuantidadeEstoque() - quantidade);

        objMedicamentoDao.alterar(medicamento);

        request.setAttribute("mensagem",
                "Compra realizada com sucesso! "
                + quantidade + "x " + medicamento.getNome()
                + " para " + cliente.getNome()
                + " — valor total: R$ " + String.format("%.2f", valorTotal)
                + ". Novo saldo da carteira: R$ "
                + String.format("%.2f", cliente.getCarteira()));

        cancelar(request, response);
    }

    private void cancelar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        codigoCliente = null;
        codigoMedicamento = null;
        quantidadeStr = "";

        request.setAttribute("opcao", "comprar");

        abrirPagina(request, response);
    }

    private void abrirPagina(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("listaClientes",
                objClienteDao.buscarTodosClientes());

        request.setAttribute("listaMedicamentos",
                objMedicamentoDao.buscarTodosMedicamentos());

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/CompraRemedio.jsp");

        dispatcher.forward(request, response);
    }
}
