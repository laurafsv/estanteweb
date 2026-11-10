<#include "cabecalho.ftlh">

<h1>Editar livro</h1>

<#if erro??>
    <p role="alert" style="color: red;">${erro}</p>
</#if>

<form method="post" action="/livros/${livro.id?c}/editar">
    <label for="titulo">Título:</label><br>
    <input type="text" id="titulo" name="titulo" value="${livro.titulo!}" required><br><br>

    <label for="autor">Autor:</label><br>
    <input type="text" id="autor" name="autor" value="${livro.autor!}" required><br><br>

    <label for="ano">Ano:</label><br>
    <input type="number" id="ano" name="ano" value="${livro.ano?c}" min="1" required><br><br>

    <label for="preco">Preço:</label><br>
    <input type="number" id="preco" name="preco" value="${livro.preco?c}" min="0.01" step="0.01" required><br><br>

    <button type="submit">Salvar alterações</button>
    <a href="/livros/${livro.id?c}">Cancelar</a>
</form>
