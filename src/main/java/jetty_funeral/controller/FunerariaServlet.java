package jetty_funeral.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jetty_funeral.model.Funeraria;
import jetty_funeral.repository.FunerariaRepository;
import jetty_funeral.service.FunerariaService;

public class FunerariaServlet extends HttpServlet {

	private final FunerariaRepository funerariaRepository;
	private final FunerariaService funerariaService = new FunerariaService();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private int idCounter = 1;

	public FunerariaServlet(FunerariaRepository funerariaRepository) {
		this.funerariaRepository = funerariaRepository;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");

		String idParam = req.getParameter("id");
		if (idParam != null) {
			int id = Integer.parseInt(idParam);
			Funeraria funeraria = funerariaRepository.getById(id);
			if (funeraria != null) {
				resp.getWriter().write(objectMapper.writeValueAsString(funeraria));
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().write("{ \"error\": \"Funerária não encontrada\" }");
			}
		} else {
			resp.getWriter().write(objectMapper.writeValueAsString(funerariaRepository.getAll()));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Funeraria funeraria = objectMapper.readValue(req.getReader(), Funeraria.class);
		funerariaService.validarFuneraria(funeraria);
		funeraria.setId(idCounter++);
		funerariaRepository.add(funeraria);
		resp.setStatus(HttpServletResponse.SC_CREATED);
		resp.setContentType("application/json");
		resp.getWriter().write(objectMapper.writeValueAsString(funeraria));
	}
}
