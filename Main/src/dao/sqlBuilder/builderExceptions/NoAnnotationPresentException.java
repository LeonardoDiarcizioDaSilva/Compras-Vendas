package dao.sqlBuilder.builderExceptions;

public class NoAnnotationPresentException extends RuntimeException {
    public NoAnnotationPresentException() {
        super("");
    }
    public NoAnnotationPresentException(String message) {
        super(message);
    }
}
