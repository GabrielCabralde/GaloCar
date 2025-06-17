package br.appLogin.appLogin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.appLogin.appLogin.model.Avaliacao;
import br.appLogin.appLogin.repository.AvaliacaoRepository;
import br.appLogin.appLogin.repository.CarroRepository;
import br.appLogin.appLogin.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class AvaliacaoController {
	
    @Autowired
    private AvaliacaoRepository avaliacaoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private CarroRepository carroRepo;

    @GetMapping("/avaliar")
    public String exibirFormularioAvaliacao(Model model, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/login";
        }

        model.addAttribute("carros", carroRepo.findAll()); // envia lista de carros para a view
        return "avaliarcarro"; // mesmo nome da sua página atual
    }

    @PostMapping("/avaliar")
    public String avaliar(@RequestParam int nota,
                          @RequestParam String comentario,
                          @RequestParam Long carroId,
                          HttpSession session) {
    	Long usuarioId = (long)session.getAttribute("usuarioId");
    	if (usuarioId == null) {
    	        return "redirect:/login"; // força login se não estiver autenticado
    	    }
    	
    	
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario);

        // Relacionamento com as entidades
        avaliacao.setUsuario(usuarioRepo.findById(usuarioId).orElse(null));
        avaliacao.setCarro(carroRepo.findById(carroId).orElse(null));

        avaliacaoRepo.save(avaliacao);
        return "redirect:/"; // redireciona após avaliar
    }
    
    // ✅ Listar todas as avaliações
    @GetMapping("/avaliacoes")
    public String listarAvaliacoes(Model model) {
        List<Avaliacao> avaliacoes = avaliacaoRepo.findAll();
        model.addAttribute("avaliacoes", avaliacoes);
        return "lista-avaliacoes"; // você deve criar essa view
    }

    // ✅ Buscar avaliação por ID
    @GetMapping("/avaliacao/{id}")
    public String buscarPorId(@PathVariable Long id, Model model) {
        Optional<Avaliacao> avaliacao = avaliacaoRepo.findById(id);
        if (avaliacao.isPresent()) {
            model.addAttribute("avaliacao", avaliacao.get());
            return "avaliacao-detalhes"; // você deve criar essa view
        } else {
            model.addAttribute("erro", "Avaliação não encontrada");
            return "erro";
        }
    }

    // ✅ Excluir avaliação por ID
    @GetMapping("/avaliacao/excluir/{id}")
    public String excluirAvaliacao(@PathVariable Long id) {
        if (avaliacaoRepo.existsById(id)) {
            avaliacaoRepo.deleteById(id);
        }
        return "redirect:/avaliacoes";
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Encerra a sessão
        return "redirect:/login"; // Redireciona para tela de login
    }
    
    
    
    

}
