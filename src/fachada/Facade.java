package fachada;
import controller.ItemController;
import controller.UsuarioController;
import easyaccept.EasyAccept;
import model.Item;

public class Facade {

	UsuarioController usuarioController = new UsuarioController();
	ItemController itemController = new ItemController(usuarioController);

	
	//Controller Usuario
	
	public String adicionaDoador(String docId, String nome, String email, String celular, String classe) {
		return usuarioController.adicionaDoador(docId, nome, email, celular, classe);
	}
	
	public String pesquisaUsuarioPorId(String docId) {
		return usuarioController.pesquisaUsuarioPorId(docId);
	}
	
	public String pesquisaUsuarioPorNome(String nome) {
		return usuarioController.pesquisaUsuarioPorNome(nome);
	}
	
	public String atualizaUsuario(String docId, String nome, String email, String celular) {
		return usuarioController.atualizaUsuario(docId, nome, email, celular);
	}
	
	public void removeUsuario(String docId) {
		usuarioController.removeUsuario(docId);
	}
	
	public void atualizaReceptores(String path) {
		usuarioController.atualizaReceptores(path);
	}
	
	public int adicionaItemParaDoacao(String idDoador, int idItem, Item i) {
		return usuarioController.adicionaItemParaDoacao(idDoador, idItem, i);
	}
	
	public String exibeItemParaDoacao(int idItem, String idDoador) {
		return usuarioController.exibeItemParaDoacao(idItem, idDoador);
	}
	
	public int atualizaQuantidadeItem(int idItem, String idDoador, int quantidade) {
		return usuarioController.atualizaQuantidadeItem(idItem, idDoador, quantidade);
	}
	
	public void atualizaTagsItem(int idItem, String idDoador, String tags) {
		usuarioController.atualizaTagsItem(idItem, idDoador, tags);
	}
	
	public void removeItemParaDoacao(int idItem, String idDoador) {
		usuarioController.removeItemParaDoacao(idItem, idDoador);
	}
	
	
	
	public static void main(String[] args) {
		args = new String[] { "fachada.Facade", "acceptance_tests/use_case_1.txt" };
		EasyAccept.main(args);
	}

}
