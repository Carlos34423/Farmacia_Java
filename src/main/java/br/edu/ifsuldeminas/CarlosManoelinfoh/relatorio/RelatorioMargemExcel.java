package br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio;

import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class RelatorioMargemExcel extends RelatorioExcel {

    @Override
    protected String titulo() {
        return "Margem de Lucro por Produto";
    }

    @Override
    protected String descricao() {
        return "Decisão: reajustar preços (vermelho = margem abaixo de 50%) e priorizar os produtos mais lucrativos";
    }

    @Override
    public String nomeArquivo() {
        return "relatorio-margem-produtos.xlsx";
    }

    @Override
    protected String sql() {
        return "SELECT m.nome AS medicamento, c.nome AS categoria, COALESCE(m.precoCusto,0) AS precoCusto, m.precoVenda, COALESCE(SUM(i.quantidade),0) AS unidadesVendidas "
                + "FROM medicamento m "
                + "LEFT JOIN CategoriaMedicamento c ON c.codigoCategoriaMedicamento = m.codigoCategoriaMedicamento "
                + "LEFT JOIN itemVenda i ON i.codigoMedicamento = m.codigoMedicamento "
                + "GROUP BY m.codigoMedicamento, m.nome, c.nome, m.precoCusto, m.precoVenda "
                + "ORDER BY m.nome";
    }

    @Override
    protected Coluna[] colunas() {
        return new Coluna[]{
            Coluna.dado("Medicamento", Tipo.TEXTO, 28, false),
            Coluna.dado("Categoria", Tipo.TEXTO, 18, false),
            Coluna.dado("Preço de custo", Tipo.MOEDA, 16, false),
            Coluna.dado("Preço de venda", Tipo.MOEDA, 16, false),
            Coluna.dado("Unidades vendidas", Tipo.INTEIRO, 18, true),
            Coluna.formula("Margem (R$)", Tipo.MOEDA, 14, false, "D{r}-C{r}"),
            Coluna.formula("Margem (%)", Tipo.PERCENTUAL, 12, false, "IF(D{r}=0,0,F{r}/D{r})"),
            Coluna.formula("Lucro bruto acumulado", Tipo.MOEDA, 22, true, "F{r}*E{r}")
        };
    }

    /** Margem mínima desejada; abaixo disso a célula fica vermelha. */
    private static final String MARGEM_MINIMA = "0.5";

    @Override
    protected void aposGerar(XSSFSheet aba, int primeira, int ultima) {
        if (ultima < primeira) {
            return;
        }
        SheetConditionalFormatting cf = aba.getSheetConditionalFormatting();
        ConditionalFormattingRule regra = cf.createConditionalFormattingRule(ComparisonOperator.LT, MARGEM_MINIMA);
        PatternFormatting pf = regra.createPatternFormatting();
        pf.setFillBackgroundColor(IndexedColors.ROSE.getIndex());
        pf.setFillPattern(PatternFormatting.SOLID_FOREGROUND);
        cf.addConditionalFormatting(new CellRangeAddress[]{new CellRangeAddress(primeira - 1, ultima - 1, 6, 6)}, regra);
    }
}
