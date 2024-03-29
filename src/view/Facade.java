package view;

import java.io.IOException;

import controller.ItemController;
import controller.UsuarioController;
import easyaccept.EasyAccept;

public class Facade {

	
	private UsuarioController usuarioController = new UsuarioController();
	private ItemController itemController = new ItemController(usuarioController);

	public String adicionaDoador(String docID, String nome, String email, String celular, String classe) {
		return usuarioController.adicionaDoador(docID, nome, email, celular, classe);
	}

	public String pesquisaUsuarioPorId(String docID) {
		return this.usuarioController.pesquisaUsuarioPorId(docID);
	}

	public String pesquisaUsuarioPorNome(String nome) {
		return this.usuarioController.pesquisaUsuarioPorNome(nome);
	}

	public String atualizaUsuario(String docID, String nome, String email, String celular) {
		return this.usuarioController.atualizaUsuario(docID, nome, email, celular);
	}

	public void removeUsuario(String docID) {
		this.usuarioController.removeUsuario(docID);
	}

	public void lerReceptores(String path) throws IOException {
		this.usuarioController.atualizaReceptores(path);
	}

	public void atualizaReceptores(String path) {
		this.usuarioController.atualizaReceptores(path);
	}
	
	public void adicionaDescritor(String descricao) {
		this.itemController.adicionaDescritor(descricao);
	}

	public String adicionaItemParaDoacao(String doadorID, String descricaoItem, int quantidade, String tags) {
		return this.itemController.adicionaItem(doadorID, descricaoItem, quantidade, tags);
	}

	public String exibeItem(String itemID, String doadorID) {
		return this.itemController.exibeItem(itemID, doadorID);
	}

	public String atualizaItemParaDoacao(String itemID, String doadorID, int quantidade, String tags) {
		return this.itemController.atualizaItem(itemID, doadorID, quantidade, tags);
	}

	public void removeItemParaDoacao(String itemID, String doadorID) {
		this.itemController.removeItem(itemID, doadorID);
	}
	
	public String listaDescritorDeItensParaDoacao() {
		return this.itemController.listaDescritorDeItensParaDoacao();
	}
	
	public String listaItensParaDoacao() {
		return this.itemController.listaItens("doador");
	}
	
	public String pesquisaItemParaDoacaoPorDescricao(String desc) {
		return this.itemController.pesquisaItemParaDoacaoPorDescricao(desc);
	}
	
	public String adicionaItemNecessario(String idReceptor, String descricaoItem, int quantidade, String tags) {
		return this.itemController.adicionaItem(idReceptor, descricaoItem, quantidade, tags);
	} 
	
	public String listaItensNecessarios() {
		return this.itemController.listaItens("receptor");
	}
	
	public String atualizaItemNecessario(String idReceptor, String idItem, int quantidade,String tags) {
		return this.itemController.atualizaItem(idItem, idReceptor, quantidade, tags);
	}
	
	public void removeItemNecessario(String idReceptor, String idItem) {
		this.itemController.removeItem(idItem, idReceptor);
	}
	
	public String match (String idReceptor,String idItemNecessario) {
		return this.itemController.match(idReceptor, idItemNecessario);
	}
	
	public String realizaDoacao(String idItemNecessario, String idItemDoado, String data) {
		return this.itemController.realizaDoacao(idItemNecessario,idItemDoado,data);
	}
	
	public String listaDoacoes() {
		return this.itemController.listaDoacoes();
	}
	
	public void finalizaSistema() {
		this.usuarioController.finalizaSistema();
		this.itemController.finalizaSistema();
	}
	
	public void iniciaSistema() {
		this.itemController.inicializaSistema();
		this.usuarioController.inicializaSistema();
	}

	public static void main(String[] args) {
		args = new String[] {"view.Facade", 
							 "acceptance_tests/use_case_1.txt",
							 "acceptance_tests/use_case_2.txt",
							 "acceptance_tests/use_case_3.txt",
							 "acceptance_tests/use_case_4.txt",
							 "acceptance_tests/use_case_5.txt",
							 "acceptance_tests/use_case_6.txt",
							 "acceptance_tests/use_case_7.txt"};
		EasyAccept.main(args);
	}

}
