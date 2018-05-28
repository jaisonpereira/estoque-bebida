package br.com.jaison.estoquebebida.domain;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author jpereira Historico de operacoes realizadas no estoque
 */
@Document
public class Historico extends EntidadeGenerica {

	private LocalDateTime dataOperacao;

	private Integer operacaoEstoque;

	private Estoque itemEstoque;

	private String responsavel;

	public Historico() {

	}

	public Historico(LocalDateTime dataOperacao, Integer operacaoEstoque, Estoque itemEstoque, String responsavel) {
		super();
		this.dataOperacao = dataOperacao;
		this.operacaoEstoque = operacaoEstoque;
		this.itemEstoque = itemEstoque;
		this.responsavel = responsavel;
	}

	public LocalDateTime getDataOperacao() {
		return this.dataOperacao;
	}

	public Estoque getItemEstoque() {
		return this.itemEstoque;
	}

	public Integer getOperacaoEstoque() {
		return this.operacaoEstoque;
	}

	public String getResponsavel() {
		return this.responsavel;
	}

	public void setDataOperacao(LocalDateTime dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public void setItemEstoque(Estoque itemEstoque) {
		this.itemEstoque = itemEstoque;
	}

	public void setOperacaoEstoque(Integer operacaoEstoque) {
		this.operacaoEstoque = operacaoEstoque;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

}
