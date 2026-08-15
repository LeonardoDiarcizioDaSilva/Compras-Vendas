package dao.sqlBuilder;

import dao.sqlBuilder.builderExceptions.NoAnnotationPresentException;
import models.annotations.GetClass;
import models.annotations.GetFields;

import java.lang.reflect.Field;
import java.util.*;

public class GetSQLInformations {

    String getTableName(Class clazz) {
        if (clazz.isAnnotationPresent(GetClass.class)) {
            GetClass getClass = (GetClass) clazz.getAnnotation(GetClass.class);
            return getClass.value();
        }
        return "Fail";
    }

    List<String> getFields(Class clazz) {
        try {
            Field[] getFields = clazz.getDeclaredFields();
            List<String> containerValues = new ArrayList<>();

            for (Field f : getFields) {
                if (f.isAnnotationPresent(GetFields.class)) {
                    containerValues.add(f.getAnnotation(GetFields.class).name());
                }
            }
            return containerValues;
        } catch(NoAnnotationPresentException e) {
            throw e;
        }
    }
}
