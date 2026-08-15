
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            <input type="hidden"
                   name="opcao"
                   value="comprar">

            <label>Cliente</label>
            <select name="clienteCompra"
                    class="${not empty errosCampo.cliente ? 'campo-invalido' : ''}">

                <option value="">Selecione...</option>

                <c:forEach var="cliente" items="${listaClientes}">
                    <option value="${cliente.codigoCliente}">
                        ${cliente.nome} (carteira: R$ ${cliente.carteira})
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
            <input type="text"
                   name="quantidade"
                   class="${not empty errosCampo.quantidade ? 'campo-invalido' : ''}">
            <c:if test="${not empty errosCampo.quantidade}">
                <span class="erro-campo">${errosCampo.quantidade}</span>
            </c:if>

            <div class="acoes-formulario">

                <button class="botao" type="submit">
                    Comprar
                </button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/CompraRemedioControlador?opcao=cancelar">
                    Limpar
                </a>

            </div>

        </form>

    </div>

    <div class="card-home tabela-home">

        <h2>Clientes — carteira</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Carteira (R$)</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="cliente" items="${listaClientes}">
                    <tr>
                        <td>${cliente.nome}</td>
                        <td>${cliente.cpf}</td>
                        <td>${cliente.carteira}</td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listaClientes}">
                    <tr>
                        <td colspan="3">
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
