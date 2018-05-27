package br.com.jaison.estoquebebida.enums;

/**
 * @author jpereira Enum categoriza a quantidade de seções disponiveis no
 *         sistema
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

	public static boolean isValid(Integer value) {
		if (value == null) {
			return false;
		}
		for (SecaoOrdemTypeEnum typeB : SecaoOrdemTypeEnum.values()) {
			if (value.equals(typeB.value)) {
				return true;
			}
		}
		return false;
	}

}
