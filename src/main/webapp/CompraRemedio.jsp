
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
    <title>Compra de Remédio</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Compra de Remédio</h2>

        <c:if test="${not empty mensagem}">
            <div class="mensagem sucesso">${mensagem}</div>
        </c:if>

        <c:if test="${not empty mensagemErro}">
            <div class="mensagem erro">${mensagemErro}</div>
        </c:if>

        <form class="formulario"
              method="get"
              action="${pageContext.request.contextPath}${URL_BASE}/CompraRemedioControlador">

            <label>Cliente</label>
            <select name="clienteCompra"
                    onchange="this.form.submit()"
                    class="${not empty errosCampo.cliente ? 'campo-invalido' : ''}">

                <option value="">Selecione...</option>

                <c:forEach var="cliente" items="${listaClientes}">
                    <option value="${cliente.codigoCliente}" ${cliente.codigoCliente == clienteSelecionado ? 'selected' : ''}>
                        ${cliente.nome} (carteira: R$ ${cliente.carteira} — convênio: ${cliente.convenio.nome}, ${cliente.convenio.percentual}% de desconto)
                    </option>
                </c:forEach>

            </select>
            <c:if test="${not empty errosCampo.cliente}">
                <span class="erro-campo">${errosCampo.cliente}</span>
            </c:if>

            <label>Medicamento</label>
            <select name="medicamentoCompra"
                    class="${not empty errosCampo.medicamento ? 'campo-invalido' : ''}">

                <option value="">Selecione...</option>

                <c:forEach var="medicamento" items="${listaMedicamentos}">
                    <option value="${medicamento.codigoMedicamento}">
                        ${medicamento.nome} (R$ ${medicamento.precoVenda} — estoque: ${medicamento.quantidadeEstoque})
                    </option>
                </c:forEach>

            </select>
            <c:if test="${not empty errosCampo.medicamento}">
                <span class="erro-campo">${errosCampo.medicamento}</span>
            </c:if>

            <label>Quantidade</label>
            <input type="number"
                   min="1"
                   name="quantidade"
                   value="1"
                   class="${not empty errosCampo.quantidade ? 'campo-invalido' : ''}">
            <c:if test="${not empty errosCampo.quantidade}">
                <span class="erro-campo">${errosCampo.quantidade}</span>
            </c:if>

            <div class="acoes-formulario">

                <button class="botao" type="submit" name="opcao" value="adicionar">
                    Adicionar ao carrinho
                </button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/CompraRemedioControlador?opcao=cancelar">
                    Limpar tudo
                </a>

            </div>

        </form>

    </div>

    <div class="card-home tabela-home">

        <h2>Carrinho</h2>

        <c:if test="${not empty errosCampo.carrinho}">
            <span class="erro-campo">${errosCampo.carrinho}</span>
        </c:if>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Medicamento</th>
                    <th>Preço unit. (R$)</th>
                    <th>Qtd</th>
                    <th>Subtotal (R$)</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="item" items="${carrinho}">
                    <tr>
                        <td><c:out value="${item.nome}"/></td>
                        <td><fmt:formatNumber value="${item.precoUnitario}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td>${item.quantidade}</td>
                        <td><fmt:formatNumber value="${item.subtotal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="acoes-tabela">
                            <a class="link-acao excluir"
                               href="${pageContext.request.contextPath}${URL_BASE}/CompraRemedioControlador?opcao=remover&medicamentoRemover=${item.codigoMedicamento}">
                                Remover
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty carrinho}">
                    <tr>
                        <td colspan="5">Carrinho vazio. Escolha um medicamento e a quantidade acima.</td>
                    </tr>
                </c:if>

            </tbody>

            <c:if test="${not empty carrinho}">
                <tfoot>
                    <tr>
                        <th colspan="3">Valor dos produtos</th>
                        <th colspan="2">R$ <fmt:formatNumber value="${valorBruto}" minFractionDigits="2" maxFractionDigits="2"/></th>
                    </tr>
                    <tr>
                        <th colspan="3">
                            Desconto do convênio
                            <c:choose>
                                <c:when test="${not empty convenioNome}">(${convenioNome} — <fmt:formatNumber value="${percentualDesconto}" maxFractionDigits="2"/>%)</c:when>
                                <c:otherwise>(selecione o cliente)</c:otherwise>
                            </c:choose>
                        </th>
                        <th colspan="2">- R$ <fmt:formatNumber value="${valorDesconto}" minFractionDigits="2" maxFractionDigits="2"/></th>
                    </tr>
                    <tr>
                        <th colspan="3">Valor final</th>
                        <th colspan="2">R$ <fmt:formatNumber value="${valorFinal}" minFractionDigits="2" maxFractionDigits="2"/></th>
                    </tr>
                </tfoot>
            </c:if>

        </table>

        <c:if test="${not empty carrinho}">
            <form class="formulario"
                  method="get"
                  action="${pageContext.request.contextPath}${URL_BASE}/CompraRemedioControlador">
                <div class="acoes-formulario">
                    <button class="botao" type="submit" name="opcao" value="comprar">
                        Finalizar compra
                    </button>
                </div>
            </form>
        </c:if>

    </div>

    <div class="card-home tabela-home">

        <h2>Clientes — carteira</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Carteira (R$)</th>
                    <th>Convênio</th>
                    <th>Desconto</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="cliente" items="${listaClientes}">
                    <tr>
                        <td>${cliente.nome}</td>
                        <td>${cliente.cpf}</td>
                        <td>${cliente.carteira}</td>
                        <td>${cliente.convenio.nome}</td>
                        <td>${cliente.convenio.percentual}%</td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listaClientes}">
                    <tr>
                        <td colspan="5">
                            Nenhum cliente cadastrado ainda.
                        </td>
                    </tr>
                </c:if>

            </tbody>

        </table>

    </div>

    <div class="card-home tabela-home">

        <h2>Medicamentos — estoque</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Preço (R$)</th>
                    <th>Estoque</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="medicamento" items="${listaMedicamentos}">
                    <tr>
                        <td>${medicamento.nome}</td>
                        <td>${medicamento.precoVenda}</td>
                        <td>${medicamento.quantidadeEstoque}</td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listaMedicamentos}">
                    <tr>
                        <td colspan="3">
                            Nenhum medicamento cadastrado ainda.
                        </td>
                    </tr>
                </c:if>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>
