package hablamos.controller;

import java.util.List;


import hablamos.model.User;
import hablamos.util.FileIOManager;

public class UserManager {
	
	private FileIOManager fileManager=new FileIOManager();
	private User currentUser;

	public List<User> getUserList() {
        return fileManager.getUser();
    }   

	    public void createUser(int id , String username, String password) 
	    {
	     fileManager.addUser( id,username, password);  
	    }
	    public void GetUserDetail()
		{
	    	fileManager.getUserDetails();
		}
	    public void AddUser(int id,String username)
	    {
	    	fileManager.AddExternalUser(id, username);
	    }
	    public void GetExternalUser()
	    {
	    	fileManager.getExternalUserDetail();
	    }

	    public User getCurrentUser() {
	        return currentUser;
	    }

	    public void setCurrentUser(User user) {
	        this.currentUser = user;
	    }

}
