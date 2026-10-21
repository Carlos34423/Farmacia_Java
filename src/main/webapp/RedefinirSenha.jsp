<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redefinir Senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Redefinir Senha</h2>

        <c:if test="${not empty mensagemErroRedefinicao}">
            <div class="mensagem erro">${mensagemErroRedefinicao}</div>
        </c:if>

        <form class="formulario"
              method="post"
              action="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">

            <input type="hidden" name="opcao" value="confirmarRedefinicao">
            <input type="hidden" name="token" value="${token}">

            <label>Nova senha</label>
            <input type="password" name="novaSenha" required>

            <div class="acoes-formulario">
                <button class="botao" type="submit">Salvar nova senha</button>
            </div>

        </form>

    </div>

</div>

</body>
</html>
