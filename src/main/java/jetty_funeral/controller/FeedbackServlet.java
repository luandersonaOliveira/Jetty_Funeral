package jetty_funeral.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jetty_funeral.exception.ValidationException;
import jetty_funeral.model.Feedback;
import jetty_funeral.repository.FeedbackRepository;
import jetty_funeral.repository.FunerariaRepository;
import jetty_funeral.repository.UserRepository;
import jetty_funeral.service.FeedbackService;

public class FeedbackServlet extends HttpServlet {

	private final FeedbackRepository feedbackRepository;
	private final FeedbackService feedbackService;
	private final FunerariaRepository funerariaRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public FeedbackServlet(FeedbackRepository feedbackRepo, UserRepository userRepo,
			FunerariaRepository funerariaRepo) {
		this.feedbackRepository = feedbackRepo;
		this.userRepository = userRepo;
		this.funerariaRepository = funerariaRepo;
		this.feedbackService = new FeedbackService(feedbackRepo, userRepo, funerariaRepo);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String userIdParam = req.getParameter("userId");
		String funerariaIdParam = req.getParameter("funerariaId");

		resp.setContentType("application/json");

		if (userIdParam != null) {
			int userId = Integer.parseInt(userIdParam);

			if (userRepository.getById(userId) == null) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().write("{\"error\": \"Usuário não encontrado\"}");
				return;
			}

			List<Feedback> feedbacks = feedbackRepository.getByUserId(userId);
			if (feedbacks.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().write("{\"error\": \"Nenhum feedback encontrado para este usuário.\"}");
				return;
			}

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write(objectMapper.writeValueAsString(feedbacks));
			return;
		}

		if (funerariaIdParam != null) {
			int funerariaId = Integer.parseInt(funerariaIdParam);

			if (funerariaRepository.getById(funerariaId) == null) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().write("{\"error\": \"Funerária não encontrada\"}");
				return;
			}

			List<Feedback> feedbacks = feedbackRepository.getByFunerariaId(funerariaId);
			if (feedbacks.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().write("{\"error\": \"Nenhum feedback encontrado para esta funerária.\"}");
				return;
			}

			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write(objectMapper.writeValueAsString(feedbacks));
			return;
		}

		// Nenhum parâmetro? Lista todos os feedbacks

		List<Feedback> todos = feedbackRepository.getAll();
		if (todos.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.getWriter().write("{\"error\": \"Nenhum feedback cadastrado ainda.\"}");
			return;
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().write(objectMapper.writeValueAsString(todos));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			Feedback feedback = objectMapper.readValue(req.getReader(), Feedback.class);
			feedbackService.validarFeedback(feedback);

			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_CREATED);
			resp.getWriter().write(objectMapper.writeValueAsString(feedback));
		} catch (ValidationException e) {
			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}
}
