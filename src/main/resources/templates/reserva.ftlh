<h1>Reserva de Livros</h1>

<#if erro??>
    <h1>${erro}</h1>
</#if>

<form method="POST" action="/reserva">
    <input type="text" name="nome" value="${nome!}">

    <input type="tel" name="telefone">

    <button type="submit">Reservar</button>
</form>