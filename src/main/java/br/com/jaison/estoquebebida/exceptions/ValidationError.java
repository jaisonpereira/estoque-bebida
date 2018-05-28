package br.com.jaison.estoquebebida.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	private static final long serialVersionUID = 1L;

	public void addError(String fieldName, String messagem) {
		this.errors.add(new FieldMessage(fieldName, messagem));
	}

	public List<FieldMessage> getErrors() {
		return this.errors;
	}
}
