package models.userController;

import dao.GenericDAO;
import models.IService;
import models.annotations.GetFields;
import models.userController.userThrows.NoSuchInformations;
import models.userController.userThrows.NotUserFound;
import models.userController.userThrows.UserAlredyExists;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService implements IService<UserController> {

    private static GenericDAO<UserController> dao = new GenericDAO<>();

    @Override
    public void signUp(Object... args) throws SQLException {
        UserController us = userCreation(args);
        if (!userAlredyExists(us.getCode())) {
            try {
                Constructor<?> constructor = UserController.class.getDeclaredConstructors()[0];
                int constructorCount = constructor.getParameterCount();
                if (constructorCount != args.length)
                    throw new NoSuchInformations();
                dao.save(us);
            } catch (RuntimeException e) {
                throw e;
            }
            return;
        }
        throw new UserAlredyExists();
    }

    @Override
    public UserController findById(String code) {
        try {
            if (!userAlredyExists(code)) {
                ResultSet rs = dao.findByID(UserController.class, code);
                return userFinder(rs);
            }
            throw new NotUserFound();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String code) {
        try {
            if (!userAlredyExists(code)) {
                dao.deleteAll(UserController.class, code);
                return;
            }
            throw new NotUserFound();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean userAlredyExists(String code) throws SQLException{
        Object us = dao.findByID(UserController.class, code);

        if (us != null)
            return false;
        return true;
    }

    private UserController userCreation(Object... args) {
        UserController us = new UserController();

        Field[] getFields = UserController.class.getDeclaredFields();
        Method invokeMethods;
        try {
            int argsPos = 0;
            for (int i = 0; i < getFields.length; i++) {
                String fieldName;
                if (!getFields[i].isAnnotationPresent(GetFields.class)) {
                    continue;
                }
                fieldName = getFields[i].getName().toUpperCase().charAt(0) +
                        getFields[i].getName().substring(1);

                if (args[argsPos] instanceof Number) {
                    invokeMethods = us.getClassType().getMethod("set" + fieldName,
                            getFields[i].getType());
                    invokeMethods.invoke(us, args[argsPos]);
                    argsPos++;
                    continue;
                }
                invokeMethods = us.getClassType().getMethod("set" + fieldName, getFields[i].getType());
                invokeMethods.invoke(us, args[argsPos]);
                argsPos++;
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return us;
    }

    private UserController userFinder(ResultSet rs) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        UserController us = new UserController();

        Field[] getFields = us.getClassType().getDeclaredFields();
        Method invokeMethod;

        while (rs.next()) {
            for (Field f : getFields) {
                if (!f.isAnnotationPresent(GetFields.class)) {
                    continue;
                }
                Class<?> fieldType = f.getType();
                invokeMethod = us.getClassType().getMethod("set" + f.getName().toUpperCase().charAt(0) +
                        f.getName().substring(1), fieldType);
                invokeMethod.invoke(us, rs.getObject(f.getName()));
            }
        }
        return us;
    }
}
