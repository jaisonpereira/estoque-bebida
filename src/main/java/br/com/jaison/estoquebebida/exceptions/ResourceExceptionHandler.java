package br.com.jaison.estoquebebida.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author jpereira Classe responsavel por manipular excecoes
 *
 */

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(NotFoundObjectException.class)
	public ResponseEntity<StandardError> objectNotFound(NotFoundObjectException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Não encontrado", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(RegraDeNegocioValidationException.class)
	public ResponseEntity<StandardError> validation(RegraDeNegocioValidationException e, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Erro Validação ", e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

}
