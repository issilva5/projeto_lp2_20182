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

		if (this.descritores.containsKey(descricao.trim().toLowerCase())) {
			throw new UnsupportedOperationException("Descritor de Item ja existente: " + descricao.trim().toLowerCase() + ".");
		}

		this.descritores.put(descricao, new Descritor(descricao.trim().toLowerCase(), 0));

	}

	
	/**
	 * Adiciona um item para um dado usuário.
	 * 
	 * @param idUsuario identificador do usuário.
	 * @param descricaoItem descrição do item.
	 * @param quantidade quantidade do item.
	 * @param tags tags do item.
	 * @return identificador do item.
	 */
	public String adicionaItem(String idUsuario, String descricaoItem, int quantidade, String tags) {

		if (idUsuario == null || idUsuario.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

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

		int[] r = this.usuarioController.adicionaItem(idUsuario, "" + this.numeroID, descricaoItem, quantidade, tags);
		
		int delta = r[1];

		this.descritores.get(descricaoItem).changeQuant(delta);

		return String.valueOf(r[0]);
	}

	/**
	 * Exibe um item de um doador específico.
	 * 
	 * @param idItem   identificador do item a ser exibido.
	 * @param idDoador identificador do usuário.
	 * @return String contendo a representação do item.
	 */
	public String exibeItem(String idItem, String idDoador) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

		return this.usuarioController.exibeItemParaDoacao(idItem, idDoador);

	}

	/**
	 * Atualiza a quantidade ou as tags de um item de um doador.
	 * 
	 * @param idItem     identificador do item.
	 * @param idDoador   identificador do doador.
	 * @param quantidade nova quantidade do item.
	 * @param tags       novas tags.
	 * @return String contendo a representação do item.
	 */

	public String atualizaItemParaDoacao(String idItem, String idDoador, int quantidade, String tags) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

//		if (quantidade < 0) {
//			throw new IllegalArgumentException("Entrada invalida: quantidade nao pode ser negativa.");
//		}

		if (quantidade <= 0) {
			this.usuarioController.atualizaTagsItem(idItem, idDoador, tags);
			return this.usuarioController.exibeItemParaDoacao(idItem, idDoador);
		}

		int delta = this.usuarioController.atualizaQuantidadeItem(idItem, idDoador, quantidade);
		String descritor = this.usuarioController.getItemDescritor(idItem, idDoador);
		this.descritores.get(descritor).changeQuant(delta);

		return this.usuarioController.exibeItemParaDoacao(idItem, idDoador);

	}

	/**
	 * Remove item para doacao a partir do id do tem e do id do doador.
	 * 
	 * @param idItem identificador do item.
	 * @param idDoador identificador do usuário.
	 */

	public void removeItemParaDoacao(String idItem, String idDoador) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (idDoador == null || idDoador.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		String descritor = this.usuarioController.getItemDescritor(idItem, idDoador);
		int delta = this.usuarioController.removeItemParaDoacao(idItem, idDoador);
		
		this.descritores.get(descritor).changeQuant(delta * -1);
	}

	/**
	 * Pega a quantidade de um descritor do sistema.
	 * 
	 * @param descritor descritor procurado.
	 * @return quantidade.
	 */
	public int getDescritorQuant(String descritor) {
		return this.descritores.get(descritor).getQuantidade();
	}

}
