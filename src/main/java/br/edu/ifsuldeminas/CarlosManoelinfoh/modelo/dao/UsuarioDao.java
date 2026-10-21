package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * DAO do usuário do sistema. Segue exatamente o mesmo padrão do restante do
 * projeto (extende {@link GenericoDAO}, reaproveitando save/buscarPorId
 * já existentes, sem nenhum método novo na classe genérica).
 */
public class UsuarioDao extends GenericoDAO<Usuario> {

    public void cadastrar(Usuario objUsuario) {

        String sql =
            "INSERT INTO usuario (nomeusuario,senhahash,email) VALUES(?,?,?)";

        save(sql,
                objUsuario.getNomeUsuario(),
                objUsuario.getSenhaHash(),
                objUsuario.getEmail());
    }

    public void atualizarSenha(Integer id, String novaSenhaHash) {

        String sql =
            "UPDATE usuario SET senhahash=? WHERE id=?";

        save(sql, novaSenhaHash, id);
    }

    public void salvarTokenRecuperacao(Integer id, String token, Calendar tokenExpiracao) {

        String sql =
            "UPDATE usuario SET token=?, tokenexpiracao=? WHERE id=?";

        save(sql, token, tokenExpiracao, id);
    }

    public void invalidarToken(Integer id) {

        String sql =
            "UPDATE usuario SET token=NULL, tokenexpiracao=NULL WHERE id=?";

        save(sql, id);
    }

    private static class UsuarioRowMapper implements RowMapper<Usuario> {

        @Override
        public Usuario mapRow(ResultSet rs) throws SQLException {

            Usuario objUsuario = new Usuario();

            objUsuario.setId(rs.getInt("id"));
            objUsuario.setNomeUsuario(rs.getString("nomeusuario"));
            objUsuario.setSenhaHash(rs.getString("senhahash"));
            objUsuario.setEmail(rs.getString("email"));

            return objUsuario;
        }
    }

    private static class UsuarioRecuperacaoRowMapper implements RowMapper<Usuario> {

        @Override
        public Usuario mapRow(ResultSet rs) throws SQLException {

            Usuario objUsuario = new Usuario();

            objUsuario.setId(rs.getInt("id"));
            objUsuario.setNomeUsuario(rs.getString("nomeusuario"));
            objUsuario.setSenhaHash(rs.getString("senhahash"));
            objUsuario.setEmail(rs.getString("email"));
            objUsuario.setToken(rs.getString("token"));

            Timestamp tokenExpiracao = rs.getTimestamp("tokenexpiracao");
            if (tokenExpiracao != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(tokenExpiracao.getTime());
                objUsuario.setTokenExpiracao(calendar);
            }

            return objUsuario;
        }
    }

    public Usuario buscarPorNomeUsuario(String nomeUsuario) {

        String sql =
            "SELECT id,nomeusuario,senhahash,email FROM usuario WHERE nomeusuario=?";

        return buscarPorId(sql,
                new UsuarioRowMapper(),
                nomeUsuario);
    }

    public Usuario buscarPorEmail(String email) {

        String sql =
            "SELECT id,nomeusuario,senhahash,email FROM usuario WHERE email=?";

        return buscarPorId(sql,
                new UsuarioRowMapper(),
                email);
    }

    public Usuario buscarPorToken(String token) {

        String sql =
            "SELECT * FROM usuario WHERE token=?";

        return buscarPorId(sql,
                new UsuarioRecuperacaoRowMapper(),
                token);
    }
}
