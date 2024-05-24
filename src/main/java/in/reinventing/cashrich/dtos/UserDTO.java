package in.reinventing.cashrich.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
	@Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "Username must be alphanumeric and have length between 4 and 15 characters")
	private String username;

	@Size(min = 8, max = 15, message = "Password length must be between 8 and 15 characters")
	/*
	 * @Pattern.List({ @Pattern(regexp = ".*[a-z].*", message =
	 * "Password must contain at least one lowercase letter"),
	 * 
	 * @Pattern(regexp = ".*[A-Z].*", message =
	 * "Password must contain at least one uppercase letter"),
	 * 
	 * @Pattern(regexp = ".*\\d.*", message =
	 * "Password must contain at least one digit"),
	 * 
	 * @Pattern(regexp = ".*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*", message =
	 * "Password must contain at least one special character") })
	 */private String password;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@Email(message = "Invalid email address")
	private String email;

	@NotBlank(message = "Mobile number is required")
	private String mobile;

	// Getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
