package io.express.persist;

public class DBException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DBException(Exception e) {
        super(e);
    }
}
