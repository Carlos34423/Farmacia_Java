package br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio;


public class RelatorioTopClientesExcel extends RelatorioExcel {

    @Override
    protected String titulo() {
        return "Clientes que Mais Compram";
    }

    @Override
    protected String descricao() {
        return "Decisão: criar programa de fidelidade e reativar clientes inativos";
    }

    @Override
    public String nomeArquivo() {
        return "relatorio-top-clientes.xlsx";
    }

    @Override
    protected String sql() {
        return "SELECT cl.nome AS cliente, ci.nome AS cidade, co.nome AS convenio, COUNT(v.codigoVenda) AS qtdCompras, COALESCE(SUM(v.valorTotal),0) AS totalGasto, MAX(v.dataVenda) AS ultimaCompra "
                + "FROM cliente cl "
                + "LEFT JOIN cidade ci ON ci.codigoCidade = cl.codigoCidade "
                + "LEFT JOIN convenio co ON co.codigoConvenio = cl.codigoConvenio "
                + "LEFT JOIN venda v ON v.codigoCliente = cl.codigoCliente "
                + "GROUP BY cl.codigoCliente, cl.nome, ci.nome, co.nome "
                + "ORDER BY totalGasto DESC";
    }

    @Override
    protected Coluna[] colunas() {
        return new Coluna[]{
            Coluna.dado("Cliente", Tipo.TEXTO, 28, false),
            Coluna.dado("Cidade", Tipo.TEXTO, 20, false),
            Coluna.dado("Convênio", Tipo.TEXTO, 18, false),
            Coluna.dado("Nº de compras", Tipo.INTEIRO, 14, true),
            Coluna.dado("Total gasto", Tipo.MOEDA, 18, true),
            Coluna.dado("Última compra", Tipo.DATA, 16, false),
            Coluna.formula("Ticket médio", Tipo.MOEDA, 16, false, "IF(D{r}=0,0,E{r}/D{r})")
        };
    }

}
