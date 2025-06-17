package br.appLogin.appLogin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.appLogin.appLogin.model.Carro;
import br.appLogin.appLogin.repository.CarroRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarroController {
	
	 @Autowired
	    private CarroRepository carroRepo;

	    @GetMapping("/carro/cadastro")
	    public String exibirFormularioCadastro() {
	        return "cadastrocarro"; // nome do HTML (src/main/resources/templates/cadastro-carro.html)
	    }

	    @PostMapping("/carro/cadastrar")
	    public String cadastrarCarro(@ModelAttribute Carro carro) {
	        carroRepo.save(carro);
	        return "redirect:/"; // redireciona para página inicial após cadastro
	    }
	    
	    // LISTAR TODOS OS CARROS
	    @GetMapping("/carros")
	    public String listarCarros(Model model) {
	        List<Carro> carros = carroRepo.findAll();
	        model.addAttribute("carros", carros);
	        return "lista-carros";
	    }

	    // LISTAR CARRO POR ID
	    @GetMapping("/carro/{id}")
	    public String visualizarCarro(@PathVariable Long id, Model model) {
	        Optional<Carro> carroOptional = carroRepo.findById(id);
	        if (carroOptional.isPresent()) {
	            model.addAttribute("carro", carroOptional.get());
	            return "carro-detalhes";
	        } else {
	            model.addAttribute("erro", "Carro não encontrado");
	            return "erro";
	        }
	    }

	    // EXCLUIR CARRO POR ID
	    @GetMapping("/carro/excluir/{id}")
	    public String excluirCarro(@PathVariable Long id) {
	        if (carroRepo.existsById(id)) {
	            carroRepo.deleteById(id);
	        }
	        return "redirect:/carros";
	    }
	
	    

}
