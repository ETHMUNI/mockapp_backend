package org.example.Exceptions;

public class CarNotFoundException extends Exception {
    private final int status;

    public CarNotFoundException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
