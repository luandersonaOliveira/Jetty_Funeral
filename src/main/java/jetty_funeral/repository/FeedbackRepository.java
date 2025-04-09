package jetty_funeral.repository;

import java.util.ArrayList;
import java.util.List;

import jetty_funeral.model.Feedback;

public class FeedbackRepository {
	private final List<Feedback> feedbacks = new ArrayList<>();

	public void add(Feedback feedback) {
		feedbacks.add(feedback);
	}

	public List<Feedback> getAll() {
		return feedbacks;
	}

	public List<Feedback> getByFunerariaId(int idFuneraria) {
		return feedbacks.stream().filter(f -> f.getIdFuneraria() == idFuneraria).toList();
	}

	public List<Feedback> getByUserId(int idUser) {
		return feedbacks.stream().filter(f -> f.getIdUser() == idUser).toList();
	}
}
