package br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio;


public class RelatorioVendasFuncionarioExcel extends RelatorioExcel {

    @Override
    protected String titulo() {
        return "Vendas por Funcionário";
    }

    @Override
    protected String descricao() {
        return "Decisão: reconhecer desempenho, definir metas e comissões";
    }

    @Override
    public String nomeArquivo() {
        return "relatorio-vendas-funcionario.xlsx";
    }

    @Override
    protected String sql() {
        return "SELECT fu.nome AS funcionario, ca.nome AS cargo, COUNT(v.codigoVenda) AS qtdVendas, COALESCE(SUM(v.valorTotal),0) AS totalVendido "
                + "FROM funcionario fu "
                + "LEFT JOIN cargo ca ON ca.codigoCargo = fu.codigoCargo "
                + "LEFT JOIN venda v ON v.codigoFuncionario = fu.codigoFuncionario "
                + "GROUP BY fu.codigoFuncionario, fu.nome, ca.nome "
                + "ORDER BY totalVendido DESC";
    }

    @Override
    protected Coluna[] colunas() {
        return new Coluna[]{
            Coluna.dado("Funcionário", Tipo.TEXTO, 28, false),
            Coluna.dado("Cargo", Tipo.TEXTO, 18, false),
            Coluna.dado("Nº de vendas", Tipo.INTEIRO, 14, true),
            Coluna.dado("Total vendido", Tipo.MOEDA, 18, true),
            Coluna.formula("Ticket médio", Tipo.MOEDA, 16, false, "IF(C{r}=0,0,D{r}/C{r})"),
            Coluna.formula("% do total", Tipo.PERCENTUAL, 12, true, "IF(SUM(D{first}:D{last})=0,0,D{r}/SUM(D{first}:D{last}))")
        };
    }

}
