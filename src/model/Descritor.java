package model;

/**
 * Representação dos descritores de itens do sistema.
 */
public class Descritor {
	
	/**
	 * Nome do descritor.
	 */
	private String nome;
	
	/**
	 * Quantidade de itens registrados com esse descritor.
	 */
	private int quantidade;
	
	/**
	 * Inicializa um descritor.
	 * 
	 * @param nome Nome do descritor.
	 * @param quantidade Quantidade do descritor.
	 */
	public Descritor(String nome, int quantidade) {
		this.nome = nome;
		this.quantidade = quantidade;
	}
	
	/**
	 * Muda a quantidade do descritor.
	 * 
	 * @param delta valor a ser soma na quantidade.
	 */
	public void changeQuant(int delta) {
		this.quantidade += delta;
	}

	/**
	 * Retorna o nome do descritor.
	 * @return Nome do descritor.
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Retorna a quantidade do descritor.
	 * @return Quantidade do descritor.
	 */
	public int getQuantidade() {
		return this.quantidade;
	}

	/**
	 * Implementa o hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	/**
	 * Implementa o equals.
	 * Dois descritores são iguais se tem o mesmo nome.
	 */
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
