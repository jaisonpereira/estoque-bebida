package br.com.jaison.estoquebebida.exceptions;

/**
 * Exception generica valida regra de negocio
 * 
 * @author jpereira
 *
 */
public class RegraDeNegocioValidationException extends Exception {

	public RegraDeNegocioValidationException(String message) {
		super(message);
	}

}
