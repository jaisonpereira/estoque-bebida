package br.com.jaison.estoquebebida.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author jpereira Agrega items e secoes do estoque no sistema
 *
 */
@Document
public class Estoque extends EntidadeGenerica {

	private Integer numeroSecao;

	private Bebida bebida;

	public Estoque() {
	}

	public Estoque(Integer numeroSecao) {
		super();
		this.numeroSecao = numeroSecao;
	}

	public Estoque(Integer numeroSecao, Bebida bebida) {
		super();
		this.numeroSecao = numeroSecao;
		this.bebida = bebida;
	}

	public Bebida getBebida() {
		return this.bebida;
	}

	public Integer getNumeroSecao() {
		return this.numeroSecao;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}

	public void setNumeroSecao(Integer numeroSecao) {
		this.numeroSecao = numeroSecao;
	}

}
