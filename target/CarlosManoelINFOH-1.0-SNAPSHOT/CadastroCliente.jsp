
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
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
                   class="${not empty errosCampo.nome ? 'campo-invalido' : ''}"
                   value="${clienteEdicao.nome}">
            <c:if test="${not empty errosCampo.nome}">
                <span class="erro-campo">${errosCampo.nome}</span>
            </c:if>

            <label>CPF</label>
            <input type="text"
                   name="cpf"
                   class="${not empty errosCampo.cpf ? 'campo-invalido' : ''}"
                   value="${clienteEdicao.cpf}">
            <c:if test="${not empty errosCampo.cpf}">
                <span class="erro-campo">${errosCampo.cpf}</span>
            </c:if>

            <label>Telefone</label>
            <input type="text"
                   name="telefone"
                   value="${clienteEdicao.telefone}">

            <label>Email</label>
            <input type="text"
                   name="email"
                   value="${clienteEdicao.email}">

            <label>Carteira (R$)</label>
            <input type="text"
                   name="carteira"
                   class="${not empty errosCampo.carteira ? 'campo-invalido' : ''}"
                   value="${clienteEdicao.carteira}">
            <c:if test="${not empty errosCampo.carteira}">
                <span class="erro-campo">${errosCampo.carteira}</span>
            </c:if>

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
                    <th>Carteira (R$)</th>
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
                        <td>${cliente.carteira}</td>

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
                        <td colspan="9">
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