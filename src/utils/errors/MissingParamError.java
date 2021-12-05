package utils.errors;

public class MissingParamError extends Exception {
    public MissingParamError(String message) {
        super(message);
    }
}