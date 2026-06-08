package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

public class Convenio {
    private Integer codigoConvenio;
    private String nome;
    private Double percentual;

    public Integer getCodigoConvenio() {
        return codigoConvenio;
    }

    public void setCodigoConvenio(Integer codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public void salvar(Convenio objConvenio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
