package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.CategoriaMedicamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriaMedicamentoDao extends GenericoDAO<CategoriaMedicamento> {

    public void salvar(CategoriaMedicamento objCategoriaMedicamento) {
        String sql = "INSERT INTO categoriaMedicamento(nome,descricao) VALUES(?,?)";
        save(sql, objCategoriaMedicamento.getNome(), objCategoriaMedicamento.getDescricao());
    }

    public void alterar(CategoriaMedicamento objCategoriaMedicamento) {
        String sql = "UPDATE categoriaMedicamento SET nome=?,descricao=? WHERE codigoCategoriaMedicamento=?";
        save(sql, objCategoriaMedicamento.getNome(), objCategoriaMedicamento.getDescricao(),
                objCategoriaMedicamento.getCodigoCategoriaMedicamento());
    }

    public void excluir(CategoriaMedicamento objCategoriaMedicamento) {
        String sql = "DELETE FROM categoriaMedicamento WHERE codigoCategoriaMedicamento=?";
        save(sql, objCategoriaMedicamento.getCodigoCategoriaMedicamento());
    }

    private static class CategoriaMedicamentoRowMapper implements RowMapper<CategoriaMedicamento> {

        @Override
        public CategoriaMedicamento mapRow(ResultSet rs) throws SQLException {
            CategoriaMedicamento objCategoriaMedicamento = new CategoriaMedicamento();
            objCategoriaMedicamento.setCodigoCategoriaMedicamento(rs.getInt("codigoCategoriaMedicamento"));
            objCategoriaMedicamento.setNome(rs.getString("nome"));
            objCategoriaMedicamento.setDescricao(rs.getString("descricao"));
            return objCategoriaMedicamento;
        }
    }

    public List<CategoriaMedicamento> buscarTodasCategoriasMedicamento() {
        String sql = "SELECT * FROM categoriaMedicamento ORDER BY codigoCategoriaMedicamento";
        return buscarTodos(sql, new CategoriaMedicamentoRowMapper());
    }

    public CategoriaMedicamento buscarCategoriaMedicamentoPorId(int id) {
        String sql = "SELECT * FROM categoriaMedicamento WHERE codigoCategoriaMedicamento = ?";
        return buscarPorId(sql, new CategoriaMedicamentoRowMapper(), id);
    }
}
