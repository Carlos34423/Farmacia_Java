package br.edu.ifsuldeminas.CarlosManoelinfoh.relatorio;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.ConnectionFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Base dos relatórios em Excel (Apache POI) - abordagem 3 da atividade.
 * Cada relatório define título, SQL e colunas; esta classe monta a planilha
 * (título, cabeçalho, dados, fórmulas, linha de total, filtro e painel congelado).
 */
public abstract class RelatorioExcel {

    public enum Tipo { TEXTO, INTEIRO, MOEDA, PERCENTUAL, DATA }

    /** Coluna da planilha. Se "formula" não for nula, a célula é uma fórmula do Excel. */
    public static class Coluna {
        final String titulo;
        final Tipo tipo;
        final int largura;
        final boolean somar;
        final String formula;

        public Coluna(String titulo, Tipo tipo, int largura, boolean somar, String formula) {
            this.titulo = titulo;
            this.tipo = tipo;
            this.largura = largura;
            this.somar = somar;
            this.formula = formula;
        }

        public static Coluna dado(String titulo, Tipo tipo, int largura, boolean somar) {
            return new Coluna(titulo, tipo, largura, somar, null);
        }

        /** Placeholders: {r} = linha atual, {first}/{last} = primeira/última linha de dados. */
        public static Coluna formula(String titulo, Tipo tipo, int largura, boolean somar, String formula) {
            return new Coluna(titulo, tipo, largura, somar, formula);
        }
    }

    protected abstract String titulo();

    protected abstract String descricao();

    protected abstract String sql();

    protected abstract Coluna[] colunas();

    public abstract String nomeArquivo();

    /** Gancho opcional (ex.: formatação condicional). Linhas do Excel são 1-based. */
    protected void aposGerar(XSSFSheet aba, int primeiraLinha, int ultimaLinha) {
    }

    /** Busca os dados no MySQL. Colunas com fórmula não vêm do SQL. */
    public List<Object[]> buscarDados() throws SQLException {
        List<Object[]> linhas = new ArrayList<>();
        try (Connection con = ConnectionFactory.getIntance().getConnetion();
                PreparedStatement ps = con.prepareStatement(sql());
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            while (rs.next()) {
                Object[] l = new Object[n];
                for (int i = 0; i < n; i++) {
                    l[i] = rs.getObject(i + 1);
                }
                linhas.add(l);
            }
        }
        return linhas;
    }

    /**
     * Monta a planilha. "linhas" traz só as colunas que não são fórmula, na ordem
     * em que aparecem em colunas().
     */
    public void gerar(List<Object[]> linhas, OutputStream saida) throws IOException {
        Coluna[] cols = colunas();
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet aba = wb.createSheet(titulo().length() > 31 ? titulo().substring(0, 31) : titulo());
            XSSFColor azul = new XSSFColor(new byte[]{(byte) 0x1F, (byte) 0x6F, (byte) 0x8B}, null);

            XSSFFont fTitulo = wb.createFont();
            fTitulo.setBold(true);
            fTitulo.setFontHeightInPoints((short) 16);
            XSSFCellStyle sTitulo = wb.createCellStyle();
            sTitulo.setFont(fTitulo);

            XSSFFont fSub = wb.createFont();
            fSub.setItalic(true);
            fSub.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
            XSSFCellStyle sSub = wb.createCellStyle();
            sSub.setFont(fSub);

            XSSFFont fCab = wb.createFont();
            fCab.setBold(true);
            fCab.setColor(IndexedColors.WHITE.getIndex());
            XSSFCellStyle sCab = wb.createCellStyle();
            sCab.setFont(fCab);
            sCab.setFillForegroundColor(azul);
            sCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            sCab.setAlignment(HorizontalAlignment.CENTER);

            CellStyle[] normal = new CellStyle[Tipo.values().length];
            CellStyle[] total = new CellStyle[Tipo.values().length];
            XSSFFont fTot = wb.createFont();
            fTot.setBold(true);
            for (Tipo t : Tipo.values()) {
                normal[t.ordinal()] = estilo(wb, t, null);
                total[t.ordinal()] = estilo(wb, t, fTot);
            }

            int r = 0;
            Row rt = aba.createRow(r++);
            Cell ct = rt.createCell(0);
            ct.setCellValue(titulo());
            ct.setCellStyle(sTitulo);
            aba.addMergedRegion(new CellRangeAddress(0, 0, 0, Math.max(0, cols.length - 1)));
            Row rd = aba.createRow(r++);
            Cell cd = rd.createCell(0);
            cd.setCellValue(descricao() + "  |  Gerado em " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            cd.setCellStyle(sSub);
            aba.addMergedRegion(new CellRangeAddress(1, 1, 0, Math.max(0, cols.length - 1)));
            r++; // linha em branco

            int linhaCab = r;
            Row rc = aba.createRow(r++);
            for (int c = 0; c < cols.length; c++) {
                Cell cell = rc.createCell(c);
                cell.setCellValue(cols[c].titulo);
                cell.setCellStyle(sCab);
                aba.setColumnWidth(c, cols[c].largura * 256);
            }

            int primeira = r + 1;                 // 1-based
            int ultima = r + linhas.size();       // 1-based
            for (Object[] dado : linhas) {
                Row row = aba.createRow(r);
                int excel = r + 1;
                int pos = 0;
                for (int c = 0; c < cols.length; c++) {
                    Cell cell = row.createCell(c);
                    cell.setCellStyle(normal[cols[c].tipo.ordinal()]);
                    if (cols[c].formula != null) {
                        cell.setCellFormula(cols[c].formula
                                .replace("{r}", String.valueOf(excel))
                                .replace("{first}", String.valueOf(primeira))
                                .replace("{last}", String.valueOf(ultima)));
                    } else {
                        preencher(cell, pos < dado.length ? dado[pos] : null);
                        pos++;
                    }
                }
                r++;
            }

            if (!linhas.isEmpty()) {
                Row rtot = aba.createRow(r);
                for (int c = 0; c < cols.length; c++) {
                    Cell cell = rtot.createCell(c);
                    cell.setCellStyle(total[cols[c].tipo.ordinal()]);
                    if (c == 0) {
                        cell.setCellValue("TOTAL");
                        cell.setCellStyle(total[Tipo.TEXTO.ordinal()]);
                    } else if (cols[c].somar) {
                        String L = org.apache.poi.ss.util.CellReference.convertNumToColString(c);
                        cell.setCellFormula("SUM(" + L + primeira + ":" + L + ultima + ")");
                    }
                }
                aba.setAutoFilter(new CellRangeAddress(linhaCab, r - 1, 0, cols.length - 1));
            }
            aba.createFreezePane(0, linhaCab + 1);
            aposGerar(aba, primeira, ultima);
            wb.write(saida);
        }
    }

    private static CellStyle estilo(XSSFWorkbook wb, Tipo t, Font fonte) {
        CellStyle s = wb.createCellStyle();
        if (fonte != null) {
            s.setFont(fonte);
            s.setBorderTop(BorderStyle.THIN);
        }
        org.apache.poi.ss.usermodel.DataFormat df = wb.createDataFormat();
        switch (t) {
            case INTEIRO:
                s.setDataFormat(df.getFormat("#,##0"));
                break;
            case MOEDA:
                s.setDataFormat(df.getFormat("\"R$\" #,##0.00"));
                break;
            case PERCENTUAL:
                s.setDataFormat(df.getFormat("0.0%"));
                break;
            case DATA:
                s.setDataFormat(df.getFormat("dd/mm/yyyy"));
                s.setAlignment(HorizontalAlignment.CENTER);
                break;
            default:
                break;
        }
        return s;
    }

    private static void preencher(Cell cell, Object v) {
        if (v == null) {
            cell.setBlank();
        } else if (v instanceof Number) {
            cell.setCellValue(((Number) v).doubleValue());
        } else if (v instanceof java.sql.Timestamp) {
            cell.setCellValue(new Date(((java.sql.Timestamp) v).getTime()));
        } else if (v instanceof java.util.Date) {
            cell.setCellValue(new Date(((java.util.Date) v).getTime()));
        } else {
            cell.setCellValue(String.valueOf(v));
        }
    }
}
