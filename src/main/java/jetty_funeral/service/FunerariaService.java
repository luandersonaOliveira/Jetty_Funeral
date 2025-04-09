package jetty_funeral.service;

import jetty_funeral.exception.ValidationException;
import jetty_funeral.model.Funeraria;
import jetty_funeral.repository.FunerariaRepository;

public class FunerariaService {

	private final FunerariaRepository funerariaRepository;

	public FunerariaService(FunerariaRepository funerariaRepository) {
		this.funerariaRepository = funerariaRepository;
	}

	public void validarFuneraria(Funeraria funeraria) {
		if (funeraria.getCompanyName() == null || funeraria.getCompanyName().isBlank()) {
			throw new ValidationException("Nome da empresa é obrigatório.");
		}

		if (funeraria.getDescription() == null || funeraria.getDescription().isBlank()) {
			throw new ValidationException("Descrição é obrigatória.");
		}

		// Verificação de duplicidade de companyName
		boolean nomeJaExiste = funerariaRepository.getAll().stream()
				.anyMatch(f -> f.getCompanyName().equalsIgnoreCase(funeraria.getCompanyName()));

		if (nomeJaExiste) {
			throw new ValidationException("Já existe uma funerária com esse nome.");
		}
	}
}
