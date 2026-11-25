package br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio;


public class RelatorioInventarioExcel extends RelatorioExcel {

    @Override
    protected String titulo() {
        return "Inventário de Medicamentos";
    }

    @Override
    protected String descricao() {
        return "Decisão: conferir o estoque físico e o capital parado em cada produto";
    }

    @Override
    public String nomeArquivo() {
        return "relatorio-inventario-medicamentos.xlsx";
    }

    @Override
    protected String sql() {
        return "SELECT m.codigoMedicamento, m.nome, c.nome AS categoria, fa.nome AS fabricante, f.nomeFantasia AS fornecedor, m.precoVenda, m.quantidadeEstoque "
                + "FROM medicamento m "
                + "LEFT JOIN CategoriaMedicamento c ON c.codigoCategoriaMedicamento = m.codigoCategoriaMedicamento "
                + "LEFT JOIN fabricante fa ON fa.cnpj = m.cnpjFabricante "
                + "LEFT JOIN fornecedor f ON f.codigoFornecedor = m.codigoFornecedor "
                + "ORDER BY m.nome";
    }

    @Override
    protected Coluna[] colunas() {
        return new Coluna[]{
            Coluna.dado("Código", Tipo.INTEIRO, 10, false),
            Coluna.dado("Medicamento", Tipo.TEXTO, 30, false),
            Coluna.dado("Categoria", Tipo.TEXTO, 18, false),
            Coluna.dado("Fabricante", Tipo.TEXTO, 18, false),
            Coluna.dado("Fornecedor", Tipo.TEXTO, 18, false),
            Coluna.dado("Preço de venda", Tipo.MOEDA, 16, false),
            Coluna.dado("Estoque", Tipo.INTEIRO, 10, true),
            Coluna.formula("Valor em estoque (venda)", Tipo.MOEDA, 22, true, "F{r}*G{r}")
        };
    }

}
