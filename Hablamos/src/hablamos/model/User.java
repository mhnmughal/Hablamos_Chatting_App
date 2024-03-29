package hablamos.model;

import java.io.Serializable;

public class User implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private int id;
	private String username;
    private String password;
    private String status;

    // Constructor
    public User(int id, String username, String password) {
        this.username = username;
        this.setPassword(password);
        this.id=id;
         // Default status is offline
    }
    public User(int id,String username)
    {
    	this.id=id;
    	this.username=username;
    }
    public User()
    {
    	
    	
    }
    public User(int id)
    {
    	this.id=id;
    	
    }
    // Getter and setter methods

    public String getUsername() {
        return username;
    }
    public void setUsername(String username)
    {
    	this.username=username;
    }

    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    // Additional methods for user management if needed
}
