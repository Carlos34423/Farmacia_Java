<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Forma de Pagamento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Cadastro de Forma de Pagamento</h2>
                <c:if test="${not empty mensagem}">
                    <div class="mensagem sucesso">${mensagem}</div>
                </c:if>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador">
                    <input type="hidden" name="opcao" value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">
                    <input type="hidden" name="codigoFormaPagamento" value="${formaPagamentoEdicao.codigoFormaPagamento}">

                    <label for="descricao">Descricao</label>
                    <input id="descricao" type="text" name="descricao" maxlength="80" required value="${formaPagamentoEdicao.descricao}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">${modoFormulario eq 'editar' ? 'Salvar alteracoes' : 'Cadastrar'}</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=novo">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Formas de pagamento cadastradas</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Descricao</th>
                            <th>Acoes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="formaPagamento" items="${formasPagamento}">
                            <tr>
                                <td>${formaPagamento.codigoFormaPagamento}</td>
                                <td>${formaPagamento.descricao}</td>
                                <td class="acoes-tabela">
                                    <a class="link-acao" href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=editar&codigoFormaPagamento=${formaPagamento.codigoFormaPagamento}">Editar</a>
                                    <a class="link-acao excluir" href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=excluir&codigoFormaPagamento=${formaPagamento.codigoFormaPagamento}" onclick="return confirm('Deseja excluir esta forma de pagamento?');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty formasPagamento}">
                            <tr>
                                <td colspan="3">Nenhuma forma de pagamento cadastrada ainda.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
