package beans;

public class User {
	private String email ="";
	private String password = "";
	
	private String message = "";
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User() {
	
	}
	

	public String getMessage() {
		return message;
	}

	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String user) {
		this.email = user;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validate() {		
		if(password.matches("")) {
			message = "Invalid password address";
		}
		
		if(!email.matches("\\w+\\.?\\w*@\\w+\\.\\w+")) {
			message = "Invalid email address";
			
			if(email.contains(" ")) {
				message = "email address cannot contain space";
				return false;
			}
			return false;
		}

		if(password.length() < 8) {
			message = "Password must be at least 8 characters";
			return false;
		} else if(password.matches("\\w*\\s+\\w*")) {
			message="Password cannot contain space.";
			return false;
		}
		return true;
	}
}
