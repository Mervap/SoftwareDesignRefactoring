package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

public class ServerManager {
  public static Server startServer(int port) throws Exception {
    Server server = new Server(port);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
    context.addServlet(new ServletHolder(new GetProductsServlet()),"/get-products");
    context.addServlet(new ServletHolder(new QueryServlet()),"/query");
    server.start();
    return server;
  }

}
