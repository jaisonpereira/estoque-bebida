package br.com.jaison.estoquebebida.exceptions;

/**
 * Exception valida dois tipos de bebidas em uma unica sessao
 *
 * @author jpereira
 *
 */
public class TwoDrinkTypeException extends RegraDeNegocioValidationException {

	public TwoDrinkTypeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 4624660077461294319L;

}
