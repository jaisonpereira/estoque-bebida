package br.com.jaison.estoquebebida.entities.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jaison.estoquebebida.domain.Historico;
import br.com.jaison.estoquebebida.services.HistoricoService;

/**
 * @author jpereira End point responsavel por exibir os historicos de operacoes
 *         em estoque
 */
@RestController
public class HistoricoResource {

	@Autowired
	HistoricoService service;

	@GetMapping(value = "/historico")
	public List<Historico> listarHistoricosPorSecaoDataOperacao(@RequestParam Boolean desc) {
		return this.service.listarHistoricosPorSecaoDataOperacao(desc);
	}

}
