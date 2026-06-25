<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Categoria de Medicamento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Cadastro de Categoria de Medicamento</h2>
                <c:if test="${not empty mensagem}">
                    <div class="mensagem sucesso">${mensagem}</div>
                </c:if>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaMedicamentoControlador">
                    <input type="hidden" name="opcao" value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">
                    <input type="hidden" name="codigoCategoriaMedicamento" value="${categoriaEdicao.codigoCategoriaMedicamento}">

                    <label for="nome">Nome</label>
                    <input id="nome" type="text" name="nome" maxlength="80" required value="${categoriaEdicao.nome}">

                    <label for="descricao">Descricao</label>
                    <input id="descricao" type="text" name="descricao" maxlength="255" required value="${categoriaEdicao.descricao}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">${modoFormulario eq 'editar' ? 'Salvar alteracoes' : 'Cadastrar'}</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/CategoriaMedicamentoControlador?opcao=novo">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Categorias cadastradas</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Nome</th>
                            <th>Descricao</th>
                            <th>Acoes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="categoria" items="${categorias}">
                            <tr>
                                <td>${categoria.codigoCategoriaMedicamento}</td>
                                <td>${categoria.nome}</td>
                                <td>${categoria.descricao}</td>
                                <td class="acoes-tabela">
                                    <a class="link-acao" href="${pageContext.request.contextPath}${URL_BASE}/CategoriaMedicamentoControlador?opcao=editar&codigoCategoriaMedicamento=${categoria.codigoCategoriaMedicamento}">Editar</a>
                                    <a class="link-acao excluir" href="${pageContext.request.contextPath}${URL_BASE}/CategoriaMedicamentoControlador?opcao=excluir&codigoCategoriaMedicamento=${categoria.codigoCategoriaMedicamento}" onclick="return confirm('Deseja excluir esta categoria?');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty categorias}">
                            <tr>
                                <td colspan="4">Nenhuma categoria cadastrada ainda.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
