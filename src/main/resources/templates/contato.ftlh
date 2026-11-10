<#include "cabecalho.ftlh">

<h1>Entre em Contato</h1>

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/contato">

    <label for="nome">Nome:</label><br>
    <input type="text" id="nome" name="nome" value="${nome!}"><br><br>

    <label for="mensagem">Mensagem:</label><br>
    <textarea id="mensagem" name="mensagem"></textarea><br><br>

    <button type="submit">Enviar</button>

</form>