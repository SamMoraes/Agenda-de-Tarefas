package br.com.sammoraes.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {

	public AuthenticationFilter() {
	}

	public void destroy() {
	}

	//Método utilizado para criar a regra de acesso às páginas restritas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//Criar objetos para manipular a navegação das páginas
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//lista das páginas que não precisam de autenticação do usuário
		List<String> urlsPermitidas = new ArrayList<String>();
		
		urlsPermitidas.add("/"); //página de login
		urlsPermitidas.add("/post-login"); //submit do formulário de autenticação
		urlsPermitidas.add("/register"); //página de criação de conta de usuário
		urlsPermitidas.add("/post-register"); //submit do formulário de criação de conta
		urlsPermitidas.add("/esqueci-minha-senha"); //página de esqueci minha senha
		urlsPermitidas.add("/post-esqueci-minha-senha"); //submit do formulário de lembrete de senha
		
		//Capturando o endereço da página que o usuário está acessando
		String paginaAtual = req.getServletPath();
		
		//verificando se o usuário está tentando acessar
		//alguma página de acesso restrito
		if(!urlsPermitidas.contains(paginaAtual)) {
			
			//verificar se o usuário NÃO está autenticado (gravado em sessão)
			if(req.getSession().getAttribute("usuario") == null) {
				//redirecionar o usuário de volta para a página de login
				resp.sendRedirect("/projetoWeb01/");
			}			
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}




