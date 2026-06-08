package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

public class Fornecedor {
    private Integer codigoFornecedor;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String cnpjOriginal;
    private String telefone;
    private String email;
    private String cidade;

    public Integer getCodigoFornecedor() {
        return codigoFornecedor;
    }

    public void setCodigoFornecedor(Integer codigoFornecedor) {
        this.codigoFornecedor = codigoFornecedor;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void salvar(Fornecedor objFornecedor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
