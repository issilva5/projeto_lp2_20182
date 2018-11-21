package util;
import java.util.Comparator;

import model.Usuario;

/**
 * Implementa um comparador de usuário pela ordem de inserção no sistema.
 */
public class Comparador implements Comparator<Usuario> {

	/**
	 * Implementa um comparador de usuário pela ordem de inserção no sistema.
	 */
	@Override
	public int compare(Usuario user0, Usuario user1) {
		return user0.getSistemaId() - user1.getSistemaId();
	}

}
