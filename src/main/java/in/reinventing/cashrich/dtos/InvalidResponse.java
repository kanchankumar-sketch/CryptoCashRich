package in.reinventing.cashrich.dtos;

public class InvalidResponse {
	private String message;

    public InvalidResponse(String message) {
        this.message = message;
    }
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
