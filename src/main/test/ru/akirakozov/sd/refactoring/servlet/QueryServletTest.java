package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.SQLiteBasedTest;
import ru.akirakozov.sd.refactoring.model.Product;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryServletTest extends SQLiteBasedTest {

  @Test
  public void getEmptyMax() {
    doMaxTest(null);
  }

  @Test
  public void getEmptyMin() {
    doMinTest(null);
  }

  @Test
  public void getEmptySum() {
    doSumTest(0);
  }

  @Test
  public void getEmptyCount() {
    doCountTest(0);
  }

  @Test
  public void getMax() {
    addProductsToDB(Arrays.asList(IPHONE6, PIXEL3, PIXEL2));
    doMaxTest(PIXEL3);
  }

  @Test
  public void getMin() {
    addProductsToDB(Arrays.asList(IPHONE6, PIXEL2, PIXEL3));
    doMinTest(PIXEL2);
  }

  @Test
  public void getSum() {
    addProductsToDB(Arrays.asList(IPHONE6, PIXEL2, PIXEL3));
    doSumTest(IPHONE6.getPrice() + PIXEL2.getPrice() + PIXEL3.getPrice());
  }

  @Test
  public void getCount() {
    addProductsToDB(Arrays.asList(IPHONE6, PIXEL2, PIXEL3));
    doCountTest(3);
  }

  private void doMaxTest(Product maxProduct) {
    String body = "<h1>Product with max price: </h1>\n";
    if (maxProduct != null) {
      body = body + maxProduct.getName() + "\t" + maxProduct.getPrice() + "</br>\n";
    }
    doTest(body, "max");
  }

  private void doMinTest(Product minProduct) {
    String body = "<h1>Product with min price: </h1>\n";
    if (minProduct != null) {
      body = body + minProduct.getName() + "\t" + minProduct.getPrice() + "</br>\n";
    }
    doTest(body, "min");
  }

  private void doSumTest(int summaryPrice) {
    doTest("Summary price: \n" + summaryPrice + "\n", "sum");
  }

  private void doCountTest(int productsCnt) {
    doTest("Number of products: \n" + productsCnt + "\n", "count");
  }


  private void doTest(String result, String query) {
    String expected = "<html><body>\n"
        + result
        + "</body></html>\n";
    assertEquals(expected, readHTMLByPath("query?command=" + query));
  }
}
