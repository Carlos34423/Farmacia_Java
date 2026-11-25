package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ItemVendaDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.ItemVenda;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/ItemVendaControlador")
public class ItemVendaControlador extends HttpServlet {

    private final ItemVendaDao itemVendaDao = new ItemVendaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String filtro = request.getParameter("codigoVenda");
        List<ItemVenda> itens;

        if (filtro != null && !filtro.trim().isEmpty()) {
            try {
                int codigoVenda = Integer.parseInt(filtro.trim());
                itens = itemVendaDao.buscarPorVenda(codigoVenda);
                request.setAttribute("filtroVenda", codigoVenda);
            } catch (NumberFormatException e) {
                request.setAttribute("mensagemErro", "Código de venda inválido.");
                itens = itemVendaDao.buscarTodos();
            }
        } else {
            itens = itemVendaDao.buscarTodos();
        }

        double totalGeral = 0;
        double totalBruto = 0;
        int totalQuantidade = 0;
        for (ItemVenda item : itens) {
            totalBruto += item.getSubtotal();
            totalGeral += item.getValorFinal();
            totalQuantidade += item.getQuantidade();
        }

        request.setAttribute("itens", itens);
        request.setAttribute("totalGeral", totalGeral);
        request.setAttribute("totalBruto", totalBruto);
        request.setAttribute("totalQuantidade", totalQuantidade);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/ItensVenda.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
