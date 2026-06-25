/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity;

/**
 *
 * @author hatak
 */
public class Medicamento {

    private Integer codigoMedicamento;
    private String nome;
    private String descricao;
    private Double precoVenda;
    private Integer quantidadeEstoque;

    private CategoriaMedicamento categoriaMedicamento;
    private Fabricante fabricante;
    private Fornecedor fornecedor;

    public Integer getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(Integer codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
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

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public CategoriaMedicamento getCategoriaMedicamento() {
        return categoriaMedicamento;
    }

    public void setCategoriaMedicamento(CategoriaMedicamento categoriaMedicamento) {
        this.categoriaMedicamento = categoriaMedicamento;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
