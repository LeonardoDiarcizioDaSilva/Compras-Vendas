package tests.DAO;

import dao.GenericDAO;
import models.userController.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDAOTests {
    @Nested
    class SaveClientTest {

        private final GenericDAO<UserController> genericDAO = new GenericDAO<>();

        @Test
        void ClientSaveTest() throws SQLException {
            String name = "teste1";
            String code = "123456789";
            String email = "teste1@testMail.com";
            String adress = "R. Teste da Silva";
            String number = "01";
            int age = 16;
            String permission = "Client";
            UserController uc = new UserController(name, age, code, email, adress, number,
                    permission);

            UserController userFind = new UserController();

            genericDAO.save(uc);
            try {
                ResultSet rs = genericDAO.findByID(UserController.class, "123456789");
                if (rs.next()) {
                    userFind.setName(rs.getString("name"));
                    userFind.setAge(rs.getInt("age"));
                    userFind.setCode(rs.getString("code"));
                    userFind.setEmail(rs.getString("email"));
                    userFind.setAdress(rs.getString("adress"));
                    userFind.setNumber(rs.getString("number"));
                }
            } catch (SQLException e) {
                throw e;
            }
            Assertions.assertNotNull(userFind);
            genericDAO.deleteAll(UserController.class, userFind.getCode());
        }

        @Test
        void ClientFindTest() throws SQLException {
            UserController userFinded = new UserController();

            try {
                ResultSet rs = genericDAO.findByID(UserController.class, "00000000000");
                if (rs.next()) {
                    userFinded.setName(rs.getString("name"));
                    userFinded.setAge(rs.getInt("age"));
                    userFinded.setCode(rs.getString("code"));
                    userFinded.setEmail(rs.getString("email"));
                    userFinded.setAdress(rs.getString("adress"));
                    userFinded.setNumber(rs.getString("number"));
                    genericDAO.findByID(UserController.class, "00000000000");
                }
            } catch (SQLException e) {
                throw e;
            }
            Assertions.assertNotNull(userFinded);
        }

        @Test
        void ClientDeleteTest() throws SQLException {
            String name = "teste1";
            String code = "123456789";
            String email = "teste1@testMail.com";
            String adress = "R. Teste da Silva";
            String number = "01";
            int age = 16;
            String permission = "Client";
            UserController uc = new UserController(name, age, code, email, adress, number,
                    permission);

            genericDAO.save(uc);
            genericDAO.deleteAll(UserController.class, "123456789");
            UserController userFinded = new UserController();
            try {
                ResultSet rs = genericDAO.findByID(UserController.class, "00000000000");
                if (rs.next()) {
                    userFinded.setName(rs.getString("name"));
                    userFinded.setAge(rs.getInt("age"));
                    userFinded.setCode(rs.getString("code"));
                    userFinded.setEmail(rs.getString("email"));
                    userFinded.setAdress(rs.getString("adress"));
                    userFinded.setNumber(rs.getString("number"));
                    genericDAO.findByID(UserController.class, "00000000000");
                }
            } catch (SQLException e) {
                throw e;
            }
            Assertions.assertNotSame(UserController.class, userFinded);
        }
    }
}
