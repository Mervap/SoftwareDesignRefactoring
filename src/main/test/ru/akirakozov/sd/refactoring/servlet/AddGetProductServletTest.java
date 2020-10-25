package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.ProductTableBasedTest;
import ru.akirakozov.sd.refactoring.model.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddGetProductServletTest extends ProductTableBasedTest {

  @Test
  public void addGetEmpty() {
    doTest(Collections.emptyList());
  }

  @Test
  public void addGetSingleProduct() {
    List<Product> products = Collections.singletonList(IPHONE6);
    addProductsToDB(products);
    doTest(products);
  }

  @Test
  public void addGetMultiplyProducts() {
    List<Product> products = Arrays.asList(PIXEL2, PIXEL3);
    addProductsToDB(products);
    doTest(products);
  }

  private void doTest(List<Product> products) {
    StringBuilder expected = new StringBuilder();
    expected.append("<html><body>\n");
    for (Product product : products) {
      expected.append(product.getName()).append("\t").append(product.getPrice()).append("</br>\n");
    }
    expected.append("</body></html>\n");
    assertEquals(expected.toString(), readHTMLByPath("get-products"));
  }
}
