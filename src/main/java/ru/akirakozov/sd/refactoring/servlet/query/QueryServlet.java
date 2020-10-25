package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;
import ru.akirakozov.sd.refactoring.servlet.ProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    QueryCommand queryCommand = getQueryCommandByName(command);
    if (queryCommand == null) {
      response.getWriter().println("Unknown command: " + command);
    }
    else {
      queryCommand.writeResponse(response);
    }
    response.setContentType("text/html");
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public QueryCommand getQueryCommandByName(String queryCommandName) {
    switch (queryCommandName) {
      case "max":
        return new MaxQueryCommand(databaseManager);
      case "min":
        return new MinQueryCommand(databaseManager);
      case "sum":
        return new SumQueryCommand(databaseManager);
      case "count":
        return new CountQueryCommand(databaseManager);
      default:
        return null;
    }
  }
}
