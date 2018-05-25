package br.com.jaison.estoquebebida.types;

/**
 * @author jpereira Enum que categoriza a quantidade de secao disponivel
 */
public enum SecaoOrdemTypeEnum {
	PRIMEIRA(1), SEGUNDA(2), TERCEIRA(3), QUARTA(4), QUINTA(5);

	private Integer value;

	SecaoOrdemTypeEnum(int value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}

}
