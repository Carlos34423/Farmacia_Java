<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Fornecedor</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="conteudo-home">
            <div class="card-home">
                <h2>Cadastro de Fornecedor</h2>
                <c:if test="${not empty mensagem}">
                    <div class="mensagem sucesso">${mensagem}</div>
                </c:if>
                <c:if test="${not empty mensagemErro}">
                    <div class="mensagem erro">${mensagemErro}</div>
                </c:if>

                <form class="formulario" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador">
                    <input type="hidden" name="opcao" value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">
                    <input type="hidden" name="cnpjOriginal" value="${fornecedorEdicao.cnpjOriginal}">

                    <label for="razaoSocial">Razao Social</label>
                    <input id="razaoSocial" type="text" name="razaoSocial" maxlength="120" required value="${fornecedorEdicao.razaoSocial}">

                    <label for="nomeFantasia">Nome Fantasia</label>
                    <input id="nomeFantasia" type="text" name="nomeFantasia" maxlength="120" required value="${fornecedorEdicao.nomeFantasia}">

                    <label for="cnpj">CNPJ</label>
                    <input id="cnpj" type="text" name="cnpj" maxlength="18" required value="${fornecedorEdicao.cnpj}">

                    <label for="telefone">Telefone</label>
                    <input id="telefone" type="text" name="telefone" maxlength="20" required value="${fornecedorEdicao.telefone}">

                    <label for="email">Email</label>
                    <input id="email" type="email" name="email" maxlength="120" required value="${fornecedorEdicao.email}">

                    <label for="cidade">Cidade</label>
                    <input id="cidade" type="text" name="cidade" maxlength="80" required value="${fornecedorEdicao.cidade}">

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">${modoFormulario eq 'editar' ? 'Salvar alteracoes' : 'Cadastrar'}</button>
                        <a class="botao botao-secundario" href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=novo">Limpar</a>
                    </div>
                </form>
            </div>

            <div class="card-home tabela-home">
                <h2>Fornecedores cadastrados</h2>
                <table class="tabela">
                    <thead>
                        <tr>
                            <th>Razao Social</th>
                            <th>Nome Fantasia</th>
                            <th>CNPJ</th>
                            <th>Telefone</th>
                            <th>Email</th>
                            <th>Cidade</th>
                            <th>Acoes</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="fornecedor" items="${fornecedores}">
                            <tr>
                                <td>${fornecedor.razaoSocial}</td>
                                <td>${fornecedor.nomeFantasia}</td>
                                <td>${fornecedor.cnpj}</td>
                                <td>${fornecedor.telefone}</td>
                                <td>${fornecedor.email}</td>
                                <td>${fornecedor.cidade}</td>
                                <td class="acoes-tabela">
                                    <a class="link-acao" href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=editar&cnpj=${fornecedor.cnpj}">Editar</a>
                                    <a class="link-acao excluir" href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=excluir&cnpj=${fornecedor.cnpj}" onclick="return confirm('Deseja excluir este fornecedor?');">Excluir</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty fornecedores}">
                            <tr>
                                <td colspan="7">Nenhum fornecedor cadastrado ainda.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
