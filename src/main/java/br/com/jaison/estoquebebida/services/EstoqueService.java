package br.com.jaison.estoquebebida.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jaison.estoquebebida.domain.Bebida;
import br.com.jaison.estoquebebida.domain.Estoque;
import br.com.jaison.estoquebebida.enums.SecaoOrdemTypeEnum;
import br.com.jaison.estoquebebida.enums.TipoBebidaTypes;
import br.com.jaison.estoquebebida.exceptions.CapacidadeSecaoBebidaException;
import br.com.jaison.estoquebebida.exceptions.NotFoundObjectException;
import br.com.jaison.estoquebebida.exceptions.NotFoundSecaoException;
import br.com.jaison.estoquebebida.exceptions.NotFoundTypeBebidaException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.exceptions.TwoDrinkTypeException;
import br.com.jaison.estoquebebida.repository.EstoqueRepository;

/**
 * @author jpereira Organiza regras de negocio referente ao estoque
 */
@Service
public class EstoqueService {

	@Autowired
	EstoqueRepository repository;
	@Autowired
	BebidaService serviceBebida;

	/**
	 * Conversao de ml para Litros
	 */
	private static final double CAPACIDADE_SECAO_ALCOOLICA = 500000.0;

	private static final double CAPACIDADE_SECAO_NAO_ALCOOLICA = 400000.0;

	/**
	 * Uma seção não pode ter dois ou mais tipos diferentes de bebidas (como já
	 * fora dito)
	 *
	 * @param numeroSecao
	 * @param bebida
	 * @throws RegraDeNegocioValidationException
	 */
	private void validateOnlyTypeDrink(Estoque secao) throws RegraDeNegocioValidationException {
		if (!this.repository.findByNumeroSecaoAndBebidaTipoIsNotIn(secao.getNumeroSecao(), secao.getBebida().getTipo()).isEmpty()) {
			throw new TwoDrinkTypeException("Não é permitido mais de um tipo de bebida na mesma secao ");
		}
	}

	private void validateTypeDrink(Integer tipoBebida) throws NotFoundTypeBebidaException {
		if (!TipoBebidaTypes.isValid(tipoBebida)) {
			throw new NotFoundTypeBebidaException("Tipo da bebida obrigatorio , tipos aceitos 1=alcoolico,2=nao alcoolico! ");
		}
	}

	/**
	 * Consulta dos locais disponíveis de armazenamento de um determinado volume
	 * de bebida.
	 *
	 * Esse metodo valida apenas o volume da bebida , independentemente do tipo
	 * dela
	 *
	 *
	 * @param tipoBebida
	 * @return
	 * @throws NotFoundObjectException
	 */
	public List<Estoque> consultaLocalDisponivelPorVolume(Double volume, Integer tipoBebida) throws NotFoundObjectException {
		validateTypeDrink(tipoBebida);
		if (volume == null || volume <= 0) {
			throw new NotFoundObjectException("Volume  é obrigatorio, para se realizar a consulta ");
		}
		List<Estoque> secoesDisponiveis = new ArrayList<>();
		for (SecaoOrdemTypeEnum type : SecaoOrdemTypeEnum.values()) {
			Estoque secao = new Estoque(type.value(), new Bebida(volume, tipoBebida));
			try {
				validateCapacidadeArmazenamento(secao);
			} catch (CapacidadeSecaoBebidaException e) {
				continue;
			}
			secoesDisponiveis.add(new Estoque(type.value()));
		}
		return secoesDisponiveis;

	}

	/**
	 * Consulta do volume total no estoque por cada tipo de bebida
	 *
	 * @param tipoBebida
	 * @return
	 * @throws NotFoundTypeBebidaException
	 */
	public Double consultaVolumeTotalEstoquePortipo(Integer tipoBebida) throws NotFoundTypeBebidaException {
		validateTypeDrink(tipoBebida);
		return this.repository.getVolumeTotalPorTipoBebida(tipoBebida);

	}

	public void deleteById(String id) throws NotFoundObjectException {
		Optional<Estoque> estoque = this.repository.findById(id);
		if (!estoque.isPresent()) {
			throw new NotFoundObjectException("Item De estoque inexistente, id " + id);
		}
		this.repository.delete(estoque.get());
	}

	/**
	 * Consulta das seções disponíveis para venda de determinado tipo de bebida
	 *
	 * @param tipo
	 * @return
	 * @throws NotFoundTypeBebidaException
	 */
	public List<Estoque> getAllSecoesByTipoBebida(Integer tipo) throws NotFoundTypeBebidaException {
		this.serviceBebida.validBebidaTipo(tipo);
		return this.repository.findByBebidaTipoEquals(tipo);
	}

	/**
	 * Retorna volume utilizado por secao
	 *
	 * @param numeroSecao
	 * @return
	 */
	public Double getArmazenamentoUtilizadoBySecao(Integer numeroSecao) {
		Double saldo = this.repository.getArmazenamentoUtilizadoBySecao(numeroSecao);
		return saldo != null ? saldo : 0.;
	}

	/**
	 * Retorna bebidas armazenadas na respectiva secao
	 *
	 * @throws NotFoundSecaoException
	 */
	public List<Estoque> getBebidasArmazenadasPorSecao(Integer numeroSecao) throws NotFoundSecaoException {
		isSecaoExists(numeroSecao);
		return this.repository.findByNumeroSecao(numeroSecao);
	}

	/**
	 * Valida secao existente
	 *
	 * @param numeroSecao
	 * @return
	 * @throws NotFoundSecaoException
	 */
	public Integer isSecaoExists(Integer numeroSecao) throws NotFoundSecaoException {
		if (numeroSecao == null) {
			throw new NotFoundSecaoException("Numero da seção é obrigatorio");
		}
		if (!SecaoOrdemTypeEnum.isValid(numeroSecao)) {
			throw new NotFoundSecaoException("Escolha uma seção de 1 a 5");
		}
		return numeroSecao;
	}

	public void removeAllSecoes() {
		this.repository.deleteAll();
	}

	public Estoque saveEstoque(Estoque secao) throws NotFoundSecaoException, RegraDeNegocioValidationException {
		return saveEstoque(secao.getNumeroSecao(), secao);
	}

	public Estoque saveEstoque(Integer numeroSecao, Estoque itemEstoque) throws NotFoundSecaoException, RegraDeNegocioValidationException {
		validateEstoque(numeroSecao, itemEstoque);
		return this.repository.save(itemEstoque);
	}

	/**
	 * Metodo valida capacidade de cada tipo de secao
	 *
	 *
	 * Cada seção possui capacidade de armazenamento de 500 litros de bebidas
	 * alcoólicas e 400 de não alcoólicas.
	 *
	 *
	 * @param estoque
	 * @throws CapacidadeSecaoBebidaException
	 */
	public void validateCapacidadeArmazenamento(Estoque estoque) throws CapacidadeSecaoBebidaException {
		Double atualEstoque = getArmazenamentoUtilizadoBySecao(estoque.getNumeroSecao());
		Double estoquePrevisto = atualEstoque + estoque.getBebida().getVolume();
		if (estoque.getBebida().getTipo().equals(TipoBebidaTypes.NAO_ALCOOLICA.value()) && estoquePrevisto > CAPACIDADE_SECAO_NAO_ALCOOLICA) {
			throw new CapacidadeSecaoBebidaException("Secao do tipo não alcoolica sem capacidade de armazenamento, atual " + atualEstoque
					+ " Limite: " + CAPACIDADE_SECAO_NAO_ALCOOLICA);
		} else if (estoque.getBebida().getTipo().equals(TipoBebidaTypes.ALCOOLICA.value()) && estoquePrevisto > CAPACIDADE_SECAO_ALCOOLICA) {
			throw new CapacidadeSecaoBebidaException(
					"Secao do tipo  alcoolica sem capacidade de armazenamento ,atual " + atualEstoque + " Limite: " + CAPACIDADE_SECAO_ALCOOLICA);
		}
	}

	public void validateEstoque(Integer numeroSecao, Estoque itemEstoque) throws NotFoundSecaoException, RegraDeNegocioValidationException {
		if (itemEstoque == null) {
			throw new RegraDeNegocioValidationException("Item de estoque ,Bebida Obrigatório");
		}
		itemEstoque.setNumeroSecao(isSecaoExists(numeroSecao));
		this.serviceBebida.validateFields(itemEstoque.getBebida());
		validateOnlyTypeDrink(itemEstoque);
		validateCapacidadeArmazenamento(itemEstoque);
	}

}
