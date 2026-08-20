package models.userController.userThrows;

public class NotUserFound extends RuntimeException {
    public NotUserFound() {
    }
    public NotUserFound(String message) {
        super(message);
    }
}
