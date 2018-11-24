package testJunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Item;

class ItemTest {

	private Item i;
	
	@BeforeEach
	public void setUp() {
		this.i = new Item(15, "fralda", 20, "geriatrica, grande");
	}
	
	@Test
	public void testItemIdNegativo() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			new Item(-15, "fralda", 20, "geriatrica, grande");
		});
		assertEquals("Entrada invalida: id do item nao pode ser negativo.", aux.getMessage());
	}
	
	@Test
	public void testItemDescricaoNula() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			new Item(15, null, 20, "geriatrica, grande");
		});
		assertEquals("Entrada invalida: descricao nao pode ser vazia ou nula.", aux.getMessage());
	}
	
	@Test
	public void testItemDescricaoVazia() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			new Item(15, "   ", 20, "geriatrica, grande");
		});
		assertEquals("Entrada invalida: descricao nao pode ser vazia ou nula.", aux.getMessage());
	}
	
	@Test
	public void testItemQuantNegativa() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			new Item(15, "fralda", -20, "geriatrica, grande");
		});
		assertEquals("Entrada invalida: quantidade deve ser maior que zero.", aux.getMessage());
		
		aux = assertThrows(IllegalAccessException.class, () -> {
			new Item(15, "fralda", 0, "geriatrica, grande");
		});
		assertEquals("Entrada invalida: quantidade deve ser maior que zero.", aux.getMessage());
	}
	
	@Test
	public void testItemTagNula() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			new Item(15, "fralda", -20, null);
		});
		assertEquals("Entrada invalida: tag nao pode ser nula", aux.getMessage());
	}

	@Test
	public void testGetQuantidade() {
		assertEquals(15, this.i.getQuantidade());
	}

	@Test
	public void testGetDescritor() {
		assertEquals("geriatrica, grande", this.i.getTag());
	}
	
	@Test
	public void testSetQuantidade() {
		this.i.setQuantidade(30);
		assertEquals(30, this.i.getQuantidade());
	}
	
	@Test
	public void testSetQuantidadeInvalida() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			this.i.setQuantidade(-30);
		});
		assertEquals("Entrada invalida: quantidade deve ser maior que zero.", aux.getMessage());
	}

	@Test
	public void testSetTag() {
		this.i.setTag("geriatrica, pequena");
		assertEquals("geriatrica, pequena", this.i.getTag());
	}

	@Test
	public void testSetTagNula() {
		Throwable aux = assertThrows(IllegalAccessException.class, () -> {
			this.i.setTag(null);
		});
		assertEquals("Entrada invalida: tag nao pode ser nula", aux.getMessage());
	}
	
	@Test
	public void testEqualsSelf() {
		assertEquals(this.i, this.i);
	}
	
	@Test
	public void testEqualsNull() {
		assertNotEquals(this.i, null);
	}
	
	@Test
	public void testEqualsOutraClasse() {
		assertNotEquals(this.i, "Oi");
	}
	
	@Test
	public void testEquals() {
		Item j = new Item(15, "fralda", 20, "geriatrica, grande");
		assertEquals(j, this.i);
		assertEquals(j.hashCode(), this.i.hashCode());
	}
	
	@Test
	public void testNotEquals() {
		Item j = new Item(15, "fralda", 20, "geriatrica, pequena");
		assertNotEquals(j, this.i);
		assertNotEquals(j.hashCode(), this.i.hashCode());
	}
	
	@Test
	public void testNotEqualsOrdemTags() {
		Item j = new Item(15, "fralda", 20, "grande, geriatrica");
		assertNotEquals(j, this.i);
	}
	
	@Test
	public void testNotEqualsDescritor() {
		Item j = new Item(15, "fralda geriatrica", 20, "geriatrica, pequena");
		assertNotEquals(j, this.i);
	}

}
