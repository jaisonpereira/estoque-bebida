package br.com.jaison.estoquebebida.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.jaison.estoquebebida.domain.Secao;

public class SecaoRepositoryImpl implements SecaoRepositoryCustom {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public SecaoRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;

	}

	@Override
	public Double getArmazenamentoUtilizadoBySecao(Integer numeroSecao) {

		return null;

		// match(Criteria.where("numeroSecao").is(numeroSecao)),
		// group("_id").sum("1").as("sum")
		// project("$sum").and("pageId").previousOperation()

		// mongoTemplate.aggregate(Aggregation.newAggregation(), null, null);

		// Criteria priceCriteria ='
		// where("price").gt(minPrice).andOperator(where("price").lt(maxPrice));

	}

	@Override
	public List<Secao> findBySecao(Integer numeroSecao) {

		return mongoTemplate.find(query(where("numeroSecao").is(numeroSecao)), Secao.class);
	}

}
