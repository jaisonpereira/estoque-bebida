package br.com.jaison.estoquebebida.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.jaison.estoquebebida.domain.Estoque;

public interface EstoqueRepository extends MongoRepository<Estoque, String>, EstoqueRepositoryCustom {

	public List<Estoque> findByNumeroSecao(Integer numeroSecao);

	public List<Estoque> findByNumeroSecaoAndBebidaTipoIsNotIn(Integer numeroSecao, Integer tipo);

	public List<Estoque> findByBebidaTipoEquals(Integer tipo);

}
