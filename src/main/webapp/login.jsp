<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Login</h2>

        <c:if test="${not empty mensagem}">
            <div class="mensagem sucesso">${mensagem}</div>
        </c:if>

        <c:if test="${not empty mensagemErro}">
            <div class="mensagem erro">${mensagemErro}</div>
        </c:if>

        <c:choose>

            <%-- Formulário de login --%>
            <c:when test="${empty param.modo}">

                <form class="formulario"
                      method="post"
                      action="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">

                    <input type="hidden" name="opcao" value="autenticar">

                    <label>Usuário</label>
                    <input type="text" name="nomeusuario" required>

                    <label>Senha</label>
                    <input type="password" name="senha" required>

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">Entrar</button>
                    </div>

                </form>

                <div class="acoes-formulario">
                    <a class="link-acao"
                       href="${pageContext.request.contextPath}${URL_BASE}/LoginControlador?opcao=novoCadastro">
                        Cadastrar novo usuário
                    </a>

                    <a class="link-acao"
                       href="${pageContext.request.contextPath}/login.jsp?modo=recuperar">
                        Esqueci minha senha
                    </a>
                </div>

            </c:when>

            <%-- Formulário "Esqueci minha senha" --%>
            <c:otherwise>

                <form class="formulario"
                      method="post"
                      action="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">

                    <input type="hidden" name="opcao" value="enviarRecuperacao">

                    <label>Informe o e-mail cadastrado</label>
                    <input type="email" name="email" required>

                    <div class="acoes-formulario">
                        <button class="botao" type="submit">Enviar link de recuperação</button>
                    </div>

                </form>

                <div class="acoes-formulario">
                    <a class="link-acao" href="${pageContext.request.contextPath}/login.jsp">
                        Voltar para o login
                    </a>
                </div>

            </c:otherwise>

        </c:choose>

    </div>

</div>

</body>
</html>
