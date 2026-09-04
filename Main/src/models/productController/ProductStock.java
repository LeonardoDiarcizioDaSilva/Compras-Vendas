package models.productController;

import models.annotations.GetClass;
import models.annotations.GetEmbeddedFields;
import models.annotations.GetFields;

@GetClass(tb_name = "PRODUCT_STOCK", EmbeddedTable = "PRODUCT_STOCK")
public class ProductStock extends ProductController{
    @GetFields(name = "code")
    @GetEmbeddedFields(name = "code")
    private String code;
    @GetEmbeddedFields(name = "quantity")
    @GetFields(name = "quantity")
    private int quantity;

    public ProductStock(){}

    public String getCode() {return code;}
    public void setCode(String code) {
        this.code = code;}

    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
