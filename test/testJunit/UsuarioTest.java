package testJunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import model.Usuario;

class UsuarioTest {

	@Test
	void testUsuario() {
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("  ", "email", "docId", "celular", "PESSOA_FISICA", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", "  ", "docId", "celular", "IGREJA", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", "email", "  ", "celular", "ORGAO_PUBLICO_MUNICIPAL", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", "email", "docId", "  ", "ORGAO_PUBLICO_ESTADUAL", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", "email", "docId", "celular", "  ", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario(null, "email", "docId", "celular", "classe", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", null, "  ", "celular", "classe", "status"));
		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", "email", null, "  ", "classe", "status"));
		assertThrows(IllegalArgumentException.class, () -> new Usuario("nome", "email", "docId", null, "  ", "status"));
		assertThrows(IllegalArgumentException.class, () -> new Usuario("nome", "email", "docId", "celular", null, ""));

		assertThrows(IllegalArgumentException.class,
				() -> new Usuario("nome", "email", "docId", "celular", "PESSOA", "status"));
	}

	@Test
	void testToString() {
		Usuario user1 = new Usuario("nome", "email", "docId", "celular", "PESSOA_FISICA", "doador");
		Usuario user2 = new Usuario("nome", "email", "docId", "celular", "ORGAO_PUBLICO_MUNICIPAL", "doador");
		Usuario user3 = new Usuario("nome", "email", "docId", "celular", "ORGAO_PUBLICO_ESTADUAL", "doador");
		Usuario user4 = new Usuario("nome", "email", "docId", "celular", "ORGAO_PUBLICO_FEDERAL", "doador");
		Usuario user5 = new Usuario("nome", "email", "docId", "celular", "ONG", "doador");
		Usuario user6 = new Usuario("nome", "email", "docId", "celular", "ASSOCIACAO", "doador");
		Usuario user7 = new Usuario("nome", "email", "docId", "celular", "SOCIEDADE", "doador");
		Usuario user8 = new Usuario("nome", "email", "docId", "celular", "PESSOA_FISICA", "receptor");

		assertEquals(user1.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user2.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user3.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user4.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user5.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user6.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user7.toString(), "nome/docId, email, celular, status: doador");
		assertEquals(user8.toString(), "nome/docId, email, celular, status: receptor");
	}

	@Test
	void testEqualsObject() {

		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");
		Usuario user2 = new Usuario("nome", "email", "00000000000", "celular", "ORGAO_PUBLICO_MUNICIPAL", "doador");
		Usuario user3 = new Usuario("nome", "email", "00000000000", "celular", "ORGAO_PUBLICO_ESTADUAL", "doador");
		Usuario user4 = new Usuario("nome", "email", "000000000000000", "celular", "ORGAO_PUBLICO_FEDERAL", "doador");
		Usuario user5 = new Usuario("nome", "email", "000000000000000", "celular", "ONG", "doador");
		Usuario user6 = new Usuario("nome", "email", "000000000000000", "celular", "PESSOA_FISICA", "receptor");

		assertTrue(user1.equals(user2));
		assertTrue(user1.equals(user3));
		assertTrue(user4.equals(user5));
		assertTrue(user4.equals(user6));
		assertFalse(user1.equals(user5));
		assertFalse(user1.equals(user6));
	}

	@Test
	void testAdicionaItem() {
		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");
		user1.adicionaItem("1", "guarda roupa", 1, "guarda coisas,top");
		user1.adicionaItem("2", "guarda coisa", 1, "coiseiro, bomdms");
		user1.adicionaItem("3", "coisas", 5, "coisa");

		assertThrows(IllegalArgumentException.class,
				() -> user1.adicionaItem(" ", "guarda roupa", 1, "guarda coisas,top"));
		assertThrows(IllegalArgumentException.class, () -> user1.adicionaItem("1", "  ", 1, "guarda coisas,top"));

		assertEquals(user1.exibeItem("1"), "1 - guarda roupa, tags: [guarda coisas, top], quantidade: 1");
		assertEquals(user1.exibeItem("2"), "2 - guarda coisa, tags: [coiseiro,  bomdms], quantidade: 1");
		assertEquals(user1.exibeItem("3"), "3 - coisas, tags: [coisa], quantidade: 5");
	}

	@Test
	void testRemoveItem() {
		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");
		user1.adicionaItem("1", "guarda roupa", 3, "guarda coisas,top");

		assertEquals(user1.exibeItem("1"), "1 - guarda roupa, tags: [guarda coisas, top], quantidade: 3");

		user1.removeItem("1");

		assertThrows(UnsupportedOperationException.class, () -> user1.exibeItem("1"));
		assertThrows(UnsupportedOperationException.class, () -> user1.exibeItem("4"));
	}

	@Test
	void testAtualizaQuantidadeItem() {
		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");

		user1.adicionaItem("1", "guarda roupa", 1, "guarda coisas,top");
		user1.adicionaItem("2", "guarda coisa", 1, "coiseiro, bomdms");
		user1.adicionaItem("3", "coisas", 5, "coisa");

		user1.atualizaQuantidadeItem("1", 20);
		user1.atualizaQuantidadeItem("2", 25);
		user1.atualizaQuantidadeItem("3", 30);

		assertEquals(user1.exibeItem("1"), "1 - guarda roupa, tags: [guarda coisas, top], quantidade: 20");
		assertEquals(user1.exibeItem("2"), "2 - guarda coisa, tags: [coiseiro,  bomdms], quantidade: 25");
		assertEquals(user1.exibeItem("3"), "3 - coisas, tags: [coisa], quantidade: 30");

		assertThrows(IllegalArgumentException.class, () -> user1.atualizaQuantidadeItem("3", 0));
		assertThrows(NullPointerException.class, () -> user1.atualizaQuantidadeItem("30", 3));
	}

	@Test
	void testAtualizaTagsItem() {

		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");

		user1.adicionaItem("1", "guarda roupa", 1, "guarda coisas,top");
		user1.adicionaItem("2", "guarda coisa", 1, "coiseiro, bomdms");
		user1.adicionaItem("3", "coisas", 5, "coisa");

		user1.atualizaTagsItem("1", "roupas top");
		assertEquals(user1.exibeItem("1"), "1 - guarda roupa, tags: [roupas top], quantidade: 1");
		assertThrows(UnsupportedOperationException.class, () -> user1.atualizaTagsItem("41", "roupas top"));
	}

	@Test
	void testExibeItem() {
		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");

		user1.adicionaItem("1", "guarda roupa", 1, "guarda coisas,top");
		assertEquals(user1.exibeItem("1"), "1 - guarda roupa, tags: [guarda coisas, top], quantidade: 1");
		assertThrows(UnsupportedOperationException.class, () -> user1.exibeItem("15"));
	}

	@Test
	void testExisteItem() {

		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");

		user1.adicionaItem("1", "guarda roupa", 1, "guarda coisas,top");

		assertTrue(user1.existeItem("1"));
		assertFalse(user1.existeItem("13"));

		assertThrows(IllegalArgumentException.class, () -> user1.existeItem("-2"));

	}

	@Test
	void testGetItem() {

		Usuario user1 = new Usuario("nome", "email", "00000000000", "celular", "PESSOA_FISICA", "doador");

		user1.adicionaItem("1", "guarda roupa", 1, "guarda coisas,top");
		assertEquals(user1.getItemDescritor("1"), "guarda roupa");
		assertThrows(UnsupportedOperationException.class, () -> user1.getItemDescritor("3"));
		assertThrows(IllegalArgumentException.class, () -> user1.getItemDescritor("-3"));
	}

}
