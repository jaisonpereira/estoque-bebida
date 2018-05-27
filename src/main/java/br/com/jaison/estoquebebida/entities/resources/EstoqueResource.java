package br.com.jaison.estoquebebida.entities.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jaison.estoquebebida.domain.Estoque;
import br.com.jaison.estoquebebida.domain.Historico;
import br.com.jaison.estoquebebida.exceptions.NotFoundObjectException;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.exceptions.NotFoundTypeBebidaException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.services.EstoqueService;
import br.com.jaison.estoquebebida.services.HistoricoService;

/**
 * @author jpereira End Point responsavel por gerir operacoes com estoque
 * 
 * 
 */
@RestController
public class EstoqueResource {

	@Autowired
	EstoqueService service;

	@Autowired
	HistoricoService serviceHistorico;

	/**
	 * Consulta do volume total no estoque por cada tipo de bebida
	 * 
	 * @param numeroSecao
	 * @return
	 * @throws NotFoundTypeBebidaException
	 * @throws RegraDeNegocioValidationException
	 */
	@GetMapping(value = "/estoque/{tipoBebida}")
	public Double consultaSecoesByTipoBebida(@PathVariable Integer tipoBebida) throws NotFoundTypeBebidaException {
		return service.consultaVolumeTotalEstoquePortipo(tipoBebida);
	}

	/**
	 * 
	 * Consulta dos locais disponíveis de armazenamento de um determinado volume de
	 * bebida.
	 * 
	 * 
	 * @param volume
	 * @return
	 * @throws NotFoundObjectException
	 */
	@GetMapping(value = "/estoque/disponivel/{tipoBebida}/")
	public List<Estoque> consultaLocalDisponivelPorVolume(@RequestParam("volume") Double volume,
			@PathVariable Integer tipoBebida) throws NotFoundObjectException {
		return service.consultaLocalDisponivelPorVolume(volume, tipoBebida);

	}

	/**
	 * 
	 * Consulta das seções disponíveis para venda de determinado tipo de bebida
	 * 
	 * @param volume
	 * @return
	 * @throws NotFoundTypeBebidaException
	 * @throws NotFoundObjectException
	 */
	@GetMapping(value = "/estoque/disponivel/venda/{tipoBebida}")
	public List<Estoque> consultaLocalDisponivelPorVolume(@PathVariable Integer tipoBebida)
			throws NotFoundTypeBebidaException {
		return service.getAllSecoesByTipoBebida(tipoBebida);

	}

	/**
	 * consulta das bebidas armazenadas em cada seção
	 * 
	 * @param numeroSecao
	 * @return
	 * @throws NotFoundSecaoException
	 */
	@GetMapping(value = "/estoque/secoes/{numeroSecao}")
	public List<Estoque> consultaBebidasArmazenadasPorSecao(@PathVariable Integer numeroSecao)
			throws NotFoundSecaoException {
		return service.getBebidasArmazenadasPorSecao(numeroSecao);
	}

	/**
	 * Cadastro das bebidas armazenadas em cada seção
	 * 
	 * Não há entrada ou saída de estoque sem respectivo registro no histórico.
	 * 
	 * @param numeroSecao
	 * @param secao
	 * @return
	 * @throws RegraDeNegocioValidationException
	 * @throws NotFoundObjectException
	 */
	@PostMapping(value = "/estoque/secoes/{numeroSecao}")
	public Historico consultaBebidasArmazenadasPorSecao(@PathVariable Integer numeroSecao,
			@RequestBody Historico operacaoHistoricoEstoque)
			throws RegraDeNegocioValidationException, NotFoundObjectException {
		return serviceHistorico.executaOperacaoEstoque(operacaoHistoricoEstoque, numeroSecao);

	}

}
