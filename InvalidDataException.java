public class InvalidDataException extends RuntimeException {
    // Constructor that accepts an error message to describe what error was
    public InvalidDataException(String message) {
        super(message);// Calls  constructor of RuntimeException
    }
}
