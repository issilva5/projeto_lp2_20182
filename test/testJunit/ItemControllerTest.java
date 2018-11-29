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
		this.uc.adicionaDoador("12345678910", "Ariel", "ariel@ccc", "992484833", "PESSOA_FISICA");
		this.uc.adicionaDoador("10154010408", "Itallo", "itallo@ccc", "81792479", "PESSOA_FISICA");
		this.uc.adicionaDoador("40028922000", "Isaias", "isaias@ccc", "40028922", "PESSOA_FISICA");
		this.ic = new ItemController(this.uc);
		this.ic.adicionaDescritor("cobertor");
		this.ic.adicionaDescritor("fralda");
		this.ic.adicionaItem("10154010408", "fralda", 10, "geriatrica,pequena");
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
		assertEquals(aux.getMessage(), "Descritor de Item ja existente: fralda.");
	}

	@Test
	public void testAdicionaItemDoadorNull() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItem(null, "fralda", 10, "geriatrica,grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testAdicionaItemDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItem("  ", "fralda", 10, "geriatrica,grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testAdicionaItemDescritorNull() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItem("10154010408", null, 10, "geriatrica,grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: descricao nao pode ser vazia ou nula.");
	}

	@Test
	public void testAdicionaItemDescritorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItem("10154010408", "", 10, "geriatrica,grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: descricao nao pode ser vazia ou nula.");
	}

	@Test
	public void testAdicionaItemQuantidadeZero() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItem("10154010408", "fralda", 0, "geriatrica,grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: quantidade deve ser maior que zero.");
	}

	@Test
	public void testAdicionaItemQuantidadeNegativa() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.adicionaItem("10154010408", "fralda", -10, "geriatrica,grande");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: quantidade deve ser maior que zero.");
	}

	@Test
	public void testAdicionaItemTagsNull() {
		Throwable aux = assertThrows(NullPointerException.class, () -> {
			this.ic.adicionaItem("10154010408", "fralda", 10, null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: tag nao pode ser nula");
	}

	@Test
	public void testAdicionaItemTagsVazia() {
		assertEquals("2", this.ic.adicionaItem("10154010408", "fralda", 10, ""));
	}

	@Test
	public void testAdicionaItemDescritorInexistente() {
		assertEquals("2", this.ic.adicionaItem("10154010408", "bola", 10, "ioga,idosos"));
	}

	@Test
	public void testAdicionaDoisItemDescritor() {
		assertEquals("2", this.ic.adicionaItem("10154010408", "bola", 10, "ioga,idosos"));
		assertEquals("3", this.ic.adicionaItem("10154010408", "fralda", 10, "geriatrica,grande"));
		assertEquals(20, this.ic.getDescritorQuant("fralda"));
		assertEquals(10, this.ic.getDescritorQuant("bola"));
	}

	@Test
	public void testAdicionaUsuarioInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.adicionaItem("12345678911", "fralda", 10, "");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 12345678911.");
	}

	@Test
	public void testAdicionaDoisItemDiferentesDonos() {
		this.ic.adicionaItem("10154010408", "bola", 10, "ioga,idosos");
		this.ic.adicionaItem("40028922000", "bola", 10, "geriatrica,grande");
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
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser negativo.");
	}

	@Test
	public void testExibeItemIdDoadorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem("1", null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testExibeItemIdDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.exibeItem("1", "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testExibeItemIdDoadorInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.exibeItem("1", "12345678911");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 12345678911.");
	}

	@Test
	public void testExibeItemIdItemInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.exibeItem("2", "40028922000");
		});
		assertEquals(aux.getMessage(), "Item nao encontrado: 2.");
	}

	@Test
	public void testExibeItem() {
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 10", this.ic.exibeItem("1", "10154010408"));
	}

	@Test
	public void testAtualizaItemIdNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItem(null, "4002892200", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}

	@Test
	public void testAtualizaItemIdVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItem("", "4002892200", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}

	@Test
	public void testAtualizaItemIdNegativo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItem("-4", "4002892200", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser negativo.");
	}

	@Test
	public void testAtualizaItemIdDoadorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItem("1", null, 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testAtualizaItemIdDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.atualizaItem("1", "   ", 10, "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testAtualizaItemUsuarioInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.atualizaItem("1", "123456789", 0, "");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 123456789.");
	}

	@Test
	public void testAtualizaItemItemInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.atualizaItem("2", "10154010408", 0, "huge");
		});
		assertEquals(aux.getMessage(), "Item nao encontrado: 2.");
	}

	@Test
	public void testAtualizaItemParaDoacaoTags() {
		this.ic.atualizaItem("1", "10154010408", 0, "");
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 10", this.ic.exibeItem("1", "10154010408"));
	}

	@Test
	public void testAtualizaItemParaDoacaoTags2() {
		this.ic.atualizaItem("1", "10154010408", 0, "pequena");
		assertEquals("1 - fralda, tags: [pequena], quantidade: 10", this.ic.exibeItem("1", "10154010408"));
	}

	@Test
	public void testAtualizaItemParaDoacaoQuantidadeMaior() {
		this.ic.atualizaItem("1", "10154010408", 15, "");
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 15", this.ic.exibeItem("1", "10154010408"));
		assertEquals(15, this.ic.getDescritorQuant("fralda"));
	}

	@Test
	public void testAtualizaItemParaDoacaoQuantidadeMenor() {
		this.ic.atualizaItem("1", "10154010408", 5, "");
		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 5", this.ic.exibeItem("1", "10154010408"));
		assertEquals(5, this.ic.getDescritorQuant("fralda"));
	}

	@Test
	public void testRemoveItemIdNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItem(null, "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}

	@Test
	public void testRemoveItemIdVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItem("   ", "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser vazia ou nula.");
	}

	@Test
	public void testRemoveItemIdNegativo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItem("-1", "10154010408");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do item nao pode ser negativo.");
	}

	@Test
	public void testRemoveItemIdDoadorNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItem("1", null);
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testRemoveItemIdDoadorVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			this.ic.removeItem("1", "");
		});
		assertEquals(aux.getMessage(), "Entrada invalida: id do usuario nao pode ser vazio ou nulo.");
	}

	@Test
	public void testRemoveItemUsuarioInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.removeItem("1", "123456789");
		});
		assertEquals(aux.getMessage(), "Usuario nao encontrado: 123456789.");
	}

	@Test
	public void testRemoveItemItemInexistente() {
		Throwable aux = assertThrows(UnsupportedOperationException.class, () -> {
			this.ic.removeItem("2", "10154010408");
		});
		assertEquals(aux.getMessage(), "Item nao encontrado: 2.");
	}

	@Test
	public void testRemoveItemParaDoacao() {
		assertEquals(10, this.ic.getDescritorQuant("fralda"));
		this.ic.removeItem("1", "10154010408");
		assertEquals(0, this.ic.getDescritorQuant("fralda"));
	}

	@Test
	public void testListaDescritorDeItensParaDoacao() {
		assertEquals("0 - cobertor | 10 - fralda", this.ic.listaDescritorDeItensParaDoacao());
	}

	@Test
	public void testListaItensParaDoacaoComQuantidadeIgual() {

		this.ic.adicionaItem("12345678910", "cobertor", 10, "lã,grande");

		assertEquals(
				"2 - cobertor, tags: [lã, grande], quantidade: 10, doador: Ariel/12345678910 | 1 - fralda, tags: [geriatrica, pequena], quantidade: 10, doador: Itallo/10154010408",
				this.ic.listaItens("doador"));
	}

	@Test
	public void testListaItensParaDoacao() {

		this.ic.adicionaItem("12345678910", "cobertor", 2, "lã,pequeno");

		assertEquals(
				"1 - fralda, tags: [geriatrica, pequena], quantidade: 10, doador: Itallo/10154010408 | 2 - cobertor, tags: [lã, pequeno], quantidade: 2, doador: Ariel/12345678910",
				this.ic.listaItens("doador"));
	}
	
	@Test
	public void testListaItensNecessariosVazio() {
		
		assertEquals("",this.ic.listaItens("receptor"));
	}
	
	@Test
	public void testListaItensNecessarios() {
		
		this.uc.atualizaReceptores("arquivos_sistema/novosReceptores.csv");
		
		this.ic.adicionaItem("80643201009", "fralda", 2, "bebe,pequena");
		
		this.ic.adicionaItem("58090077080", "cobertor", 10, "la,grande");
		
		assertEquals("2 - fralda, tags: [bebe, pequena], quantidade: 2, Receptor: Tomas Otavio Lucas Teixeira/80643201009 | 3 - cobertor, tags: [la, grande], quantidade: 10, Receptor: Isabelly Alice Bernardes/58090077080",this.ic.listaItens("receptor"));
	}


	@Test
	public void testPesquisaItemParaDoacaoPorDescricaoVazia() {

		assertThrows(IllegalArgumentException.class, () -> {
			this.ic.pesquisaItemParaDoacaoPorDescricao("");

		});
	}

	@Test
	public void testPesquisaItemParaDoacaoPorDescricaoNula() {

		assertThrows(IllegalArgumentException.class, () -> {
			this.ic.pesquisaItemParaDoacaoPorDescricao(null);

		});
	}
	
	@Test
	public void testPesquisaItemParaDoacaoPorDescricaoInexistente() {

		assertEquals("",this.ic.pesquisaItemParaDoacaoPorDescricao("cobertor"));
	}
	
	@Test
	public void testPesquisaItemParaDoacaoPorDescricaoUpperCase() {

		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 10",this.ic.pesquisaItemParaDoacaoPorDescricao("FRALDA"));
	}
	
	@Test
	public void testPesquisaItemParaDoacaoPorDescricaoUpperLowerCase() {

		assertEquals("1 - fralda, tags: [geriatrica, pequena], quantidade: 10",this.ic.pesquisaItemParaDoacaoPorDescricao("Fralda"));
	}
	
	
	@Test
	public void testPesquisaItemParaDoacaoPorDescricao() {
          
		this.ic.adicionaItem("12345678910", "fralda", 2, "bebe,pequena");
 
		assertEquals("2 - fralda, tags: [bebe, pequena], quantidade: 2 | 1 - fralda, tags: [geriatrica, pequena], quantidade: 10",this.ic.pesquisaItemParaDoacaoPorDescricao("fralda"));
	}	

}
