package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

public class CategoriaMedicamento {
    private Integer codigoCategoriaMedicamento;
    private String nome;
    private String descricao;

    public Integer getCodigoCategoriaMedicamento() {
        return codigoCategoriaMedicamento;
    }

    public void setCodigoCategoriaMedicamento(Integer codigoCategoriaMedicamento) {
        this.codigoCategoriaMedicamento = codigoCategoriaMedicamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void salvar(CategoriaMedicamento objCategoriaMedicamento) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
