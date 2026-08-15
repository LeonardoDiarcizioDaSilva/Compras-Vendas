package models.userController.userThrows;

public class NotUserFinded extends RuntimeException {
    public NotUserFinded() {
    }
    public NotUserFinded(String message) {
        super(message);
    }
}
