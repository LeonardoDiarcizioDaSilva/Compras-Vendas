package models;

import java.sql.SQLException;

public interface IService<T extends Persistent>{

    void registration(Object[] primaryArgs, Object[] secondArgs) throws NoSuchMethodException, SQLException;

    T findById(String code);

    void delete(String code);
}
