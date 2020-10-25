package ru.akirakozov.sd.refactoring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.query.QueryServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public abstract class ProductTableBasedTest {
  protected static final int PORT = 8081;
  protected static final String LOCALHOST = "http://localhost:" + PORT;
  protected static final String PRODUCT_TABLE_NAME = "TestProducts";
  protected static final Product IPHONE6 = new Product("IPhone6", 300);
  protected static final Product PIXEL2 = new Product("Pixel2", 200);
  protected static final Product PIXEL3 = new Product("Pixel3", 350);

  protected final ServerManager serverManager;
  protected final SQLiteProductTableManager productTableManager;

  protected ProductTableBasedTest() {
    try {
      serverManager = new ServerManager(PORT);
      productTableManager = new SQLiteProductTableManager(PRODUCT_TABLE_NAME);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  protected void setUp() {
    serverManager.addServlet(new AddProductServlet(productTableManager), "/add-product");
    serverManager.addServlet(new GetProductsServlet(productTableManager), "/get-products");
    serverManager.addServlet(new QueryServlet(productTableManager), "/query");
    productTableManager.createProductTableIfNotExists();
  }

  @AfterEach
  protected void tearDown() throws Exception {
    productTableManager.dropProductTable();
    serverManager.getServer().stop();
  }

  protected String readHTMLByPath(String path) {
    URL url = toUrl(LOCALHOST + "/" + path);
    try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
      StringBuilder res = new StringBuilder();
      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        res.append(inputLine).append("\n");
      }
      return res.toString();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  protected void addProductsToDB(List<Product> products) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      for (Product product : products) {
        String uri = LOCALHOST + "/add-product?name=" + product.getName() + "&price=" + product.getPrice();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private URL toUrl(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Malformed url: $url");
    }
  }
}