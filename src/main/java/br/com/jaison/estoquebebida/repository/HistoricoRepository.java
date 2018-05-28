package br.com.jaison.estoquebebida.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.jaison.estoquebebida.domain.Historico;

public interface HistoricoRepository extends MongoRepository<Historico, String> {

	public List<Historico> findByItemEstoqueNumeroSecaoEqualsAndItemEstoqueBebidaTipoEqualsAndDataOperacaoAfter(Integer numeroSecao,
			Integer tipoBebida, LocalDateTime dataOperacaoAfter);

}
