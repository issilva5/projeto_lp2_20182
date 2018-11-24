package controller;

import java.util.Map;
import java.util.TreeMap;

import model.Descritor;

/**
 * 
 * Implementa o controlador de item no sistema
 *
 */

public class ItemController {

	/**
	 * Mapa de descritores
	 */

	private Map<String, Descritor> descritores;

	/**
	 * Instancia do controlador do usuario
	 */

	private UsuarioController usuarioController;

	/**
	 * numero de identificacao disponível para ser alocado ao item
	 */

	private int numeroID;

	/**
	 * Constroi um controlador de item a partir da instancia do controlador do
	 * usuario
	 * 
	 * @param usuarioController controlador do usuario
	 */

	public ItemController(UsuarioController usuarioController) {
		this.descritores = new TreeMap<>();
		this.usuarioController = usuarioController;
		this.numeroID = 0;
	}

	/**
	 * Adicionar um descritor no mapa de descritores. Lanca excecao caso a descricao
	 * seja nula, vazia ou já existente.
	 * 
	 * @param descricao descricao 
	 */

	public void adicionaDescritor(String descricao) {

		if (descricao == null || descricao.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: descricao nao pode ser vazia ou nula.");
		}

		if (this.descritores.containsKey(descricao)) {
			throw new UnsupportedOperationException("Descritor de Item ja existente: " + descricao);
		}

		this.descritores.put(descricao, new Descritor(descricao, 0));

	}

	/**
	 * Adiciona item para doacao
	 * 
	 * @param idDoador
	 * @param descricaoItem
	 * @param quantidade
	 * @param tags
	 * @return
	 */

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

		if (!this.descritores.containsKey(descricaoItem)) {

			this.descritores.put(descricaoItem, new Descritor(descricaoItem, 0));
		}

		this.numeroID++;

		this.descritores.get(descricaoItem).changeQuant(quantidade);

		this.usuarioController.adicionaItemParaDoacao(idDoador, this.numeroID, descricaoItem, quantidade, tags);

		return Integer.toString(this.numeroID);
	}

	/**
	 * 
	 * @param idItem
	 * @param idDoador
	 * @return
	 */

	public String exibeItem(String idItem, String idDoador) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		return this.usuarioController.exibeItemParaDoacao(Integer.parseInt(idItem), idDoador);

	}

	/**
	 * 
	 * @param idItem
	 * @param idDoador
	 * @param quantidade
	 * @param tags
	 * @return
	 */

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

	/**
	 * Remove item para doacao a partir do id do tem e do id do doador.
	 * 
	 * @param idItem
	 * @param idDoador
	 */

	public void removeItemParaDoacao(String idItem, String idDoador) {

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazia ou nula.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		int delta = this.usuarioController.removeItemParaDoacao(Integer.parseInt(idItem), idDoador);
		String descritor = this.usuarioController.getItemDescritor(Integer.parseInt(idItem), idDoador);
		this.descritores.get(descritor).changeQuant(delta);
	}

}
