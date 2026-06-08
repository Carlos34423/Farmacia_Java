package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cargo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CargoDao extends GenericoDAO<Cargo> {

    public void salvar(Cargo objCargo) {
        String sql = "INSERT INTO cargo(nome,salarioBase) VALUES(?,?)";
        save(sql, objCargo.getNome(), objCargo.getSalarioBase());
    }

    public void alterar(Cargo objCargo) {
        String sql = "UPDATE cargo SET nome=?,salarioBase=? WHERE codigoCargo=?";
        save(sql, objCargo.getNome(), objCargo.getSalarioBase(), objCargo.getCodigoCargo());
    }

    public void excluir(Cargo objCargo) {
        String sql = "DELETE FROM cargo WHERE codigoCargo=?";
        save(sql, objCargo.getCodigoCargo());
    }

    private static class CargoRowMapper implements RowMapper<Cargo> {

        @Override
        public Cargo mapRow(ResultSet rs) throws SQLException {
            Cargo objCargo = new Cargo();
            objCargo.setCodigoCargo(rs.getInt("codigoCargo"));
            objCargo.setNome(rs.getString("nome"));
            objCargo.setSalarioBase(rs.getDouble("salarioBase"));
            return objCargo;
        }
    }

    public List<Cargo> buscarTodosCargos() {
        String sql = "SELECT * FROM cargo ORDER BY codigoCargo";
        return buscarTodos(sql, new CargoRowMapper());
    }

    public Cargo buscarCargoPorId(int id) {
        String sql = "SELECT * FROM cargo WHERE codigoCargo = ?";
        return buscarPorId(sql, new CargoRowMapper(), id);
    }
}
