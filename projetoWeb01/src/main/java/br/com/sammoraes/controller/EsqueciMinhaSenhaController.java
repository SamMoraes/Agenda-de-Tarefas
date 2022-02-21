package br.com.sammoraes.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sammoraes.entities.Usuario;
import br.com.sammoraes.interfaces.IUsuarioRepository;
import br.com.sammoraes.messages.MailMessage;
import br.com.sammoraes.models.EsqueciMinhaSenhaModel;

@Controller
public class EsqueciMinhaSenhaController {
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@RequestMapping(value = "/esqueci-minha-senha")
	public ModelAndView esqueciMinhaSenha() {

		ModelAndView modelAndView = new ModelAndView("esqueci-minha-senha");
		modelAndView.addObject("model", new EsqueciMinhaSenhaModel());

		return modelAndView;
	}

	@RequestMapping(value = "post-esqueci-minha-senha", method = RequestMethod.POST)
	public ModelAndView postEsqueciMinhaSenha(EsqueciMinhaSenhaModel model) {

		ModelAndView modelAndView = new ModelAndView("esqueci-minha-senha");

		boolean isValid = true;

		if (model.getEmail() == null || model.getEmail().trim().length() == 0) {
			modelAndView.addObject("erro_email", "Por favor, informe seu email.");
			isValid = false;
		}
		
		try {
			
			if(isValid) {
				
				//buscar o usuário no banco de dados atraves do email
				Usuario usuario = usuarioRepository.findByEmail(model.getEmail());
				
				//verificar se o usuário foi encontrado
				if(usuario != null) {
					
					//gerar uma nova senha para o usuario
					String novaSenha = String.valueOf(new Random().nextInt(999999999));
					
					//enviando um email para o usuario com esta nova senha..
					MailMessage.send(usuario.getEmail(),
									 "Nova senha gerada com sucesso - Controle de Tarefas",
									 "Olá, " + usuario.getNome() + "\n\n" +
									 "Sua nova senha de acesso é: " + novaSenha + ".\n" + 
									 "Utilize esta senha para acessar o sistema e depois atualize para uma senha de sua preferencia, através do menu 'Configurações / Atualizar Minha Senha'." + 
									 "\n\nAtt," + 
									 "Equipe SamMoraes");
					
					//atualizando a senha no banco de dados
					usuarioRepository.updateSenha(usuario.getIdUsuario(), novaSenha);
					
					modelAndView.addObject("mensagem_sucesso", "Nova senha gerada e enviada para o email com sucesso.");
					model = new EsqueciMinhaSenhaModel();
				}
				else {
					modelAndView.addObject("mensagem_erro", "O email informado é inválido.");
				}
			}			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}

		modelAndView.addObject("model", model);
		return modelAndView;
	}

}





