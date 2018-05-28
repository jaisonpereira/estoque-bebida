package br.com.jaison.estoquebebida.enums;

/**
 * @author jpereira Enum identifica operacoes de estoque suportadas pelo sistema
 *
 */
public enum OperacaoEstoqueType {
	ENTRADA(1), SAIDA(2);

	private Integer value;

	OperacaoEstoqueType(Integer value) {
		this.value = value;
	}

	public static boolean isValid(Integer value) {
		if (value == null) {
			return false;
		}
		for (OperacaoEstoqueType typeB : OperacaoEstoqueType.values()) {
			if (value.equals(typeB.value)) {
				return true;
			}
		}
		return false;
	}

	public Integer value() {
		return this.value;
	}

}
