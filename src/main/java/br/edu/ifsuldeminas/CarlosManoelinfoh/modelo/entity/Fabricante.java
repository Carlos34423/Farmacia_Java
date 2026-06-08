package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

public class Fabricante {
    private String nome;
    private String cnpj;
    private String cnpjOriginal;
    private String telefone;
    private String email;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpjOriginal() {
        return cnpjOriginal;
    }

    public void setCnpjOriginal(String cnpjOriginal) {
        this.cnpjOriginal = cnpjOriginal;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void salvar(Fabricante objFabricante) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
