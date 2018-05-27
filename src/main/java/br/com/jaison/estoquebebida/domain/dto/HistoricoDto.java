package br.com.jaison.estoquebebida.domain.dto;

import java.time.LocalDateTime;

import br.com.jaison.estoquebebida.domain.Estoque;

/**
 * @author jpereira Dto Historico de operacoes do estoque
 */
public class HistoricoDto {

	private LocalDateTime dataOperacao;

	private Integer numeroSecao;

	private Integer operacaoEstoque;

	private Estoque itemEstoque;

	private String responsavel;

	public LocalDateTime getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(LocalDateTime dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Integer getOperacaoEstoque() {
		return operacaoEstoque;
	}

	public void setOperacaoEstoque(Integer operacaoEstoque) {
		this.operacaoEstoque = operacaoEstoque;
	}

	public Estoque getItemEstoque() {
		return itemEstoque;
	}

	public void setItemEstoque(Estoque itemEstoque) {
		this.itemEstoque = itemEstoque;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Integer getNumeroSecao() {
		return numeroSecao;
	}

	public void setNumeroSecao(Integer numeroSecao) {
		this.numeroSecao = numeroSecao;
	}

}
