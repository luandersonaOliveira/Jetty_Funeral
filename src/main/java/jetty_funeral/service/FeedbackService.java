package jetty_funeral.service;

import jetty_funeral.exception.ValidationException;
import jetty_funeral.model.Feedback;
import jetty_funeral.repository.FunerariaRepository;
import jetty_funeral.repository.UserRepository;

public class FeedbackService {

	private final UserRepository userRepository;
	private final FunerariaRepository funerariaRepository;

	public FeedbackService(UserRepository userRepository, FunerariaRepository funerariaRepository) {
		this.userRepository = userRepository;
		this.funerariaRepository = funerariaRepository;
	}

	public void validarFeedback(Feedback feedback) {
		if (feedback.getAvaliacao() == null || feedback.getAvaliacao().isBlank()) {
			throw new ValidationException("Avaliação é obrigatória.");
		}

		if (feedback.getComentario() == null || feedback.getComentario().isBlank()) {
			throw new ValidationException("Comentário é obrigatório.");
		}

		if (userRepository.getById(feedback.getIdUser()) == null) {
			throw new ValidationException("Usuário não existe.");
		}

		if (funerariaRepository.getById(feedback.getIdFuneraria()) == null) {
			throw new ValidationException("Funerária não existe.");
		}
	}
}
