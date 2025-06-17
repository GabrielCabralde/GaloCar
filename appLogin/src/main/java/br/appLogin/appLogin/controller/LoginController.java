package br.appLogin.appLogin.controller;

import java.util.List;
import java.util.Optional;

//import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.appLogin.appLogin.model.Usuario;
import br.appLogin.appLogin.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioRepository ur;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String dashboard() {
		return "pagina-inicial";
	}

	@PostMapping("logar")
	public String loginUsuario(Usuario usuario, Model model, HttpSession session) {
		Usuario usuarioLogado = ur.login(usuario.getEmail(), usuario.getSenha());
		
		if (usuarioLogado != null) {
	        session.setAttribute("usuarioId", usuarioLogado.getId()); // SALVA o ID na sessão
	        session.setAttribute("usuarioNome", usuarioLogado.getNome()); // opcional: salvar nome também
	        return "redirect:/";
	    }

	    model.addAttribute("erro", "Usuário inválido");
	    return "login";
	}
	
	
	@GetMapping("/cadastro")
	public String cadastro() {
		return "cadastro";
	}
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public String cadastroUsuario(@Valid Usuario usuario, BindingResult result) {
		
		if(result.hasErrors()) {
			return "redirect:/cadastro";
			
		}
		
		ur.save(usuario);
		
		return "redirect:/login";
	}
	
	@GetMapping("/usuario/excluir/{id}")
	public String excluirUsuario(@PathVariable Long id) {
		if(ur.existsById(id)) {
			ur.deleteById(id);
		}
		return "redirect:/usuarios";
	}
	
	@GetMapping("/usuario/{id}")
	public String consultaUsuario(@PathVariable Long id, Model model) {
		Optional<Usuario> usuario = ur.findById(id);
		if(usuario.isPresent()){
			model.addAttribute("usuario", usuario.get());
			return "usuario-detalhes";
		}else {
			model.addAttribute("erro", usuario.get());
			return "erro";
		}
	}
	
	@GetMapping("/usuarios")
	public String ListarUsuarios(Model model) {
		List<Usuario> usuarios = ur.findAll();
		model.addAttribute("usuarios", usuarios);
		return "lista-usuarios";
	}
	
	

}
