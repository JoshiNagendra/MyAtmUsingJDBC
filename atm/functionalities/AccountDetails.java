package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDetails {
	static Connection connection = null;
	static PreparedStatement pstmnt = null;
	static ResultSet result = null;
	
	public static void accountDetails() {
		System.out.println("***Welcome to Know Account Details Service****");
		String query = "select * from accountdetails where accName = 'Joshi'";
		try {
			connection =atm.util.JDBCUtils.getConnection();
			if(connection!=null) {
				pstmnt =connection.prepareStatement(query);
				if(pstmnt!=null) {
					result =pstmnt.executeQuery();
					if(result!=null) {
						if(result.next()) {
							String name =result.getString(2);
							int id =result.getInt(5);
							System.out.println();
							System.out.println("           Account Name   :: "+name);
							System.out.println("           ATM id         :: "+id);
							System.out.println();
							System.out.println("***Thank you for using To Know the Account Details Service***");
							System.out.println();
						}
					}
				}
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			atm.util.JDBCUtils.closeConnection(connection, pstmnt, result, null);
		}

	}
	public static void main(String[] args) {
		accountDetails();
	}
}
