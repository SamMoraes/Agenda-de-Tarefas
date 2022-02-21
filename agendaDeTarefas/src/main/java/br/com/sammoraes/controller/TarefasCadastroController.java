package br.com.sammoraes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sammoraes.entities.Tarefa;
import br.com.sammoraes.entities.Usuario;
import br.com.sammoraes.enums.PrioridadeTarefa;
import br.com.sammoraes.helpers.DateHelper;
import br.com.sammoraes.interfaces.ITarefaRepository;
import br.com.sammoraes.models.TarefaCadastroModel;

@Controller
public class TarefasCadastroController {
	
	@Autowired
	private ITarefaRepository tarefaRepository;
	
	// Método para abrir (rota) a página de cadastro
	@RequestMapping(value = "/tarefas-cadastro")
	public ModelAndView cadastro() {

		ModelAndView modelAndView = new ModelAndView("tarefas-cadastro");
		modelAndView.addObject("model", new TarefaCadastroModel());
		modelAndView.addObject("prioridades", PrioridadeTarefa.values());
		
		return modelAndView;
	}
	
	//Método para receber o SUBMIT POST do formulário
	@RequestMapping(value = "post-tarefas-cadastro", method = RequestMethod.POST)
	public ModelAndView postCadastro(TarefaCadastroModel model, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("tarefas-cadastro");
		
		//validação dos campos do formulário
		boolean isValid = true;
		
		if(model.getNome().trim().length() == 0) {
			modelAndView.addObject("erro_nome", "Por favor, informe o nome da tarefa.");
			isValid = false;
		}
		
		if(model.getData().trim().length() == 0) {
			modelAndView.addObject("erro_data", "Por favor, informe a data da tarefa.");
			isValid = false;
		}
		
		if(model.getHora().trim().length() == 0) {
			modelAndView.addObject("erro_hora", "Por favor, informe a hora da tarefa.");
			isValid = false;
		}
		
		if(model.getPrioridade().trim().length() == 0) {
			modelAndView.addObject("erro_prioridade", "Por favor, selecione a prioridade da tarefa.");
			isValid = false;
		}
		
		if(model.getDescricao().trim().length() == 0) {
			modelAndView.addObject("erro_descricao", "Por favor, informe a descrição da tarefa.");
			isValid = false;
		}
		
		try {
			
			if(isValid) {
				
				//capturar os dados da tarefa
				Tarefa tarefa = new Tarefa();
				
				tarefa.setNome(model.getNome());
				tarefa.setData(DateHelper.toDate(model.getData()));
				tarefa.setHora(model.getHora());
				tarefa.setPrioridade(model.getPrioridade());
				tarefa.setDescricao(model.getDescricao());
				tarefa.setUsuario((Usuario) request.getSession().getAttribute("usuario"));
				
				//gravar no banco de dados..
				tarefaRepository.create(tarefa);
				
				modelAndView.addObject("mensagem_sucesso", "Tarefa cadastrada com sucesso.");
				model = new TarefaCadastroModel();
			}			
		}
		catch(Exception e) {
			//mensagem de erro
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
				
		modelAndView.addObject("model", model);
		modelAndView.addObject("prioridades", PrioridadeTarefa.values());
		
		return modelAndView;
	}

}
