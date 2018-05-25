package br.com.jaison.estoquebebida.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jaison.estoquebebida.domain.Secao;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.repository.SecaoRepository;

/**
 * @author jpereira Organiza regras de negocio referente a entidade secao
 */
@Service
public class SecoesService {

	@Autowired
	SecaoRepository repository;

	@Autowired
	BebidaService serviceBebida;

	private Integer isSecaoExists(Integer numeroSecao) throws NotFoundSecaoException {
		if (numeroSecao == null) {
			throw new NotFoundSecaoException("Numero da seção é obrigatorio");
		} else if (numeroSecao == 0 || numeroSecao > 5) {
			throw new NotFoundSecaoException("Escolha uma seção de 1 a 5");
		}
		return numeroSecao;
	}

	/**
	 * Uma seção não pode ter dois ou mais tipos diferentes de bebidas (como já fora
	 * dito)
	 * 
	 * @param numeroSecao
	 * @param bebida
	 * @throws RegraDeNegocioValidationException
	 */
	private void validateOnlyTypeDrink(Secao secao) throws RegraDeNegocioValidationException {
		if (!repository.findByNumeroSecaoAndBebidaTipoIsNotIn(secao.getNumeroSecao(), secao.getBebida().getTipo())
				.isEmpty()) {
			throw new RegraDeNegocioValidationException("Não é permitido mais de um tipo de bebida na mesma secao ");
		}

	}

	/**
	 * Retorna bebidas armazenadas na respectiva secao
	 * 
	 * @throws NotFoundSecaoException
	 */
	public List<Secao> getBebidasArmazenadasPorSecao(Integer numeroSecao) throws NotFoundSecaoException {
		isSecaoExists(numeroSecao);
		return repository.findByNumeroSecao(numeroSecao);
	}

	public Secao saveSecao(Integer numeroSecao, Secao secao)
			throws NotFoundSecaoException, RegraDeNegocioValidationException {
		secao.setNumeroSecao(isSecaoExists(numeroSecao));
		serviceBebida.validateFields(secao.getBebida());
		validateCapacidadeArmazenamento(secao);
		validateOnlyTypeDrink(secao);
		return repository.save(secao);
	}

	private void validateCapacidadeArmazenamento(Secao secao) {

	}

	public List<Secao> getTeste(Integer numeroSecao) throws NotFoundSecaoException {
		isSecaoExists(numeroSecao);
		return repository.findBySecao(numeroSecao);
	}

}
