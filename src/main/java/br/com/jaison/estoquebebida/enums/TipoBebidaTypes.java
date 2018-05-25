package br.com.jaison.estoquebebida.enums;

/**
 * @author jpereira Enum identifica tipo da bebida
 * 
 */
public enum TipoBebidaTypes {
	ALCOOLICA(1), NAO_ALCOOLICA(2);

	private Integer value;

	TipoBebidaTypes(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}

	public static boolean isValid(Integer value) {
		if (value == null) {
			return false;
		}
		for (TipoBebidaTypes typeB : TipoBebidaTypes.values()) {
			if (value.equals(typeB.value)) {
				return true;
			}
		}
		return false;
	}

}
