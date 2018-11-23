package controller;

import java.util.HashSet;
import java.util.Set;


public class ItemController {

	private Set<String> descritores;

	private UsuarioController usuarioController;

	private int numeroID;

	public ItemController(UsuarioController usuarioController) {
		this.descritores = new HashSet<>();
		this.usuarioController = usuarioController;
		this.numeroID = 1;
	}

	public void adicionaDescritor(String descricao) {

		if (descricao == null || descricao.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: descricao nao pode ser vazia ou nula.");
		}

		if (this.descritores.contains(descricao)) {
			throw new UnsupportedOperationException("Descritor de Item ja existente: " + descricao);
		}

		this.descritores.add(descricao);

	}

	public String adicionaItemParaDoacao(String idDoador, String descricaoItem, int quantidade, String tags) {

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		if (descricaoItem == null || descricaoItem.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: descricao nao pode ser vazia ou nula.");

		}

		if (quantidade <= 0) {

			throw new IllegalArgumentException("Entrada invalida: quantidade deve ser maior que zero.");

		}

		if (tags == null) {

			throw new NullPointerException("Entrada invalida: tag nao pode ser nula");

		}

	
		if (usuarioController.existeUsuario(idDoador) == false) {

			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador);

		}

		if (!this.descritores.contains(descricaoItem)) {

			this.descritores.add(descricaoItem);
		}

		this.usuarioController.adicionaItemParaDoacao(idDoador,numeroID, descricaoItem, quantidade, tags);

		this.numeroID++;

		return Integer.toString(numeroID - 1);
	}

	public String exibeItem(String idItem, String idDoador) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		if (this.usuarioController.existeUsuario(idDoador) == false) {

			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador);
		}

		if (this.usuarioController.existeItem(idDoador, idItem) == false) {

			throw new UnsupportedOperationException("Item nao encontrado: " + idItem);

		}

		return this.usuarioController.exibeItemParaDoacao(Integer.parseInt(idItem), idDoador);

	}

	public String atualizaItemParaDoacao(String idItem, String idDoador, int quantidade, String tags) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		if (this.usuarioController.existeUsuario(idDoador) == false) {

			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador);
		}

		if (quantidade < 0) {
			throw new IllegalArgumentException("Entrada invalida: quantidade nao pode ser negativa.");
		}

		if (tags.trim().isEmpty()) {

			this.usuarioController.getUsuarios(idDoador).getItem(Integer.parseInt(idItem)).setQuantidade(quantidade);
		}

		if (quantidade == 0) {

			this.usuarioController.getUsuarios(idDoador).getItem(Integer.parseInt(idItem)).setTag(tags);
		}

		return this.usuarioController.exibeItemParaDoacao(Integer.parseInt(idItem), idDoador);

	}

	public void removeItemParaDoacao(String idItem, String idDoador) {

		if (!this.usuarioController.getUsuarios(idDoador).existeItem(Integer.parseInt(idItem))) {

			throw new UnsupportedOperationException("Item nao encontrado: " + idDoador);

		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		if (!this.usuarioController.existeUsuario(idDoador)) {

			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador);
		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		this.usuarioController.removeItemParaDoacao(Integer.parseInt(idItem), idDoador);

	}

}
