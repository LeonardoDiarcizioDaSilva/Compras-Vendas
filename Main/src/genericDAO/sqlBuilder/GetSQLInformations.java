package genericDAO.sqlBuilder;

import genericDAO.sqlBuilder.builderExceptions.NoAnnotationPresentException;
import models.annotations.GetClass;
import models.annotations.GetEmbeddedFields;
import models.annotations.GetFields;

import java.lang.reflect.Field;
import java.util.*;

public class GetSQLInformations {

    public String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(GetClass.class)) {
            GetClass getClass = clazz.getAnnotation(GetClass.class);
            return getClass.tb_name();
        }
        return "Fail";
    }

    public List<String> getFields(Class<?> clazz) {
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

    public String getEmbeddedTable(Class<?> clazz) {
        if (clazz.isAnnotationPresent(GetClass.class))
            return clazz.getDeclaredAnnotation(GetClass.class).EmbeddedTable();
        throw new NoAnnotationPresentException();
    }
    public List<String> getEmbeddedFields(Class<?> clazz) {
        List<String> embeddedFields = new ArrayList<>();
        Field[] getFields = clazz.getDeclaredFields();

        for (Field f : getFields) {
            if (f.isAnnotationPresent(GetEmbeddedFields.class)) {
                Class<?> newType = f.getType();
                Field[] newTypeFields = newType.getDeclaredFields();
                for (Field nf : newTypeFields) {
                    embeddedFields.add(nf.getName());
                }
            }
        }
        return embeddedFields;
    }
}
