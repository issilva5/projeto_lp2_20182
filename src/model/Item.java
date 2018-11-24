package model;

import java.util.List;

public class Item {

	private int itemID;
	private String descritor;
	private int quantidade;
	private List<String> tags;
	
	public Item(int numeroID, String descricaoItem, int quantidade, String tags) {

		if (numeroID < 0) {
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
		
		// TODO Auto-generated constructor stub
	}

	public void setQuantidade(int quantidade) {
		// TODO Auto-generated method stub
		
	}

	public void setTag(String tags) {
		// TODO Auto-generated method stub
		// ESSA FUNÇÃO VAI TER Q CONVERTER A STRING TAGS PRA ARRAYLIST
		//CHAMAR ELA NO CONSTRUTOR TBM
	}

	public int getQuantidade() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDescritor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + descritor.hashCode();
		result = prime * result + tags.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (descritor == null) {
			if (other.descritor != null)
				return false;
		} else if (!descritor.equals(other.descritor))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}

	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
