package in.reinventing.cashrich.exception;

public class UnauthorizedException extends Exception{
	public UnauthorizedException() {
		super("Unautherized Request.");
	}
}
