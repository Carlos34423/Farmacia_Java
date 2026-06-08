<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
        <title>Farmacia</title>
     
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="banner-farmacia">
            <div class="overlay">
                <h1>Farmacia</h1>
                <p>Central de cadastros</p>
            </div>
        </div>

        <section class="conteudo-home">
            <div class="grid-cadastros">
                <div class="card-home">
                    <h2>Categoria</h2>
                    <a href="${pageContext.request.contextPath}${URL_BASE}/CategoriaMedicamentoControlador?opcao=listar">Abrir cadastro</a>
                </div>

                <div class="card-home">
                    <h2>Cargo</h2>
                    <a href="${pageContext.request.contextPath}${URL_BASE}/CargoControlador?opcao=listar">Abrir cadastro</a>
                </div>

                <div class="card-home">
                    <h2>Convenio</h2>
                    <a href="${pageContext.request.contextPath}${URL_BASE}/ConvenioControlador?opcao=listar">Abrir cadastro</a>
                </div>

                <div class="card-home">
                    <h2>Fabricante</h2>
                    <a href="${pageContext.request.contextPath}${URL_BASE}/FabricanteControlador?opcao=listar">Abrir cadastro</a>
                </div>

                <div class="card-home">
                    <h2>Forma de Pagamento</h2>
                    <a href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=listar">Abrir cadastro</a>
                </div>

                <div class="card-home">
                    <h2>Fornecedor</h2>
                    <a href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=listar">Abrir cadastro</a>
                </div>
            </div>
        </section>

        <footer class="footer-cassino">
            <div class="footer-conteudo">
                <div>
                    <h2>Farmacia</h2>
                </div>
                <div>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
                    </ul>
                </div>
            </div>
        </footer>
    </body>
</html>
