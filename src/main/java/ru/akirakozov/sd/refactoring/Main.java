package ru.akirakozov.sd.refactoring;

import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
  private static final String PRODUCT_TABLE_NAME = "PRODUCT";

  public static void main(String[] args) throws Exception {
    SQLiteDatabaseManager databaseManager = new SQLiteDatabaseManager(PRODUCT_TABLE_NAME);
    databaseManager.createProductTableIfNotExists();

    ServerManager serverManager = new ServerManager(8081);
    serverManager.addServlet(new AddProductServlet(databaseManager), "/add-product");
    serverManager.addServlet(new GetProductsServlet(databaseManager), "/get-products");
    serverManager.addServlet(new QueryServlet(databaseManager), "/query");
    serverManager.getServer().join();
  }
}
