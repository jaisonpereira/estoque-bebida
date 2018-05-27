package br.com.jaison.estoquebebida.exceptions;

/**
 * @author jpereira Exception trata secoes existentes
 */
public class NotFoundSecaoException extends NotFoundObjectException {

	public NotFoundSecaoException(String error) {
		super(error);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7601187545430441581L;

}
