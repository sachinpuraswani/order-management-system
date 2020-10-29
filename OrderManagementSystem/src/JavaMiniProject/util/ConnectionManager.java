package JavaMiniProject.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

	private static Connection con = null;
	
	//reading db properties from config.properties files
	private static String driver = null;
	private static String url = null;
	private static String userName = null;
	private static String passWord = null;
	
	static {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("config.properties"));
		} catch(IOException e) {
			e.printStackTrace();
	}
		 
	// we are passing default values that will be used in case property is missing
	driver = props.getProperty("mysql.driver","com.mysql.cj.jdbc.Driver");
	url = props.getProperty("mysql.url", "jdbc:mysql://localhost/order_management_db");
	userName = props.getProperty("mysql.username","root");
	passWord = props.getProperty("mysql.password","root123");
	}
	 
	//making constructor private to restrict object creation and implement Singleton 
	private ConnectionManager() {
	 
	}
	
	public static Connection getConnection() {
		if(con == null) {
			try {
				// not required if we are using jdbc 4.0
				Class.forName(driver);
				con = DriverManager.getConnection(url, userName, passWord);
			} catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} 
		}
		return con;
	}
	
	public static void closeConnection() {
		try {
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
