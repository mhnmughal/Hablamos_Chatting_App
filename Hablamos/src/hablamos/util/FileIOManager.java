package hablamos.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hablamos.model.User;

public class FileIOManager {
	private List<User> user=new ArrayList<>();
	
	
	public List<User> getUser() {
        return user;
    }
	public void getUserDetails() 
    {
        readUserFromFile("user.csv");
    }
	public void getExternalUserDetail()
	{
		readExternalUserFromFile("ExternalUsers.csv");
	}
	public void addUser( int id, String username,String password) {
	     User u=new User();
	    u.setId(id);
	    u.setUsername(username);
	    u.setPassword(password);
	     user.add(u);
	     writeUserToFile("user.csv");
	    }
	 public void AddExternalUser(int id,String username)
	 {
		 User u =new User();
		 u.setId(id);
		 u.setUsername(username);
		user.add(u);
		writeExternalUserToFile("ExternalUsers.csv");
	 }
	private void writeUserToFile(String filePath)
	   {
		   FileWriter fileWriter=null;
	       try  {
	    	   fileWriter = new FileWriter(filePath, true); // append mode set to true

	           File file = new File(filePath);

	           if (file.length() == 0) {
	               // If the file is empty, write the header
	               fileWriter.append("Id, Username, Password\n");
	           }
	           for (User u : user) {
	        	   fileWriter.append(String.valueOf(u.getId())).append(",");
	               fileWriter.append(u.getUsername()).append(",");
	               fileWriter.append(u.getPassword());
	               
	               fileWriter.append("\n");
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	       finally
	       {
	       	closeWriter(fileWriter);
	       }
	   }
	 private void readUserFromFile(String filePath)
	   {
		   user.clear();
	       BufferedReader reader = null;
	       try {
	           reader = new BufferedReader(new FileReader(filePath));
	           // Skip the header line
	           reader.readLine();
	           
	           String line;
	           while ((line = reader.readLine()) != null) {
	               String[] fields = line.split(",");
	               if (fields.length > 0) {
	                   User u=new User();
	                  u.setId(Integer.parseInt(fields[0]));
	                  u.setUsername(fields[1]);
	                  u.setPassword(fields[2]);
	                   user.add(u);
	               }
	           }
	       } catch (IOException | NumberFormatException e) {
	           e.printStackTrace();
	       } finally {
	           try {
	               if (reader != null) {
	                   reader.close();
	               }
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
	       }
	   }
	
    //Write a list of strings to a text file
    public static void writeToFile(List<String> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle IOException, log, or throw a custom exception
            e.printStackTrace();
        }
    }

    // Read a text file and return its content as a list of strings
    public static List<String> readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = new java.util.ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            // Handle IOException, log, or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }
    public void saveChatToFile(String sender, String receiver, String message) {
        String filePath = getChatFilePath(sender, receiver);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(sender + ": " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getChatFilePath(String user1, String user2) {
        // Assuming the file path based on user names
        return user1 + "_" + user2 + "_chat.txt";
    }
    private void writeExternalUserToFile(String filePath)
	{
		FileWriter fileWriter=null;
	       try  {
	    	   fileWriter = new FileWriter(filePath, true); // append mode set to true

	           File file = new File(filePath);

	           if (file.length() == 0) {
	               // If the file is empty, write the header
	               fileWriter.append("Id, Username\n");
	           }
	           for (User u : user) {
	        	   fileWriter.append(String.valueOf(u.getId())).append(",");
	               fileWriter.append(u.getUsername()).append(",");
	               fileWriter.append("\n");
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	       finally
	       {
	       	closeWriter(fileWriter);
	       }
	}
	private void readExternalUserFromFile(String filePath)
	{
		user.clear();
	       BufferedReader reader = null;
	       try {
	           reader = new BufferedReader(new FileReader(filePath));
	           // Skip the header line
	           reader.readLine();
	           
	           String line;
	           while ((line = reader.readLine()) != null) {
	               String[] fields = line.split(",");
	               if (fields.length > 0) {
	                   User u=new User();
	                  u.setId(Integer.parseInt(fields[0]));
	                  u.setUsername(fields[1]);
	                 
	                   user.add(u);
	               }
	           }
	       } catch (IOException | NumberFormatException e) {
	           e.printStackTrace();
	       } finally {
	           try {
	               if (reader != null) {
	                   reader.close();
	               }
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
	       }
	}
	
 // Helper method to close FileWriter
    private void closeWriter(FileWriter writer) {
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to close BufferedReader
    private void closeReader(BufferedReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}