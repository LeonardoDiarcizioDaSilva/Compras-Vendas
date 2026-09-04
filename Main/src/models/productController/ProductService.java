package models.productController;

import genericDAO.GenericDAO;
import models.IService;
import models.annotations.GetEmbeddedFields;
import models.annotations.GetFields;
import models.productController.dao.ProductDAO;
import models.productController.dao.productSQLBuilder.ProductSQLBuilder;
import models.productController.productThrows.ProductNoExists;
import models.userController.UserController;
import models.userController.userThrows.NoSuchInformations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductService implements IService<ProductController> {
    private static ProductDAO productDAO;
    public ProductService() {
        productDAO = new ProductDAO(new ProductSQLBuilder<>());
    }

    @Override
    public void registration(Object[] productArgs, Object[] stockArgs) {
        try {
            ProductController pc = productCreation(productArgs, stockArgs);
            if (productExists(pc.getCode())) {
                int dbTotalQuantity = productFind(productDAO.findByID(ProductController.class, pc.getCode())).getStock().getQuantity();
                int newQuantity = pc.getStock().getQuantity();
                int totalSum = dbTotalQuantity + newQuantity;
                pc.getStock().setQuantity(totalSum);
                productDAO.upsert(pc);
                return;
            }
            productDAO.save(pc);
            productDAO.save(pc.getStock());
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductController findById(String code) throws ProductNoExists{
        try {
            if (!productExists(code)) {
                throw new ProductNoExists();
            }
            return productFind(productDAO.findByID(ProductController.class, code));
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(String code) {
        try {
            if (!productExists(code)) {
                throw new ProductNoExists();
            }
            productDAO.deleteAll(ProductStock.class, code);
            productDAO.deleteAll(ProductController.class, code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Boolean productExists(String code) throws SQLException {
        ProductController productExist = null;
        try {
            productExist = productFind(productDAO.findByID(ProductController.class, code));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (productExist == null)
            return false;
        return true;
    }

    private ProductController productCreation(Object[] productArgs, Object[] stockArgs) {
        int constructor = ProductController.class.getDeclaredConstructors()[0].getParameterCount();
        if (constructor != productArgs.length)
            throw new NoSuchInformations();

        ProductController pc = new ProductController();
        ProductStock ps = null;

        Field[] getFields = pc.getClassType().getDeclaredFields();
        Method invokeMethod;

        try {
            for (Field f : getFields) {
                if (f.isAnnotationPresent(GetEmbeddedFields.class)) {
                    ps = new ProductStock();
                    Field[] innerFields = ps.getClassType().getDeclaredFields();
                    int argsCount = 0;
                    for (Field sf : innerFields) {
                        invokeMethod = ps.getClassType().getDeclaredMethod("set" +
                                sf.getName().toUpperCase().charAt(0) +
                                sf.getName().substring(1), sf.getType());
                        invokeMethod.invoke(ps, stockArgs[argsCount]);
                        argsCount++;
                    }
                }
            }

            int argsCount = 0;
            for (Field f : getFields) {
                invokeMethod = pc.getClassType().getMethod("set" +
                        f.getName().toUpperCase().charAt(0) +
                        f.getName().substring(1), productArgs[argsCount].getClass());
                if (f.getType().isInstance(ps)) {
                    invokeMethod.invoke(pc, ps);
                    argsCount++;
                    continue;
                }
                invokeMethod.invoke(pc, productArgs[argsCount]);
                argsCount++;
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return pc;
    }

    private ProductController productFind(ResultSet rs) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ProductController pc = null;
        ProductStock ps;

        if (rs.next()) {
            pc = new ProductController();
            Field[] getFields = pc.getClassType().getDeclaredFields();
            Method invokeMethod;
            ps = new ProductStock();

            do {
                for (Field f : getFields) {
                    if (f.isAnnotationPresent(GetEmbeddedFields.class)) {
                        ResultSet psResult = productDAO.findByID(ProductStock.class, pc.getCode());
                        Field[] innerFields = ps.getClassType().getDeclaredFields();
                        while(psResult.next()) {
                            for (Field sf : innerFields) {
                                invokeMethod = ps.getClassType().getDeclaredMethod("set" +
                                        sf.getName().toUpperCase().charAt(0) +
                                        sf.getName().substring(1), sf.getType());
                                invokeMethod.invoke(ps, psResult.getObject(sf.getName()));
                            }
                        }
                    }
                    if (f.getType().isInstance(ps)) {
                        invokeMethod = pc.getClassType().getDeclaredMethod("set" +
                                f.getName().toUpperCase().charAt(0) +
                                f.getName().substring(1), f.getType());
                        invokeMethod.invoke(pc, ps);
                        continue;
                    }
                    invokeMethod = pc.getClassType().getDeclaredMethod("set" +
                            f.getName().toUpperCase().charAt(0) + f.getName().substring(1),
                            f.getType());
                    invokeMethod.invoke(pc, rs.getObject(f.getName()));
                }
            } while (rs.next());
        }

        return pc;
    }
}
