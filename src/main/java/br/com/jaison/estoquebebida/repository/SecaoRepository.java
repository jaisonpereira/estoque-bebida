package br.com.jaison.estoquebebida.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.jaison.estoquebebida.domain.Secao;

public interface SecaoRepository extends MongoRepository<Secao, String>, SecaoRepositoryCustom {

	public List<Secao> findByNumeroSecao(Integer numeroSecao);

	public List<Secao> findByNumeroSecaoAndBebidaTipoIsNotIn(Integer numeroSecao, Integer tipo);

}
