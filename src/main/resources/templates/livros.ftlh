<#include "cabecalho.ftlh">

<h1>Lista de Livros</h1>

<p>Ordenar por:
    <a href="/livros/html">Padrão</a> |
    <a href="/livros/html?ordem=titulo">Título</a> |
    <a href="/livros/html?ordem=autor">Autor</a> |
    <a href="/livros/html?ordem=ano">Ano</a> |
    <a href="/livros/html?ordem=preco">Preço</a>
</p>

<#if livros?size == 0>
    <p>Nenhum livro no acervo no momento.</p>
<#else>
    <ul>
        <#list livros as livro>
            <li>
                <a href="/livros/${livro.id?c}">${livro.titulo}</a>
                - ${livro.autor} - R$ ${livro.preco?c}
                <a href="/livros/${livro.id?c}/editar">Editar</a>
                <form method="post" action="/livros/${livro.id?c}/excluir" style="display: inline;"
                      onsubmit="return confirm('Deseja mesmo excluir este livro? Essa ação não pode ser desfeita.');">
                    <button type="submit">Excluir</button>
                </form>
            </li>
        </#list>
    </ul>
</#if>
