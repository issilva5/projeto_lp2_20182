package testJunit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Descritor;

class DescritorTest {

	private Descritor d;
	
	@BeforeEach
	public void setUp() {
		this.d = new Descritor("fralda", 15);
	}
	
	@Test
	void testDescritorNomeNulo() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			new Descritor(null, 15);
		});
		assertEquals("Entrada invalida: descricao nao pode ser vazia ou nula.", aux.getMessage());
	}
	
	@Test
	void testDescritorNomeVazio() {
		Throwable aux = assertThrows(IllegalArgumentException.class, () -> {
			new Descritor("", 15);
		});
		assertEquals("Entrada invalida: descricao nao pode ser vazia ou nula.", aux.getMessage());
	}

	@Test
	void testGetNome() {
		assertEquals("fralda", this.d.getNome());
	}

	@Test
	void testGetQuantidade() {
		assertEquals(15, this.d.getQuantidade());
	}
	
	@Test
	void testChangeQuantUp() {
		this.d.changeQuant(5);
		assertEquals(20, this.d.getQuantidade());
	}
	
	@Test
	void testChangeQuantDown() {
		this.d.changeQuant(-5);
		assertEquals(10, this.d.getQuantidade());
	}

	@Test
	void testEqualsSelf() {
		assertEquals(this.d, this.d);
	}
	
	@Test
	void testEqualsNull() {
		assertNotEquals(this.d, null);
	}
	
	@Test
	void testEqualsDifClass() {
		assertNotEquals(this.d, "Oi");
	}
	
	@Test
	void testEquals() {
		Descritor e = new Descritor("fralda", 15);
		assertEquals(e, this.d);
		assertEquals(e.hashCode(), this.d.hashCode());
	}
	
	@Test
	void testNotEquals() {
		Descritor e = new Descritor("cadeira", 15);
		assertNotEquals(e, this.d);
	}
	
	@Test
	void testCompareTo() {
		Descritor e = new Descritor("cadeira", 15);
		assertTrue(e.compareTo(this.d) < 0);
	}
	
	@Test
	void testToString() {
		assertEquals("15 - fralda", this.d.toString());
	}

}
