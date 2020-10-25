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
    try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
      String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
          "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
          " NAME           TEXT    NOT NULL, " +
          " PRICE          INT     NOT NULL)";
      Statement stmt = c.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
    }
  }

  public String getTableName() {
    return tableName;
  }
}
