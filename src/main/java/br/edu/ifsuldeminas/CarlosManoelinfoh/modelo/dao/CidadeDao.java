/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;


import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cidade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author Tulio Dias
 */
public class CidadeDao extends GenericoDAO<Cidade>{
    
    public void salvar(Cidade objCidade){
        String sql = "INSERT INTO CIDADE(NOME,UF) VALUES(?,?)";
        save(sql, objCidade.getNome(), objCidade.getUf());
    }
    public void alterar(Cidade objCidade){
        String sql = "UPDATE CIDADE SET NOME=?,UF=? WHERE codigoCidade=?";
        save(sql, objCidade.getNome(), objCidade.getUf(), objCidade.getCodigoCidade());
        
    }
    public void excluir(Cidade objCidade){
        String sql = "DELETE FROM CIDADE WHERE codigoCidade=?";
        save(sql, objCidade.getCodigoCidade());
    }
    
    private static class CidadeRowMapper implements RowMapper<Cidade>{

        @Override
        public Cidade mapRow(ResultSet rs) throws SQLException {
            Cidade objCidade = new Cidade();
            objCidade.setCodigoCidade(rs.getInt("codigoCidade"));
            objCidade.setNome(rs.getString("nome"));
            objCidade.setUf(rs.getString("uf"));
           
            return objCidade;
        }
        
    }
    
    public List<Cidade> buscarTodasCidades(){
        String sql = "SELECT * FROM CIDADE";
        return buscarTodos(sql, new CidadeRowMapper());
    }
    
    public Cidade buscarCidadePorId(int id){
        String sql = "SELECT * FROM CIDADE WHERE codigoCidade = ?";
        return buscarPorId(sql, new CidadeRowMapper(), id);
    }
    
    
}
