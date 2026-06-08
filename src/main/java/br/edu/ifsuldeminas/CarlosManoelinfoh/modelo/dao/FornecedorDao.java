package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Fornecedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FornecedorDao extends GenericoDAO<Fornecedor> {

    public void salvar(Fornecedor objFornecedor) {
        String sql = "INSERT INTO fornecedor(razaoSocial,nomeFantasia,cnpj,telefone,email,cidade) VALUES(?,?,?,?,?,?)";
        save(sql, objFornecedor.getRazaoSocial(), objFornecedor.getNomeFantasia(),
                objFornecedor.getCnpj(), objFornecedor.getTelefone(),
                objFornecedor.getEmail(), objFornecedor.getCidade());
    }

    public void alterar(Fornecedor objFornecedor) {
        String sql = "UPDATE fornecedor SET razaoSocial=?,nomeFantasia=?,cnpj=?,telefone=?,email=?,cidade=? WHERE cnpj=?";
        save(sql, objFornecedor.getRazaoSocial(), objFornecedor.getNomeFantasia(),
                objFornecedor.getCnpj(), objFornecedor.getTelefone(),
                objFornecedor.getEmail(), objFornecedor.getCidade(),
                objFornecedor.getCnpjOriginal());
    }

    public void excluir(Fornecedor objFornecedor) {
        String sql = "DELETE FROM fornecedor WHERE cnpj=?";
        save(sql, objFornecedor.getCnpj());
    }

    private static class FornecedorRowMapper implements RowMapper<Fornecedor> {

        @Override
        public Fornecedor mapRow(ResultSet rs) throws SQLException {
            Fornecedor objFornecedor = new Fornecedor();
            objFornecedor.setCodigoFornecedor(rs.getInt("codigoFornecedor"));
            objFornecedor.setRazaoSocial(rs.getString("razaoSocial"));
            objFornecedor.setNomeFantasia(rs.getString("nomeFantasia"));
            objFornecedor.setCnpj(rs.getString("cnpj"));
            objFornecedor.setTelefone(rs.getString("telefone"));
            objFornecedor.setEmail(rs.getString("email"));
            objFornecedor.setCidade(rs.getString("cidade"));

            return objFornecedor;
        }
    }

    public List<Fornecedor> buscarTodosFornecedores() {
        String sql = "SELECT * FROM fornecedor ORDER BY codigoFornecedor";
        return buscarTodos(sql, new FornecedorRowMapper());
    }

    public Fornecedor buscarFornecedorPorCnpj(String cnpj) {
        String sql = "SELECT * FROM fornecedor WHERE cnpj = ?";
        return buscarPorId(sql, new FornecedorRowMapper(), cnpj);
    }
}
