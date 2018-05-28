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
		return this.dataOperacao;
	}

	public Estoque getItemEstoque() {
		return this.itemEstoque;
	}

	public Integer getNumeroSecao() {
		return this.numeroSecao;
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

	public void setNumeroSecao(Integer numeroSecao) {
		this.numeroSecao = numeroSecao;
	}

	public void setOperacaoEstoque(Integer operacaoEstoque) {
		this.operacaoEstoque = operacaoEstoque;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

}
