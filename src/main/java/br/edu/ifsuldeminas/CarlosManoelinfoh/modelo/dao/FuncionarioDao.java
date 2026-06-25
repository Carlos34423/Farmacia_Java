/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Funcionario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hatak
 */


public class FuncionarioDao extends GenericoDAO<Funcionario> {

    public void salvar(Funcionario objFuncionario) {

        String sql =
            "INSERT INTO funcionario (nome,cpf,telefone,email,codigoCargo,codigoCidade) VALUES(?,?,?,?,?,?)";

        save(sql,
                objFuncionario.getNome(),
                objFuncionario.getCpf(),
                objFuncionario.getTelefone(),
                objFuncionario.getEmail(),
                objFuncionario.getCargo().getCodigoCargo(),
                objFuncionario.getCidade().getCodigoCidade());
    }

    public void alterar(Funcionario objFuncionario) {

        String sql =
            "UPDATE funcionario SET nome=?, cpf=?, telefone=?, email=?, codigoCargo=?, codigoCidade=? WHERE codigoFuncionario=?";

        save(sql,
                objFuncionario.getNome(),
                objFuncionario.getCpf(),
                objFuncionario.getTelefone(),
                objFuncionario.getEmail(),
                objFuncionario.getCargo().getCodigoCargo(),
                objFuncionario.getCidade().getCodigoCidade(),
                objFuncionario.getCodigoFuncionario());
    }

    public void excluir(Integer codigoFuncionario) {

        String sql =
            "DELETE FROM funcionario WHERE codigoFuncionario=?";

        save(sql, codigoFuncionario);
    }

    private static class FuncionarioRowMapper implements RowMapper<Funcionario> {

        CargoDao cargoDao = new CargoDao();
        CidadeDao cidadeDao = new CidadeDao();

        @Override
        public Funcionario mapRow(ResultSet rs) throws SQLException {

            Funcionario objFuncionario = new Funcionario();

            objFuncionario.setCodigoFuncionario(
                    rs.getInt("codigoFuncionario"));

            objFuncionario.setNome(
                    rs.getString("nome"));

            objFuncionario.setCpf(
                    rs.getString("cpf"));

            objFuncionario.setTelefone(
                    rs.getString("telefone"));

            objFuncionario.setEmail(
                    rs.getString("email"));

            objFuncionario.setCargo(
                    cargoDao.buscarCargoPorId(
                            rs.getInt("codigoCargo")));

            objFuncionario.setCidade(
                    cidadeDao.buscarCidadePorId(
                            rs.getInt("codigoCidade")));

            return objFuncionario;
        }
    }

    public List<Funcionario> buscarTodosFuncionarios() {

        String sql =
            "SELECT * FROM funcionario ORDER BY codigoFuncionario";

        return buscarTodos(sql, new FuncionarioRowMapper());
    }

    public Funcionario buscarFuncionarioPorId(Integer codigoFuncionario) {

        String sql =
            "SELECT * FROM funcionario WHERE codigoFuncionario=?";

        return buscarPorId(sql,
                new FuncionarioRowMapper(),
                codigoFuncionario);
    }
}