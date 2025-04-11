package jetty_funeral.repository;

import java.util.ArrayList;
import java.util.List;

import jetty_funeral.model.Funeraria;

public class FunerariaRepository {
	private final List<Funeraria> funerarias = new ArrayList<>();
	private int idCounter = 1;

	public void add(Funeraria funeraria) {
		funeraria.setId(idCounter++);
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
