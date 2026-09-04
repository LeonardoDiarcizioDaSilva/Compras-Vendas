package genericDAO;

import connectionFactory.ConnectionFactory;
import genericDAO.sqlBuilder.SQLBuilder;
import models.Persistent;
import models.userController.userThrows.NotUserFound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDAO<T extends Persistent>{
    protected final SQLBuilder<T> sqlBuilder;

    public GenericDAO(SQLBuilder<T> sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    public GenericDAO() {
        this.sqlBuilder = new SQLBuilder<>();
    }

    Connection connection;
    PreparedStatement sql;

    public void save(T entity) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();
            sql = connection.prepareStatement(sqlBuilder.saveBuilder(entity.getClassType()));
            sqlBuilder.saveStatement(sql, entity);
            sql.executeUpdate();
        } catch(SQLException e) {
            throw e;
        } finally {
            if (!connection.isClosed())
                connection.close();
            if (!sql.isClosed())
                sql.close();
        }
    }

    public ResultSet findByID(Class<?> clazz, String code) throws SQLException {
        try {
            connection = ConnectionFactory.getConnection();
            sql = connection.prepareStatement(sqlBuilder.findByCode(clazz, code));
            return sql.executeQuery();
        } catch (SQLException e) {
            throw new NotUserFound();
        }
    }

    public T findAll(String argument) {
        return null;
    }

    public void alterObject(T oldEntity, T newEntity) {

    }

    public void delete(Class<?> clazz, T entity) {
    }

    public void deleteAll(Class<?> clazz, String code) {
        try {
            connection = ConnectionFactory.getConnection();
            sql = connection.prepareStatement(sqlBuilder.deleteAll(clazz, code));
            sql.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
