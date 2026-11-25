<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
        <title>Relatórios - Farmácia</title>
        <style>
            .rel-wrap { max-width: 1000px; margin: 20px auto; padding: 0 16px; }
            .rel-filtros { background: #fff; padding: 14px 18px; border-radius: 6px; margin-bottom: 18px; }
            .rel-filtros label { margin-right: 14px; font-size: 14px; }
            .rel-filtros input { padding: 5px; }
            .rel-grupo h2 { margin: 22px 0 8px; color: #1F6F8B; }
            .rel-card { background: #fff; border-radius: 6px; padding: 12px 16px; margin-bottom: 10px;
                        display: flex; justify-content: space-between; align-items: center; gap: 12px; }
            .rel-card .txt small { display: block; color: #666; margin-top: 3px; }
            .rel-card a { display: inline-block; margin-left: 6px; padding: 6px 10px; background: #1F6F8B;
                          color: #fff; border-radius: 4px; text-decoration: none; font-size: 13px; }
            .rel-card a.sec { background: #5b8a72; }
        </style>
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <div class="rel-wrap">
            <h1>Relatórios gerenciais</h1>

            <div class="rel-filtros">
                <label>Data inicial <input type="date" id="dataInicial"></label>
                <label>Data final <input type="date" id="dataFinal"></label>
                <label>Vencimento em até <input type="number" id="dias" value="90" min="0" style="width:70px"> dias</label>
            </div>

            <div class="rel-grupo">
                <h2>Abordagem 1 - JasperReports (PDF, Excel, CSV, HTML)</h2>
                <%
                    String[][] jasper = {
                        {"vendasMes", "1. Vendas por mês", "Evolução do faturamento. Decide metas, campanhas e sazonalidade.", "periodo"},
                        {"topMedicamentos", "2. Top 10 medicamentos mais vendidos", "Decide o que comprar mais e o que promover.", "periodo"},
                        {"estoqueMinimo", "3. Estoque abaixo do mínimo", "Lista de reposição por fornecedor. Decide o pedido de compra.", ""},
                        {"vencimento", "4. Medicamentos a vencer", "Itens vencidos ou perto do vencimento. Decide liquidação ou devolução.", "dias"},
                        {"categoria", "5. Vendas por categoria", "Participação de cada categoria na receita. Decide o mix de produtos.", "periodo"}
                    };
                    for (String[] r : jasper) {
                %>
                <div class="rel-card">
                    <div class="txt"><strong><%= r[1] %></strong><small><%= r[2] %></small></div>
                    <div>
                        <a href="#" onclick="abrir('jasper','<%= r[0] %>','pdf','<%= r[3] %>');return false;">PDF</a>
                        <a class="sec" href="#" onclick="abrir('jasper','<%= r[0] %>','xlsx','<%= r[3] %>');return false;">Excel</a>
                        <a class="sec" href="#" onclick="abrir('jasper','<%= r[0] %>','csv','<%= r[3] %>');return false;">CSV</a>
                        <a class="sec" href="#" onclick="abrir('jasper','<%= r[0] %>','html','<%= r[3] %>');return false;">HTML</a>
                    </div>
                </div>
                <% } %>
            </div>

            <div class="rel-grupo">
                <h2>Abordagem 3 - Apache POI (Excel com fórmulas)</h2>
                <%
                    String[][] poi = {
                        {"inventario", "6. Inventário de medicamentos", "Estoque e capital parado por produto."},
                        {"funcionarios", "7. Vendas por funcionário", "Ranking, ticket médio e % do total. Decide metas e comissões."},
                        {"clientes", "8. Clientes que mais compram", "Decide programa de fidelidade e reativação de clientes."},
                        {"fornecedores", "9. Estoque por fornecedor", "Quem concentra mais capital em estoque. Decide negociação."},
                        {"margem", "10. Margem de lucro por produto", "Margem em R$ e %, com alerta abaixo de 50%. Decide reajuste de preço."}
                    };
                    for (String[] r : poi) {
                %>
                <div class="rel-card">
                    <div class="txt"><strong><%= r[1] %></strong><small><%= r[2] %></small></div>
                    <div><a href="#" onclick="abrir('excel','<%= r[0] %>','xlsx','');return false;">Baixar Excel</a></div>
                </div>
                <% } %>
            </div>
        </div>

        <script>
            var BASE = '${pageContext.request.contextPath}${URL_BASE}';
            (function () {
                var hoje = new Date(), ini = new Date();
                ini.setMonth(ini.getMonth() - 6);
                document.getElementById('dataFinal').value = hoje.toISOString().slice(0, 10);
                document.getElementById('dataInicial').value = ini.toISOString().slice(0, 10);
            })();
            function abrir(tipoRel, tipo, formato, filtros) {
                var url;
                if (tipoRel === 'excel') {
                    url = BASE + '/RelatorioExcelControlador?tipo=' + tipo;
                } else {
                    url = BASE + '/RelatorioJasperControlador?tipo=' + tipo + '&formato=' + formato;
                    if (filtros === 'periodo') {
                        url += '&dataInicial=' + document.getElementById('dataInicial').value
                             + '&dataFinal=' + document.getElementById('dataFinal').value;
                    } else if (filtros === 'dias') {
                        url += '&dias=' + document.getElementById('dias').value;
                    }
                }
                if (formato === 'pdf' || formato === 'html') { window.open(url, '_blank'); }
                else { window.location = url; }
            }
        </script>
    </body>
</html>
