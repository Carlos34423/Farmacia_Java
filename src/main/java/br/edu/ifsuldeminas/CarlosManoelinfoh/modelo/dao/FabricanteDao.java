package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Fabricante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FabricanteDao extends GenericoDAO<Fabricante> {

    public void salvar(Fabricante objFabricante) {
        String sql = "INSERT INTO fabricante(nome,cnpj,telefone,email) VALUES(?,?,?,?)";
        save(sql, objFabricante.getNome(), objFabricante.getCnpj(),
                objFabricante.getTelefone(), objFabricante.getEmail());
    }

    public void alterar(Fabricante objFabricante) {
        String sql = "UPDATE fabricante SET nome=?,cnpj=?,telefone=?,email=? WHERE cnpj=?";
        save(sql, objFabricante.getNome(), objFabricante.getCnpj(),
                objFabricante.getTelefone(), objFabricante.getEmail(), objFabricante.getCnpjOriginal());
    }

    public void excluir(Fabricante objFabricante) {
        String sql = "DELETE FROM fabricante WHERE cnpj=?";
        save(sql, objFabricante.getCnpj());
    }

    private static class FabricanteRowMapper implements RowMapper<Fabricante> {

        @Override
        public Fabricante mapRow(ResultSet rs) throws SQLException {
            Fabricante objFabricante = new Fabricante();
            objFabricante.setNome(rs.getString("nome"));
            objFabricante.setCnpj(rs.getString("cnpj"));
            objFabricante.setTelefone(rs.getString("telefone"));
            objFabricante.setEmail(rs.getString("email"));
            return objFabricante;
        }
    }

    public List<Fabricante> buscarTodosFabricantes() {
        String sql = "SELECT * FROM fabricante ORDER BY nome";
        return buscarTodos(sql, new FabricanteRowMapper());
    }

    public Fabricante buscarFabricantePorCnpj(String cnpj) {
        String sql = "SELECT * FROM fabricante WHERE cnpj = ?";
        return buscarPorId(sql, new FabricanteRowMapper(), cnpj);
    }
}
