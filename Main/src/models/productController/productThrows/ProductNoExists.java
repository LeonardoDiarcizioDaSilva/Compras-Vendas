package models.productController.productThrows;

public class ProductNoExists extends RuntimeException {
    public ProductNoExists() {}

    public ProductNoExists(String message) {
        super(message);
    }
}
