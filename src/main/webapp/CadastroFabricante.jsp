<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Fabricante</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Cadastro de Fabricante</h2>
                <c:if test="${not empty mensagem}">
                    <div class="mensagem sucesso">${mensagem}</div>
                </c:if>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FabricanteControlador">
                    <input type="hidden" name="opcao" value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">
                    <input type="hidden" name="cnpjOriginal" value="${fabricanteEdicao.cnpjOriginal}">

                    <label for="nome">Nome</label>
                    <input id="nome" type="text" name="nome" maxlength="120" required value="${fabricanteEdicao.nome}">

                    <label for="cnpj">CNPJ</label>
                    <input id="cnpj" type="text" name="cnpj" maxlength="18" required value="${fabricanteEdicao.cnpj}">

                    <label for="telefone">Telefone</label>
                    <input id="telefone" type="text" name="telefone" maxlength="20" required value="${fabricanteEdicao.telefone}">

                    <label for="email">Email</label>
                    <input id="email" type="email" name="email" maxlength="120" required value="${fabricanteEdicao.email}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">${modoFormulario eq 'editar' ? 'Salvar alteracoes' : 'Cadastrar'}</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/FabricanteControlador?opcao=novo">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Fabricantes cadastrados</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>CNPJ</th>
                            <th>Telefone</th>
                            <th>Email</th>
                            <th>Acoes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="fabricante" items="${fabricantes}">
                            <tr>
                                <td>${fabricante.nome}</td>
                                <td>${fabricante.cnpj}</td>
                                <td>${fabricante.telefone}</td>
                                <td>${fabricante.email}</td>
                                <td class="acoes-tabela">
                                    <a class="link-acao" href="${pageContext.request.contextPath}${URL_BASE}/FabricanteControlador?opcao=editar&cnpj=${fabricante.cnpj}">Editar</a>
                                    <a class="link-acao excluir" href="${pageContext.request.contextPath}${URL_BASE}/FabricanteControlador?opcao=excluir&cnpj=${fabricante.cnpj}" onclick="return confirm('Deseja excluir este fabricante?');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty fabricantes}">
                            <tr>
                                <td colspan="5">Nenhum fabricante cadastrado ainda.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
