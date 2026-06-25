/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hatak
 */




public class ClienteDao extends GenericoDAO<Cliente> {

    public void salvar(Cliente objCliente) {

        String sql =
            "INSERT INTO cliente (nome,cpf,telefone,email,codigoCidade,codigoConvenio) VALUES(?,?,?,?,?,?)";

        save(sql,
                objCliente.getNome(),
                objCliente.getCpf(),
                objCliente.getTelefone(),
                objCliente.getEmail(),
                objCliente.getCidade().getCodigoCidade(),
                objCliente.getConvenio().getCodigoConvenio());
    }

    public void alterar(Cliente objCliente) {

        String sql =
            "UPDATE cliente SET nome=?, cpf=?, telefone=?, email=?, codigoCidade=?, codigoConvenio=? WHERE codigoCliente=?";

        save(sql,
                objCliente.getNome(),
                objCliente.getCpf(),
                objCliente.getTelefone(),
                objCliente.getEmail(),
                objCliente.getCidade().getCodigoCidade(),
                objCliente.getConvenio().getCodigoConvenio(),
                objCliente.getCodigoCliente());
    }

    public void excluir(Integer codigoCliente) {

        String sql =
            "DELETE FROM cliente WHERE codigoCliente=?";

        save(sql, codigoCliente);
    }

    private static class ClienteRowMapper implements RowMapper<Cliente> {

        CidadeDao cidadeDao = new CidadeDao();
        ConvenioDao convenioDao = new ConvenioDao();

        @Override
        public Cliente mapRow(ResultSet rs) throws SQLException {

            Cliente objCliente = new Cliente();

            objCliente.setCodigoCliente(rs.getInt("codigoCliente"));
            objCliente.setNome(rs.getString("nome"));
            objCliente.setCpf(rs.getString("cpf"));
            objCliente.setTelefone(rs.getString("telefone"));
            objCliente.setEmail(rs.getString("email"));

            objCliente.setCidade(
                    cidadeDao.buscarCidadePorId(
                            rs.getInt("codigoCidade")));

            objCliente.setConvenio(
                    convenioDao.buscarConvenioPorId(
                            rs.getInt("codigoConvenio")));

            return objCliente;
        }
    }

    public List<Cliente> buscarTodosClientes() {

        String sql =
                "SELECT * FROM cliente ORDER BY codigoCliente";

        return buscarTodos(sql, new ClienteRowMapper());
    }

    public Cliente buscarClientePorId(Integer codigoCliente) {

        String sql =
                "SELECT * FROM cliente WHERE codigoCliente=?";

        return buscarPorId(sql,
                new ClienteRowMapper(),
                codigoCliente);
    }
}