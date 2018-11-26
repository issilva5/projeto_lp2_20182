package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Representacao de um Item
 *
 */
public class Item implements Comparable<Item> {

	/**
	 * Identificador do item no sistema.
	 */

	private String itemID;

	/**
	 * Descritor do item.
	 */
	private String descritor;

	/**
	 * Quantidade do item.
	 */
	private int quantidade;

	/**
	 * Tags do item.
	 */
	private List<String> tags;
	
	/**
	 * Possuidor do item.
	 */
	private String dono;

	/**
	 * Inicializa um item.
	 * 
	 * @param numeroID identificador do item.
	 * @param descricaoItem descricao do item.
	 * @param quantidade quantidade do item.
	 * @param tags tags do item.
	 * @param documento de identificacao do dono do item.
	 */

	public Item(String numeroID, String descricaoItem, int quantidade, String tags, String dono) {

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
		this.dono = dono;
	}

	/**
	 * Retorno o possuidor desse item.
	 * 
	 * @return possuidor do item.
	 */
	public String getDono() {
		return this.dono;
	}

	/**
	 * Retorna o identificador do item.
	 * 
	 * @return identificador do item.
	 */
	public String getItemID() {
		return this.itemID;
	}

	/**
	 * Altera a quantidade de um item.
	 * 
	 * @param quantidade nova quantidade.
	 * @return Diferença entre a nova quantidade e a quatidade antiga.
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
	 * Define as tags do item.
	 * 
	 * @param tags tags do item.
	 */
	public void setTag(String tags) {
		
		if (tags == null) return;
		
		List<String> aux = new ArrayList<>();
		aux.addAll(Arrays.asList(tags.split(",")));
		this.tags = aux;
	}

	/**
	 * Retorna a quantidade do item.
	 * 
	 * @return quantidade do item.
	 */
	public int getQuantidade() {

		return this.quantidade;
	}

	/**
	 * Retorna o descritor o item.
	 * 
	 * @return descritor do item.
	 */
	public String getDescritor() {

		return this.descritor;
	}

	/**
	 * Implementa o toString.
	 */
	@Override
	public String toString() {

		return this.itemID + " - " + this.descritor + ", tags: " + this.getTag() + ", quantidade: " + this.quantidade;

	}


	/**
	 * Implementa o hashCode.
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
	 * Implementa o equals.
	 * Dois itens são iguais se tiverem o mesmo descritor, e as mesmas tags na mesma ordem.
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
	 * Retorna as tags do item.
	 * 
	 * @return tags do item.
	 */
	public String getTag() {

		return this.tags.toString();
	}

	/**
	 * Implementa o compareTo. Pela ordem alfabética do descritor.
	 */
	@Override
	public int compareTo(Item o) {
		return this.descritor.compareTo(o.descritor);
	}

}
