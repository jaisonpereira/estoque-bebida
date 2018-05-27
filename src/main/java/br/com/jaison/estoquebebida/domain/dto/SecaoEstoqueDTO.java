package br.com.jaison.estoquebebida.domain.dto;

/**
 * @author jpereira Dto Estoque Secoes
 * 
 */
public class SecaoEstoqueDTO {

	private Integer numeroSecao;

	private Double total;

	private Integer tipoBebida;

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getNumeroSecao() {
		return numeroSecao;
	}

	public void setNumeroSecao(Integer numeroSecao) {
		this.numeroSecao = numeroSecao;
	}

	public Integer getTipoBebida() {
		return tipoBebida;
	}

	public void setTipoBebida(Integer tipoBebida) {
		this.tipoBebida = tipoBebida;
	}

}
