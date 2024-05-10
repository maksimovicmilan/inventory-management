package controller;

public class MaterialNotfoundException extends RuntimeException {
    public MaterialNotfoundException(String message) {
        super(message);
    }
}