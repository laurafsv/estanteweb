package br.com.estanteweb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.server.ResponseStatusException;

class LivroControllerTest {

    @Test
    void deveAbrirFormularioComDadosAtuais() {
        LivroRepository repositorio = org.mockito.Mockito.mock(LivroRepository.class);
        LivroController controller = new LivroController(repositorio);
        Livro livro = criarLivro(3L);
        when(repositorio.buscarPorId(3L)).thenReturn(livro);
        ExtendedModelMap modelo = new ExtendedModelMap();

        String pagina = controller.editarLivro(3L, modelo);

        assertEquals("livro-editar", pagina);
        assertEquals(livro, modelo.get("livro"));
    }

    @Test
    void deveAtualizarSomenteOLivroEscolhido() {
        LivroRepository repositorio = org.mockito.Mockito.mock(LivroRepository.class);
        LivroController controller = new LivroController(repositorio);
        when(repositorio.buscarPorId(3L)).thenReturn(criarLivro(3L));
        when(repositorio.atualizar(any(Livro.class))).thenReturn(1);

        String resultado = controller.atualizarLivro(
                3L, "Novo título", "Nova autora", 2024, 32.50, new ExtendedModelMap());

        ArgumentCaptor<Livro> registro = ArgumentCaptor.forClass(Livro.class);
        verify(repositorio).atualizar(registro.capture());
        assertEquals(3L, registro.getValue().getId());
        assertEquals("Novo título", registro.getValue().getTitulo());
        assertEquals("redirect:/livros/3", resultado);
    }

    @Test
    void naoDeveSalvarCamposInvalidos() {
        LivroRepository repositorio = org.mockito.Mockito.mock(LivroRepository.class);
        LivroController controller = new LivroController(repositorio);
        when(repositorio.buscarPorId(3L)).thenReturn(criarLivro(3L));
        ExtendedModelMap modelo = new ExtendedModelMap();

        String pagina = controller.atualizarLivro(
                3L, "", "Autora", 2024, 29.90, modelo);

        assertEquals("livro-editar", pagina);
        assertTrue(modelo.containsAttribute("erro"));
        verify(repositorio, never()).atualizar(any(Livro.class));
    }

    @Test
    void deveExcluirERedirecionarParaListagem() {
        LivroRepository repositorio = org.mockito.Mockito.mock(LivroRepository.class);
        LivroController controller = new LivroController(repositorio);
        when(repositorio.excluir(3L)).thenReturn(1);

        assertEquals("redirect:/livros/html", controller.excluirLivro(3L));
        verify(repositorio).excluir(3L);
    }

    @Test
    void deveRetornar404ParaLivroInexistente() {
        LivroRepository repositorio = org.mockito.Mockito.mock(LivroRepository.class);
        LivroController controller = new LivroController(repositorio);

        ResponseStatusException erroEdicao = assertThrows(
                ResponseStatusException.class,
                () -> controller.editarLivro(999L, new ExtendedModelMap()));
        assertEquals(HttpStatus.NOT_FOUND, erroEdicao.getStatusCode());

        ResponseStatusException erroExclusao = assertThrows(
                ResponseStatusException.class,
                () -> controller.excluirLivro(999L));
        assertEquals(HttpStatus.NOT_FOUND, erroExclusao.getStatusCode());
    }

    private Livro criarLivro(Long id) {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo("Livro de exemplo");
        livro.setAutor("Autora de exemplo");
        livro.setAno(2020);
        livro.setPreco(29.90);
        return livro;
    }
}
