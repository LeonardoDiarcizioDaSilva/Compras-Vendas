package models.userController.userThrows;

public class NoSuchInformations extends RuntimeException {
    public NoSuchInformations() {

    }

    public NoSuchInformations(String message) {
        super(message);
    }
}
