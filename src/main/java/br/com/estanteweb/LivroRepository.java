package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LivroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public LivroRepository() {
    }

    public LivroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Salva um novo livro no banco
    public void salvar(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, ano, preco) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, livro.getTitulo(), livro.getAutor(), livro.getAno(), livro.getPreco());
    }

    // Atualiza somente o registro correspondente ao identificador informado.
    public int atualizar(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, ano = ?, preco = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getPreco(),
                livro.getId()
        );
    }

    // Remove somente o livro identificado na cláusula WHERE.
    public int excluir(Long id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Busca todos os livros no banco
    public List<Livro> listarTodos() {

        String sql = "SELECT * FROM livro";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    // Busca um livro específico pelo identificador
    public Livro buscarPorId(Long id) {
        String sql = "SELECT * FROM livro WHERE id = ?";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, id);

        if (linhas.isEmpty()) {
            return null;
        }

        return Livro.deMap(linhas.get(0));
    }

    // Ordena a listagem somente por colunas permitidas.
    // O nome de uma coluna não pode ser passado como parâmetro SQL (?);
    // por isso validamos a opção antes de montar o ORDER BY.
    public List<Livro> listarOrdenadoPor(String coluna) {
        if (coluna == null) {
            return listarTodos();
        }

        String colunaPermitida = switch (coluna) {
            case "titulo" -> "titulo";
            case "autor" -> "autor";
            case "ano" -> "ano";
            case "preco" -> "preco";
            default -> null;
        };

        if (colunaPermitida == null) {
            return listarTodos();
        }

        String sql = "SELECT * FROM livro ORDER BY " + colunaPermitida;
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);
        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    // Encontra outros títulos do mesmo autor, excluindo o livro atual.
    public List<Livro> listarDoMesmoAutor(String autor, Long idAtual) {
        String sql = "SELECT * FROM livro WHERE autor = ? AND id <> ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, autor, idAtual);
        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    // Mantém compatibilidade caso outro ponto chame listarTodas
    public List<Livro> listarTodas() {
        return listarTodos();
    }

    // Conta quantos livros existem no banco
    public int contarLivros() {

        String sql = "SELECT COUNT(*) FROM livro";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Busca livros até determinado preço
    public List<Livro> listarAtePreco(double precoMaximo) {

        String sql = "SELECT * FROM livro WHERE preco <= ?";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql, precoMaximo);

        List<Livro> livrosAtePreco = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livrosAtePreco.add(Livro.deMap(linha));
        }

        return livrosAtePreco;
    }

    // Soma o preço de todos os livros (Desafio Missão 4.4)
    public double somarValorAcervo() {

        String sql = "SELECT SUM(preco) FROM livro";

        Double total = jdbcTemplate.queryForObject(sql, Double.class);

        return total != null ? total : 0.0;
    }

    // Nome alternativo para manter a compatibilidade com as aulas anteriores
    public double valorTotal() {
        return somarValorAcervo();
    }
}