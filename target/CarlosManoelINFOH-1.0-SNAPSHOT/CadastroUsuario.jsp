<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Usuário</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Cadastro de Usuário</h2>

        <c:if test="${not empty mensagemErroCadastro}">
            <div class="mensagem erro">${mensagemErroCadastro}</div>
        </c:if>

        <form class="formulario"
              method="post"
              action="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">

            <input type="hidden" name="opcao" value="cadastrar">

            <label>Nome de usuário</label>
            <input type="text" name="nomeusuario" required>

            <label>Senha</label>
            <input type="password" name="senha" required>

            <label>E-mail</label>
            <input type="email" name="email" required>

            <div class="acoes-formulario">

                <button class="botao" type="submit">Cadastrar</button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}/login.jsp">
                    Voltar
                </a>

            </div>

        </form>

    </div>

</div>

</body>
</html>
