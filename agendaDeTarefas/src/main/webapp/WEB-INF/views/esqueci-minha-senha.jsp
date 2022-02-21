<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>

<head>

<title>COTI Informática - Projeto Agenda</title>

<!-- folhas de estilo CSS da página -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>

<body class="bg-secondary">

	<div class="card mt-5 col-md-4 offset-md-4">
		<div class="card-body text-center">
			
			<h5 class="card-title">Esqueci minha senha</h5>
			<p class="card-text">Entre com seu email para recuperar a senha de acesso.</p>
			
			<hr/>
			
			<!-- formulário para preenchimento dos dados do usuário (login) -->
			<form method="post" action="post-esqueci-minha-senha">
			
				<label>Email de acesso:</label>
				<form:input path="model.email" type="text" class="form-control" placeholder="Ex: joaopedro@gmail.com"/>			
				<span class="text-danger">
					${erro_email}
				</span>
				<br/>
				
				<div class="d-grid">
					<input type="submit" value="Recuperar Senha" class="btn btn-success"/>
				</div>
			
			</form>
			
			<div class="mt-3 mb-3">
				
				<div class="text-success">
					<strong>${mensagem_sucesso}</strong>
				</div>
				
				<div class="text-danger">
					<strong>${mensagem_erro}</strong>
				</div>
				
			</div>
			
			<div class="d-grid">
				<a href="/agendaDeTarefas/">Voltar para a página de login.</a>
			</div>

		</div>
	</div>

	<!-- arquivos javascript da página -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>




