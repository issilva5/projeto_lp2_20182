package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representacao de um Usuario
 */
@SuppressWarnings("unused")
public class Usuario {

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
	private String docID;

	/**
	 * Celular do usuario
	 */
	private String celular;

	/**
	 * Status do tipo de usuario
	 */
	private String status;

	/**
	 * Itens do usuafrio cadastrados no sistema.
	 */
	private Map<String, Item> itens;

	/**
	 * 
	 * Construir um usuario a partir do nome,email, documento de identificao,
	 * celular e o status do usuario. O status do usuario pode ser 'receptor' ou
	 * 'doador'.
	 * 
	 * @param nome      nome do usuario
	 * @param email     e-mail do usuario
	 * @param docID     documento de identificacao do usuario
	 * @param celular   celular do usuario
	 * @param classe    classe do usuario
	 * @param status    tipo de usuario
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
		this.docID = docId;
		this.celular = celular;
		this.classe = classe;
		this.status = status;
		this.itens = new HashMap<>();
	}

	/**
	 * Altera o nome do usuario.
	 * 
	 * @param nome nome do usuario
	 */

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Altera o email do usuario.
	 * 
	 * @param email e-mail do usuario
	 */

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Altera o celular do usuario.
	 * 
	 * @param celular celular do usuario
	 */

	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * Obtem o nome do usuario
	 * 
	 * @return nome do usuario
	 */

	public String getNome() {
		return nome;
	}

	/**
	 * Obtem o documento de identificacao do usuario.
	 * 
	 * @return documento identificacao do usuario
	 */

	public String getDocID() {
		return docID;
	}

	/**
	 * Metodo sobrescrito da classe Object responsavel por obter a representacao
	 * textual do usuario
	 */

	@Override
	public String toString() {
		return this.nome + "/" + this.docID + ", " + this.email + ", " + this.celular + ", status: " + this.status;
	}

	/**
	 * Método sobrescrito da classe Object responsavel por criar um codigo hash do
	 * usuario.
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docID == null) ? 0 : docID.hashCode());
		return result;
	}

	/**
	 * Compara se dois usuarios sao iguais a partir do documento de identificacao.
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
		if (docID == null) {
			if (other.docID != null)
				return false;
		} else if (!docID.equals(other.docID))
			return false;
		return true;
	}

	/**
	 * Adiciona um novo item a coleção de itens do usuário.
	 * 
	 * @param numeroID      identificador único do item a ser adicionado.
	 * @param idDoador      identificador do doador
	 * @param descricaoItem descricao do item
	 * @param quantidade    quantidade do item
	 * @param tags          tags do item
	 */
	public int[] adicionaItem(String numeroID, String descricaoItem, int quantidade, String tags) {
		
		Item i = new Item(numeroID, descricaoItem, quantidade, tags, this.docID);
		
		for(String aux : this.itens.keySet()) {
			if(i.equals(this.itens.get(aux))) {
				String numAux = this.itens.get(aux).getItemID();
				int d = this.itens.get(aux).getQuantidade();
				this.itens.remove(aux);
				
				i = new Item(numAux, descricaoItem, quantidade, tags, this.docID);
				
				this.itens.put(numAux, i);
				
				int[] ret = {Integer.parseInt(numAux), this.itens.get(numAux).getQuantidade() - d};
				
				return ret;
			}
		}
		
		this.itens.put(numeroID, i);
		int[] ret = {Integer.parseInt(numeroID), quantidade};
		return ret;
	}

	/**
	 * Remove um item da coleção de itens do usuário.
	 * 
	 * @param itemId identificador único do item a ser adicionado.
	 * @return quantidade do item removido.
	 */
	public int removeItem(String itemId) {
		
		if(this.itens.size() == 0) {
			throw new UnsupportedOperationException("O Usuario nao possui itens cadastrados.");
		}
		
		if (!this.itens.containsKey(itemId)) {

			throw new UnsupportedOperationException("Item nao encontrado: " + itemId + ".");

		}
		
		return this.itens.remove(itemId).getQuantidade();
	}

	/**
	 * Atualiza a quantidade de um item.
	 * 
	 * @param idItem     identificador único do item a ser adicionado.
	 * @param quantidade nova quantidade do item.
	 * @return diferença entre a quantidade antiga e a nova.
	 */
	public int atualizaQuantidadeItem(String idItem, int quantidade) {
		if (!this.itens.containsKey(idItem)) {
			throw new UnsupportedOperationException("Item nao encontrado: " + idItem + ".");
		}
		
		int aux = (this.itens.get(idItem).getQuantidade() - quantidade) * (-1);
		this.itens.get(idItem).setQuantidade(quantidade);
		return aux;
	}

	/**
	 * Atualiza as tags de um item.
	 * 
	 * @param itemID identificador único do item a ser adicionado.
	 * @param tags   novas tags do item.
	 */
	public void atualizaTagsItem(String itemID, String tags) {
		
		if (!this.itens.containsKey(itemID)) {
			
			throw new UnsupportedOperationException("Item nao encontrado: " + itemID + ".");

		}
		
		this.itens.get(itemID).setTag(tags);
	}

	/**
	 * Retorna a representação textual de um item.
	 * 
	 * @param itemID identificador único do item a ser adicionado.
	 * @return a representação textual de um item.
	 */
	public String exibeItem(String itemID) {

		if (!this.itens.containsKey(itemID)) {

			throw new UnsupportedOperationException("Item nao encontrado: " + itemID + ".");

		}

		return this.itens.get(itemID).toString();
	}

	/**
	 * Verifica se o item esta vinculado ao usuario
	 * 
	 * @param itemID identificador do item
	 * @return booleano indicando se o item esta vinculado
	 */

	public boolean existeItem(String itemID) {

		if (Integer.parseInt(itemID) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (!this.itens.containsKey(itemID)) {

			return false;
		}

		return true;
	}

	/**
	 * Obtem o descritor de um item do usuário.
	 * @param itemID identificador do item.
	 * @return descritor do item.
	 */
	public String getItemDescritor(String itemID) {

		if(this.itens.size() == 0) {
			throw new UnsupportedOperationException("O Usuario nao possui itens cadastrados.");
		}
		
		if (Integer.parseInt(itemID) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (!this.itens.containsKey(itemID)) {

			throw new UnsupportedOperationException("Item nao encontrado: " + itemID + ".");
		}

		return this.itens.get(itemID).getDescritor();

	}

	/**
	 * Retorna o status do usuário.
	 * 
	 * @return status do usuário.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * Retorna os itens do usuário.
	 * 
	 * @return itens do usuário.
	 */
	public Collection<Item> getItens() {
		return this.itens.values();
	}

}
