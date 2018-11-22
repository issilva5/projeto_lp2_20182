package model;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representacao de um Usuario
 */
@SuppressWarnings("unused")
public abstract class Usuario {

	/**
	 * Nome do usuario
	 */

	private String nome;

	/**
	 * E-mail do usuario
	 * 
	 */
	private String email;

	/**
	 * Classe do usuario
	 */

	private String classe;

	/**
	 * Documento de idetificacao do usuario
	 */
	private String docId;

	/**
	 * Celular do usuario
	 */
	private String celular;

	/**
	 * Status do tipo de usuario
	 */
	private String status;

	/**
	 * Itens do usuário cadastrados no sistema.
	 */
	private Map<Integer, Item> itens;
	
	/**
	 * 
	 * Metodo responsavel por construir um usuario a partir do nome,email, documento
	 * de identificao, celular e o status do usuario. O status do usuario pode ser
	 * 'receptor' ou 'doador'.
	 * 
	 * @param nome    nome do usuario
	 * @param email   e-mail do usuario
	 * @param docId   documento de identificacao do usuario
	 * @param celular celular do usuario
	 * @param classe  classe do usuario
	 * @param status  tipo de usuario
	 * @param sistemaId número do cadastro do usuário no sitema.
	 */

	public Usuario(String nome, String email, String docId, String celular, String classe, String status) {
		
		final List<String> classesPermitidas = Arrays.asList("PESSOA_FISICA", "IGREJA", "ORGAO_PUBLICO_MUNICIPAL",
				"ORGAO_PUBLICO_ESTADUAL", "ORGAO_PUBLICO_FEDERAL", "ONG", "ASSOCIACAO", "SOCIEDADE");

		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: nome nao pode ser vazio ou nulo.");
		}

		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: email nao pode ser vazio ou nulo.");
		}

		if (docId == null || docId.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
		}

		if (celular == null || celular.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: celular nao pode ser vazio ou nulo.");
		}

		if (classe == null || classe.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: classe nao pode ser vazia ou nula.");
		}

		if (!classesPermitidas.contains(classe)) {
			throw new IllegalArgumentException("Entrada invalida: opcao de classe invalida.");
		}

		this.nome = nome;
		this.email = email;
		this.docId = docId;
		this.celular = celular;
		this.classe = classe;
		this.status = status;
		this.itens = new HashMap<>();
	}

	/**
	 * Metodo responsavel por alterar o nome do usuario.
	 * 
	 * @param nome nome do usuario
	 */

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Metodo responsavel por alterar o email do usuario.
	 * 
	 * @param email e-mail do usuario
	 */

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Metodo responsavel por alterar o celular do usuario.
	 * 
	 * @param celular celular do usuario
	 */

	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * Metodo responsavel por obter o nome do usuario
	 * 
	 * @return nome do usuario
	 */

	public String getNome() {
		return nome;
	}

	/**
	 * Metodo responsavel por obter o documento de identificao do usuario.
	 * 
	 * @return documento identificao do usuario
	 */

	public String getDocId() {
		return docId;
	}

	/**
	 * Metodo sobrescrito da classe Object responsavel por obter a representacao
	 * textual do usuario
	 */

	@Override
	public String toString() {
		return this.nome + "/" + this.docId + ", " + this.email + ", " + this.celular + ", status: " + this.status;
	}

	/**
	 * Método sobrescrito da classe Object responsavel por criar um codigo hash do
	 * usuario.
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docId == null) ? 0 : docId.hashCode());
		return result;
	}

	/**
	 * Metodo responsavel comparar dois usuarios a partir do documento de
	 * identificacao.
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (docId == null) {
			if (other.docId != null)
				return false;
		} else if (!docId.equals(other.docId))
			return false;
		return true;
	}
	
	/**
	 * Adiciona um novo item a coleção de itens do usuário.
	 * 
	 * @param idItem identificador único do item a ser adicionado.
	 * @param i item a ser adicionado.
	 */
	public void adicionaItem(int idItem, Item i) {
		this.itens.put(idItem, i);
	}
	
	/**
	 * Remove um item da coleção de itens do usuário.
	 * 
	 * @param idItem identificador único do item a ser adicionado.
	 */
	public void removeItem(int idItem) {
		
		if (!this.itens.containsKey(idItem)) {

			throw new UnsupportedOperationException("Item nao encontrado: " + this.docId);

		}
		
		this.itens.remove(idItem);
	}
	
	/**
	 * Atualiza a quantidade de um item.
	 * 
	 * @param idItem identificador único do item a ser adicionado.
	 * @param quantidade nova quantidade do item.
	 */
	public void atualizaQuantidadeItem(int idItem, int quantidade) {
		this.itens.get(idItem).setQuantidade(quantidade);
	}
	
	/**
	 * Atualiza as tags de um item.
	 * 
	 * @param idItem identificador único do item a ser adicionado.
	 * @param tags novas tags do item.
	 */
	public void atualizaTagsItem(int idItem, String tags) {
		this.itens.get(idItem).setTag(tags);
	}
	
	/**
	 * Retorna a representação textual de um item.
	 * 
	 * @param idItem identificador único do item a ser adicionado.
	 * @return a representação textual de um item.
	 */
	public String exibeItem(int idItem) {
		
		if (!this.itens.containsKey(idItem)) {

			throw new UnsupportedOperationException("Item nao encontrado: " + this.docId);

		}
		
		return this.itens.get(idItem).toString();
	}
	

}
