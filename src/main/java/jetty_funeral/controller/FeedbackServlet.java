package jetty_funeral.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jetty_funeral.model.Feedback;
import jetty_funeral.repository.FeedbackRepository;
import jetty_funeral.repository.FunerariaRepository;
import jetty_funeral.repository.UserRepository;
import jetty_funeral.service.FeedbackService;

public class FeedbackServlet extends HttpServlet {

	private final FeedbackRepository feedbackRepository = new FeedbackRepository();
	private final FeedbackService feedbackService;
	private FunerariaRepository funerariaRepository = new FunerariaRepository();
	private UserRepository userRepository = new UserRepository();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private int idCounter = 1;

	public FeedbackServlet(UserRepository userRepo, FunerariaRepository funerariaRepo) {
		this.userRepository = userRepo;
		this.funerariaRepository = funerariaRepo;
		this.feedbackService = new FeedbackService(userRepo, funerariaRepo);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");

		String userIdParam = req.getParameter("userId");
		String funerariaIdParam = req.getParameter("funerariaId");

		if (userIdParam != null) {
			int userId = Integer.parseInt(userIdParam);
			resp.getWriter().write(objectMapper.writeValueAsString(feedbackRepository.getByUserId(userId)));
		} else if (funerariaIdParam != null) {
			int funerariaId = Integer.parseInt(funerariaIdParam);
			resp.getWriter().write(objectMapper.writeValueAsString(feedbackRepository.getByFunerariaId(funerariaId)));
		} else {
			resp.getWriter().write(objectMapper.writeValueAsString(feedbackRepository.getAll()));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Feedback feedback = objectMapper.readValue(req.getReader(), Feedback.class);

		if (!userRepository.existsById(feedback.getIdUser())) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{ \"error\": \"Usuário não encontrado\" }");
			return;
		}

		if (!funerariaRepository.existsById(feedback.getIdFuneraria())) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{ \"error\": \"Funerária não encontrada\" }");
			return;
		}

		feedbackService.validarFeedback(feedback);
		feedback.setId(idCounter++);
		feedbackRepository.add(feedback);

		resp.setStatus(HttpServletResponse.SC_CREATED);
		resp.setContentType("application/json");
		resp.getWriter().write(objectMapper.writeValueAsString(feedback));
	}
}
