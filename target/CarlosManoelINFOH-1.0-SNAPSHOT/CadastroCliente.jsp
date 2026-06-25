
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="Latin1"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=Latin1">
    <title>Cadastro de Cliente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Cadastro de Cliente</h2>

        <c:if test="${not empty mensagem}">
            <div class="mensagem sucesso">${mensagem}</div>
        </c:if>

        <c:if test="${not empty mensagemErro}">
            <div class="mensagem erro">${mensagemErro}</div>
        </c:if>

        <form class="formulario"
              method="get"
              action="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador">

            <input type="hidden"
                   name="opcao"
                   value="${opcao}">

            <input type="hidden"
                   name="codigoCliente"
                   value="${clienteEdicao.codigoCliente}">

            <label>Nome</label>
            <input type="text"
                   name="nome"
                   value="${clienteEdicao.nome}">

            <label>CPF</label>
            <input type="text"
                   name="cpf"
                   value="${clienteEdicao.cpf}">

            <label>Telefone</label>
            <input type="text"
                   name="telefone"
                   value="${clienteEdicao.telefone}">

            <label>Email</label>
            <input type="text"
                   name="email"
                   value="${clienteEdicao.email}">

            <label>Cidade</label>

            <select name="cidadeCliente">

                <option value="">Selecione...</option>

                <c:forEach var="cidade" items="${listaCidade}">
                    <option value="${cidade.codigoCidade}"
                        ${clienteEdicao.cidade.codigoCidade == cidade.codigoCidade ? 'selected="selected"' : ''}>
                        ${cidade.nome}
                    </option>
                </c:forEach>

            </select>

            <label>Convênio</label>

            <select name="convenioCliente">

                <option value="">Selecione...</option>

                <c:forEach var="convenio" items="${listaConvenio}">
                    <option value="${convenio.codigoConvenio}"
                        ${clienteEdicao.convenio.codigoConvenio == convenio.codigoConvenio ? 'selected="selected"' : ''}>
                        ${convenio.nome}
                    </option>
                </c:forEach>

            </select>

            <div class="acoes-formulario">

                <button class="botao" type="submit">
                    Salvar
                </button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador?opcao=cancelar">
                    Limpar
                </a>

            </div>

        </form>

    </div>

    <div class="card-home tabela-home">

        <h2>Clientes cadastrados</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Telefone</th>
                    <th>Email</th>
                    <th>Cidade</th>
                    <th>Convênio</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="cliente" items="${clientes}">

                    <tr>

                        <td>${cliente.codigoCliente}</td>
                        <td>${cliente.nome}</td>
                        <td>${cliente.cpf}</td>
                        <td>${cliente.telefone}</td>
                        <td>${cliente.email}</td>
                        <td>${cliente.cidade.nome}</td>
                        <td>${cliente.convenio.nome}</td>

                        <td class="acoes-tabela">

                            <a class="link-acao"
                               href="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador?opcao=enviarAlterar&codigoCliente=${cliente.codigoCliente}">
                                Editar
                            </a>

                            <a class="link-acao excluir"
                               href="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador?opcao=enviarExcluir&codigoCliente=${cliente.codigoCliente}"
                               onclick="return confirm('Deseja excluir este cliente?');">
                                Excluir
                            </a>

                        </td>

                    </tr>

                </c:forEach>

                <c:if test="${empty clientes}">
                    <tr>
                        <td colspan="8">
                            Nenhum cliente cadastrado ainda.
                        </td>
                    </tr>
                </c:if>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>