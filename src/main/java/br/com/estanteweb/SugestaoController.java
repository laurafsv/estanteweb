package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SugestaoController {

    @Autowired
    private SugestaoRepository repositorio;

    public SugestaoController() {
    }

    public SugestaoController(SugestaoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping("/sugestoes/nova")
    public String novaSugestao() {
        return "sugestao-nova";
    }

    @PostMapping("/sugestoes")
    public String salvarSugestao(
            @RequestParam String nomeCliente,
            @RequestParam String livroSugerido,
            Model model) {

        if (nomeCliente == null || nomeCliente.isBlank()
                || livroSugerido == null || livroSugerido.isBlank()) {

            model.addAttribute("erro", "Preencha seu nome e o livro sugerido.");
            model.addAttribute("nomeCliente", nomeCliente);
            model.addAttribute("livroSugerido", livroSugerido);

            return "sugestao-nova";
        }

        Sugestao sugestao = new Sugestao();
        sugestao.setNomeCliente(nomeCliente);
        sugestao.setLivroSugerido(livroSugerido);

        repositorio.salvar(sugestao);

        return "redirect:/sugestoes?enviada=true";
    }

    @GetMapping("/sugestoes")
    public String listarSugestoes(
            @RequestParam(required = false) Boolean enviada,
            Model model) {

        model.addAttribute("sugestoes", repositorio.listarTodas());
        model.addAttribute("enviada", enviada);

        return "sugestoes";
    }

}