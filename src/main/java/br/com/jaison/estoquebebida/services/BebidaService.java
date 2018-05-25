package br.com.jaison.estoquebebida.services;

import org.springframework.stereotype.Service;

import br.com.jaison.estoquebebida.domain.Bebida;
import br.com.jaison.estoquebebida.enums.TipoBebidaTypes;
import br.com.jaison.estoquebebida.exceptions.RegraDeNegocioValidationException;

/**
 * @author jpereira Regras de negocio que gerencia bebidas
 */
@Service
public class BebidaService {

	public void validateFields(Bebida bebida) throws RegraDeNegocioValidationException {
		if (bebida == null) {
			throw new RegraDeNegocioValidationException("Bebida nao informada ");
		}
		StringBuilder validate = new StringBuilder();
		if (bebida.getNome() == null || bebida.getNome().isEmpty()) {
			validate.append("Nome da bebida obrigatorio ! \n");
		}
		if (bebida.getVolume() == null || !(bebida.getVolume() > 0)) {
			validate.append("Volume da bebida em ml obrigatorio ! \n");
		}

		if (!TipoBebidaTypes.isValid(bebida.getTipo())) {
			validate.append("Tipo da bebida obrigatorio , tipos aceitos 1=alcoolico,2=nao alcoolico! \n");
		}

		if (!validate.toString().isEmpty()) {
			throw new RegraDeNegocioValidationException(validate.toString());
		}
	}

}
