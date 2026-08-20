package models;

import java.sql.SQLException;

public interface IService<T extends Persistent>{

    void signUp(Object... args) throws NoSuchMethodException, SQLException;

    T findById(String code);

    void delete(String code);
}
