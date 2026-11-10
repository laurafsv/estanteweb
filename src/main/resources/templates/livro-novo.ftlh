<#include "cabecalho.ftlh">

<h1>Cadastrar Novo Livro</h1>

<#if erro??>
    <p style="color: red;">${erro}</p>
</#if>

<form method="post" action="/livros">

    <label for="titulo">Título:</label><br>
    <input type="text"
           id="titulo"
           name="titulo"
           value="${titulo!}"><br><br>

    <label for="autor">Autor:</label><br>
    <input type="text"
           id="autor"
           name="autor"
           value="${autor!}"><br><br>

    <label for="ano">Ano:</label><br>
    <input type="number"
           id="ano"
           name="ano"
           value="${(ano?c)!}"><br><br>

    <label for="preco">Preço:</label><br>
    <input type="number"
           step="0.01"
           id="preco"
           name="preco"
           value="${(preco?c)!}"><br><br>

    <button type="submit">Cadastrar Livro</button>

</form>
