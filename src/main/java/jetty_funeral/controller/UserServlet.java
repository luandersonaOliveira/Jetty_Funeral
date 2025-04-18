package jetty_funeral.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jetty_funeral.exception.ValidationException;
import jetty_funeral.model.User;
import jetty_funeral.repository.UserRepository;
import jetty_funeral.service.UserService;

public class UserServlet extends HttpServlet {

	private UserRepository userRepository = new UserRepository();
	private UserService userService = new UserService(userRepository);
	private final ObjectMapper objectMapper = new ObjectMapper();

	public UserServlet(UserRepository userRepository, UserService userService) {
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		String idParam = req.getParameter("id");

		if (idParam != null) {
			try {
				int id = Integer.parseInt(idParam);
				User user = userRepository.getById(id);

				if (user != null) {
					resp.getWriter().write(objectMapper.writeValueAsString(user));
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
					resp.getWriter().write("{ \"error\": \"Usuário não encontrado\" }");
				}
			} catch (ValidationException e) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.getWriter().write("{ \"error\": \"ID inválido\" }");
			}
		} else {
			// Se não tiver parâmetro, retorna todos
			resp.getWriter().write(objectMapper.writeValueAsString(userRepository.getAll()));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			User user = objectMapper.readValue(req.getReader(), User.class);
			userService.validarUsuario(user);

			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_CREATED);
			resp.getWriter().write(objectMapper.writeValueAsString(user));
		} catch (ValidationException e) {
			resp.setContentType("application/json");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}
}
