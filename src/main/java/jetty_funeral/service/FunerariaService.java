package jetty_funeral.service;

import jetty_funeral.model.Funeraria;

public class FunerariaService {

	public void validarFuneraria(Funeraria funeraria) {
		if (funeraria.getCompanyName() == null || funeraria.getCompanyName().isBlank()) {
			throw new IllegalArgumentException("Nome da empresa é obrigatório.");
		}

		if (funeraria.getDescription() == null || funeraria.getDescription().isBlank()) {
			throw new IllegalArgumentException("Descrição é obrigatória.");
		}

		if (funeraria.getDescription().length() < 10) {
			throw new IllegalArgumentException("Descrição deve ter no mínimo 10 caracteres.");
		}
	}
}
