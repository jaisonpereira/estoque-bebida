package br.com.jaison.estoquebebida.exceptions;

/**
 * Exception generica valida regra de negocio
 * 
 * @author jpereira
 *
 */
public class RegraDeNegocioValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6240689220953261648L;

	public RegraDeNegocioValidationException(String message) {
		super(message);
	}

}
