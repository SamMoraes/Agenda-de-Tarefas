package br.com.sammoraes.interfaces;

import java.util.Date;
import java.util.List;

import br.com.sammoraes.entities.Tarefa;

public interface ITarefaRepository {

	void create(Tarefa tarefa) throws Exception;
	
	void update(Tarefa tarefa) throws Exception;
	
	void delete(Tarefa tarefa) throws Exception;
	
	List<Tarefa> findAll(Integer idUsuario) throws Exception;
	
	List<Tarefa> findByDatas(Date dataMin, Date dataMax, Integer idUsuario) throws Exception;
	
	Tarefa findById(Integer idTarefa, Integer idUsuario) throws Exception;
}
