/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.CategoriaMedicamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hatak
 */
public class CategoriaMedicamentoDao extends GenericoDAO<CategoriaMedicamento> {

    public void salvar(CategoriaMedicamento objCategoriaMedicamento) {

        String sql =
                "INSERT INTO CategoriaMedicamento(nome,descricao) VALUES(?,?)";

        save(sql,
                objCategoriaMedicamento.getNome(),
                objCategoriaMedicamento.getDescricao());
    }

    public void alterar(CategoriaMedicamento objCategoriaMedicamento) {

        String sql =
                "UPDATE CategoriaMedicamento SET nome=?, descricao=? WHERE codigoCategoriaMedicamento=?";

        save(sql,
                objCategoriaMedicamento.getNome(),
                objCategoriaMedicamento.getDescricao(),
                objCategoriaMedicamento.getCodigoCategoriaMedicamento());
    }

    public void excluir(Integer codigoCategoriaMedicamento) {

        String sql =
                "DELETE FROM CategoriaMedicamento WHERE codigoCategoriaMedicamento=?";

        save(sql, codigoCategoriaMedicamento);
    }

    private static class CategoriaMedicamentoRowMapper
            implements RowMapper<CategoriaMedicamento> {

        @Override
        public CategoriaMedicamento mapRow(ResultSet rs)
                throws SQLException {

            CategoriaMedicamento objCategoriaMedicamento =
                    new CategoriaMedicamento();

            objCategoriaMedicamento.setCodigoCategoriaMedicamento(
                    rs.getInt("codigoCategoriaMedicamento"));

            objCategoriaMedicamento.setNome(
                    rs.getString("nome"));

            objCategoriaMedicamento.setDescricao(
                    rs.getString("descricao"));

            return objCategoriaMedicamento;
        }
    }

    public List<CategoriaMedicamento> buscarTodasCategoriasMedicamentos() {

        String sql =
                "SELECT * FROM CategoriaMedicamento ORDER BY codigoCategoriaMedicamento";

        return buscarTodos(sql,
                new CategoriaMedicamentoRowMapper());
    }

    public CategoriaMedicamento buscarCategoriaMedicamentoPorId(
            Integer codigoCategoriaMedicamento) {

        String sql =
                "SELECT * FROM CategoriaMedicamento WHERE codigoCategoriaMedicamento=?";

        return buscarPorId(sql,
                new CategoriaMedicamentoRowMapper(),
                codigoCategoriaMedicamento);
    }
}
