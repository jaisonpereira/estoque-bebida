package br.com.jaison.estoquebebida.repository;

public interface EstoqueRepositoryCustom {

	Double getArmazenamentoUtilizadoBySecao(Integer numeroSecao);

	Double getVolumeTotalPorTipoBebida(Integer tipoBebida);

}
