package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.FormaPagamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FormaPagamentoDao extends GenericoDAO<FormaPagamento> {

    public void salvar(FormaPagamento objFormaPagamento) {
        String sql = "INSERT INTO formaPagamento(descricao) VALUES(?)";
        save(sql, objFormaPagamento.getDescricao());
    }

    public void alterar(FormaPagamento objFormaPagamento) {
        String sql = "UPDATE formaPagamento SET descricao=? WHERE codigoFormaPagamento=?";
        save(sql, objFormaPagamento.getDescricao(), objFormaPagamento.getCodigoFormaPagamento());
    }

    public void excluir(FormaPagamento objFormaPagamento) {
        String sql = "DELETE FROM formaPagamento WHERE codigoFormaPagamento=?";
        save(sql, objFormaPagamento.getCodigoFormaPagamento());
    }

    private static class FormaPagamentoRowMapper implements RowMapper<FormaPagamento> {

        @Override
        public FormaPagamento mapRow(ResultSet rs) throws SQLException {
            FormaPagamento objFormaPagamento = new FormaPagamento();
            objFormaPagamento.setCodigoFormaPagamento(rs.getInt("codigoFormaPagamento"));
            objFormaPagamento.setDescricao(rs.getString("descricao"));
            return objFormaPagamento;
        }
    }

    public List<FormaPagamento> buscarTodasFormasPagamento() {
        String sql = "SELECT * FROM formaPagamento ORDER BY codigoFormaPagamento";
        return buscarTodos(sql, new FormaPagamentoRowMapper());
    }

    public FormaPagamento buscarFormaPagamentoPorId(int id) {
        String sql = "SELECT * FROM formaPagamento WHERE codigoFormaPagamento = ?";
        return buscarPorId(sql, new FormaPagamentoRowMapper(), id);
    }
}
