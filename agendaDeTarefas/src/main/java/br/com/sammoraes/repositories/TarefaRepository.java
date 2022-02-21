package br.com.sammoraes.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.sammoraes.entities.Tarefa;
import br.com.sammoraes.helpers.DateHelper;
import br.com.sammoraes.interfaces.ITarefaRepository;

public class TarefaRepository implements ITarefaRepository {

	private JdbcTemplate jdbcTemplate;
	
	public TarefaRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void create(Tarefa tarefa) throws Exception {

		String sql = "INSERT INTO TAREFA(NOME, DESCRICAO, DATA, HORA, PRIORIDADE, IDUSUARIO) "
				   + "VALUES(?, ?, ?, ?, ?, ?)";

		Object[] params = {
			tarefa.getNome(),
			tarefa.getDescricao(),
			DateHelper.toString(tarefa.getData()),
			tarefa.getHora(),
			tarefa.getPrioridade(),
			tarefa.getUsuario().getIdUsuario()
		};
		
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void update(Tarefa tarefa) throws Exception {

		String sql = "UPDATE TAREFA SET NOME = ?, DESCRICAO = ?, DATA = ?, HORA = ?, PRIORIDADE = ? "
				   + "WHERE IDTAREFA = ? AND IDUSUARIO = ?";
		
		Object[] params = {
				tarefa.getNome(),
				tarefa.getDescricao(),
				DateHelper.toString(tarefa.getData()),
				tarefa.getHora(),
				tarefa.getPrioridade(),
				tarefa.getIdTarefa(),
				tarefa.getUsuario().getIdUsuario()
			};

		jdbcTemplate.update(sql, params);
	}

	@Override
	public void delete(Tarefa tarefa) throws Exception {

		String sql = "DELETE FROM TAREFA WHERE IDTAREFA = ? AND IDUSUARIO = ?";
		
		Object[] params = {
				tarefa.getIdTarefa(),
				tarefa.getUsuario().getIdUsuario()
			};
		
		jdbcTemplate.update(sql, params);
	}

	@Override
	public List<Tarefa> findAll(Integer idUsuario) throws Exception {

		String sql = "SELECT * FROM TAREFA WHERE IDUSUARIO = ? ORDER BY DATA, HORA";
		
		Object[] params = {
			idUsuario
		};
		
		List<Tarefa> lista = jdbcTemplate.query(sql, params, new RowMapper<Tarefa>() {
			@Override
			public Tarefa mapRow(ResultSet rs, int rowNum) throws SQLException {

				Tarefa tarefa = new Tarefa();
				
				tarefa.setIdTarefa(rs.getInt("IDTAREFA"));
				tarefa.setNome(rs.getString("NOME"));
				tarefa.setDescricao(rs.getString("DESCRICAO"));
				tarefa.setData(rs.getDate("DATA"));
				tarefa.setHora(rs.getString("HORA"));
				tarefa.setPrioridade(rs.getString("PRIORIDADE"));
				
				return tarefa;
			}	
		});
		
		return lista;
	}

	@Override
	public List<Tarefa> findByDatas(Date dataMin, Date dataMax, Integer idUsuario) throws Exception {

		String sql = "SELECT * FROM TAREFA WHERE IDUSUARIO = ? AND DATA BETWEEN ? AND ? "
				   + "ORDER BY DATA, HORA";
		
		Object[] params = {
				idUsuario,
				DateHelper.toString(dataMin), 
				DateHelper.toString(dataMax)
			};
		
		List<Tarefa> lista = jdbcTemplate.query(sql, params, new RowMapper<Tarefa>() {
			@Override
			public Tarefa mapRow(ResultSet rs, int rowNum) throws SQLException {

				Tarefa tarefa = new Tarefa();
				
				tarefa.setIdTarefa(rs.getInt("IDTAREFA"));
				tarefa.setNome(rs.getString("NOME"));
				tarefa.setDescricao(rs.getString("DESCRICAO"));
				tarefa.setData(rs.getDate("DATA"));
				tarefa.setHora(rs.getString("HORA"));
				tarefa.setPrioridade(rs.getString("PRIORIDADE"));
				
				return tarefa;
			}	
		});
		
		return lista;
	}

	@Override
	public Tarefa findById(Integer idTarefa, Integer idUsuario) throws Exception {

		String sql = "SELECT * FROM TAREFA WHERE IDTAREFA = ? AND IDUSUARIO = ?";
		
		Object[] params = {
				idTarefa,
				idUsuario
			};
		
		List<Tarefa> lista = jdbcTemplate.query(sql, params, new RowMapper<Tarefa>() {
			@Override
			public Tarefa mapRow(ResultSet rs, int rowNum) throws SQLException {

				Tarefa tarefa = new Tarefa();
				
				tarefa.setIdTarefa(rs.getInt("IDTAREFA"));
				tarefa.setNome(rs.getString("NOME"));
				tarefa.setDescricao(rs.getString("DESCRICAO"));
				tarefa.setData(rs.getDate("DATA"));
				tarefa.setHora(rs.getString("HORA"));
				tarefa.setPrioridade(rs.getString("PRIORIDADE"));
				
				return tarefa;
			}	
		});
		
		if(lista != null && lista.size() == 1) {
			return lista.get(0);
		}
		else {
			return null;
		}	
	}
}
