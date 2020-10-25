package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.HttpBodyResponseBuilder;
import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;

public class SumQueryCommand extends QueryCommand {
  public SumQueryCommand(SQLiteDatabaseManager databaseManager) {
    super(databaseManager);
  }

  @Override
  protected String getQueryResponse() {
    HttpBodyResponseBuilder responseBuilder = new HttpBodyResponseBuilder();
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
    return responseBuilder.toString();
  }
}
