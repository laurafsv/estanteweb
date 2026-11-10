package br.com.estanteweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PaginaController {

    @GetMapping("/")
    public String paginaInicial() {
        return "<h1>EstanteWeb: livros usados com história, boas vindas</h1>";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "<h1>Sobre a EstanteWeb</h1><p>A EstanteWeb é uma plataforma online que conecta leitores apaixonados por livros usados. Nosso objetivo é proporcionar uma experiência única, permitindo que os usuários compartilhem suas histórias literárias e encontrem obras com significado especial.</p>";
    }

    @GetMapping("/contato")
    public String contato(Model model) {
        return "contato";
    }

    @PostMapping("/contato")
    public String enviarContato(
            @RequestParam String nome,
            @RequestParam String mensagem,
            Model model) {

        if (mensagem == null || mensagem.isBlank()) {
            model.addAttribute("erro", "A mensagem não pode ficar em branco.");
            model.addAttribute("nome", nome);
            return "contato";
        }

        return "redirect:/contato/sucesso";
    }

    @GetMapping("/contato/sucesso")
    public String contatoSucesso() {
        return "contato-sucesso";
    }

}
