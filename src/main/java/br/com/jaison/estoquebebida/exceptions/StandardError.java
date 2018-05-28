package br.com.jaison.estoquebebida.exceptions;

import java.io.Serializable;

/**
 * @author jpereira Tratamento de erros no sistema
 */
public class StandardError implements Serializable {
	private Long timestamp;

	private Integer status;

	private String error;

	private String message;

	private String path;

	public StandardError(Long timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	private static final long serialVersionUID = 1L;

	public String getError() {
		return this.error;
	}

	public String getMessage() {
		return this.message;
	}

	public String getPath() {
		return this.path;
	}

	public Integer getStatus() {
		return this.status;
	}

	public Long getTimestamp() {
		return this.timestamp;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
