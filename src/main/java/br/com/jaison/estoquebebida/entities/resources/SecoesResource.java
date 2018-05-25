package br.com.jaison.estoquebebida.entities.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.jaison.estoquebebida.domain.Secao;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.services.SecoesService;

/**
 * @author jpereira End Point responsavel por gerir operacoes com secoes
 */
@RestController
public class SecoesResource {

	@Autowired
	SecoesService service;

	/**
	 * consulta das bebidas armazenadas em cada seção com suas
	 * 
	 * @param numeroSecao
	 * @return
	 * @throws NotFoundSecaoException
	 */
	@GetMapping(value = "/secoes/{numeroSecao}")
	public List<Secao> getBebidasArmazenadasPorSecao(@PathVariable Integer numeroSecao) throws NotFoundSecaoException {
		return service.getBebidasArmazenadasPorSecao(numeroSecao);
	}

	/**
	 * Cadastro e consulta das bebidas armazenadas em cada seção com suas
	 * respectivas queries.
	 * 
	 * 
	 * @param numeroSecao
	 * @param secao
	 * @return
	 * @throws NotFoundSecaoException
	 * @throws RegraDeNegocioValidationException
	 */
	@PostMapping(value = "/secoes/{numeroSecao}")
	public Secao setBebidasArmazenadasPorSecao(@PathVariable Integer numeroSecao, @RequestBody Secao secao)
			throws NotFoundSecaoException, RegraDeNegocioValidationException {
		return service.saveSecao(numeroSecao, secao);
	}

}
