package models.productController.dao.productSQLBuilder;

import genericDAO.sqlBuilder.SQLBuilder;
import models.Persistent;
import models.annotations.GetEmbeddedFields;
import models.annotations.GetFields;
import models.productController.ProductController;
import models.productController.ProductStock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductSQLBuilder<T extends Persistent> extends SQLBuilder<ProductController> {
    //INSERT DE PRODUTOS QUE JA EXISTEM, USADO APENAS PARA PRODUCT
    public String upsertBuilder(Class<?> clazz) {
        List<String> informationsList = sqlInformations.getEmbeddedFields(clazz);
        Field[] getFields = clazz.getDeclaredFields();
        String code = "";
        for (Field f : getFields) {
            if (f.isAnnotationPresent(GetFields.class) && f.getAnnotation(GetFields.class).name().equals("code")) {
                code = f.getAnnotation(GetFields.class).name();
                break;
            }
        }

        return "UPDATE " + sqlInformations.getEmbeddedTable(clazz).toUpperCase() +
                " SET (" + String.join(", ", informationsList) + ") = (" +
                getSize(informationsList) + ") WHERE code = " + code;
    }
    public void upsertSave(PreparedStatement stm, ProductController pc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        Field[] getFields = pc.getClassType().getDeclaredFields();
        Method invokeMethod;
        ProductStock ps = pc.getStock();

        for (int i = 0; i < getFields.length; i++) {
            if (getFields[i].getType().isInstance(ps)) {
                Field[] innerFields = ps.getClass().getDeclaredFields();
                int count = 1;
                Object obj;
                for (Field f : innerFields) {
                    invokeMethod = ps.getClass().getDeclaredMethod("get" +
                            f.getName().toUpperCase().charAt(0) +
                            f.getName().substring(1));
                    obj = invokeMethod.invoke(ps);
                    stm.setObject(count, obj);
                    count++;
                }
            }
        }
    }

    private CharSequence getSize(List<String> values) {
        List<String> totalSize = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            totalSize.add("?");
        }
        return String.join(", ", totalSize);
    }
}
