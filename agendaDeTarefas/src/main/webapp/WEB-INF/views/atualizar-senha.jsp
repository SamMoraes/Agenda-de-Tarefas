<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>

<head>

<title>Agenda de Tarefas - Atualizar Senha</title>

<!-- Folha de estilos CSS do bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>

<body>

	<jsp:include page="/WEB-INF/views/components/menu.jsp" />

	<div class="container mt-4">
		
		<h5>Atualizar minha senha</h5>
		<p>Preencha os campos abaixo para alterar sua senha de acesso.</p>
		<hr/>
		
		<form method="post" action="post-atualizar-senha">
		
			<div class="row">
				<div class="col-md-4">
					<label>Informe a senha atual:</label>
					<form:input path="model.senhaAtual" type="password" class="form-control" placeholder="Digite aqui"/>
					<span class="text-danger">
						${erro_senhaAtual}
					</span>
				</div>		
				<div class="col-md-4">
					<label>Informe a nova senha:</label>
					<form:input path="model.novaSenha" type="password" class="form-control" placeholder="Digite aqui"/>
					<span class="text-danger">
						${erro_novaSenha}
					</span>
				</div>	
				<div class="col-md-4">
					<label>Confirme a nova senha:</label>
					<form:input path="model.novaSenhaConfirmacao" type="password" class="form-control" placeholder="Digite aqui"/>
					<span class="text-danger">
						${erro_novaSenhaConfirmacao}
					</span>
				</div>	
			</div>
			
			<div class="mt-3">
				<div class="col-md-12">
					<input type="submit" value="Salvar Alterações" class="btn btn-success"/>
				</div>
			</div>
		
		</form>
		
		<div class="text-success mt-2">
			<strong>${mensagem_sucesso}</strong>
		</div>
		
		<div class="text-danger mt-2">
			<strong>${mensagem_erro}</strong>
		</div>
		
	</div>

	<!-- Arquivos javascript -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>


