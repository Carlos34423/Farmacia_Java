package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

/**
 * Item de uma venda (tabela itemVenda), com dados de apoio vindos de venda,
 * cliente e medicamento para exibição na tela.
 */
public class ItemVenda {

    private Integer codigoItemVenda;
    private Integer codigoVenda;
    private Integer codigoMedicamento;
    private Integer quantidade;
    private Double precoUnitario;

    // Campos de apoio (JOIN)
    private String dataVenda;
    private String nomeCliente;
    private String nomeMedicamento;
    private Double subtotal;
    private Double percentualDesconto;
    private Double valorFinal;

    public Integer getCodigoItemVenda() { return codigoItemVenda; }
    public void setCodigoItemVenda(Integer codigoItemVenda) { this.codigoItemVenda = codigoItemVenda; }

    public Integer getCodigoVenda() { return codigoVenda; }
    public void setCodigoVenda(Integer codigoVenda) { this.codigoVenda = codigoVenda; }

    public Integer getCodigoMedicamento() { return codigoMedicamento; }
    public void setCodigoMedicamento(Integer codigoMedicamento) { this.codigoMedicamento = codigoMedicamento; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }

    public String getDataVenda() { return dataVenda; }
    public void setDataVenda(String dataVenda) { this.dataVenda = dataVenda; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getNomeMedicamento() { return nomeMedicamento; }
    public void setNomeMedicamento(String nomeMedicamento) { this.nomeMedicamento = nomeMedicamento; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getPercentualDesconto() { return percentualDesconto; }
    public void setPercentualDesconto(Double percentualDesconto) { this.percentualDesconto = percentualDesconto; }

    public Double getValorFinal() { return valorFinal; }
    public void setValorFinal(Double valorFinal) { this.valorFinal = valorFinal; }
}
