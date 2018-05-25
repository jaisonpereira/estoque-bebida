package br.com.jaison.estoquebebida.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

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

		MatchOperation matchNumeroSecao = getMatchNumeroSecao(numeroSecao);

		TypedAggregation<Secao> aggregation = newAggregation(Secao.class,
				group("numeroSecao").sum("bebida.volume").as("total"));

		// return mongoTemplate.find(query(where("numeroSecao").is(numeroSecao)),
		// Secao.class);
		return mongoTemplate.aggregate(aggregation, Secao.class).getMappedResults();
	}

	private MatchOperation getMatchNumeroSecao(Integer numeroSecao) {
		Criteria criteria = where("numeroSecao").is(numeroSecao);
		return match(criteria);
	}

	private GroupOperation getGroupOperation() {
		return group("numeroSecao").last("numeroSecao").as("numeroSecao").addToSet("id").as("productIds").avg("price")
				.as("averagePrice").sum("price").as("totalRevenue");
	}

}
