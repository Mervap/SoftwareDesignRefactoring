package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.HttpBodyResponseBuilder;
import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;
import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public class CountQueryCommand extends QueryCommand {
  public CountQueryCommand(SQLiteDatabaseManager databaseManager) {
    super(databaseManager);
  }

  @Override
  protected String getQueryResponse() {
    HttpBodyResponseBuilder responseBuilder = new HttpBodyResponseBuilder();
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
    return responseBuilder.toString();
  }
}
