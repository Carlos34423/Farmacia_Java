<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Cargo</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Cadastro de Cargo</h2>
                <c:if test="${not empty mensagem}">
                    <div class="mensagem sucesso">${mensagem}</div>
                </c:if>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/CargoControlador">
                    <input type="hidden" name="opcao" value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">
                    <input type="hidden" name="codigoCargo" value="${cargoEdicao.codigoCargo}">

                    <label for="nome">Nome</label>
                    <input id="nome" type="text" name="nome" maxlength="80" required value="${cargoEdicao.nome}">

                    <label for="salarioBase">Salario Base</label>
                    <input id="salarioBase" type="number" step="0.01" min="0" name="salarioBase" required value="${cargoEdicao.salarioBase}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">${modoFormulario eq 'editar' ? 'Salvar alteracoes' : 'Cadastrar'}</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/CargoControlador?opcao=novo">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Cargos cadastrados</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Nome</th>
                            <th>Salario Base</th>
                            <th>Acoes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cargo" items="${cargos}">
                            <tr>
                                <td>${cargo.codigoCargo}</td>
                                <td>${cargo.nome}</td>
                                <td>${cargo.salarioBase}</td>
                                <td class="acoes-tabela">
                                    <a class="link-acao" href="${pageContext.request.contextPath}${URL_BASE}/CargoControlador?opcao=editar&codigoCargo=${cargo.codigoCargo}">Editar</a>
                                    <a class="link-acao excluir" href="${pageContext.request.contextPath}${URL_BASE}/CargoControlador?opcao=excluir&codigoCargo=${cargo.codigoCargo}" onclick="return confirm('Deseja excluir este cargo?');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty cargos}">
                            <tr>
                                <td colspan="4">Nenhum cargo cadastrado ainda.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
