package model;

/**
 * Representacao de um usuario doador
 *
 */

public class Doador  extends Usuario{
	
	/**
	 * Constroi um usuario doador a partir do nome,email, documento
	 * de identificado , celular e classe. O status do usuario sera
	 * 'doador'.
	 * 
	 * @param nome nome do usuario doador
	 * @param email email do usuario doador
	 * @param docId documento de identificacao do usuario doador
	 * @param celular celular do usuario doador
	 * @param sistemaId número do cadastro do usuário no sitema.
	 */

	public Doador(String nome, String email, String docId, String celular,String classe) {
		super(nome, email, docId, celular,classe,"doador");
	}

}
