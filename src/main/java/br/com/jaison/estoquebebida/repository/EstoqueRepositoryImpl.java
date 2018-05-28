package br.com.jaison.estoquebebida.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import br.com.jaison.estoquebebida.domain.Estoque;
import br.com.jaison.estoquebebida.domain.dto.SecaoEstoqueDTO;

/**
 * @author jpereira Implementacao de metodos de consultas adicionais para
 *         EstoqueRepository
 */
public class EstoqueRepositoryImpl implements EstoqueRepositoryCustom {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public EstoqueRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	private static final String NUMERO_SECAO = "numeroSecao";

	private MatchOperation getMatchNumeroSecao(Integer numeroSecao) {
		Criteria criteria = where(NUMERO_SECAO).is(numeroSecao);
		return match(criteria);
	}

	private MatchOperation getMatchTipoBebida(Integer tipoBebida) {
		Criteria criteria = where("tipoBebida").is(tipoBebida);
		return match(criteria);
	}

	private TypedAggregation<Estoque> getSumBebidaVolumeGroupedByBebidaTipo(MatchOperation match) {
		return newAggregation(Estoque.class, group("bebida.tipo").first("bebida.tipo").as("tipoBebida").sum("bebida.volume").as("total"), match);
	}

	private TypedAggregation<Estoque> getSumBebidaVolumeGroupedByNumeroSecao(MatchOperation match) {
		return newAggregation(Estoque.class, group(NUMERO_SECAO).first(NUMERO_SECAO).as(NUMERO_SECAO).sum("bebida.volume").as("total"), match);
	}

	@Override
	public Double getArmazenamentoUtilizadoBySecao(Integer numeroSecao) {
		List<SecaoEstoqueDTO> result = this.mongoTemplate
				.aggregate(getSumBebidaVolumeGroupedByNumeroSecao(getMatchNumeroSecao(numeroSecao)), Estoque.class, SecaoEstoqueDTO.class)
				.getMappedResults();
		return result != null && !result.isEmpty() ? result.get(0).getTotal() : 0;
	}

	/*
	 * (non-Javadoc) Consulta do volume total no estoque por cada tipo de bebida
	 */
	@Override
	public Double getVolumeTotalPorTipoBebida(Integer tipoBebida) {
		List<SecaoEstoqueDTO> result = this.mongoTemplate
				.aggregate(getSumBebidaVolumeGroupedByBebidaTipo(getMatchTipoBebida(tipoBebida)), Estoque.class, SecaoEstoqueDTO.class)
				.getMappedResults();
		return result != null && !result.isEmpty() ? result.get(0).getTotal() : 0;

	}

}
