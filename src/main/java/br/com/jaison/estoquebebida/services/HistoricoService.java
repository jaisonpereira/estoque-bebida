package br.com.jaison.estoquebebida.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import br.com.jaison.estoquebebida.domain.Historico;
import br.com.jaison.estoquebebida.enums.OperacaoEstoqueType;
import br.com.jaison.estoquebebida.enums.TipoBebidaTypes;
import br.com.jaison.estoquebebida.exceptions.NotFoundObjectException;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;
import br.com.jaison.estoquebebida.repository.HistoricoRepository;

/**
 * @author jpereira Classe responsavel por gerenciar regras de Historico e
 *         Operacoes em estoque
 */
@Service
public class HistoricoService {

	@Autowired
	EstoqueService serviceEstoque;

	@Autowired
	HistoricoRepository repository;

	/**
	 * Uma seção não pode receber bebidas não alcoólicas se recebeu alcoólicas
	 * no mesmo dia. Ex: Seção 2 começou o dia com 50 litros de bebidas
	 * alcoólicas que foram consumidas do estoque, só poderá receber não
	 * alcoólicas no dia seguinte.
	 *
	 * @throws RegraDeNegocioValidationException
	 */
	private void validaPeriodoEstocagemTipoBebidaAlcoolica(Historico operacaoEstoqueHistorico) throws RegraDeNegocioValidationException {
		if (operacaoEstoqueHistorico.getItemEstoque().getBebida().getTipo().equals(TipoBebidaTypes.NAO_ALCOOLICA.value())) {

			// verifica horario apos as 23:59:59 evitando problemas com timezone
			LocalDateTime dataOper = LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59);
			/*
			 * Verifica se houve movimentacao na determinada secao com bebidas
			 * do tipo alcoolica
			 */
			List<Historico> historicoOperacoesComBebidasAlcoolicasNessaSecao = this.repository
					.findByItemEstoqueNumeroSecaoEqualsAndItemEstoqueBebidaTipoEqualsAndDataOperacaoAfter(
							operacaoEstoqueHistorico.getItemEstoque().getNumeroSecao(), TipoBebidaTypes.ALCOOLICA.value(), dataOper);

			if (!historicoOperacoesComBebidasAlcoolicasNessaSecao.isEmpty()) {
				throw new RegraDeNegocioValidationException(
						"Essa seção nao pode receber Bebidas do tipo Não Alcoolica, pois Houve Operacoes com Bebidas Alcoolicas na data de Hoje");

			}

		}

	}

	private void validateHistoricoFields(Historico historico) throws RegraDeNegocioValidationException {
		if (historico == null) {
			throw new RegraDeNegocioValidationException("Por favor insira dos dados para processar a operacao de estoque");
		}
		StringBuilder sb = new StringBuilder();
		if (!OperacaoEstoqueType.isValid(historico.getOperacaoEstoque())) {
			sb.append(" Operacao de estoque invalida ");
		}

		if (historico.getResponsavel() == null || historico.getResponsavel().trim().isEmpty()) {
			sb.append("Responsavel não informado ");
		}

		if (!sb.toString().isEmpty()) {
			throw new RegraDeNegocioValidationException(sb.toString());
		}

		if (historico.getDataOperacao() == null) {
			historico.setDataOperacao(LocalDateTime.now());
		}

	}

	private void validateOperacaoEstoque(Historico operacaoEstoqueHistorico) throws RegraDeNegocioValidationException, NotFoundObjectException {

		if (operacaoEstoqueHistorico.getOperacaoEstoque().equals(OperacaoEstoqueType.ENTRADA.value())) {
			validaPeriodoEstocagemTipoBebidaAlcoolica(operacaoEstoqueHistorico);
			this.serviceEstoque.saveEstoque(operacaoEstoqueHistorico.getItemEstoque());
		} else if (operacaoEstoqueHistorico.getOperacaoEstoque().equals(OperacaoEstoqueType.SAIDA.value())) {
			if (operacaoEstoqueHistorico.getItemEstoque().getId() == null || operacaoEstoqueHistorico.getItemEstoque().getId().isEmpty()) {
				throw new RegraDeNegocioValidationException("Informe o ID para realizar a operacao de saida do item de estoque");
			}

			this.serviceEstoque.deleteById(operacaoEstoqueHistorico.getItemEstoque().getId());

		} else {
			throw new RegraDeNegocioValidationException("Operacao desconhecida de estoque nao pode ser validada");
		}
	}

	/**
	 *
	 * Não há entrada ou saída de estoque sem respectivo registro no histórico.
	 *
	 * @return
	 * @throws RegraDeNegocioValidationException
	 * @throws NotFoundObjectException
	 *
	 *             Registro deve conter horário, tipo, volume, seção e
	 *             responsável pela entrada
	 *
	 *
	 */
	public Historico executaOperacaoEstoque(Historico operacaoEstoqueHistorico, Integer numeroSecao)
			throws RegraDeNegocioValidationException, NotFoundObjectException {
		this.serviceEstoque.isSecaoExists(numeroSecao);

		validateHistoricoFields(operacaoEstoqueHistorico);
		this.serviceEstoque.validateEstoque(numeroSecao, operacaoEstoqueHistorico.getItemEstoque());
		validateOperacaoEstoque(operacaoEstoqueHistorico);
		return this.repository.save(operacaoEstoqueHistorico);
	}

	/**
	 * Lista historico de operacoes de estoque
	 *
	 * @param dataAsc
	 *
	 * @return
	 */
	public List<Historico> listarHistoricosPorSecaoDataOperacao(boolean asc) {
		if (asc) {
			return this.repository.findAll(Sort.by(new Order(Direction.ASC, "itemEstoque.numeroSecao"), new Order(Direction.ASC, "dataOperacao")));
		} else {
			return this.repository.findAll(Sort.by(new Order(Direction.DESC, "itemEstoque.numeroSecao"), new Order(Direction.DESC, "dataOperacao")));
		}
	}

	public void removeAllHistorico() {
		this.repository.deleteAll();
	}

}
