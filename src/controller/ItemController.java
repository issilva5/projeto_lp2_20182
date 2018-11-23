package controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Descritor;


public class ItemController {

	private Map<String, Descritor> descritores;

	private UsuarioController usuarioController;

	private int numeroID;

	public ItemController(UsuarioController usuarioController) {
		this.descritores = new TreeMap<>();
		this.usuarioController = usuarioController;
		this.numeroID = 0;
	}

	public void adicionaDescritor(String descricao) {

		if (descricao == null || descricao.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: descricao nao pode ser vazia ou nula.");
		}

		if (this.descritores.containsKey(descricao)) {
			throw new UnsupportedOperationException("Descritor de Item ja existente: " + descricao);
		}

		this.descritores.put(descricao, new Descritor(descricao, 0));

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

		if (!this.descritores.containsKey(descricaoItem)) {

			this.descritores.put(descricaoItem, new Descritor(descricaoItem, 0));
		}

		this.numeroID++;
		
		this.descritores.get(descricaoItem).changeQuant(quantidade);
		
		this.usuarioController.adicionaItemParaDoacao(idDoador,this.numeroID, descricaoItem, quantidade, tags);

		return Integer.toString(this.numeroID);
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
			int delta = this.usuarioController.atualizaQuantidadeItem(Integer.parseInt(idItem), idDoador, quantidade);
			String descritor = this.usuarioController.getItemDescritor(Integer.parseInt(idItem), idDoador);
			this.descritores.get(descritor).changeQuant(delta);
		}

		if (quantidade == 0) {

			this.usuarioController.atualizaTagsItem(Integer.parseInt(idItem), idDoador, tags);
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

		int delta = this.usuarioController.removeItemParaDoacao(Integer.parseInt(idItem), idDoador);
		String descritor = this.usuarioController.getItemDescritor(Integer.parseInt(idItem), idDoador);
		this.descritores.get(descritor).changeQuant(delta);
	}

}
