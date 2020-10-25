package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.Servlet;

public class ServerManager {
  public static Server startServer(int port) throws Exception {
    Server server = new Server(port);
    server.start();
    return server;
  }

  public static ServletContextHandler createContext(Server server) {
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);
    return  context;
  }

  public static void addServlet(ServletContextHandler contextHandler, Servlet servlet, String pathSpec) {
    contextHandler.addServlet(new ServletHolder(servlet), pathSpec);
  }
}
