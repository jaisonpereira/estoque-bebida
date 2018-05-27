package br.com.jaison.estoquebebida;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

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
import br.com.jaison.estoquebebida.domain.Historico;
import br.com.jaison.estoquebebida.enums.OperacaoEstoqueType;
import br.com.jaison.estoquebebida.enums.SecaoOrdemTypeEnum;
import br.com.jaison.estoquebebida.enums.TipoBebidaTypes;
import br.com.jaison.estoquebebida.exceptions.CapacidadeSecaoBebidaException;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.exceptions.TwoDrinkTypeException;
import br.com.jaison.estoquebebida.services.EstoqueService;
import br.com.jaison.estoquebebida.services.HistoricoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OperacaoEstoqueTests {

	private static final String RESPONSAVEL = "Jaison Pereira";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EstoqueService service;

	@Autowired
	private HistoricoService serviceHistorico;

	@Before
	public void setUp() {
		serviceHistorico.removeAllHistorico();
		service.removeAllSecoes();

	}

	/**
	 * Valida mensagem de busca por secao inexistente
	 * 
	 * @throws NotFoundSecaoException
	 */
	@Test
	public void testConsultaSecaoNotFound() throws NotFoundSecaoException {
		ResponseEntity<Estoque> secoesResponse = restTemplate.getForEntity("/estoque/secoes/6", Estoque.class);
		assertThat(secoesResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	/**
	 * Valida secao nula na camada de service
	 * 
	 * @throws NotFoundSecaoException
	 */
	@Test(expected = NotFoundSecaoException.class)
	public void consultaSecaoNulaService() throws NotFoundSecaoException {
		service.getBebidasArmazenadasPorSecao(null);
	}

	/**
	 * Null object
	 * 
	 * @throws NotFoundSecaoException
	 */
	@Test
	public void postOperacaoEstoqueVazia() {
		Historico operacaoEstoque = new Historico();
		ResponseEntity<Estoque> secoesResponse = restTemplate.postForEntity("/estoque/secoes/1", operacaoEstoque,
				Estoque.class);
		assertThat(secoesResponse.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test(expected = TwoDrinkTypeException.class)
	public void testaEntradaDeDoisTiposDeBebida() throws NotFoundSecaoException, RegraDeNegocioValidationException {
		Estoque secao = new Estoque(1, new Bebida("Vodka ", 45.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao);
		Estoque secao1 = new Estoque(1, new Bebida("Whisky ", 45.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao1);
		Estoque secao2 = new Estoque(1, new Bebida("Agua de Coco ", 45.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao2);
	}

	@Test(expected = CapacidadeSecaoBebidaException.class)
	public void testaLimiteCapacidadeArmazenamentoSecaoAlcolica()
			throws NotFoundSecaoException, RegraDeNegocioValidationException {
		Estoque secao = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Vodka ", 125000.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao);

		Estoque secao1 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Whisky ", 125000.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao1);

		Estoque secao2 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Cerveja ", 125000.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao2);

		Estoque secao3 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Catuaba ", 125001.0, TipoBebidaTypes.ALCOOLICA.value()));
		service.saveEstoque(secao3);
	}

	@Test(expected = CapacidadeSecaoBebidaException.class)
	public void testaLimiteCapacidadeArmazenamentoSecaoNaoAlcolica()
			throws NotFoundSecaoException, RegraDeNegocioValidationException {
		Estoque secao = new Estoque(SecaoOrdemTypeEnum.SEGUNDA.value(),
				new Bebida("Agua de Coco ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao);

		Estoque secao1 = new Estoque(SecaoOrdemTypeEnum.SEGUNDA.value(),
				new Bebida("Coca cola ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao1);

		Estoque secao2 = new Estoque(SecaoOrdemTypeEnum.SEGUNDA.value(),
				new Bebida("Dolly Seu amiguinho ", 100000.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao2);

		Estoque secao3 = new Estoque(SecaoOrdemTypeEnum.SEGUNDA.value(),
				new Bebida("Itubaina ", 100001.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));
		service.saveEstoque(secao3);
	}

	@Test
	public void testTodasOperacoesDeEntradaGravamHistorico() {
		LocalDateTime dataOperacao = LocalDateTime.now();

		Estoque item1 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Agua de coco", 785.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));

		Historico operacaoEstoque = new Historico(dataOperacao, OperacaoEstoqueType.ENTRADA.value(), item1,
				RESPONSAVEL);

		ResponseEntity<Historico> secoesResponse = restTemplate.postForEntity("/estoque/secoes/1", operacaoEstoque,
				Historico.class);
		assertEquals(HttpStatus.OK, secoesResponse.getStatusCode());
		ResponseEntity<Historico> secoesResponse2 = restTemplate.postForEntity("/estoque/secoes/1", operacaoEstoque,
				Historico.class);
		assertEquals(HttpStatus.OK, secoesResponse2.getStatusCode());
		assertEquals(2, serviceHistorico.listarHistoricosPorSecaoDataOperacao(true).size());
	}

	@Test
	public void testSaidaItemInexistenteEstoque() {
		LocalDateTime dataOperacao = LocalDateTime.now();

		Estoque item1 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Agua de coco", 785.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));

		Historico operacaoEstoque = new Historico(dataOperacao, OperacaoEstoqueType.SAIDA.value(), item1, RESPONSAVEL);

		ResponseEntity<Historico> secoesResponse = restTemplate.postForEntity("/estoque/secoes/1", operacaoEstoque,
				Historico.class);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, secoesResponse.getStatusCode());

	}

	@Test
	public void testaMovimentacoesDeBebidasAlcoolicasNoMesmoDia() {
		LocalDateTime dataOperacao = LocalDateTime.now();

		Estoque item1 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("PINGA BRABA", 785.0, TipoBebidaTypes.ALCOOLICA.value()));

		Historico operacaoEstoqueEntrada = new Historico(dataOperacao, OperacaoEstoqueType.ENTRADA.value(), item1,
				RESPONSAVEL);

		ResponseEntity<Historico> responseEntrada = restTemplate.postForEntity("/estoque/secoes/1",
				operacaoEstoqueEntrada, Historico.class);
		assertEquals(HttpStatus.OK, responseEntrada.getStatusCode());

		String idProdutoBaixa = responseEntrada.getBody().getItemEstoque().getId();
		item1.setId(idProdutoBaixa);

		Historico operacaoEstoqueSaida = new Historico(dataOperacao, OperacaoEstoqueType.SAIDA.value(), item1,
				RESPONSAVEL);
		ResponseEntity<Historico> responseSaida = restTemplate.postForEntity("/estoque/secoes/1", operacaoEstoqueSaida,
				Historico.class);
		assertEquals(HttpStatus.OK, responseSaida.getStatusCode());

		Estoque item2 = new Estoque(SecaoOrdemTypeEnum.PRIMEIRA.value(),
				new Bebida("Guarana", 785.0, TipoBebidaTypes.NAO_ALCOOLICA.value()));

		Historico operacaoEstoqueEntradaNaoAlcoolica = new Historico(dataOperacao, OperacaoEstoqueType.ENTRADA.value(),
				item2, RESPONSAVEL);

		ResponseEntity<Historico> responseEntradaNaoAlcoolica = restTemplate.postForEntity("/estoque/secoes/1",
				operacaoEstoqueEntradaNaoAlcoolica, Historico.class);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntradaNaoAlcoolica.getStatusCode());

	}

}
