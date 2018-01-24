package exception;

public class DBException extends AppException {
    private static final long serialVersionUID = 123492374352L;

    public DBException() {
        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

}
