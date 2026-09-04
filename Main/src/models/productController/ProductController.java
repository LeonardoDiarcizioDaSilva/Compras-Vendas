package models.productController;

import models.Persistent;
import models.annotations.GetClass;
import models.annotations.GetEmbeddedFields;
import models.annotations.GetFields;

@GetClass(tb_name = "TB_PRODUCT", EmbeddedTable = "PRODUCT_STOCK")
public class ProductController implements Persistent{
    @GetFields(name = "name")
    private String name;
    @GetFields(name = "price")
    private Double price;
    @GetFields(name = "code")
    private String code;
    @GetFields(name = "description")
    private String description;
    @GetEmbeddedFields(name = "stock")
    private ProductStock stock;

    public ProductController(String name, Double price, String code, String description, ProductStock stock) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.description = description;
        this.stock = stock;
    }
    public ProductController() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStock getStock() {
        return stock;
    }

    public void setStock(ProductStock stock) {
        this.stock = stock;
    }

    @Override
    public Class<?> getClassType() {
        return this.getClass();
    }
}
