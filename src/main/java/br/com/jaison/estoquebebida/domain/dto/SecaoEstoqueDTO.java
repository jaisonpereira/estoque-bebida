package br.com.jaison.estoquebebida.domain.dto;

/**
 * @author jpereira Dto Estoque Secoes
 *
 */
public class SecaoEstoqueDTO {

	private Integer numeroSecao;

	private Double total;

	private Integer tipoBebida;

	public Integer getNumeroSecao() {
		return this.numeroSecao;
	}

	public Integer getTipoBebida() {
		return this.tipoBebida;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setNumeroSecao(Integer numeroSecao) {
		this.numeroSecao = numeroSecao;
	}

	public void setTipoBebida(Integer tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
