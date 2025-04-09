package jetty_funeral.service;

import jetty_funeral.model.User;

public class UserService {

	public void validarUsuario(User user) {
		if (user.getUsername() == null || user.getUsername().isBlank()) {
			throw new IllegalArgumentException("Username é obrigatório.");
		}

		if (user.getName() == null || user.getName().isBlank()) {
			throw new IllegalArgumentException("Nome é obrigatório.");
		}

		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new IllegalArgumentException("Senha é obrigatória.");
		}

		if (user.getPassword().length() < 6) {
			throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
		}
	}
}
