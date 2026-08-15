package connectionFactory.connectionExceptions;

public class FailedToConnectExcepetion extends RuntimeException {
    public FailedToConnectExcepetion() {
        super("teste");
    }

    public FailedToConnectExcepetion(String message) {
        super(message);
    }
}
