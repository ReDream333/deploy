package ru.kpfu.itis.kononenko.exception;

public class ConnectionException extends RuntimeException {
    public ConnectionException() {
        super("Не удалось подключиться к базе");
    }
    public ConnectionException(String message) {
        super(message);
    }
}
