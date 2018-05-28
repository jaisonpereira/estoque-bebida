package br.com.jaison.estoquebebida.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {

	private String fieldName;

	private String message;

	public FieldMessage() {
	}

	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	private static final long serialVersionUID = 1L;

	public String getFieldName() {
		return this.fieldName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
