<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>

<head>

<title>Agenda de Tarefas - Relatório</title>

<!-- Folha de estilos CSS do bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>

<body>

	<jsp:include page="/WEB-INF/views/components/menu.jsp" />

	<div class="container mt-4">

		<h5>Relatório de Tarefas</h5>
		<p>Utilize os campos abaixo para gerar o relatório de tarefas.</p>
		<hr />

		<form method="post" action="post-tarefas-relatorio">

			<div class="row mb-3">
				<div class="col-md-3">
					<label>Data de início:</label>
					<form:input path="model.dataMin" type="date" class="form-control" />
					<span class="text-danger">
						${erro_dataMin}
					</span>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col-md-3">
					<label>Data de Término:</label>
					<form:input path="model.dataMax" type="date" class="form-control" />
					<span class="text-danger">
						${erro_dataMax}
					</span>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col-md-3">
					<input type="submit" value="Gerar Relatório"
						class="btn btn-success" />
				</div>
			</div>

		</form>

	</div>

	<!-- Arquivos javascript -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>

</html>