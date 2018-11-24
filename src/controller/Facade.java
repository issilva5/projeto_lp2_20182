package controller;

import java.io.IOException;

import easyaccept.EasyAccept;

public class Facade {

	UsuarioController usuarioController = new UsuarioController();
	ItemController itemController = new ItemController(usuarioController);

	// Controller Usuario

	public String adicionaDoador(String docId, String nome, String email, String celular, String classe) {
		return usuarioController.adicionaDoador(docId, nome, email, celular, classe);
	}

	public String pesquisaUsuarioPorId(String docId) {
		return this.usuarioController.pesquisaUsuarioPorId(docId);
	}

	public String pesquisaUsuarioPorNome(String nome) {
		return this.usuarioController.pesquisaUsuarioPorNome(nome);
	}

	public String atualizaUsuario(String docId, String nome, String email, String celular) {
		return this.usuarioController.atualizaUsuario(docId, nome, email, celular);
	}

	public void removeUsuario(String docId) {
		this.usuarioController.removeUsuario(docId);
	}

	public void lerReceptores(String path) throws IOException {
		this.usuarioController.lerReceptores(path);
	}

	public void atualizaReceptores(String path) {
		this.usuarioController.atualizaReceptores(path);
	}

	// ITEM CONTROLLER

	public String adicionaItemParaDoacao(String idDoador, String descricaoItem, int quantidade, String tags) {
		return this.itemController.adicionaItemParaDoacao(idDoador, descricaoItem, quantidade, tags);
	}

	public String exibeItem(String idItem, String idDoador) {
		return this.itemController.exibeItem(idItem, idDoador);
	}

	public String atualizaItemParaDoacao(String idItem, String idDoador, int quantidade, String tags) {
		return this.itemController.atualizaItemParaDoacao(idItem, idDoador, quantidade, tags);
	}

	public void removeItemParaDoacao(String idItem, String idDoador) {
		this.itemController.removeItemParaDoacao(idItem, idDoador);
	}

	// TESTES DE ACEITAÇÃO

	public static void main(String[] args) {
		args = new String[] { "controller.Facade", "acceptance_tests/use_case_1.txt" };
		EasyAccept.main(args);
	}

}
