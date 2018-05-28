package br.com.jaison.estoquebebida.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author jpereira Entidade Generica utilizada para mapear documents MongoDB
 */
@Document
public class EntidadeGenerica {

	@Id
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
