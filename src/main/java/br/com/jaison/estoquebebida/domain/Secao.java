package br.com.jaison.estoquebebida.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Secao extends EntidadeGenerica {

	private Integer numeroSecao;
	
	private Bebida bebida;

	public Integer getNumeroSecao() {
		return numeroSecao;
	}

	public void setNumeroSecao(Integer numeroSecao) {
		this.numeroSecao = numeroSecao;
	}

	public Bebida getBebida() {
		return bebida;
	}

	public void setBebida(Bebida bebida) {
		this.bebida = bebida;
	}

}
