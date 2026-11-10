<#include "cabecalho.ftlh">

<h1>Sugira um Livro</h1>

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/sugestoes">

    <label for="nomeCliente">Seu Nome:</label><br>
    <input type="text"
           id="nomeCliente"
           name="nomeCliente"
           value="${nomeCliente!}"><br><br>

    <label for="livroSugerido">Livro Sugerido:</label><br>
    <input type="text"
           id="livroSugerido"
           name="livroSugerido"
           value="${livroSugerido!}"><br><br>

    <button type="submit">Enviar Sugestão</button>

</form>