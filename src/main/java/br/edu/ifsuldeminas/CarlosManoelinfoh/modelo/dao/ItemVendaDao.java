package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.ItemVenda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Consulta (somente leitura) dos itens de venda.
 */
public class ItemVendaDao extends GenericoDAO<ItemVenda> {

    private static final String SQL_BASE
            = "SELECT i.codigoItemVenda, i.codigoVenda, i.codigoMedicamento, i.quantidade, i.precoUnitario, "
            + "DATE_FORMAT(v.dataVenda, '%d/%m/%Y %H:%i') AS dataVenda, "
            + "c.nome AS nomeCliente, m.nome AS nomeMedicamento, "
            + "(i.quantidade * i.precoUnitario) AS subtotal, "
            + "v.percentualDesconto AS percentualDesconto, "
            + "ROUND(i.quantidade * i.precoUnitario * (1 - v.percentualDesconto / 100), 2) AS valorFinal "
            + "FROM itemVenda i "
            + "JOIN venda v ON v.codigoVenda = i.codigoVenda "
            + "LEFT JOIN cliente c ON c.codigoCliente = v.codigoCliente "
            + "LEFT JOIN medicamento m ON m.codigoMedicamento = i.codigoMedicamento ";

    private static class ItemVendaRowMapper implements RowMapper<ItemVenda> {

        @Override
        public ItemVenda mapRow(ResultSet rs) throws SQLException {
            ItemVenda item = new ItemVenda();
            item.setCodigoItemVenda(rs.getInt("codigoItemVenda"));
            item.setCodigoVenda(rs.getInt("codigoVenda"));
            item.setCodigoMedicamento(rs.getInt("codigoMedicamento"));
            item.setQuantidade(rs.getInt("quantidade"));
            item.setPrecoUnitario(rs.getDouble("precoUnitario"));
            item.setDataVenda(rs.getString("dataVenda"));
            item.setNomeCliente(rs.getString("nomeCliente"));
            item.setNomeMedicamento(rs.getString("nomeMedicamento"));
            item.setSubtotal(rs.getDouble("subtotal"));
            item.setPercentualDesconto(rs.getDouble("percentualDesconto"));
            item.setValorFinal(rs.getDouble("valorFinal"));
            return item;
        }
    }

    public List<ItemVenda> buscarTodos() {
        return buscarTodos(SQL_BASE + "ORDER BY v.dataVenda DESC, i.codigoItemVenda DESC", new ItemVendaRowMapper());
    }

    public List<ItemVenda> buscarPorVenda(int codigoVenda) {
        // codigoVenda é int (já validado no controlador), então a concatenação é segura
        return buscarTodos(SQL_BASE + "WHERE i.codigoVenda = " + codigoVenda + " ORDER BY i.codigoItemVenda",
                new ItemVendaRowMapper());
    }
}
