package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

import java.util.Calendar;

/**
 * Representa um usuário do sistema (login/sessão).
 * A senha nunca é mantida em texto puro: apenas o hash gerado pelo BCrypt
 * é armazenado em {@link #senhaHash}.
 */
public class Usuario {

    private Integer id;
    private String nomeUsuario;
    private String senhaHash;
    private String email;

    // Usados apenas pela funcionalidade "Esqueci minha senha".
    private String token;
    private Calendar tokenExpiracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Calendar getTokenExpiracao() {
        return tokenExpiracao;
    }

    public void setTokenExpiracao(Calendar tokenExpiracao) {
        this.tokenExpiracao = tokenExpiracao;
    }
}
