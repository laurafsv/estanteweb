package br.com.estanteweb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

class LivroRepositoryTest {

    @Test
    void deveAtualizarApenasOIdInformado() {
        JdbcTemplate banco = org.mockito.Mockito.mock(JdbcTemplate.class);
        LivroRepository repositorio = new LivroRepository(banco);
        Livro livro = new Livro();
        livro.setId(4);
        livro.setTitulo("Novo título");
        livro.setAutor("Autora");
        livro.setAno(2025);
        livro.setPreco(19.90);

        String sql = "UPDATE livro SET titulo = ?, autor = ?, ano = ?, preco = ? WHERE id = ?";
        when(banco.update(sql, "Novo título", "Autora", 2025, 19.90, 4L)).thenReturn(1);

        assertEquals(1, repositorio.atualizar(livro));
        verify(banco).update(sql, "Novo título", "Autora", 2025, 19.90, 4L);
    }

    @Test
    void deveExcluirApenasOIdInformado() {
        JdbcTemplate banco = org.mockito.Mockito.mock(JdbcTemplate.class);
        LivroRepository repositorio = new LivroRepository(banco);
        String sql = "DELETE FROM livro WHERE id = ?";
        when(banco.update(sql, 4L)).thenReturn(1);

        assertEquals(1, repositorio.excluir(4L));
        verify(banco).update(sql, 4L);
    }
}
