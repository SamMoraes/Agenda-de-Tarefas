<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>

<title>Agenda de Tarefas - Consulta</title>

<!-- Folha de estilos CSS do bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>

<body>

	<jsp:include page="/WEB-INF/views/components/menu.jsp" />

	<div class="container mt-4">
		
		<h5>Seja bem vindo à Agenda de tarefas!</h5>
		<hr/>
		
		<form method="post" action="post-tarefas-consulta">
		
			<div class="row">
				<div class="col-md-3">
					<label>Data de início:</label>
					<form:input path="model.dataMin" type="date" class="form-control"/>
					<span class="text-danger">
						${erro_dataMin}
					</span>
				</div>
				<div class="col-md-3">
					<label>Data de término:</label>
					<form:input path="model.dataMax" type="date" class="form-control"/>
					<span class="text-danger">
						${erro_dataMax}
					</span>
				</div>
				<div class="col-md-6">
					<input type="submit" value="Pesquisar Tarefas" class="btn btn-success mt-4"/>
				</div>
			</div>
		
		</form>
		
		<div class="text-success mt-2">
			<strong>${mensagem_sucesso}</strong>
		</div>
		
		<div class="text-danger mt-2">
			<strong>${mensagem_erro}</strong>
		</div>
		
		<c:if test="${tarefas.size() > 0}">
		
			<div class="row">
				<div class="col-md-3">
				
					<div class="row mb-3">
						<div class="col-md-12">
							<div class="card bg-success">
								<div class="card-body">
									<div class="row">
										<div class="col-md-8">
											<div class="text-white">Prioridade Baixa</div>
										</div>
										<div class="col-md-4 text-center">
											<strong class="text-white">${qtdbaixa}</strong>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row mb-3">
						<div class="col-md-12">
							<div class="card bg-warning">
								<div class="card-body">
									<div class="row">
										<div class="col-md-8">
											<div class="text-white">Prioridade Média</div>
										</div>
										<div class="col-md-4 text-center">
											<strong class="text-white">${qtdmedia}</strong>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row mb-3">
						<div class="col-md-12">
							<div class="card bg-danger">
								<div class="card-body">
									<div class="row">
										<div class="col-md-8">
											<div class="text-white">Prioridade Alta</div>
										</div>
										<div class="col-md-4 text-center">
											<strong class="text-white">${qtdalta}</strong>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
				<div class="col-md-9">
					<div id="grafico"></div>
				</div>
			</div>
		
		
			<table class="table table-sm table-hover mt-3">
				<thead>
					<tr>
						<th>Nome da tarefa</th>
						<th>Data</th>
						<th>Hora</th>
						<th>Descrição</th>
						<th>Prioridade</th>
						<th>Operações</th>
					</tr>
				</thead>
				<tbody>
				
					<c:forEach items="${tarefas}" var="t">
					
						<tr>
							<td>${t.nome}</td>
							<td>
								<fmt:formatDate value="${t.data}" pattern="E dd/MM/yyyy"/>
							</td>
							<td>${t.hora}</td>
							<td>${t.descricao}</td>
							<td>
								<c:if test="${t.prioridade == 'BAIXA'}">
									<span class="badge bg-success">BAIXA</span>
								</c:if>
								<c:if test="${t.prioridade == 'MEDIA'}">
									<span class="badge bg-warning">MÉDIA</span>
								</c:if>
								<c:if test="${t.prioridade == 'ALTA'}">
									<span class="badge bg-danger">ALTA</span>
								</c:if>
							</td>
							<td>
								<a href="/agendaDeTarefas/tarefas-edicao?id=${t.idTarefa}" 
								   	class="btn btn-primary btn-sm">
									Alterar
								</a>
								<a href="/agendaDeTarefas/tarefas-exclusao?id=${t.idTarefa}" 
									onclick="return confirm('Deseja realmente excluir a tarefa ${t.nome}?');"
									class="btn btn-danger btn-sm">
									Excluir
								</a>
							</td>
						</tr>			
					
					</c:forEach>				
						
				</tbody>
				<tfoot>
					<tr>
						<td colspan="6">
							Quantidade de tarefas: ${tarefas.size()}
						</td>
					</tr>
				</tfoot>
			</table>
		
		</c:if>
		
	</div>

	<!-- Arquivos javascript -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
		
	<!-- Arquivos javascript do HighCharts -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/highcharts-3d.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script>
	<script src="https://code.highcharts.com/modules/export-data.js"></script>
	
	<script>
		
		var array = [];
		
		//dados do gráfico
		array.push(['Prioridade ALTA', ${qtdalta}]);
		array.push(['Prioridade MEDIA', ${qtdmedia}]);
		array.push(['Prioridade BAIXA', ${qtdbaixa}]);
		
		//desenhando o gráfico
		new Highcharts.Chart({
			chart: {
				type : 'pie',
				renderTo: 'grafico',
				height: 240
			},
			plotOptions: {
				pie : {
					innerSize: '60%',
					dataLabels: { enable: true }
				}
			},
			title: {
				text: 'Tarefas por prioridade'
			},
			series: [
				{ data: array }
			],
			colors: ['#d9534f', '#f0ad4e', '#5cb85c']
		});
		
	</script>

</body>

</html>


