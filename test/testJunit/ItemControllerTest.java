package testJunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ItemController;
import controller.UsuarioController;

class ItemControllerTest {

	private ItemController ic;
	private UsuarioController uc;
	
	@BeforeEach
	public void setUp() {
		this.uc = new UsuarioController();
		this.uc.adicionaDoador("10154010408", "Itallo", "itallo@ccc", "81792479", "PESSOA_FISICA");
		this.uc.adicionaDoador("40028922000", "Isaias", "isaias@ccc", "40028922", "PESSOA_FISICA");
		this.ic = new ItemController(this.uc);
		this.ic.adicionaDescritor("fralda");
		this.ic.adicionaItemParaDoacao("10154010408", "fralda", 10, "geriatrica, pequena");
	}
	
	@Test
	public void testAdicionaDescritorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaDescritor(null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: descricao nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAdicionaDescritorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaDescritor("  ");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: descricao nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAdicionaDescritorExistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.adicionaDescritor("fralda");
		});
		assertEquals(aux.getMessage(), "Descritor de Item ja existente: fralda");
	}

	@Test
	public void testAdicionaItemDoadorNull() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao(null, "fralda", 10, "geriatrica, grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAdicionaItemDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao("  ", "fralda", 10, "geriatrica, grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAdicionaItemDescritorNull() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao("10154010408", null, 10, "geriatrica, grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: descricao nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAdicionaItemDescritorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao("10154010408", "", 10, "geriatrica, grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: descricao nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAdicionaItemQuantidadeZero() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao("10154010408", "fralda", 0, "geriatrica, grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: quantidade deve ser maior que zero.");
	}
	
	@Test
	public void testAdicionaItemQuantidadeNegativa() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao("10154010408", "fralda", -10, "geriatrica, grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: quantidade deve ser maior que zero.");
	}
	
	@Test
	public void testAdicionaItemTagsNull() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItemParaDoacao("10154010408", "fralda", 10, null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: tag nao pode ser nula");
	}
	
	@Test
	public void testAdicionaItemTagsVazia() {
		assertEquals("2", this.ic.adicionaItemParaDoacao("10154010408", "fralda", 10, ""));
	}
	
	@Test
	public void testAdicionaItemDescritorInexistente() {
		assertEquals("2", this.ic.adicionaItemParaDoacao("10154010408", "bola", 10, "ioga, idosos"));
	}
	
	@Test
	public void testAdicionaDoisItemDescritor() {
		assertEquals("2", this.ic.adicionaItemParaDoacao("10154010408", "bola", 10, "ioga, idosos"));
		assertEquals("3", this.ic.adicionaItemParaDoacao("10154010408", "fralda", 10, "geriatrica, grande"));
		assertEquals(20, this.ic.getDescritorQuant("fralda"));
		assertEquals(10, this.ic.getDescritorQuant("bola"));
	}
	
	@Test
	public void testAdicionaUsuarioInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.adicionaItemParaDoacao("12345678911", "fralda", 10, null);
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 12345678911");
	}
	
	@Test
	public void testAdicionaDoisItemDiferentesDonos() {
		this.ic.adicionaItemParaDoacao("10154010408", "bola", 10, "ioga, idosos");
		this.ic.adicionaItemParaDoacao("40028922000", "bola", 10, "geriatrica, grande");
		assertEquals(20, this.ic.getDescritorQuant("bola"));
	}

	@Test
	public void testExibeItemIdNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem(null, "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testExibeItemIdVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem("   ", "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testExibeItemIdNegativo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem("-1", "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: Entrada invalida: id do item nao pode ser negativo..");
	}
	
	@Test
	public void testExibeItemIdDoadorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem("1", null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testExibeItemIdDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem("1", "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testExibeItemIdDoadorInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.exibeItem("1", "12345678911");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 12345678911");
	}
	
	@Test
	public void testExibeItemIdItemInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.exibeItem("2", "40028922000");
		});
		assertEquals(aux.getMessage(), "Item nao encontrado: 2");
	}
	
	@Test
	public void testExibeItem() {
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 10", this.ic.exibeItem("1", "10154010408"));
	}

	@Test
	public void testAtualizaItemIdNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao(null, "4002892200", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAtualizaItemIdVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("", "4002892200", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAtualizaItemIdNegativo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("-4", "4002892200", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAtualizaItemIdDoadorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("1", null, 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAtualizaItemIdDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("1", "   ", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testAtualizaItemQuantidadeNegativa() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("1", "10154010408", -10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: quantidade nao pode ser negativa.");
	}
	
	@Test
	public void testAtualizaItemUsuarioInexistente() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("1", "123456789", 0, "");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 123456789");
	}
	
	@Test
	public void testAtualizaItemItemInexistente() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItemParaDoacao("2", "10154010408", 0, "huge");
		});
		assertEquals(aux.getMessage(), "Item nao encontrado: 2");
	}
	
	@Test
	public void testAtualizaItemParaDoacaoTags() {
		this.ic.atualizaItemParaDoacao("1", "10154010408", 0, "");
		assertEquals("1 - fralda, tags: [], quantidade: 10", this.ic.exibeItem("1", "10154010408"));
	}
	
	@Test
	public void testAtualizaItemParaDoacaoTags2() {
		this.ic.atualizaItemParaDoacao("1", "10154010408", 0, "pequena");
		assertEquals("1 - fralda, tags: [pequena], quantidade: 10", this.ic.exibeItem("1", "10154010408"));
	}
	
	@Test
	public void testAtualizaItemParaDoacaoQuantidadeMaior() {
		this.ic.atualizaItemParaDoacao("1", "10154010408", 15, "");
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 15", this.ic.exibeItem("1", "10154010408"));
		assertEquals(15, this.ic.getDescritorQuant("fralda"));
	}
	
	@Test
	public void testAtualizaItemParaDoacaoQuantidadeMenor() {
		this.ic.atualizaItemParaDoacao("1", "10154010408", 5, "");
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 5", this.ic.exibeItem("1", "10154010408"));
		assertEquals(5, this.ic.getDescritorQuant("fralda"));
	}
	
	@Test
	public void testRemoveItemIdNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao(null, "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testRemoveItemIdVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao("   ", "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testRemoveItemIdNegativo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao("-1", "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: Entrada invalida: id do item nao pode ser negativo..");
	}
	
	@Test
	public void testRemoveItemIdDoadorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao("1", null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testRemoveItemIdDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao("1", "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usario nao pode ser vazia ou nula.");
	}
	
	@Test
	public void testRemoveItemUsuarioInexistente() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao("1", "123456789");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 123456789");
	}
	
	@Test
	public void testRemoveItemItemInexistente() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItemParaDoacao("2", "10154010408");
		});
		assertEquals(aux.getMessage(), "Item nao encontrado: 2");
	}

	@Test
	public void testRemoveItemParaDoacao() {
		this.ic.removeItemParaDoacao("1", "10154010408");
		assertEquals(0, this.ic.getDescritorQuant("fralda"));
	}

}
