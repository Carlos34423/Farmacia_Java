/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Medicamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hatak
 */


public class MedicamentoDao extends GenericoDAO<Medicamento> {

    public void salvar(Medicamento objMedicamento) {

        String sql =
            "INSERT INTO medicamento (nome,descricao,precoVenda,quantidadeEstoque,codigoCategoriaMedicamento,cnpjFabricante,codigoFornecedor) VALUES(?,?,?,?,?,?,?)";

        save(sql,
                objMedicamento.getNome(),
                objMedicamento.getDescricao(),
                objMedicamento.getPrecoVenda(),
                objMedicamento.getQuantidadeEstoque(),
                objMedicamento.getCategoriaMedicamento().getCodigoCategoriaMedicamento(),
                objMedicamento.getFabricante().getCnpj(),
                objMedicamento.getFornecedor().getCodigoFornecedor());
    }

    public void alterar(Medicamento objMedicamento) {

        String sql =
            "UPDATE medicamento SET nome=?, descricao=?, precoVenda=?, quantidadeEstoque=?, codigoCategoriaMedicamento=?, cnpjFabricante=?, codigoFornecedor=? WHERE codigoMedicamento=?";

        save(sql,
                objMedicamento.getNome(),
                objMedicamento.getDescricao(),
                objMedicamento.getPrecoVenda(),
                objMedicamento.getQuantidadeEstoque(),
                objMedicamento.getCategoriaMedicamento().getCodigoCategoriaMedicamento(),
                objMedicamento.getFabricante().getCnpj(),
                objMedicamento.getFornecedor().getCodigoFornecedor(),
                objMedicamento.getCodigoMedicamento());
    }

    public void excluir(Integer codigoMedicamento) {

        String sql =
            "DELETE FROM medicamento WHERE codigoMedicamento=?";

        save(sql, codigoMedicamento);
    }

    private static class MedicamentoRowMapper implements RowMapper<Medicamento> {

        CategoriaMedicamentoDao categoriaDao =
                new CategoriaMedicamentoDao();

        FabricanteDao fabricanteDao =
                new FabricanteDao();

        FornecedorDao fornecedorDao =
                new FornecedorDao();

        @Override
        public Medicamento mapRow(ResultSet rs) throws SQLException {

            Medicamento objMedicamento =
                    new Medicamento();

            objMedicamento.setCodigoMedicamento(
                    rs.getInt("codigoMedicamento"));

            objMedicamento.setNome(
                    rs.getString("nome"));

            objMedicamento.setDescricao(
                    rs.getString("descricao"));

            objMedicamento.setPrecoVenda(
                    rs.getDouble("precoVenda"));

            objMedicamento.setQuantidadeEstoque(
                    rs.getInt("quantidadeEstoque"));

            objMedicamento.setCategoriaMedicamento(
                    categoriaDao.buscarCategoriaMedicamentoPorId(
                            rs.getInt("codigoCategoriaMedicamento")));

objMedicamento.setFabricante(
        fabricanteDao.buscarFabricantePorCnpj(
                rs.getString("cnpjFabricante")));

            objMedicamento.setFornecedor(
                    fornecedorDao.buscarFornecedorPorId(
                            rs.getInt("codigoFornecedor")));

            return objMedicamento;
        }
    }

    public List<Medicamento> buscarTodosMedicamentos() {

        String sql =
            "SELECT * FROM medicamento ORDER BY codigoMedicamento";

        return buscarTodos(sql,
                new MedicamentoRowMapper());
    }

    public Medicamento buscarMedicamentoPorId(Integer codigoMedicamento) {

        String sql =
            "SELECT * FROM medicamento WHERE codigoMedicamento=?";

        return buscarPorId(sql,
                new MedicamentoRowMapper(),
                codigoMedicamento);
    }
}
