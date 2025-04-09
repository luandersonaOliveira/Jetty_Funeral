package jetty_funeral;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import jetty_funeral.controller.FeedbackServlet;
import jetty_funeral.controller.FunerariaServlet;
import jetty_funeral.controller.UserServlet;
import jetty_funeral.repository.FunerariaRepository;
import jetty_funeral.repository.UserRepository;
import jetty_funeral.service.FunerariaService;
import jetty_funeral.service.UserService;

public class Main {
	public static void main(String[] args) throws Exception {
		// Criando o servidor na porta 8080
		Server server = new Server(8080);

		// Contexto da aplicação
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		// Criando repositórios compartilhados
		UserRepository userRepository = new UserRepository();
		UserService userService = new UserService(userRepository);
		FunerariaRepository funerariaRepository = new FunerariaRepository();
		FunerariaService funerariaService = new FunerariaService(funerariaRepository);

		// Registrando servlets e injetando dependências
		context.addServlet(new ServletHolder(new UserServlet(userRepository, userService)), "/user");
		context.addServlet(new ServletHolder(new FunerariaServlet(funerariaRepository, funerariaService)), "/funeraria");
		context.addServlet(new ServletHolder(new FeedbackServlet(userRepository, funerariaRepository)), "/feedback");

		// Iniciando o servidor
		server.start();
		server.join();
	}
}
