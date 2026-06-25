<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Medicamento</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>

<%@include file="menu.jsp" %>

<div class="conteudo-home">

    <div class="card-home">

        <h2>Cadastro de Medicamento</h2>
                    
        <form class="formulario"
              method="get"
              action="${pageContext.request.contextPath}${URL_BASE}/MedicamentoControlador">

            <input type="hidden"
                   name="opcao"
                   value="${modoFormulario eq 'editar' ? 'confirmarAlterar' : 'cadastrar'}">

            <input type="hidden"
                   name="codigoMedicamento"
                   value="${medicamentoEdicao.codigoMedicamento}">

            <label>Nome</label>
            <input type="text"
                   name="nome"
                   required
                   value="${medicamentoEdicao.nome}">

            <label>Descrição</label>
            <input type="text"
                   name="descricao"
                   required
                   value="${medicamentoEdicao.descricao}">

            <label>Preço de Venda</label>
            <input type="number"
                   step="0.01"
                   name="precoVenda"
                   required
                   value="${medicamentoEdicao.precoVenda}">

            <label>Quantidade em Estoque</label>
            <input type="number"
                   name="quantidadeEstoque"
                   required
                   value="${medicamentoEdicao.quantidadeEstoque}">

            <label>Categoria</label>
            <select name="codigoCategoriaMedicamento" required>

                <option value="">Selecione...</option>

                <c:forEach var="categoria" items="${listaCategorias}">
                    <option value="${categoria.codigoCategoriaMedicamento}"
                        ${medicamentoEdicao.categoriaMedicamento.codigoCategoriaMedicamento
                        == categoria.codigoCategoriaMedicamento
                        ? 'selected="selected"' : ''}>
                        ${categoria.nome}
                    </option>
                </c:forEach>

            </select>

            <label>Fabricante</label>
            <select name="cnpjFabricante" required>

                <option value="">Selecione...</option>

                <c:forEach var="fabricante" items="${listaFabricantes}">
                    <option value="${fabricante.cnpj}"
                        ${medicamentoEdicao.fabricante.cnpj
                        == fabricante.cnpj
                        ? 'selected="selected"' : ''}>
                        ${fabricante.nome}
                    </option>
                </c:forEach>

            </select>

            <label>Fornecedor</label>
            <select name="codigoFornecedor" required>

                <option value="">Selecione...</option>

                <c:forEach var="fornecedor" items="${listaFornecedores}">
                    <option value="${fornecedor.codigoFornecedor}"
                        ${medicamentoEdicao.fornecedor.codigoFornecedor
                        == fornecedor.codigoFornecedor
                        ? 'selected="selected"' : ''}>
                        ${fornecedor.nomeFantasia}
                    </option>
                </c:forEach>

            </select>

            <div class="acoes-formulario">
                <button class="botao" type="submit">
                    ${modoFormulario eq 'editar'
                    ? 'Salvar Alterações'
                    : 'Cadastrar'}
                </button>

                <a class="botao botao-secundario"
                   href="${pageContext.request.contextPath}${URL_BASE}/MedicamentoControlador?opcao=novo">
                    Limpar
                </a>
            </div>

        </form>

    </div>

    <div class="card-home tabela-home">

        <h2>Medicamentos Cadastrados</h2>

        <table class="tabela">

            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>Descrição</th>
                    <th>Preço</th>
                    <th>Estoque</th>
                    <th>Categoria</th>
                    <th>Fabricante</th>
                    <th>Fornecedor</th>
                    <th>Ações</th>
                </tr>
            </thead>

            <tbody>

                <c:forEach var="medicamento" items="${medicamentos}">
                    <tr>

                        <td>${medicamento.codigoMedicamento}</td>
                        <td>${medicamento.nome}</td>
                        <td>${medicamento.descricao}</td>
                        <td>${medicamento.precoVenda}</td>
                        <td>${medicamento.quantidadeEstoque}</td>
                        <td>${medicamento.categoriaMedicamento.nome}</td>
                        <td>${medicamento.fabricante.nome}</td>
                        <td>${medicamento.fornecedor.nomeFantasia}</td>

                        <td class="acoes-tabela">

                            <a class="link-acao"
                               href="${pageContext.request.contextPath}${URL_BASE}/MedicamentoControlador?opcao=editar&codigoMedicamento=${medicamento.codigoMedicamento}">
                                Editar
                            </a>

                            <a class="link-acao excluir"
                               href="${pageContext.request.contextPath}${URL_BASE}/MedicamentoControlador?opcao=excluir&codigoMedicamento=${medicamento.codigoMedicamento}"
                               onclick="return confirm('Deseja excluir este medicamento?');">
                                Excluir
                            </a>

                        </td>

                    </tr>
                </c:forEach>

                <c:if test="${empty medicamentos}">
                    <tr>
                        <td colspan="9">
                            Nenhum medicamento cadastrado.
                        </td>
                    </tr>
                </c:if>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>