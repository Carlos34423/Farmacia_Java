package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

public class FormaPagamento {
    private Integer codigoFormaPagamento;
    private String descricao;

    public Integer getCodigoFormaPagamento() {
        return codigoFormaPagamento;
    }

    public void setCodigoFormaPagamento(Integer codigoFormaPagamento) {
        this.codigoFormaPagamento = codigoFormaPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void salvar(FormaPagamento objFormaPagamento) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
