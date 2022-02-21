package br.com.sammoraes.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.sammoraes.entities.Usuario;
import br.com.sammoraes.interfaces.IUsuarioRepository;

public class UsuarioRepository implements IUsuarioRepository {

	// componente do Spring para acesso ao banco de dados
	// atraves do DataSource configurado na classe MvcConfiguration
	private JdbcTemplate jdbcTemplate;

	// construtor para receber a conexão do banco de dados
	public UsuarioRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Usuario usuario) throws Exception {

		String sql = "INSERT INTO USUARIO(NOME, EMAIL, SENHA) VALUES(?, ?, MD5(?))";

		Object[] params = {
			usuario.getNome(),
			usuario.getEmail(),
			usuario.getSenha()
		};
		
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Usuario findByEmail(String email) throws Exception {

		String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";
		
		Object[] params = {
			email
		};
		
		List<Usuario> lista = jdbcTemplate.query(sql, params, new RowMapper<Usuario>() {
			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Usuario usuario = new Usuario();
				
				usuario.setIdUsuario(rs.getInt("IDUSUARIO"));
				usuario.setNome(rs.getString("NOME"));
				usuario.setEmail(rs.getString("EMAIL"));
				usuario.setSenha(rs.getString("SENHA"));
				
				return usuario;
			}	
		});
		
		//verificar se algum usuario foi encontrado na consulta
		if(lista != null && lista.size() == 1) {
			return lista.get(0); //retornando o usuário encontrado
		}
		else {
			return null; //retornando vazio
		}
	}

	@Override
	public Usuario findByEmailAndSenha(String email, String senha) throws Exception {

		String sql = "SELECT * FROM USUARIO WHERE EMAIL = ? AND SENHA = MD5(?)";
		
		Object[] params = {
			email,
			senha
		};
		
		List<Usuario> lista = jdbcTemplate.query(sql, params, new RowMapper<Usuario>() {
			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Usuario usuario = new Usuario();
				
				usuario.setIdUsuario(rs.getInt("IDUSUARIO"));
				usuario.setNome(rs.getString("NOME"));
				usuario.setEmail(rs.getString("EMAIL"));
				usuario.setSenha(rs.getString("SENHA"));
				
				return usuario;
			}	
		});
		
		//verificar se algum usuario foi encontrado na consulta
		if(lista != null && lista.size() == 1) {
			return lista.get(0); //retornando o usuário encontrado
		}
		else {
			return null; //retornando vazio
		}		
	}

	@Override
	public void updateSenha(Integer idUsuario, String novaSenha) throws Exception {

		String sql = "UPDATE USUARIO SET SENHA = MD5(?) WHERE IDUSUARIO = ?";
		
		Object[] params = {
			novaSenha,
			idUsuario
		};
		
		jdbcTemplate.update(sql, params);
	}
}



