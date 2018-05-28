package br.com.jaison.estoquebebida.domain;

/**
 * @author jpereira
 *
 *         Representa uma bebida do tipo embededd no sistema
 *
 */
public class Bebida {

	private String nome;

	/**
	 * O volume das bebidas é calculo por ml
	 */
	private Double volume;

	private Integer tipo;

	public Bebida() {

	}

	public Bebida(Double volume, Integer tipo) {
		this.volume = volume;
		this.tipo = tipo;
	}

	public Bebida(String nome, Double volume, Integer tipo) {
		super();
		this.nome = nome;
		this.volume = volume;
		this.tipo = tipo;
	}

	public String getNome() {
		return this.nome;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

}
