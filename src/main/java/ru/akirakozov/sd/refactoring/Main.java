package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        SQLiteDatabaseManager databaseManager = new SQLiteDatabaseManager("PRODUCT");
        databaseManager.createProductTableIfNotExists();
        Server server = ServerManager.startServer(8081);
        ServletContextHandler contextHandler = ServerManager.createContext(server);
        ServerManager.addServlet(contextHandler, new AddProductServlet(databaseManager), "/add-product");
        ServerManager.addServlet(contextHandler, new GetProductsServlet(databaseManager),"/get-products");
        ServerManager.addServlet(contextHandler, new QueryServlet(databaseManager),"/query");
        server.join();
    }
}
