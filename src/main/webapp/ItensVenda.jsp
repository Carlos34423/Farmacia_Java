<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Itens de Venda</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Itens de Venda</h2>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ItemVendaControlador">
                    <label for="codigoVenda">Filtrar por código da venda</label>
                    <input id="codigoVenda" type="number" min="1" name="codigoVenda" value="${filtroVenda}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">Filtrar</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/ItemVendaControlador">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Itens vendidos</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Item</th>
                            <th>Venda</th>
                            <th>Data</th>
                            <th>Cliente</th>
                            <th>Medicamento</th>
                            <th>Qtd</th>
                            <th>Preço unit.</th>
                            <th>Subtotal</th>
                            <th>Desconto convênio</th>
                            <th>Valor final</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${itens}">
                            <tr>
                                <td>${item.codigoItemVenda}</td>
                                <td>${item.codigoVenda}</td>
                                <td>${item.dataVenda}</td>
                                <td><c:out value="${item.nomeCliente}"/></td>
                                <td><c:out value="${item.nomeMedicamento}"/></td>
                                <td>${item.quantidade}</td>
                                <td>R$ <fmt:formatNumber value="${item.precoUnitario}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                <td>R$ <fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                                <td><fmt:formatNumber value="${item.percentualDesconto}" minFractionDigits="0" maxFractionDigits="2"/>%</td>
                                <td>R$ <fmt:formatNumber value="${item.valorFinal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty itens}">
                            <tr>
                                <td colspan="10">Nenhum item de venda encontrado.</td>
                            </tr>
                        </c:if>
                    </tbody>
                    <c:if test="${not empty itens}">
                        <tfoot>
                            <tr>
                                <th colspan="5">Total</th>
                                <th>${totalQuantidade}</th>
                                <th></th>
                                <th>R$ <fmt:formatNumber value="${totalBruto}" minFractionDigits="2" maxFractionDigits="2"/></th>
                                <th></th>
                                <th>R$ <fmt:formatNumber value="${totalGeral}" minFractionDigits="2" maxFractionDigits="2"/></th>
                            </tr>
                        </tfoot>
                    </c:if>
                </table>
            </div>
        </div>
    </body>
</html>
