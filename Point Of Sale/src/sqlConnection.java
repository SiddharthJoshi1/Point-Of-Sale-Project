
import java.sql.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
public class sqlConnection {
	//This class is created in order to provide the connectivity to the SQLite database file
	//Creating the default connection object
	Connection conn = null;
	
	public static Connection dbConnector(){
		try{
			
			//Creating the java database connection driver in order to connect to the SQLite database
			Class.forName("org.sqlite.JDBC");
			
			//Using the driver to get the source file to which the connection will be established 
			//The database file was placed in the source folder in order to let transfer between different systems occur
			Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:Point of Sale Database.sqlite");
			//Message shown only when the connection is successful 
			//JOptionPane.showMessageDialog(null, "Connection to the server was successful");
			//returns the updated connection object
			return conn;
			
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		 
		
	}
	

}

