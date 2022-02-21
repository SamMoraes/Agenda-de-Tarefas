package br.com.sammoraes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sammoraes.entities.Usuario;
import br.com.sammoraes.interfaces.IUsuarioRepository;
import br.com.sammoraes.models.LoginModel;

@Controller
public class LoginController {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	// método para abrir a página login.jsp (página inicial)
	@RequestMapping(value = "/")
	public ModelAndView login() {

		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("model", new LoginModel());

		return modelAndView;
	}

	// método para capturar o SUBMIT POST do formulário
	@RequestMapping(value = "post-login", method = RequestMethod.POST)
	public ModelAndView postLogin(LoginModel model, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("login");
		
		try {
			
			boolean isValid = true;
			
			//validar o campo 'email' como obrigatório
			if(model.getEmail().trim().length() == 0) {
				modelAndView.addObject("erro_email", "Por favor, informe seu email de acesso.");
				isValid = false;
			}

			//validar o campo 'senha' como obrigatório
			if(model.getSenha().trim().length() == 0) {
				modelAndView.addObject("erro_senha", "Por favor, informe sua senha de acesso.");
				isValid = false;
			}
			
			if(isValid) {
				
				//consultar o usuario no banco de dados, baseado no email e na senha
				Usuario usuario = usuarioRepository.findByEmailAndSenha(model.getEmail(), model.getSenha());
				
				//verificar se o usuario foi encontrado
				if(usuario != null) {
					
					//gravar os dados do usuário em uma sessão
					request.getSession().setAttribute("usuario", usuario);
					
					//redirecionar para a página de consulta de tarefas..
					modelAndView.setViewName("redirect:tarefas-consulta");
				}
				else {
					modelAndView.addObject("mensagem_erro", "Acesso negado. Usuário inválido.");
				}
			}
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", "Ocorreu um erro: " + e.getMessage());
		}
		
		modelAndView.addObject("model", model);
		return modelAndView;
	}
	
	// método para abrir a página login.jsp (página inicial)
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {

		//apagando os dados do usuário gravado em sessão
		request.getSession().removeAttribute("usuario");
		
		//redirecionar para a página de login
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/");
		
		return modelAndView;
	}	
}



