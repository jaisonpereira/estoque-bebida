package br.com.jaison.estoquebebida;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jaison.estoquebebida.domain.Bebida;
import br.com.jaison.estoquebebida.domain.Estoque;
import br.com.jaison.estoquebebida.enums.SecaoOrdemTypeEnum;
import br.com.jaison.estoquebebida.enums.TipoBebidaTypes;
import br.com.jaison.estoquebebida.exceptions.NotFoundObjectException;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.exceptions.NotFoundTypeBebidaException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.services.EstoqueService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EstoqueTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EstoqueService service;

	@Before
	public void setUp() {
		service.removeAllSecoes();
	}

	/**
	 * Valida mensagem de busca por tipo de bebida inexistente
	 * 
	 */
	@Test
	public void testBebidaInexistenteSistema() throws NotFoundSecaoException {
		ResponseEntity<Estoque> secoesResponse = restTemplate.getForEntity("/estoque/3", Estoque.class);
		assertThat(secoesResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	/**
	 * Valida secao nula na camada de service
	 * 
	 * @throws NotFoundSecaoException
	 * @throws RegraDeNegocioValidationException
	 * @throws NotFoundTypeBebidaException
	 */
	@Test(expected = NotFoundTypeBebidaException.class)
	public void testaSecaoInexistenteService() throws NotFoundTypeBebidaException {
		service.consultaVolumeTotalEstoquePortipo(6);
	}

	/**
	 * valida se a quantidade inserida é a quantidade retornada de volume,
	 * independentemente da secao armazenada,foi validado uma insercao de um tipo de
	 * bebida diferente nesse teste
	 * 
	 * @throws RegraDeNegocioValidationException
	 * @throws NotFoundSecaoException
	 * @throws NotFoundTypeBebidaException
	 */
	@Test
	public void testaConsultaEstoqueArmazenadoPortipo()
			throws RegraDeNegocioValidationException, NotFoundSecaoException, NotFoundTypeBebidaException {
		Estoque secao = new Estoque(SecaoOrdemTypeEnum.SEGUNDA.value(),
				new Bebida("Agua de Coco ", 100025.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao);
		Estoque secao1 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Coca cola ", 120000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao1);

		Estoque secao2 = new Estoque(SecaoOrdemTypeEnum.TERCEIRA.value(),
				new Bebida("FANTA ", 140000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao2);

		Estoque secao3 = new Estoque(SecaoOrdemTypeEnum.QUARTA.value(),
				new Bebida("FANTA ", 100000.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao3);

		assertThat(service.consultaVolumeTotalEstoquePortipo(TipoBebidaTypes.NAO_ALCOOLICA.value())).isEqualTo(360025.);

	}

	/**
	 * 
	 * valida metodo de consulta de secoes disponiveis para armazenar um determinado
	 * volume de um determinado tipo de bebida
	 * 
	 * @throws RegraDeNegocioValidationException
	 * @throws NotFoundObjectException
	 */
	@Test
	public void testSecoesDisponiveisParaArmazenamento()
			throws RegraDeNegocioValidationException, NotFoundObjectException {
		Estoque secao = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Agua de Coco ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao);
		Estoque secao1 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Coca cola ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao1);

		Estoque secao2 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("FANTA ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao2);

		Estoque secao3 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("GUARANA ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao3);

		assertEquals(service.consultaLocalDisponivelPorVolume(45.7, TipoBebidaTypes.NAO_ALCOOLICA.value()).size(),
				(SecaoOrdemTypeEnum.values().length - 1));

	}

	/**
	 * valida consulta de secoes disponiveis para venda a partir de um determinado
	 * tipo de bebida
	 * 
	 * 
	 * @throws RegraDeNegocioValidationException
	 * @throws NotFoundObjectException
	 */
	@Test
	public void consultaSecoesDisponiveisPorTipoBebida()
			throws RegraDeNegocioValidationException, NotFoundObjectException {
		Estoque secao = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Agua de Coco ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao);
		Estoque secao1 = new Estoque(SecaoOrdemTypeEnum.SEGUNDA.value(),
				new Bebida("Coca cola ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao1);
		Estoque secao2 = new Estoque(SecaoOrdemTypeEnum.TERCEIRA.value(),
				new Bebida("Vodka ", 100000.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao2);

		assertEquals(service.getAllSecoesByTipoBebida(TipoBebidaTypes.NAO_ALCOOLICA.value()).size(), (2));

	}

}
