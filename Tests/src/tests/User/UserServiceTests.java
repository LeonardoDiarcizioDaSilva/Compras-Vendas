package tests.User;

import models.IService;
import models.userController.UserController;
import models.userController.UserService;
import models.userController.userThrows.NotUserFound;
import models.userController.userThrows.UserAlredyExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserServiceTests {

    private static IService<UserController> userService = new UserService();

    @Test
    void singUpTest() throws NoSuchMethodException, SQLException {
        String name = "testando";
        int age = 18;
        String code = "12345678900";
        String email = "testeDaSilva@testando.com.br";
        String adress = "R. onde judas perdeus as botas";
        String number = "333";
        String permission = "CLIENT";

        userService.registration(new Object[]{name, age, code, email, adress, number, permission},
                new Object[]{});
        Assertions.assertNotNull(userService.findById(code));
        userService.delete(code);
    }
    @Test
    void findUserTest() {
        Assertions.assertNotNull(userService.findById("00000000000"));
    }
    @Test
    void userDelete() throws SQLException, NoSuchMethodException {
        String name = "testando";
        int age = 18;
        String code = "12345678900";
        String email = "testeDaSilva@testando.com.br";
        String adress = "R. onde judas perdeus as botas";
        String number = "333";
        String permission = "CLIENT";

        userService.registration(new Object[]{name, age, code, email, adress, number, permission},
                new Object[]{});
        userService.delete(code);
        Assertions.assertThrows(NotUserFound.class, () -> {
            userService.findById(code);
        });
    }
}
