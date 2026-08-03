<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Cidade</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>

<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Cadastro de Cidade</h2>


        <c:if test="${not empty mensagem}">
            <div class="mensagem sucesso">
                ${mensagem}
            </div>
        </c:if>


        <c:if test="${not empty mensagemErro}">
            <div class="mensagem erro">
                ${mensagemErro}
            </div>
        </c:if>


        <form class="formulario"
              method="get"
              action="${pageContext.request.contextPath}${URL_BASE}/CidadeControlador">


<input type="hidden"
       name="opcao"
       value="${opcao}">


            <input type="hidden"
                   name="codigoCidade"
                   value="${codigoCidade}">


            <label>Nome da Cidade</label>

            <input type="text"
                   name="nome"
                   maxlength="80"
                   required
                   value="${nome}">


            <label>UF</label>

            <input type="text"
                   name="uf"
                   maxlength="2"
                   required
                   value="${uf}">


            <div class="acoes-formulario">

                <button class="botao" type="submit">
                    Salvar
                </button>


                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/CidadeControlador?opcao=cancelar">
                    Limpar
                </a>

            </div>


        </form>

    </div>



    <div class="card-home tabela-home">

        <h2>Cidades cadastradas</h2>


        <table class="tabela">

            <thead>

                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>UF</th>
                    <th>Ações</th>
                </tr>

            </thead>


            <tbody>


            <c:forEach var="cidade" items="${cidades}">

                <tr>

                    <td>${cidade.codigoCidade}</td>

                    <td>${cidade.nome}</td>

                    <td>${cidade.uf}</td>


                    <td class="acoes-tabela">


                        <a class="link-acao"
                           href="${pageContext.request.contextPath}${URL_BASE}/CidadeControlador?opcao=enviarAlterar&codigoCidade=${cidade.codigoCidade}">
                            Editar
                        </a>


                        <a class="link-acao excluir"
                           href="${pageContext.request.contextPath}${URL_BASE}/CidadeControlador?opcao=confirmarExcluir&codigoCidade=${cidade.codigoCidade}"
                           onclick="return confirm('Deseja excluir esta cidade?');">
                            Excluir
                        </a>


                    </td>

                </tr>


            </c:forEach>


            <c:if test="${empty cidades}">

                <tr>
                    <td colspan="4">
                        Nenhuma cidade cadastrada.
                    </td>
                </tr>

            </c:if>


            </tbody>

        </table>


    </div>


</div>


</body>

</html>