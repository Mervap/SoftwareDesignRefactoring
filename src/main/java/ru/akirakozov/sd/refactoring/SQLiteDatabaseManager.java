package ru.akirakozov.sd.refactoring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabaseManager {
  private final Connection databaseConnection;
  private final String tableName;

  public SQLiteDatabaseManager(String tableName) throws SQLException {
    this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:test.db");
    this.tableName = tableName;
  }

  public void createProductTableIfNotExists() throws SQLException {
    executeUpdateStatement(
        "CREATE TABLE IF NOT EXISTS " + tableName +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)");
  }

  public void dropProductTable() throws SQLException {
    executeUpdateStatement("DROP TABLE " + tableName);
  }

  public String getTableName() {
    return tableName;
  }

  private void executeUpdateStatement(String query) throws SQLException {
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      Statement stmt = c.createStatement();
      stmt.executeUpdate(query);
      stmt.close();
    }
  }
}
