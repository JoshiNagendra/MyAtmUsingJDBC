package atm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class JDBCUtils {
	private JDBCUtils() {
		
	}
	//The utility method for creating connection with JDBC
	public static Connection getConnection() throws SQLException, IOException {
		Properties prop = new Properties();
		File f = new File("src//atm//properties//jdbcutil.properties");
		FileInputStream is = new FileInputStream(f);
		prop.load(is);
		String url = prop.getProperty("url");
		String username = prop.getProperty("Username");
		String password = prop.getProperty("Password");
		Connection connection = DriverManager.getConnection(url,username,password);
		return connection;
	}
	//The utility method for closing connections
	public static void closeConnection(Connection connection,PreparedStatement pstmnt, ResultSet result, Scanner sc) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmnt != null) {
			try {
				pstmnt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(result!=null) {
			try {
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if(sc!=null) {
			sc.close();
		}
	}


}
