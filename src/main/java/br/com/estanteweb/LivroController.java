package br.com.estanteweb;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class LivroController {

    private final LivroRepository repositorio;

    public LivroController(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    // Página com todos os livros, com ordenação opcional pela query string
    @GetMapping({"/livros", "/livros/html"})
    public String listarLivros(
            @RequestParam(required = false) String ordem,
            Model model) {

        List<Livro> livros = (ordem == null)
                ? repositorio.listarTodos()
                : repositorio.listarOrdenadoPor(ordem);

        model.addAttribute("livros", livros);
        return "livros";
    }

    // Exibe a ficha de um livro a partir do ID no caminho da URL
    @GetMapping("/livros/{id}")
    public String detalharLivro(@PathVariable Long id, Model model) {
        Livro livro = repositorio.buscarPorId(id);

        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("livro", livro);
        model.addAttribute("outrosLivros", repositorio.listarDoMesmoAutor(livro.getAutor(), id));
        return "livro";
    }

    // Formulário de novo livro
    @GetMapping("/livros/novo")
    public String novoLivro() {
        return "livro-novo";
    }

    // Processamento do cadastro de livro com validação
    @PostMapping("/livros")
    public String salvarLivro(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false, defaultValue = "0") int ano,
            @RequestParam(required = false, defaultValue = "0") double preco,
            Model model) {

        if (titulo == null || titulo.isBlank()
                || autor == null || autor.isBlank()
                || preco <= 0) {

            model.addAttribute("erro", "Preencha título, autor e um preço maior que zero.");
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);

            return "livro-novo";
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);

        repositorio.salvar(livro);

        return "redirect:/livros/html";
    }

    // Abre o formulário de edição com os dados atuais do livro.
    @GetMapping("/livros/{id}/editar")
    public String editarLivro(@PathVariable Long id, Model model) {
        Livro livro = repositorio.buscarPorId(id);

        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("livro", livro);
        return "livro-editar";
    }

    // Salva a edição por POST. O identificador nunca é alterado pelo formulário.
    @PostMapping("/livros/{id}/editar")
    public String atualizarLivro(
            @PathVariable Long id,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false, defaultValue = "0") int ano,
            @RequestParam(required = false, defaultValue = "0") double preco,
            Model model) {

        if (repositorio.buscarPorId(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);

        if (titulo == null || titulo.isBlank()
                || autor == null || autor.isBlank()
                || ano <= 0 || preco <= 0 || !Double.isFinite(preco)) {
            model.addAttribute("erro", "Preencha título, autor, ano e um preço maior que zero.");
            model.addAttribute("livro", livro);
            return "livro-editar";
        }

        livro.setTitulo(titulo.trim());
        livro.setAutor(autor.trim());

        if (repositorio.atualizar(livro) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/livros/" + id;
    }

    // Exclusão somente por POST, para que nenhum link apague um registro.
    @PostMapping("/livros/{id}/excluir")
    public String excluirLivro(@PathVariable Long id) {
        if (repositorio.excluir(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:/livros/html";
    }

    // Conta quantos livros existem no banco
    @GetMapping("/livros/total")
    @ResponseBody
    public String calcularTotal() {

        int total = repositorio.contarLivros();

        return String.format(
                "<h1>Total de Livros: %d</h1>",
                total
        );
    }

    // Busca livros pelo preço máximo
    @GetMapping("/livros/busca")
    public ResponseEntity<String> precoMaximo(
            @RequestParam(defaultValue = "999") double precoMaximo) {

        List<Livro> livros =
                repositorio.listarAtePreco(precoMaximo);

        if (livros.isEmpty()) {

            String mensagem = String.format(
                    "<h1>Nenhum livro encontrado com o preço até R$ %.2f.</h1>",
                    precoMaximo
            );

            return ResponseEntity
                    .status(404)
                    .body(mensagem);
        }

        StringBuilder sb = new StringBuilder();

        sb.append(
                String.format(
                        "<h1>Livros até R$: %.2f</h1>",
                        precoMaximo
                )
        );

        sb.append("<ul>");

        for (Livro livro : livros) {

            sb.append("<li>")
                    .append(livro.getTitulo())
                    .append(" - ")
                    .append(livro.getAutor())
                    .append(" (")
                    .append(livro.getAno())
                    .append(") - R$ ")
                    .append(livro.getPreco())
                    .append("</li>");
        }

        sb.append("</ul>");

        return ResponseEntity
                .ok()
                .header(
                        "X-Total-Encontrados",
                        String.valueOf(livros.size())
                )
                .body(sb.toString());
    }

    // Mostra o navegador utilizado pelo usuário
    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(
            @RequestHeader("User-Agent") String userAgent) {

        return "<h1>Você acessou o sistema usando:</h1>"
                + "<p>"
                + userAgent
                + "</p>";
    }

    // Calcula o valor total dos livros
    @GetMapping("/livros/valor-total")
    @ResponseBody
    public double valorTotal() {

        return repositorio.valorTotal();
    }
}