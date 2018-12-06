package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Classe que representa uma doacao.
 * @author Isaias Martins
 *
 */
public class Doacao implements Comparable<Doacao>{
	
	private LocalDate tempo;
	private String doacao;
	private String descritor;
	
	/**
	 * Constroi uma doacao.
	 * @param doacao Descricao da doacao.
	 * @param descritor Descritor do item doado.
	 */
	public Doacao(String doacao, String descritor) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
		String data = doacao.substring(0, 10);
		this.tempo = LocalDate.parse(data, fmt);
		this.doacao = doacao;
		this.descritor = descritor;
	}

	/**
	 * Metodo que implementa o toString.
	 */
	@Override
	public String toString() {
		return this.doacao;
	}

	/**
	 * Implementa o compareTo.
	 * Compara por data e desempata pela ordem alfabetica do descritor.
	 */
	@Override
	public int compareTo(Doacao o) {
		if(this.tempo.compareTo(o.tempo)==0) {
			return this.descritor.compareTo(o.descritor);
		}
		
		return this.tempo.compareTo(o.tempo);
	}

}
