package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

/**
 * @author akirakozov
 */
public class QueryServlet extends ProductServlet {
    public QueryServlet(SQLiteDatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            List<Product> maxProduct = databaseManager.selectProducts("ORDER BY PRICE DESC LIMIT 1");
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println("<html><body>");
            responseWriter.println("<h1>Product with max price: </h1>");
            for (Product product : maxProduct) {
                responseWriter.println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            responseWriter.println("</body></html>");
        } else if ("min".equals(command)) {
            List<Product> maxProduct = databaseManager.selectProducts("ORDER BY PRICE LIMIT 1");
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println("<html><body>");
            responseWriter.println("<h1>Product with min price: </h1>");
            for (Product product : maxProduct) {
                responseWriter.println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            responseWriter.println("</body></html>");
        } else if ("sum".equals(command)) {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println("<html><body>");
            responseWriter.println("Summary price: ");
            databaseManager.executeQueryStatement("SELECT SUM(price) FROM " + databaseManager.getTableName(), resultSet -> {
                try {
                    if (resultSet.next()) {
                        responseWriter.println(resultSet.getInt(1));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            responseWriter.println("</body></html>");
        } else if ("count".equals(command)) {
            PrintWriter responseWriter = response.getWriter();
            responseWriter.println("<html><body>");
            responseWriter.println("Number of products: ");
            databaseManager.executeQueryStatement("SELECT COUNT(*) FROM " + databaseManager.getTableName(), resultSet -> {
                try {
                    if (resultSet.next()) {
                        responseWriter.println(resultSet.getInt(1));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            responseWriter.println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
