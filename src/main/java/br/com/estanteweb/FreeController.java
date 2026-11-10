package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class FreeController {
    
    @GetMapping("/free")
    public String free(Model container) {

        String nome = "Baby do Biruleibe Leibe";
        String sobrenome = "Aires";

        container.addAttribute("nome", nome);
        container.addAttribute("sobrenome", sobrenome);

        return "ola";
    }

}
