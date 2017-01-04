package databaseConnection;

import java.sql.*;
import java.util.Properties;

public class DBConnection {
    Connection connection;
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
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("useSSL", "true");

            if (connection == null)
                connection = DriverManager.getConnection("jdbc:mysql://" + databaseAddress, properties);
            
		} catch(Exception ex){
			ex.getMessage();
		}
		return connection;
	}

    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }
}
