package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.SQLiteDatabaseManager;

import javax.servlet.http.HttpServlet;

public abstract class ProductServlet extends HttpServlet {
  protected final SQLiteDatabaseManager databaseManager;

  public ProductServlet(SQLiteDatabaseManager databaseManager) {
    this.databaseManager = databaseManager;
  }
}
