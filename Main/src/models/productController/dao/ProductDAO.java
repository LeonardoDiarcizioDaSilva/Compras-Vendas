package models.productController.dao;

import connectionFactory.ConnectionFactory;
import genericDAO.GenericDAO;
import models.productController.ProductController;
import models.productController.ProductService;
import models.productController.dao.productSQLBuilder.ProductSQLBuilder;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO extends GenericDAO<ProductController> {
    private final ProductSQLBuilder<ProductController> sqlBuilder;
    public ProductDAO(ProductSQLBuilder<ProductController> sqlBuilder) {
        super(sqlBuilder);
        this.sqlBuilder = sqlBuilder;
    }

    public void upsert(ProductController product) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            stm = connection.prepareStatement(this.sqlBuilder.upsertBuilder(product.getClassType()));
            this.sqlBuilder.upsertSave(stm, product);
            stm.executeUpdate();
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null)
                connection.close();
            if (stm != null)
                stm.close();
        }
    }
}
