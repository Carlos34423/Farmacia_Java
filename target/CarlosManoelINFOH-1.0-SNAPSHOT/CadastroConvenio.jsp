<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Convenio</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Cadastro de Convenio</h2>
                <c:if test="${not empty mensagem}">
                    <div class="mensagem sucesso">${mensagem}</div>
                </c:if>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ConvenioControlador">
                    <input type="hidden" name="opcao" value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">
                    <input type="hidden" name="codigoConvenio" value="${convenioEdicao.codigoConvenio}">

                    <label for="nome">Nome</label>
                    <input id="nome" type="text" name="nome" maxlength="80" required value="${convenioEdicao.nome}">

                    <label for="percentual">Percentual de Desconto</label>
                    <input id="percentual" type="number" step="0.01" min="0" name="percentual" required value="${convenioEdicao.percentual}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">${modoFormulario eq 'editar' ? 'Salvar alteracoes' : 'Cadastrar'}</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/ConvenioControlador?opcao=novo">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Convenios cadastrados</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Nome</th>
                            <th>Percentual</th>
                            <th>Acoes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="convenio" items="${convenios}">
                            <tr>
                                <td>${convenio.codigoConvenio}</td>
                                <td>${convenio.nome}</td>
                                <td>${convenio.percentual}</td>
                                <td class="acoes-tabela">
                                    <a class="link-acao" href="${pageContext.request.contextPath}${URL_BASE}/ConvenioControlador?opcao=editar&codigoConvenio=${convenio.codigoConvenio}">Editar</a>
                                    <a class="link-acao excluir" href="${pageContext.request.contextPath}${URL_BASE}/ConvenioControlador?opcao=excluir&codigoConvenio=${convenio.codigoConvenio}" onclick="return confirm('Deseja excluir este convenio?');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty convenios}">
                            <tr>
                                <td colspan="4">Nenhum convenio cadastrado ainda.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
