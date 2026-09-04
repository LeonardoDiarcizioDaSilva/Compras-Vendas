package tests.Product;

import models.IService;
import models.productController.ProductController;
import models.productController.ProductService;
import models.productController.ProductStock;
import models.productController.productThrows.ProductNoExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.SQLException;

public class ProductControllerServiceTests {
    IService<ProductController> productService = new ProductService();

    @Test
    void productCreation() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String name = "produto teste";
        Double price = 100.00D;
        String code = "000-000-001";
        String description = "testando o cadastramento de um produto";
        int quantity = 10;

        productService.registration(new Object[]{name, price, code, description, ProductStock.class.newInstance()},
                new Object[]{code, quantity});
        Assertions.assertNotNull(productService.findById(code));
        productService.delete(code);
    }

    @Test
    void productFinderTest() {
        Assertions.assertNotNull(productService.findById("000-000-000"));
    }

    @Test
    void productDeleteTest() throws InstantiationException, IllegalAccessException, SQLException, NoSuchMethodException {
        String name = "produto teste";
        Double price = 100.00D;
        String code = "000-000-001";
        String description = "testando o cadastramento de um produto";
        int quantity = 10;

        productService.registration(new Object[]{name, price, code, description, ProductStock.class.newInstance()},
                new Object[]{code, quantity});
        productService.delete(code);
        Assertions.assertThrows(ProductNoExists.class, () -> {
            productService.findById(code);
        });
    }
}
