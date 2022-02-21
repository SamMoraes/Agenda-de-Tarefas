package br.com.sammoraes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sammoraes.entities.Usuario;
import br.com.sammoraes.interfaces.IUsuarioRepository;
import br.com.sammoraes.models.RegisterModel;

@Controller
public class RegisterController {
	
	//inicializando o repositorio
	@Autowired //faz a inicialização automática do atributo
	private IUsuarioRepository usuarioRepository;

	// Método executado para abrir a página
	@RequestMapping(value = "register")
	public ModelAndView register() {

		ModelAndView modelAndView = new ModelAndView("register");
		modelAndView.addObject("model", new RegisterModel());

		return modelAndView;
	}

	// Método para receber os dados enviados pelo formulário (POST)
	@RequestMapping(value = "post-register", method = RequestMethod.POST)
	public ModelAndView postRegister(RegisterModel model) {

		ModelAndView modelAndView = new ModelAndView("register");

		try {
			
			boolean isValid = true;
			
			if (model.getNome().trim().length() == 0) {
				modelAndView.addObject("erro_nome", "Por favor, informe seu nome de usuário.");
				isValid = false;
			}

			if (model.getEmail().trim().length() == 0) {
				modelAndView.addObject("erro_email", "Por favor, informe seu email de acesso.");
				isValid = false;
			}

			if (model.getSenha().trim().length() == 0) {
				modelAndView.addObject("erro_senha", "Por favor, informe sua senha de acesso.");
				isValid = false;
			}

			if (!model.getSenha().equals(model.getSenhaConfirmacao())) {
				modelAndView.addObject("erro_senhaConfirmacao", "Senhas não conferem, por favor verifique.");
				isValid = false;
			}
			
			//verificar se o email informado já está cadastrado no banco de dados
			if(usuarioRepository.findByEmail(model.getEmail()) != null) {
				modelAndView.addObject("erro_email", "O email informado já foi cadastrado no sistema, tente outro.");
				isValid = false;
			}
			
			//verificar se não há erros
			if(isValid) {
				
				Usuario usuario = new Usuario();
				
				usuario.setNome(model.getNome());
				usuario.setEmail(model.getEmail());
				usuario.setSenha(model.getSenha());
				
				usuarioRepository.create(usuario);
				
				modelAndView.addObject("mensagem_sucesso", "Parabéns, sua conta de usuário foi criada com sucesso!");
				model = new RegisterModel(); //limpar os campos da página
			}			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}

		modelAndView.addObject("model", model);
		return modelAndView;
	}
}
