
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="Latin1"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=Latin1">
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

        <form class="formulario"
              method="get"
              action="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador">

            <input type="hidden"
                   name="opcao"
                   value="${opcao}">

            <input type="hidden"
                   name="codigoFornecedor"
                   value="${codigoFornecedor}">

            <label>Razão Social</label>
            <input type="text"
                   name="razaoSocial"
                   value="${razaoSocial}">

            <label>Nome Fantasia</label>
            <input type="text"
                   name="nomeFantasia"
                   value="${nomeFantasia}">

            <label>CNPJ</label>
            <input type="text"
                   name="cnpj"
                   value="${cnpj}">

            <label>Telefone</label>
            <input type="text"
                   name="telefone"
                   value="${telefone}">

            <label>Email</label>
            <input type="text"
                   name="email"
                   value="${email}">

            <label>Cidade</label>

            <select name="cidadeFuncionario">

                <option value="">Selecione...</option>

                <c:forEach var="cidade" items="${listaCidade}">
                    <option value="${cidade.codigoCidade}"
                        ${cidade.codigoCidade == cidadeFuncionario ? 'selected="selected"' : ''}>
                        ${cidade.nome}
                    </option>
                </c:forEach>

            </select>

            <div class="acoes-formulario">

                <button class="botao" type="submit">
                    Salvar
                </button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=cancelar">
                    Limpar
                </a>

            </div>

        </form>

    </div>

    <div class="card-home tabela-home">

        <h2>Fornecedores cadastrados</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Código</th>
                    <th>Razão Social</th>
                    <th>Nome Fantasia</th>
                    <th>CNPJ</th>
                    <th>Telefone</th>
                    <th>Email</th>
                    <th>Cidade</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="fornecedor" items="${fornecedores}">

                    <tr>

                        <td>${fornecedor.codigoFornecedor}</td>
                        <td>${fornecedor.razaoSocial}</td>
                        <td>${fornecedor.nomeFantasia}</td>
                        <td>${fornecedor.cnpj}</td>
                        <td>${fornecedor.telefone}</td>
                        <td>${fornecedor.email}</td>
                        <td>${fornecedor.cidade.nome}</td>

                        <td class="acoes-tabela">

                            <a class="link-acao"
                               href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=enviarAlterar&codigoFornecedor=${fornecedor.codigoFornecedor}">
                                Editar
                            </a>

                            <a class="link-acao excluir"
                               href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=enviarExcluir&codigoFornecedor=${fornecedor.codigoFornecedor}"
                               onclick="return confirm('Deseja excluir este fornecedor?');">
                                Excluir
                            </a>

                        </td>

                    </tr>

                </c:forEach>

                <c:if test="${empty fornecedores}">
                    <tr>
                        <td colspan="8">
                            Nenhum fornecedor cadastrado ainda.
                        </td>
                    </tr>
                </c:if>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>