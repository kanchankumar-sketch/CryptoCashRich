package in.reinventing.cashrich.exception;

public class ResourceNotFoundException extends Exception {
	public ResourceNotFoundException() {
		super("Resource not found.");
	}
}
