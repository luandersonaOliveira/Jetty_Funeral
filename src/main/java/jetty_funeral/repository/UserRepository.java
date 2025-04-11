package jetty_funeral.repository;

import java.util.ArrayList;
import java.util.List;

import jetty_funeral.model.User;

public class UserRepository {
	private final List<User> users = new ArrayList<>();
	private int idCounter = 1;

	public void addUser(User user) {
		user.setId(idCounter++);
		users.add(user);
	}

	public List<User> getAll() {
		return users;
	}

	public User getById(int id) {
		return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
	}

	public boolean existsById(int id) {
		return getById(id) != null;
	}
}
