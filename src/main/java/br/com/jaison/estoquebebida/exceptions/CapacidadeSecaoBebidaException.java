package br.com.jaison.estoquebebida.exceptions;

/**
 * Exception capacidade de tipos de bebidas
 *
 * @author jpereira
 *
 */
public class CapacidadeSecaoBebidaException extends RegraDeNegocioValidationException {

	public CapacidadeSecaoBebidaException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -7181753320021969512L;

}
