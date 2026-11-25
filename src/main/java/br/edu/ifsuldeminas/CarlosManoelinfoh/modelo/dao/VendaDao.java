package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.ItemCarrinho;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Grava o histórico de vendas (tabelas venda e itemVenda) usado pelos relatórios.
 */
public class VendaDao {

    /** Registra uma venda de um único item. codigoFuncionario e codigoFormaPagamento podem ser null. */
    public void registrar(Integer codigoCliente, Integer codigoFuncionario, Integer codigoFormaPagamento,
            Integer codigoMedicamento, int quantidade, double precoUnitario, double percentualDesconto) {

        double total = Math.round(quantidade * precoUnitario * (1 - percentualDesconto / 100.0) * 100.0) / 100.0;

        try (Connection con = ConnectionFactory.getIntance().getConnetion()) {
            con.setAutoCommit(false);
            try (PreparedStatement pv = con.prepareStatement(
                    "INSERT INTO venda (dataVenda, codigoCliente, codigoFuncionario, codigoFormaPagamento, percentualDesconto, valorTotal) VALUES (NOW(),?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                pv.setInt(1, codigoCliente);
                if (codigoFuncionario == null) {
                    pv.setNull(2, java.sql.Types.INTEGER);
                } else {
                    pv.setInt(2, codigoFuncionario);
                }
                if (codigoFormaPagamento == null) {
                    pv.setNull(3, java.sql.Types.INTEGER);
                } else {
                    pv.setInt(3, codigoFormaPagamento);
                }
                pv.setDouble(4, percentualDesconto);
                pv.setDouble(5, total);
                pv.executeUpdate();
                int codigoVenda;
                try (ResultSet ks = pv.getGeneratedKeys()) {
                    ks.next();
                    codigoVenda = ks.getInt(1);
                }
                try (PreparedStatement pi = con.prepareStatement(
                        "INSERT INTO itemVenda (codigoVenda, codigoMedicamento, quantidade, precoUnitario) VALUES (?,?,?,?)")) {
                    pi.setInt(1, codigoVenda);
                    pi.setInt(2, codigoMedicamento);
                    pi.setInt(3, quantidade);
                    pi.setDouble(4, precoUnitario);
                    pi.executeUpdate();
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar a venda", e);
        }
    }

    /** Registra UMA venda com vários itens (carrinho). O desconto do convênio vale para todos os itens. */
    public void registrarItens(Integer codigoCliente, Integer codigoFuncionario, Integer codigoFormaPagamento,
            List<ItemCarrinho> itens, double percentualDesconto) {

        double bruto = 0;
        for (ItemCarrinho it : itens) {
            bruto += it.getQuantidade() * it.getPrecoUnitario();
        }
        double total = Math.round(bruto * (1 - percentualDesconto / 100.0) * 100.0) / 100.0;

        try (Connection con = ConnectionFactory.getIntance().getConnetion()) {
            con.setAutoCommit(false);
            try (PreparedStatement pv = con.prepareStatement(
                    "INSERT INTO venda (dataVenda, codigoCliente, codigoFuncionario, codigoFormaPagamento, percentualDesconto, valorTotal) VALUES (NOW(),?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                pv.setInt(1, codigoCliente);
                if (codigoFuncionario == null) {
                    pv.setNull(2, java.sql.Types.INTEGER);
                } else {
                    pv.setInt(2, codigoFuncionario);
                }
                if (codigoFormaPagamento == null) {
                    pv.setNull(3, java.sql.Types.INTEGER);
                } else {
                    pv.setInt(3, codigoFormaPagamento);
                }
                pv.setDouble(4, percentualDesconto);
                pv.setDouble(5, total);
                pv.executeUpdate();
                int codigoVenda;
                try (ResultSet ks = pv.getGeneratedKeys()) {
                    ks.next();
                    codigoVenda = ks.getInt(1);
                }
                try (PreparedStatement pi = con.prepareStatement(
                        "INSERT INTO itemVenda (codigoVenda, codigoMedicamento, quantidade, precoUnitario) VALUES (?,?,?,?)")) {
                    for (ItemCarrinho it : itens) {
                        pi.setInt(1, codigoVenda);
                        pi.setInt(2, it.getCodigoMedicamento());
                        pi.setInt(3, it.getQuantidade());
                        pi.setDouble(4, it.getPrecoUnitario());
                        pi.addBatch();
                    }
                    pi.executeBatch();
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar a venda", e);
        }
    }
}
