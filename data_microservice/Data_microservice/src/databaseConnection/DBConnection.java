package databaseConnection;

import java.sql.*;

public class DBConnection {
	private String databaseAddress;
	private String password;
	private String user;
	
	public DBConnection(String databaseAddress, String password, String user){
		this.databaseAddress = databaseAddress;
		this.password = password;
		this.user = user;
	}
	
	public Connection getConnection(){
		try{
			Connection connection = DriverManager.getConnection("jdbc:mysql://"+ databaseAddress, user, password);
			return connection;
		} catch(Exception ex){
			ex.getMessage();
		}
		return null;
	}
}
