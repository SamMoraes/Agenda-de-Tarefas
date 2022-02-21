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
import br.com.sammoraes.models.TarefaEdicaoModel;

@Controller
public class TarefasEdicaoController {

	@Autowired
	private ITarefaRepository tarefaRepository;

	// Método para abrir (rota) a página de edicao
	@RequestMapping(value = "/tarefas-edicao")
	public ModelAndView edicao(int id, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("tarefas-edicao");		
		TarefaEdicaoModel model = new TarefaEdicaoModel();
		
		try {
			
			//buscando o usuário autenticado na sessão
			Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
			
			//buscando a tarefa no banco de dados atraves do ID..
			Tarefa tarefa = tarefaRepository.findById(id, usuario.getIdUsuario());
			
			//carregando os dados na página
			model.setIdTarefa(tarefa.getIdTarefa());
			model.setNome(tarefa.getNome());
			model.setData(DateHelper.toString(tarefa.getData()));
			model.setHora(tarefa.getHora());
			model.setDescricao(tarefa.getDescricao());
			model.setPrioridade(tarefa.getPrioridade());
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		
		modelAndView.addObject("model", model);
		modelAndView.addObject("prioridades", PrioridadeTarefa.values());
		
		return modelAndView;
	}
	
	@RequestMapping(value = "post-tarefas-edicao", method = RequestMethod.POST)
	public ModelAndView postEdicao(TarefaEdicaoModel model, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("tarefas-edicao");	
		
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
				
				Tarefa tarefa = new Tarefa();
				
				tarefa.setIdTarefa(model.getIdTarefa());
				tarefa.setNome(model.getNome());
				tarefa.setData(DateHelper.toDate(model.getData()));
				tarefa.setHora(model.getHora());
				tarefa.setPrioridade(model.getPrioridade());
				tarefa.setDescricao(model.getDescricao());
				tarefa.setUsuario((Usuario) request.getSession().getAttribute("usuario"));
				
				tarefaRepository.update(tarefa);
			
				modelAndView.addObject("mensagem_sucesso", "Tarefa atualizada com sucesso.");
			}			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}
		
		modelAndView.addObject("model", model);
		modelAndView.addObject("prioridades", PrioridadeTarefa.values());
		
		return modelAndView;
		
	}
}
