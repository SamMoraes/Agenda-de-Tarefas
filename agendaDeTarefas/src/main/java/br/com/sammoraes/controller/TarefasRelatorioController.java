package br.com.sammoraes.controller;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.sammoraes.entities.Tarefa;
import br.com.sammoraes.entities.Usuario;
import br.com.sammoraes.helpers.DateHelper;
import br.com.sammoraes.interfaces.ITarefaRepository;
import br.com.sammoraes.models.TarefaRelatorioModel;
import br.com.sammoraes.reports.TarefaReportPdf;

@Controller
public class TarefasRelatorioController {

	@Autowired
	private ITarefaRepository tarefaRepository;

	// Método para abrir (rota) a página de relatorio
	@RequestMapping(value = "/tarefas-relatorio")
	public ModelAndView relatorio() {

		ModelAndView modelAndView = new ModelAndView("tarefas-relatorio");
		modelAndView.addObject("model", new TarefaRelatorioModel());

		return modelAndView;
	}

	@RequestMapping(value = "post-tarefas-relatorio", method = RequestMethod.POST)
	public ModelAndView postRelatorio(TarefaRelatorioModel model, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("tarefas-relatorio");
		modelAndView.addObject("model", model);

		boolean isValid = true;

		if (model.getDataMin().trim().length() == 0) {
			modelAndView.addObject("erro_dataMin", "Por favor, informe a data de início.");
			isValid = false;
		}

		if (model.getDataMax().trim().length() == 0) {
			modelAndView.addObject("erro_dataMax", "Por favor, informe a data de término.");
			isValid = false;
		}
		
		try {
			
			if(isValid) {
				
				//capturando o usuário autenticado na sessão
				Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
				
				//capturando as datas informadas
				Date dataMin = DateHelper.toDate(model.getDataMin());
				Date dataMax = DateHelper.toDate(model.getDataMax());
				
				//consultando as tarefas
				List<Tarefa> tarefas = tarefaRepository.findByDatas(dataMin, dataMax, usuario.getIdUsuario());
				
				//gerando o documento PDF
				TarefaReportPdf tarefaReport = new TarefaReportPdf();
				ByteArrayInputStream stream = tarefaReport.create(dataMin, dataMax, usuario, tarefas);
				
				//DOWNLOAD do documento PDF
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition", "attachment; filename=relatorio_tarefas.pdf");
				
				byte[] dados = stream.readAllBytes();
				
				OutputStream out = response.getOutputStream();
				out.write(dados, 0, dados.length);
				out.flush();
				out.close();
				
				response.getOutputStream().flush();
				return null;
			}			
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem_erro", e.getMessage());
		}

		return modelAndView;
	}
}
