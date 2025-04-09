package jetty_funeral.repository;

import jetty_funeral.model.Funeraria;

import java.util.ArrayList;
import java.util.List;

public class FunerariaRepository {
	private final List<Funeraria> funerarias = new ArrayList<>();

	public void add(Funeraria funeraria) {
		funerarias.add(funeraria);
	}

	public List<Funeraria> getAll() {
		return funerarias;
	}

	public Funeraria getById(int id) {
		return funerarias.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
	}

	public boolean existsById(int id) {
		return getById(id) != null;
	}
}
