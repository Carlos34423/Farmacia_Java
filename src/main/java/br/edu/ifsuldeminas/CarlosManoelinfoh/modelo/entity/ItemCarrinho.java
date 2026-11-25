package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

/**
 * Item do carrinho da tela de compra de remédio (guardado na sessão).
 */
public class ItemCarrinho {

    private Integer codigoMedicamento;
    private String nome;
    private Double precoUnitario;
    private Integer quantidade;

    public ItemCarrinho(Integer codigoMedicamento, String nome, Double precoUnitario, Integer quantidade) {
        this.codigoMedicamento = codigoMedicamento;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public Integer getCodigoMedicamento() { return codigoMedicamento; }
    public void setCodigoMedicamento(Integer codigoMedicamento) { this.codigoMedicamento = codigoMedicamento; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getSubtotal() { return precoUnitario * quantidade; }
}
