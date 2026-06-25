
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="Latin1"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=Latin1">
    <title>Cadastro de Funcionário</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Cadastro de Funcionário</h2>

        <c:if test="${not empty mensagem}">
            <div class="mensagem sucesso">${mensagem}</div>
        </c:if>

        <form class="formulario"
              method="get"
              action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador">

            <input type="hidden"
                   name="opcao"
                   value="${empty opcao ? 'cadastrar' : opcao}">

            <input type="hidden"
                   name="codigoFuncionario"
                   value="${funcionarioEdicao.codigoFuncionario}">

            <label>Nome</label>
            <input type="text"
                   name="nome"
                   value="${funcionarioEdicao.nome}">

            <label>CPF</label>
            <input type="text"
                   name="cpf"
                   value="${funcionarioEdicao.cpf}">

            <label>Telefone</label>
            <input type="text"
                   name="telefone"
                   value="${funcionarioEdicao.telefone}">

            <label>Email</label>
            <input type="text"
                   name="email"
                   value="${funcionarioEdicao.email}">

            <label>Cargo</label>
            <select name="cargoFuncionario">

                <option value="">Selecione...</option>

                <c:forEach var="cargo" items="${listaCargo}">
                    <option value="${cargo.codigoCargo}"
                        ${funcionarioEdicao.cargo.codigoCargo == cargo.codigoCargo ? 'selected="selected"' : ''}>
                        ${cargo.nome}
                    </option>
                </c:forEach>

            </select>

            <label>Cidade</label>
            <select name="cidadeFuncionario">

                <option value="">Selecione...</option>

                <c:forEach var="cidade" items="${listaCidade}">
                    <option value="${cidade.codigoCidade}"
                        ${funcionarioEdicao.cidade.codigoCidade == cidade.codigoCidade ? 'selected="selected"' : ''}>
                        ${cidade.nome}
                    </option>
                </c:forEach>

            </select>

            <div class="acoes-formulario">

                <button class="botao" type="submit">
                    Salvar
                </button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=cancelar">
                    Limpar
                </a>

            </div>

        </form>

    </div>

    <div class="card-home tabela-home">

        <h2>Funcionários cadastrados</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Telefone</th>
                    <th>Email</th>
                    <th>Cargo</th>
                    <th>Cidade</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="funcionario" items="${funcionarios}">

                    <tr>

                        <td>${funcionario.codigoFuncionario}</td>
                        <td>${funcionario.nome}</td>
                        <td>${funcionario.cpf}</td>
                        <td>${funcionario.telefone}</td>
                        <td>${funcionario.email}</td>
                        <td>${funcionario.cargo.nome}</td>
                        <td>${funcionario.cidade.nome}</td>

                        <td class="acoes-tabela">

                            <a class="link-acao"
                               href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=enviarAlterar&codigoFuncionario=${funcionario.codigoFuncionario}">
                                Editar
                            </a>

                            <a class="link-acao excluir"
                               href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=enviarExcluir&codigoFuncionario=${funcionario.codigoFuncionario}"
                               onclick="return confirm('Deseja excluir este funcionário?');">
                                Excluir
                            </a>

                        </td>

                    </tr>

                </c:forEach>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>