package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.HttpBodyResponseBuilder;
import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class QueryCommand {
  protected SQLiteDatabaseManager databaseManager;

  public QueryCommand(SQLiteDatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  protected abstract String getQueryResponse();

  public void writeResponse(HttpServletResponse response) throws IOException {
    response.getWriter().print(getQueryResponse());
  }
}
