package br.com.jaison.estoquebebida.exceptions;

/**
 * @author jpereira Exception trata objetos inexistentes no sistema
 */
public class NotFoundObjectException extends Exception {

	public NotFoundObjectException(String message) {
		super(message);
	}

	private static final long serialVersionUID = -4880849281177329344L;

}
