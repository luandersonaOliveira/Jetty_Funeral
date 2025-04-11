package jetty_funeral.service;

import jetty_funeral.exception.ValidationException;
import jetty_funeral.model.User;
import jetty_funeral.repository.UserRepository;

public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validarUsuario(User user) {
		if (user.getUsername() == null || user.getUsername().isBlank()) {
			throw new ValidationException("Username é obrigatório.");
		}

		if (user.getName() == null || user.getName().isBlank()) {
			throw new ValidationException("Nome é obrigatório.");
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new ValidationException("Senha é obrigatória.");
		}

		// Verificação de duplicidade de username
		boolean usernameJaExiste = userRepository.getAll().stream()
				.anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));

		if (usernameJaExiste) {
			throw new ValidationException("Já existe um usuário com esse username.");
		}

		userRepository.addUser(user);
	}
}
