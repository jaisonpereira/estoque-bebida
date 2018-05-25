package br.com.jaison.estoquebebida.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.jaison.estoquebebida.domain.Secao;

public interface SecaoRepository extends MongoRepository<Secao, String> {

	public List<Secao> findByNumeroSecao(Integer numeroSecao);

	public List<Secao> findByNumeroSecaoAndBebidaTipoIsNotIn(Integer numeroSecao, Integer tipo);

//	@Query("db.students.aggregate([ {   $project: {   bebida: { $max: \"$quizzes\"}, "
//			+ "       labMax: { $max: \"$labs\" }, " + "       examMax: { $max: [ \"$final\", \"$midterm\" ] } "
//			+ "     } " + "   } " + "])")
	public Secao findCustomByDomain(String domain);

}
