package testJunit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import controller.UsuarioController;

class UsuarioControllerTest {

	UsuarioController controller = new UsuarioController();
	
	@Test
	void testAdicionaDoador() {
	//"PESSOA_FISICA", "IGREJA", "ORGAO_PUBLICO_MUNICIPAL",	"ORGAO_PUBLICO_ESTADUAL", "ORGAO_PUBLICO_FEDERAL", "ONG", "ASSOCIACAO", "SOCIEDADE"	
		
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaDoador("1", "nome","celular", "email", "doador"));
		
		controller.adicionaDoador("1", "nome","celular", "email", "PESSOA_FISICA");
		controller.adicionaDoador("2", "nome","celular", "email", "IGREJA");
		controller.adicionaDoador("3", "nome","celular", "email", "ORGAO_PUBLICO_MUNICIPAL");
		controller.adicionaDoador("4", "nome","celular", "email", "ORGAO_PUBLICO_ESTADUAL");
		controller.adicionaDoador("5", "nome","celular", "email", "ORGAO_PUBLICO_FEDERAL");
		controller.adicionaDoador("6", "nome","celular", "email", "ONG");
		controller.adicionaDoador("7", "nome","celular", "email", "ASSOCIACAO");
		controller.adicionaDoador("8", "nome","celular", "email", "SOCIEDADE");

		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaDoador(" ", "nome","celular", "email", "SOCIEDADE"));
		
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaDoador("8", " ","celular", "email", "SOCIEDADE"));
		
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaDoador("8", "nome"," ", "email", "SOCIEDADE"));
		
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaDoador("8", "nome","celular", " ", "SOCIEDADE"));
		
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaDoador("8", "nome","celular", "email", " "));
		
	}

	@Test
	void testPesquisaUsuarioPorId() {
		controller.atualizaReceptores("arquivos_sistema/atualizaReceptores.csv");

		controller.adicionaDoador("1", "nome","celular", "email", "PESSOA_FISICA");
		controller.adicionaDoador("2", "nome","celular", "email", "IGREJA");
		
		assertEquals(controller.pesquisaUsuarioPorId("1"),"nome/1, celular, email, status: doador");
		assertEquals(controller.pesquisaUsuarioPorId("2"),"nome/2, celular, email, status: doador");
		assertEquals(controller.pesquisaUsuarioPorId("84473712044"),"Murilo Luiz Brito/84473712044, muriloluiz@ipmmi.org.br, (31) 99770-7474, status: receptor");

		assertThrows(UnsupportedOperationException.class,
				() -> controller.pesquisaUsuarioPorId("10"));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.pesquisaUsuarioPorId("-10"));
	}

	@Test
	void testPesquisaUsuarioPorNome() {
		controller.atualizaReceptores("arquivos_sistema/atualizaReceptores.csv");

		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");
		controller.adicionaDoador("2", "nome2","celular", "email", "IGREJA");
		
		assertEquals(controller.pesquisaUsuarioPorNome("nome1"),"nome1/1, celular, email, status: doador");
		assertEquals(controller.pesquisaUsuarioPorNome("nome2"),"nome2/2, celular, email, status: doador");
		assertEquals(controller.pesquisaUsuarioPorNome("Murilo Luiz Brito"),"Murilo Luiz Brito/84473712044, muriloluiz@ipmmi.org.br, (31) 99770-7474, status: receptor");

		controller.adicionaDoador("3", "nome2","celular", "email", "IGREJA");
		assertEquals(controller.pesquisaUsuarioPorNome("nome2"),"nome2/2, celular, email, status: doador | nome2/3, celular, email, status: doador");

		assertThrows(IllegalArgumentException.class,
				() -> controller.pesquisaUsuarioPorNome(" "));
		assertThrows(IllegalArgumentException.class,
				() -> controller.pesquisaUsuarioPorNome(null));
	}

	@Test
	//TODO
	void testAtualizaUsuario() {
		
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		assertEquals(controller.pesquisaUsuarioPorId("1"),"nome1/1, celular, email, status: doador");

		
		assertEquals(controller.atualizaUsuario("1", "", "celularnovo", ""),"nome1/1, celularnovo, email, status: doador");
		assertEquals(controller.pesquisaUsuarioPorId("1"),"nome1/1, celularnovo, email, status: doador");

		assertEquals(controller.atualizaUsuario("1", "nomenovo", " ", ""),"nomenovo/1, celularnovo, email, status: doador");
		assertEquals(controller.atualizaUsuario("1", " ", " ", "emailnovo"),"nomenovo/1, celularnovo, emailnovo, status: doador");
		assertEquals(controller.atualizaUsuario("1", " ", " ", ""),"nomenovo/1, celularnovo, emailnovo, status: doador");
	
		assertThrows(IllegalArgumentException.class,
				() -> controller.atualizaUsuario("", " ", " ", ""));
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.atualizaUsuario("-1", " ", " ", ""));
		
	}

	@Test
	void testRemoveUsuario() {
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.atualizaReceptores("arquivos_sistema/atualizaReceptores.csv");

		assertEquals(controller.pesquisaUsuarioPorId("1"),"nome1/1, celular, email, status: doador");
		controller.removeUsuario("1");

		assertThrows(UnsupportedOperationException.class,
				() -> controller.pesquisaUsuarioPorId("1"));
		
		assertEquals(controller.pesquisaUsuarioPorId("84473712044"),"Murilo Luiz Brito/84473712044, muriloluiz@ipmmi.org.br, (31) 99770-7474, status: receptor");
		controller.removeUsuario("84473712044");
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.pesquisaUsuarioPorId("84473712044"));

	}

	@Test
	//TODO
	void testAtualizaReceptores() {
		String path = "arquivos_sistema/atualizaReceptores.csv";
		controller.atualizaReceptores(path);
	}

	@Test
	void testAdicionaItemParaDoacao() {
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.adicionaItemParaDoacao("1", "01", "roupa", 2,"guarda,roupa");
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.adicionaItemParaDoacao(" ", "01", "roupa", 2,"guarda,roupa"));
		assertThrows(NumberFormatException.class,
				() -> controller.adicionaItemParaDoacao("1", " ", "roupa", 2,"guarda,roupa"));
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaItemParaDoacao("1", "01", " ", 2,"guarda,roupa"));
		assertThrows(IllegalArgumentException.class,
				() -> controller.adicionaItemParaDoacao("1", "01", "roupa", -1,"guarda,roupa"));
	}

	@Test
	void testExibeItemParaDoacao() {
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.adicionaItemParaDoacao("1", "01", "roupa", 2,"guarda,roupa");
		
		assertEquals(controller.exibeItemParaDoacao("01", "1"),"01 - roupa, tags: [guarda, roupa], quantidade: 2");
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.exibeItemParaDoacao("1", "1"));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.exibeItemParaDoacao("01", "4"));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.exibeItemParaDoacao("1", " "));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.exibeItemParaDoacao(" ", "1"));
	}

	@Test
	void testAtualizaQuantidadeItem() {
		
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.adicionaItemParaDoacao("1", "01", "roupa", 2,"guarda,roupa");

		assertEquals(controller.exibeItemParaDoacao("01", "1"),"01 - roupa, tags: [guarda, roupa], quantidade: 2");
		
		controller.atualizaQuantidadeItem("01", "1", 5);
		assertEquals(controller.exibeItemParaDoacao("01", "1"),"01 - roupa, tags: [guarda, roupa], quantidade: 5");
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.atualizaQuantidadeItem(" ", "1",4));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.atualizaQuantidadeItem("01", "",4));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.atualizaQuantidadeItem("41", "1",4));
		assertThrows(IllegalArgumentException.class,
				() -> controller.atualizaQuantidadeItem("01", "1",0));
	}

	@Test
	void testAtualizaTagsItem() {
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.adicionaItemParaDoacao("1", "01", "roupa", 2,"guarda,roupa");
		
		controller.atualizaTagsItem("01", "1", "so,top");
		assertEquals(controller.exibeItemParaDoacao("01", "1"),"01 - roupa, tags: [so, top], quantidade: 2");
	}

	@Test
	void testRemoveItemParaDoacao() {
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.adicionaItemParaDoacao("1", "01", "roupa", 2,"guarda,roupa");
		assertEquals(controller.exibeItemParaDoacao("01", "1"),"01 - roupa, tags: [guarda, roupa], quantidade: 2");
		assertEquals(controller.removeItemParaDoacao("01", "1"),2);
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.removeItemParaDoacao("01", "1"));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.removeItemParaDoacao("031", "11"));
	}

	@Test
	void testGetItemDescritor() {
		controller.adicionaDoador("1", "nome1","celular", "email", "PESSOA_FISICA");		
		controller.adicionaItemParaDoacao("1", "01", "roupa", 2,"guarda,roupa");
		
		assertEquals(controller.getItemDescritor("01", "1"),"roupa");
		
		assertThrows(UnsupportedOperationException.class,
				() -> controller.getItemDescritor("011", "1"));
		assertThrows(UnsupportedOperationException.class,
				() -> controller.getItemDescritor("01", "41"));
		
	}

}
