<#include "cabecalho.ftlh">

<h1>${livro.titulo}</h1>

<p><strong>Autor:</strong> ${livro.autor}</p>
<p><strong>Ano:</strong> ${livro.ano?c}</p>
<p><strong>Preço:</strong> R$ ${livro.preco?c}</p>

<p><a href="/livros/${livro.id?c}/editar">Editar livro</a></p>

<form method="post" action="/livros/${livro.id?c}/excluir"
      onsubmit="return confirm('Deseja mesmo excluir este livro? Essa ação não pode ser desfeita.');">
    <button type="submit">Excluir livro</button>
</form>

<#if outrosLivros?has_content>
    <h2>Outros títulos do mesmo autor</h2>
    <ul>
        <#list outrosLivros as outro>
            <li><a href="/livros/${outro.id?c}">${outro.titulo}</a></li>
        </#list>
    </ul>
</#if>

<p><a href="/livros/html">Voltar para o acervo</a></p>
