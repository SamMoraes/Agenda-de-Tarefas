package br.com.sammoraes.models;

public class AtualizarSenhaModel {

	private String senhaAtual;
	private String novaSenha;
	private String novaSenhaConfirmacao;

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaConfirmacao() {
		return novaSenhaConfirmacao;
	}

	public void setNovaSenhaConfirmacao(String novaSenhaConfirmacao) {
		this.novaSenhaConfirmacao = novaSenhaConfirmacao;
	}

}



