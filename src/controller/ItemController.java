package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Item;

public class ItemController {

	private Set<String> descritores;

	private Map<Integer, Item> itens;

	private UsuarioController usuarioController;

	private int numeroID;

	public ItemController(UsuarioController usuarioController) {
		this.descritores = new HashSet<>();
		this.itens = new HashMap<>();
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

		this.itens.put(this.numeroID, new Item(this.numeroID, descricaoItem, idDoador, quantidade, tags));

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

		if (this.itens.containsKey(Integer.parseInt(idItem)) == false) {

			throw new UnsupportedOperationException("Item nao encontrado: " + idItem);

		}

		return this.itens.get(Integer.parseInt(idItem)).toString();

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

			this.itens.get(Integer.parseInt(idItem)).setQuantidade(quantidade);
		}

		if (quantidade == 0) {

			this.itens.get(Integer.parseInt(idItem)).setTag(tags);
		}

		return this.itens.get(Integer.parseInt(idItem)).toString();

	}

	public void removeItemParaDoacao(String idItem, String idDoador) {

		if (!this.itens.containsKey(Integer.parseInt(idItem))) {

			throw new UnsupportedOperationException("Item nao encontrado: " + idDoador);

		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		if (this.usuarioController.existeUsuario(idDoador) == false) {

			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador);
		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}
		
		this.itens.remove(Integer.parseInt(idItem));
		
	}
	
	public void removerItensUsuario(String idUsuario) {
		
	}

}
