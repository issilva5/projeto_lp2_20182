package model;

import java.util.List;

public class Item {

	private int itemID;
	private String descritor;
	private int quantidade;
	private List<String> tags;
	
	public Item(int numeroID, String descricaoItem, int quantidade, String tags) {
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
		result = prime * result + ((descritor == null) ? 0 : descritor.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
	

}
