package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import model.Item;
import model.Usuario;
import util.ComparaItem;

/**
 * Implementa o Controlador de Usuarios do sistema.
 */
public class UsuarioController {

	/**
	 * Mapa contendo os usuários do sistema.
	 */
	private Map<String, Usuario> usuarios;

	/**
	 * Inicializa o controller.
	 */
	public UsuarioController() {
		this.usuarios = new LinkedHashMap<>();
	}

	/**
	 * Adicionar um usuário doador ao sistema. Lança exceção caso algum parâmetro
	 * passado seja nulo ou vazio, ou ainda caso a classe passada não seja permitida
	 * pelo sistema.
	 * 
	 * @param docId     documento de identificacao do usuario
	 * @param nome      nome do usuario
	 * @param email     e-mail do usuario
	 * @param celular   celular do usuario
	 * @param classe    classe do usuario
	 * @param sistemaId número do cadastro do usuário no sitema.
	 * @return String contendo o documento de identificação em caso de sucesso no
	 *         cadastro.
	 */
	public String adicionaDoador(String docId, String nome, String email, String celular, String classe) {

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

		if (this.usuarios.containsKey(docId)) {
			throw new UnsupportedOperationException("Usuario ja existente: " + docId + ".");
		}

		Usuario aux = new Usuario(nome, email, docId, celular, classe, "doador");
		this.usuarios.put(docId, aux);
		return docId;
	}

	/**
	 * Pesquisa por um usuário no sistema pelo seu documento de identificação. Lança
	 * exceção caso o documento passado seja vazio, nulo ou não cadastrado.
	 * 
	 * @param docId documento de identificação.
	 * @return String contendo a representação do usuário.
	 */
	public String pesquisaUsuarioPorId(String docId) {

		if (docId == null || docId.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
		}

		if (!this.usuarios.containsKey(docId)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + docId + ".");
		}

		return this.usuarios.get(docId).toString();

	}

	/**
	 * Pesquisa por usuários com um nome dado. Caso hajam mais de um usuário com
	 * mesmo nome, todos são listados na ordem em que foram inseridos no sistema.
	 * Lança exceção caso o nome passado seja vazio, nulo ou não esteja cadastrado.
	 * 
	 * @param nome nome a ser pesquisado.
	 * @return String contendo a representação de todos os usuários com o dado nome.
	 */
	public String pesquisaUsuarioPorNome(String nome) {

		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: nome nao pode ser vazio ou nulo.");
		}

		String texto = "";

		for (Usuario u : this.usuarios.values()) {
			if (u.getNome().equals(nome)) {
				texto += u.toString() + " | ";
			}
		}

		if (texto.isEmpty()) {
			throw new NoSuchElementException("Usuario nao encontrado: " + nome + ".");
		}

		return texto.length() == 0 ? "" : texto.substring(0, texto.length() - 3);

	}

	/**
	 * Atualiza o nome, email ou celular de um usuário a partir de seu Id. Lança
	 * exceção caso o documento de identificação seja nulo ou vazio.
	 * 
	 * @param docId   documento de identificação.
	 * @param nome    novo nome.
	 * @param email   novo email.
	 * @param celular novo celular.
	 */
	public String atualizaUsuario(String docId, String nome, String email, String celular) {

		if (docId == null || docId.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
		}

		if (!this.usuarios.containsKey(docId)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + docId + ".");
		}

		if (nome != null && !nome.trim().isEmpty()) {
			this.usuarios.get(docId).setNome(nome);
			return this.usuarios.get(docId).toString();
		}

		if (email != null && !email.trim().isEmpty()) {
			this.usuarios.get(docId).setEmail(email);
			return this.usuarios.get(docId).toString();
		}

		if (celular != null && !celular.trim().isEmpty()) {
			this.usuarios.get(docId).setCelular(celular);
			return this.usuarios.get(docId).toString();
		}

		return this.usuarios.get(docId).toString();

	}

	/**
	 * Remove um usuário do sistema a partir de seu documento de identificação.
	 * Lança exceção caso o documento passado seja nulo, vazio ou não esteja
	 * cadastrado.
	 * 
	 * @param docId documento de identificação.
	 */
	public void removeUsuario(String docId) {

		if (docId == null || docId.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
		}

		if (!this.usuarios.containsKey(docId)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + docId + ".");
		}

		this.usuarios.remove(docId);
	}

	/**
	 * Atualiza os dados dos usuários receptores cadastrados, e cadastrado caso este
	 * ainda não tenha sido feito, a partir de um arquivo csv.
	 * 
	 * @param path caminho do arquivo csv.
	 */
	public void atualizaReceptores(String path) {
		try {
			LineNumberReader lnr = new LineNumberReader(new FileReader(path));

			String linha = lnr.readLine(); // ignorando o cabeçalho
			linha = lnr.readLine();
			while (linha != null) {
				String[] args = linha.split(",");
				this.adicionaReceptor(args);
				linha = lnr.readLine();
			}

			lnr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void adicionaReceptor(String[] args) {
		String docId = args[0];
		String nome = args[1];
		String email = args[2];
		String celular = args[3];
		String classe = args[4];

		if (this.usuarios.containsKey(docId)) {
			this.usuarios.get(docId).setNome(nome);
			this.usuarios.get(docId).setEmail(email);
			this.usuarios.get(docId).setCelular(celular);
			return;
		}

		Usuario aux = new Usuario(nome, email, docId, celular, classe, "receptor");
		this.usuarios.put(docId, aux);
	}

	/**
	 * Adiciona um item para doação em um usuário doador.
	 * 
	 * @param idDoador      identificador do usuário a ter item associado.
	 * @param idItem        identificador do item a ser associado.
	 * @param descricaoItem descritor do item.
	 * @param quantidade    quantidade do item.
	 * @param tags          tags do item.
	 */
	public int[] adicionaItem(String idDoador, String idItem, String descricaoItem, int quantidade, String tags) {

		if (!this.usuarios.containsKey(idDoador)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador + ".");
		}

		return this.usuarios.get(idDoador).adicionaItem(idItem, descricaoItem, quantidade, tags);
	}

	/**
	 * Exibe um item de um doador específico.
	 * 
	 * @param idItem   identificador do item a ser exibido.
	 * @param idDoador identificador do usuário.
	 * @return String contendo a representação do item.
	 */
	public String exibeItemParaDoacao(String idItem, String idDoador) {

		if (!this.usuarios.containsKey(idDoador)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador + ".");
		}

		return this.usuarios.get(idDoador).exibeItem(idItem);
	}

	/**
	 * Atualiza a quantidade de um item de um dado usuário doador.
	 * 
	 * @param idItem     identificador do item.
	 * @param idDoador   identificador do usuário.
	 * @param quantidade novo quantidade do item.
	 * @return diferença entre a quantidade antiga e nova.
	 */
	public int atualizaQuantidadeItem(String idItem, String idDoador, int quantidade) {

		if (!this.usuarios.containsKey(idDoador)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador + ".");
		}

		return this.usuarios.get(idDoador).atualizaQuantidadeItem(idItem, quantidade);

	}

	/**
	 * Atualiza as tags de um item de um dado usuário doador.
	 * 
	 * @param idItem   identificador do item.
	 * @param idDoador identificador do usuário.
	 * @param tags     novas tagas do item.
	 */
	public void atualizaTagsItem(String idItem, String idDoador, String tags) {

		if (!this.usuarios.containsKey(idDoador)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador + ".");
		}

		this.usuarios.get(idDoador).atualizaTagsItem(idItem, tags);
	}

	/**
	 * Remove um item de um usuário doador.
	 * 
	 * @param idItem   identificador do item.
	 * @param idDoador identificador do usuário.
	 * @return quantidade que o item tinha ao ser removido.
	 */
	public int removeItemParaDoacao(String idItem, String idDoador) {

		if (!this.usuarios.containsKey(idDoador)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador + ".");
		}

		return this.usuarios.get(idDoador).removeItem(idItem);

	}

	/**
	 * Pega o descritor de um item de um usuário doador.
	 * 
	 * @param idItem   identificador do item.
	 * @param idDoador identificador do usuário.
	 * @return descritor do item.
	 */
	public String getItemDescritor(String idItem, String idDoador) {

		if (!this.usuarios.containsKey(idDoador)) {
			throw new UnsupportedOperationException("Usuario nao encontrado: " + idDoador + ".");
		}

		return this.usuarios.get(idDoador).getItemDescritor(idItem);
	}

	private List<Item> getListaItens(String status) {
		
		List<Item> itens = new ArrayList<>();
		
		for(Usuario u: this.usuarios.values()) {
			if(u.getStatus().equals(status)) {
				itens.addAll(u.getItens());
			}
		}
		
		return itens;
	}
	
	/**
	 * Lista itens cadastrados no sistema.
	 * 
	 * @param status tipo de item a ser lista 'doador' ou 'receptor'
	 * @return String contendo a listagem dos itens.
	 */
	public String listaItens(String status) {
		
		List<Item> itens = this.getListaItens(status);
		Collections.sort(itens, new ComparaItem());
		
		String texto = "";
		for(Item i : itens) {
			texto += i.toString() + ", doador: " + this.usuarios.get(i.getDono()).getNome() + "/" + i.getDono() +  " | ";
		}
		
		return texto.length() == 0 ? "" : texto.substring(0, texto.length() - 3);
	}

	/**
	 *  Pesquisa itens no sistema cuja descrição contenha uma string dada.
	 * 
	 * @param desc descrição buscada.
	 * @return String contendo os itens com a respectiva descrição.
	 */
	public String pesquisaItemParaDoacaoPorDescricao(String desc) {
		List<Item> itens = this.getListaItens("doador");
		Collections.sort(itens);
		
		String texto = "";
		for(Item i : itens) {
			if(i.getDescritor().toLowerCase().contains(desc.toLowerCase())) {
				texto += i.toString() + " | ";
			}
		}
		
		return texto.length() == 0 ? "" : texto.substring(0, texto.length() - 3);
	}

}
