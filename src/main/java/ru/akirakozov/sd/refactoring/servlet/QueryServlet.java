package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HttpBodyResponseBuilder;
import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
    HttpBodyResponseBuilder responseBuilder = new HttpBodyResponseBuilder();
    if ("max".equals(command)) {
      List<Product> maxProduct = databaseManager.selectProducts("ORDER BY PRICE DESC LIMIT 1");
      responseBuilder.appendH1("Product with max price: ");
      responseBuilder.appendProductList(maxProduct);
    } else if ("min".equals(command)) {
      List<Product> minProduct = databaseManager.selectProducts("ORDER BY PRICE LIMIT 1");
      responseBuilder.appendH1("Product with min price: ");
      responseBuilder.appendProductList(minProduct);
    } else if ("sum".equals(command)) {
      responseBuilder.appendRow("Summary price: ");
      databaseManager.executeQueryStatement("SELECT SUM(price) FROM " + databaseManager.getTableName(), resultSet -> {
        try {
          if (resultSet.next()) {
            responseBuilder.appendRow(resultSet.getInt(1));
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      });
    } else if ("count".equals(command)) {
      responseBuilder.appendRow("Number of products: ");
      databaseManager.executeQueryStatement("SELECT COUNT(*) FROM " + databaseManager.getTableName(), resultSet -> {
        try {
          if (resultSet.next()) {
            responseBuilder.appendRow(resultSet.getInt(1));
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      });
    } else {
      response.getWriter().println("Unknown command: " + command);
    }

    response.getWriter().print(responseBuilder.toString());
    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
