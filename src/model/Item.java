package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Representacao de um Item
 *
 */
public class Item {

	/**
	 * 
	 */

	private String itemID;

	/**
	 * 
	 */
	private String descritor;

	/**
	 * 
	 */
	private int quantidade;

	/**
	 * 
	 */
	private List<String> tags;

	/**
	 * 
	 * @param numeroID
	 * @param descricaoItem
	 * @param quantidade
	 * @param tags
	 */

	public Item(String numeroID, String descricaoItem, int quantidade, String tags) {

		if (Integer.parseInt(numeroID) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
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
		
        this.itemID = numeroID;
		this.descritor = descricaoItem;
		this.setTag(tags);
		this.quantidade = quantidade;

	}

	/**
	 * 
	 * @return
	 */

	public String getItemID() {
		return this.itemID;
	}

	/**
	 * 
	 * @param quantidade
	 */

	public int setQuantidade(int quantidade) {

		if (quantidade <= 0) {
			throw new IllegalArgumentException("Entrada invalida: quantidade deve ser maior que zero.");
		}
		int aux = quantidade - this.quantidade;
		this.quantidade = quantidade;
		return aux;
	}

	/**
	 * 
	 * @param tags
	 */

	public void setTag(String tags) {
		
		if (tags == null) return;
		
		List<String> aux = new ArrayList<>();
		aux.addAll(Arrays.asList(tags.split(",")));
		this.tags = aux;
	}

	/**
	 * 
	 * @return
	 */

	public int getQuantidade() {

		return this.quantidade;
	}

	/**
	 * 
	 * @return
	 */

	public String getDescritor() {

		return this.descritor;
	}

	/**
	 * 
	 */

	@Override
	public String toString() {

		return this.itemID + " - " + this.descritor + ", tags: " + this.getTag() + ", quantidade: " + this.quantidade;

	}

	/**
	 * 
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + descritor.hashCode();
		result = prime * result + tags.hashCode();
		return result;
	}

	/**
	 * 
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (!descritor.equals(other.descritor))
			return false;
		if (!tags.equals(other.tags))
			return false;
		return true;
	}

	/**
	 * 
	 * @return
	 */

	public String getTag() {

		return this.tags.toString();
	}

}
