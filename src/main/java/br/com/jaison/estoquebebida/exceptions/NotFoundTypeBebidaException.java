package br.com.jaison.estoquebebida.exceptions;

/**
 * @author jpereira Exception trata tipo de bebidas inexistentes no sistema
 */
public class NotFoundTypeBebidaException extends NotFoundObjectException {

	public NotFoundTypeBebidaException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7064573982271871263L;

}
