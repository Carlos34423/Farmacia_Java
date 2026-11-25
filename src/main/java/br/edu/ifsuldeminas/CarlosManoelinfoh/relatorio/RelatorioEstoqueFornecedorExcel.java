package br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio;


public class RelatorioEstoqueFornecedorExcel extends RelatorioExcel {

    @Override
    protected String titulo() {
        return "Estoque por Fornecedor";
    }

    @Override
    protected String descricao() {
        return "Decisão: negociar prazos e preços com quem concentra mais capital em estoque";
    }

    @Override
    public String nomeArquivo() {
        return "relatorio-estoque-fornecedor.xlsx";
    }

    @Override
    protected String sql() {
        return "SELECT COALESCE(f.nomeFantasia,'(sem fornecedor)') AS fornecedor, COUNT(m.codigoMedicamento) AS produtos, COALESCE(SUM(m.quantidadeEstoque),0) AS unidades, "
                + "COALESCE(SUM(m.quantidadeEstoque*COALESCE(m.precoCusto,0)),0) AS valorCusto, COALESCE(SUM(m.quantidadeEstoque*m.precoVenda),0) AS valorVenda "
                + "FROM medicamento m "
                + "LEFT JOIN fornecedor f ON f.codigoFornecedor = m.codigoFornecedor "
                + "GROUP BY f.codigoFornecedor, f.nomeFantasia "
                + "ORDER BY valorCusto DESC";
    }

    @Override
    protected Coluna[] colunas() {
        return new Coluna[]{
            Coluna.dado("Fornecedor", Tipo.TEXTO, 28, false),
            Coluna.dado("Produtos", Tipo.INTEIRO, 12, true),
            Coluna.dado("Unidades em estoque", Tipo.INTEIRO, 20, true),
            Coluna.dado("Valor de custo", Tipo.MOEDA, 18, true),
            Coluna.dado("Valor de venda", Tipo.MOEDA, 18, true),
            Coluna.formula("Margem potencial", Tipo.MOEDA, 18, true, "E{r}-D{r}")
        };
    }

}
