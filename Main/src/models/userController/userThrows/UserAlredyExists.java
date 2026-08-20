package models.userController.userThrows;

public class UserAlredyExists extends RuntimeException {
    public UserAlredyExists() {
    }

    public UserAlredyExists(String message) {
        super(message);
    }
}
