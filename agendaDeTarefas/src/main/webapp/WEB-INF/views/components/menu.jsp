<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Agenda de Tarefas</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/agendaDeTarefas/tarefas-consulta">Minhas Tarefas</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Operações
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/agendaDeTarefas/tarefas-cadastro">Cadastrar Tarefa</a></li>
            <li><a class="dropdown-item" href="/agendaDeTarefas/tarefas-relatorio">Relatório de Tarefas</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Configurações
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/agendaDeTarefas/atualizar-senha">Atualizar Minha Senha</a></li>
          </ul>
        </li>
      </ul>
      
      <form class="d-flex mt-1 mb-0">
          <div class="text-white">
          	<small><strong>${usuario.nome}</strong> (${usuario.email})</small>&nbsp;&nbsp;
          	<a href="/agendaDeTarefas/logout" class="btn btn-outline-light btn-sm"
          		onclick="return confirm('Deseja realmente sair do sistema?')">
            	Sair do Sistema
          	</a>
          </div>
      </form>
      
    </div>
  </div>
</nav>




