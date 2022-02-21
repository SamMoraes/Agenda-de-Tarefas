package br.com.sammoraes.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.sammoraes.interfaces.ITarefaRepository;
import br.com.sammoraes.interfaces.IUsuarioRepository;
import br.com.sammoraes.repositories.TarefaRepository;
import br.com.sammoraes.repositories.UsuarioRepository;

@Configuration
@ComponentScan(basePackages="br.com.cotiinformatica")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean //método de configuração do Spring
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	/*
	 * Método para criar a conexão com o banco de dados do MySQL (DATA SOUCE)
	 */
	@Bean //método de configuração do Spring
	public DataSource getDataSource() {
		
		//configuar a conexão com o banco de dados do mysql:
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(" ENDERECO DO BANCO DE DADOS ");
		dataSource.setUsername(" USUARIO DO BANCO DE DADOS ");
		dataSource.setPassword(" SENHA DO BANCO DE DADOS");
		
		return dataSource;
	}
	
	/*
	 * Registrar todos os repositorios criados no projeto
	 */
	@Bean
	public IUsuarioRepository getUsuarioRepository() {
		return new UsuarioRepository(getDataSource());
	}
	
	@Bean
	public ITarefaRepository getTarefaRepository() {
		return new TarefaRepository(getDataSource());
	}
}





