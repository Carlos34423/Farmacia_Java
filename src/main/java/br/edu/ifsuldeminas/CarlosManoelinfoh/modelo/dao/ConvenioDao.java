package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Convenio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConvenioDao extends GenericoDAO<Convenio> {

    public void salvar(Convenio objConvenio) {
        String sql = "INSERT INTO convenio(nome,percentualDesconto) VALUES(?,?)";
        save(sql, objConvenio.getNome(), objConvenio.getPercentual());
    }

    public void alterar(Convenio objConvenio) {
        String sql = "UPDATE convenio SET nome=?,percentualDesconto=? WHERE codigoConvenio=?";
        save(sql, objConvenio.getNome(), objConvenio.getPercentual(), objConvenio.getCodigoConvenio());
    }

    public void excluir(Convenio objConvenio) {
        String sql = "DELETE FROM convenio WHERE codigoConvenio=?";
        save(sql, objConvenio.getCodigoConvenio());
    }

    private static class ConvenioRowMapper implements RowMapper<Convenio> {

        @Override
        public Convenio mapRow(ResultSet rs) throws SQLException {
            Convenio objConvenio = new Convenio();
            objConvenio.setCodigoConvenio(rs.getInt("codigoConvenio"));
            objConvenio.setNome(rs.getString("nome"));
            objConvenio.setPercentual(rs.getDouble("percentualDesconto"));
            return objConvenio;
        }
    }

    public List<Convenio> buscarTodosConvenios() {
        String sql = "SELECT * FROM convenio ORDER BY codigoConvenio";
        return buscarTodos(sql, new ConvenioRowMapper());
    }

    public Convenio buscarConvenioPorId(int id) {
        String sql = "SELECT * FROM convenio WHERE codigoConvenio = ?";
        return buscarPorId(sql, new ConvenioRowMapper(), id);
    }
}
