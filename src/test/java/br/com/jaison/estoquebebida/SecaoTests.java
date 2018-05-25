package br.com.jaison.estoquebebida;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jaison.estoquebebida.domain.Secao;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.services.SecoesService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SecaoTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private SecoesService service;

	/**
	 * Valida mensagem de busca por secao inexistente
	 * 
	 * @throws NotFoundSecaoException
	 */
	@Test
	public void getSecaonNotFound() throws NotFoundSecaoException {
		ResponseEntity<Secao> secoesResponse = restTemplate.getForEntity("/secoes/6", Secao.class);
		assertThat(secoesResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	/**
	 * Valida secao nula na camada de service
	 * 
	 * @throws NotFoundSecaoException
	 */
	@Test(expected = NotFoundSecaoException.class)
	public void getSecaoNulaNotFound() throws NotFoundSecaoException {
		service.getBebidasArmazenadasPorSecao(null);
	}

	/**
	 * Null object
	 * 
	 * @throws NotFoundSecaoException
	 */
	@Test
	public void postSecaonNull() throws NotFoundSecaoException {
		Secao secao = new Secao();
		ResponseEntity<Secao> secoesResponse = restTemplate.postForEntity("/secoes/1", secao, Secao.class);
		assertThat(secoesResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
