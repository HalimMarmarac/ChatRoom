package chatroom.DTO;

public class User {

	// private data fields
	private String username;
	private String password;
	
	// constructors
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public User(String username){
		this.username = username;
	}
	
	public User(){
	}
	
	// getters and setters
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
	
}
