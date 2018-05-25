package br.com.jaison.estoquebebida.repository;

import java.util.List;

import br.com.jaison.estoquebebida.domain.Secao;

public interface SecaoRepositoryCustom {

	Double getArmazenamentoUtilizadoBySecao(Integer numeroSecao);

	List<Secao> findBySecao(Integer numeroSecao);

}
