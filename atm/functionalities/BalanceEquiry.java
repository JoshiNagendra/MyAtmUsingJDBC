package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceEquiry {
	static Connection connection = null;
	static PreparedStatement pstmnt = null;
	static ResultSet result = null;
	public static void balanceEnquiry() throws InterruptedException {
		System.out.println("*** Welcome to the Balance Enquiry Service ***");
	
		String query = "select * from accountdetails";
		try {
			connection =atm.util.JDBCUtils.getConnection();
			if(connection!=null) {
				pstmnt =connection.prepareStatement(query);
				if(pstmnt != null) {
					result =pstmnt.executeQuery();
					if(result!= null) {
						if(result.next()) {
							int balance =result.getInt(3);
							for(int i=0;i<3;i++) {
								System.out.println("Fetching....");
								Thread.sleep(2000);
							}
							System.out.println();
							System.out.println("          Account Balance :: "+balance);
							System.out.println();
							System.out.println("*** Thank you for using Balance Enquiry Service ***");
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		balanceEnquiry();
	}

}
