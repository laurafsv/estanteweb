package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


@Controller
public class ReservaController {
    
    @GetMapping("/reserva")
    public String reserva() {
        return "reserva";
    }

    @PostMapping("/reserva")
    public String postReserva(
        @RequestParam String nome,
        @RequestParam String telefone,
        Model container) 
        {
            if (telefone.isBlank()) {
                container.addAttribute("erro", " o campo Telefone é obrigatório");
                container.addAttribute("nome", nome);
                return "reserva";
            }

            return "reserva";
        }
}
