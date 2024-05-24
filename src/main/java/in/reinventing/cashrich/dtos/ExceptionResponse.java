package in.reinventing.cashrich.dtos;

public class ExceptionResponse {
	private String message;
	private int status;
	private String details;
	public ExceptionResponse(String message, int status, String details) {
		super();
		this.message = message;
		this.status = status;
		this.details = details;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "ExceptionResponse [message=" + message + ", status=" + status + ", details=" + details + "]";
	}

}
