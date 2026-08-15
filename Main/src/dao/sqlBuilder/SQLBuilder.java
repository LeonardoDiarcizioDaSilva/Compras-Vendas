package dao.sqlBuilder;

import models.Persistent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLBuilder<T extends Persistent> {

    private final GetSQLInformations sqlInformations = new GetSQLInformations();

    public String saveBuilder(Class clazz) {
        List<String> sqlFields = sqlInformations.getFields(clazz);

        return "INSERT INTO " + sqlInformations.getTableName(clazz).toUpperCase() +
                " (" + String.join(", ", sqlFields) + ") VALUES(" + getValues(sqlFields) + ")";
    }

    public void saveStatement(PreparedStatement stm, T entity){
        List<String> values = sqlInformations.getFields(entity.getClassType());
        Method getMethod;

        try {
            for (int i = 0; i < values.size(); i++) {
                String charPosition = values.get(i);
                Object obj;

                getMethod = entity.getClassType().getDeclaredMethod("get" +
                        charPosition.toUpperCase().charAt(0) +
                        charPosition.substring(1));
                obj = getMethod.invoke(entity);

                stm.setObject(i + 1, obj);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String findByCode(Class clazz, String code) {
        return "SELECT * FROM " + sqlInformations.getTableName(clazz) + " WHERE code = '" + code + "';";
    }

    public String deleteAll(Class clazz, String code) {
        return "DELETE FROM " + sqlInformations.getTableName(clazz) + " WHERE code = '" + code + "';";
    }

    private CharSequence getValues(List<String> l) {
        List<String> values = new ArrayList<>();

        for (int i = 0; i < l.size(); i++) {
            values.add("?");
        }
        return String.join(", ", values);
    }
}
