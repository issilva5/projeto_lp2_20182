package model;

/**
 * 
 * Representacao de um usuario receptor
 *
 */

public class Receptor extends Usuario {
	
	/**
	 * Metodo responsavel por construir um usuario receptor a partir do nome,email, documento
	 * de identificado, celular e classe. O status do usuario sera 
	 * 'receptor'
	 * 
	 * @param nome nome do usuario receptor
	 * @param email email do usuario receptor
	 * @param docId documento de identificacao do usuario receptor
	 * @param celular celular do usuario receptor
	 * @param classe classe do usuario receptor
	 * @param sistemaId número do cadastro do usuário no sitema.
	 */

	public Receptor(String nome, String email, String docId, String celular,String classe, int sistemaId) {
		super(nome, email, docId, celular,classe,"receptor", sistemaId);
	}

}
