package model;

public class Descritor {
	
	private String nome;
	private int quantidade;
	
	public Descritor(String nome, int quantidade) {
		this.nome = nome;
		this.quantidade = quantidade;
	}
	
	public void changeQuant(int delta) {
		this.quantidade += delta;
	}

	public String getNome() {
		return this.nome;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Descritor other = (Descritor) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
