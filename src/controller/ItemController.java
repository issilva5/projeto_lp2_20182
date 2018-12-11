package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import model.*;
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
	private List<Doacao> doacao = new ArrayList<>();
	private List<String> registro;

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
		this.registro = new ArrayList<>();
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

		descricao = descricao.trim().toLowerCase();

		if (this.descritores.containsKey(descricao)) {
			throw new UnsupportedOperationException("Descritor de Item ja existente: " + descricao + ".");
		}

		this.descritores.put(descricao, new Descritor(descricao, 0));

	}

	/**
	 * Adiciona um item para um dado usuário.
	 * 
	 * @param idUsuario     identificador do usuário.
	 * @param descricaoItem descrição do item.
	 * @param quantidade    quantidade do item.
	 * @param tags          tags do item.
	 * @return identificador do item.
	 */
	public String adicionaItem(String idUsuario, String descricaoItem, int quantidade, String tags) {

		if (idUsuario == null || idUsuario.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

		if (descricaoItem == null || descricaoItem.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: descricao nao pode ser vazia ou nula.");

		}

		descricaoItem = descricaoItem.trim().toLowerCase();

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
	 * @param idItem    identificador do item a ser exibido.
	 * @param idUsuario identificador do usuário.
	 * @return String contendo a representação do item.
	 */
	public String exibeItem(String idItem, String idUsuario) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (idUsuario == null || idUsuario.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

		return this.usuarioController.exibeItem(idItem, idUsuario);

	}

	/**
	 * Atualiza a quantidade ou as tags de um item de um doador.
	 * 
	 * @param idItem     identificador do item.
	 * @param idUsuario  identificador do doador.
	 * @param quantidade nova quantidade do item.
	 * @param tags       novas tags.
	 * @return String contendo a representação do item.
	 */

	public String atualizaItem(String idItem, String idUsuario, int quantidade, String tags) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (Long.parseLong(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (idUsuario == null || idUsuario.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

//		if (quantidade < 0) {
//			throw new IllegalArgumentException("Entrada invalida: quantidade nao pode ser negativa.");
//		}

		if (tags != null && !tags.trim().isEmpty())
			this.usuarioController.atualizaTagsItem(idItem, idUsuario, tags);

		if (quantidade > 0) {
			int delta = this.usuarioController.atualizaQuantidadeItem(idItem, idUsuario, quantidade);

			String descritor = this.usuarioController.getItemDescritor(idItem, idUsuario);
			this.descritores.get(descritor).changeQuant(delta);
		}

		return this.usuarioController.exibeItem(idItem, idUsuario);

	}

	/**
	 * Remove item para doacao a partir do id do tem e do id do doador.
	 * 
	 * @param idItem    identificador do item.
	 * @param idUsuario identificador do usuário.
	 */

	public void removeItem(String idItem, String idUsuario) {

		if (idItem == null || idItem.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser vazia ou nula.");

		}

		if (idUsuario == null || idUsuario.trim().isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

		if (Integer.parseInt(idItem) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		String descritor = this.usuarioController.getItemDescritor(idItem, idUsuario);
		int delta = this.usuarioController.removeItem(idItem, idUsuario);

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

	/**
	 * Lista os descritores que constam no sistema, bem como suas quantidades.
	 * 
	 * @return String contendo os descritores cadastrados.
	 */
	public String listaDescritorDeItensParaDoacao() {
		String texto = "";

		for (Descritor d : this.descritores.values()) {
			texto += d.toString() + " | ";
		}
		return texto.length() == 0 ? "" : texto.substring(0, texto.length() - 3);
	}

	/**
	 * Lista itens cadastrados no sistema.
	 * 
	 * @param status tipo de item a ser lista 'doador' ou 'receptor'
	 * @return String contendo a listagem dos itens.
	 */
	public String listaItens(String status) {
		return this.usuarioController.listaItens(status);
	}

	/**
	 * Pesquisa itens no sistema cuja descrição contenha uma string dada.
	 * 
	 * @param desc descrição buscada.
	 * @return String contendo os itens com a respectiva descrição.
	 */
	public String pesquisaItemParaDoacaoPorDescricao(String desc) {

		if (desc == null || desc.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: texto da pesquisa nao pode ser vazio ou nulo.");
		}

		desc = desc.trim().toLowerCase();

		return this.usuarioController.pesquisaItemParaDoacaoPorDescricao(desc);
	}

	/**
	 * 
	 * Realiza match de itens para doacao com base em um item necessario
	 * 
	 * @param idReceptor       identificador do receptor
	 * @param idItemNecessario identificador do item necessario
	 * @return String contendo itens para doacao que fazem match com o item
	 *         necessario
	 */

	public String match(String idReceptor, String idItemNecessario) {

		if (idReceptor == null || idReceptor.isEmpty()) {

			throw new IllegalArgumentException("Entrada invalida: id do usuario nao pode ser vazio ou nulo.");

		}

		if (Integer.parseInt(idItemNecessario) < 0) {

			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");

		}

		return this.usuarioController.match(idReceptor, idItemNecessario);

	}

	/**
	 * Metodo que realiza Doacao
	 * 
	 * @param idItemNecessario Id do item necessario
	 * @param idItemDoado      Id do item doado
	 * @param data             Data da doação
	 * @return String contendo a descricao da doacao
	 */
	public String realizaDoacao(String idItemNecessario, String idItemDoado, String data) {

		if (Integer.parseInt(idItemNecessario) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}
		if (Integer.parseInt(idItemDoado) < 0) {
			throw new IllegalArgumentException("Entrada invalida: id do item nao pode ser negativo.");
		}

		if (data == null || data.trim().isEmpty()) {
			throw new IllegalArgumentException("Entrada invalida: data nao pode ser vazia ou nula.");
		}

		String[] vetor = this.usuarioController.realizaDoacao(idItemNecessario, idItemDoado, data);

		this.descritores.get(vetor[0]).changeQuant(Integer.parseInt(vetor[1]));

		this.registro.add(vetor[2]);
		this.doacao.add(new Doacao(vetor[2], vetor[0]));
		return vetor[2];

	}

	/**
	 * Metodo para listar doacoes. A listagem é ordenada pela data.
	 * 
	 * @return String contendo a listagem.
	 */
	public String listaDoacoes() {
		String texto = "";

		Collections.sort(this.doacao);

		for (Doacao d : this.doacao) {
			texto += d.toString() + " | ";
		}

		return texto.length() == 0 ? "" : texto.substring(0, texto.length() - 3);
	}

	/**
	 * Encerra o sistema salvando os descritores e historico de doações num arquivo.
	 * 
	 * @throws IOException
	 */
	public void finalizaSistema() {
		this.salvaDescritores();
		this.salvaDoacoes();
	}

	private void salvaDoacoes() {

		try {
			File doacoes = new File("arquivos_sistema/doacoes");
			FileOutputStream fos = new FileOutputStream(doacoes);
			ObjectOutputStream os = new ObjectOutputStream(fos);

			os.writeInt(this.doacao.size());
			
			for (Doacao d : this.doacao) {
				os.writeObject(d);
			}

			os.close();
			fos.close();

			this.doacao.clear();
		} catch (IOException e) {

			throw new RuntimeException("Falha ao fechar sistema");

		}

	}

	private void salvaDescritores() {

		try {
			File descritores = new File("arquivos_sistema/descritores");
			FileOutputStream fos = new FileOutputStream(descritores);
			ObjectOutputStream os = new ObjectOutputStream(fos);

			os.writeInt(this.descritores.size());
			
			for (Descritor d : this.descritores.values()) {
				os.writeObject(d);
			}

			os.close();
			fos.close();

			this.descritores.clear();
		} catch (IOException e) {

			throw new RuntimeException("Falha ao fechar sistema");

		}

	}

	public void inicializaSistema() {

		this.leDoacoes();
		this.leDescritores();

	}

	private void leDescritores() {

		try {

			File descritores = new File("arquivos_sistema/descritores");
			FileInputStream fis = new FileInputStream(descritores);
			ObjectInputStream is = new ObjectInputStream(fis);
			Descritor d;

			int tam = is.readInt();
			
			for(int i = 0; i < tam; i++) {
				d = (Descritor) is.readObject();
				this.descritores.put(d.getNome(), d);
			}

			is.close();
			fis.close();

		}  catch(FileNotFoundException f) {
			
			return;
			
		} catch (IOException e) {

			throw new RuntimeException("Falha ao iniciar sistema");

		} catch (ClassNotFoundException e) {

			throw new RuntimeException("Falha ao iniciar sistema");

		}

	}

	private void leDoacoes() {

		try {

			File doacoes = new File("arquivos_sistema/doacoes");
			FileInputStream fos = new FileInputStream(doacoes);
			ObjectInputStream os = new ObjectInputStream(fos);
			Doacao d;

			int tam = os.readInt();
			
			for(int i = 0; i < tam; i++) {
				d = (Doacao) os.readObject();
				this.doacao.add(d);
			}

			os.close();
			fos.close();

		} catch(FileNotFoundException f) {
			
			return;
			
		} catch (IOException e) {

			throw new RuntimeException("Falha ao iniciar sistema");

		} catch (ClassNotFoundException e) {

			throw new RuntimeException("Falha ao iniciar sistema");

		}

	}

}
