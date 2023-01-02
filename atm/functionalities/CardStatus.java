package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CardStatus {
	static  Connection connection=null;
	static PreparedStatement pstmnt = null;
	static ResultSet result = null;
	static Scanner sc = null;
	static String status;
	static String otpStatus;
	
	
	
	public static void changeToBlockStatus() {

		String query = "update accountdetails set status = ? where accName ='Joshi'";
		try {
			connection =atm.util.JDBCUtils.getConnection();
			if(connection!= null) {
				pstmnt =connection.prepareStatement(query);
				if(pstmnt!=null) {
					pstmnt.setString(1,"Blocked");
					int rowsEffec =pstmnt.executeUpdate();
					if(rowsEffec==1) {
						System.out.println("Card is blocked ");
					}
				}
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			atm.util.JDBCUtils.closeConnection(connection, pstmnt, result, sc);
		}
	}
	public static String statusCheck() {
		String query = "select * from accountdetails";
		try {
			connection =atm.util.JDBCUtils.getConnection();
			if(connection!=null) {
				pstmnt =connection.prepareStatement(query);
				if(pstmnt!=null) {
					result =pstmnt.executeQuery();
					if(result!=null) {
						if(result.next()) {
							status =result.getString(8);
							
						}
					}
				}
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
		
	}
	
	public static void changeToActiveStatus() {
		String query = "update accountdetails set status = ? where accName ='Joshi'";
		try {
			connection =atm.util.JDBCUtils.getConnection();
			if(connection!=null) {
				pstmnt =connection.prepareStatement(query);
				if(pstmnt!=null) {
					pstmnt.setString(1,"Active");
					int rowsEffec =pstmnt.executeUpdate();
					if(rowsEffec==1) {
						System.out.println("Card Unblocked SuccessFully");
					}
				}
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			atm.util.JDBCUtils.closeConnection(connection, pstmnt, result, sc);
		}
	}
	public static int otp() {
		 
        int random = ThreadLocalRandom.current().nextInt(1000,10000);

      
        String query = "update accountdetails set otp = ? where accName = 'Joshi'"; 
        try {
        	connection =atm.util.JDBCUtils.getConnection();
        	if(connection !=null) {
        		pstmnt =connection.prepareStatement(query);
        		if(pstmnt!=null) {
        			pstmnt.setInt(1, random);
        			int rowsEffec =pstmnt.executeUpdate();
        			if(rowsEffec==1) {
        				System.out.println("Otp generated ");
        			}
        		}
        	}
        	
        }catch(SQLException se) {
        	se.printStackTrace();
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
        	atm.util.JDBCUtils.closeConnection(connection, pstmnt, null, null);
        }
        return random;
		
	}
	
	public static String otpVerification() {
		int otp =otp();
		System.out.println("Please Enter the OTP  ::");
		Scanner sc = new Scanner(System.in);
		int userOtp = sc.nextInt();
		if(otp ==userOtp) {
			System.out.println("OTP Verified Successfully");
			System.out.println();
			otpStatus = "success";
		}else {
			System.out.println("Invalid OTP");
			otpStatus = "Invalid";
		}
		return otpStatus;
	}
	
	public static void activeStatus() {
		String otp = otpVerification();
		if(otp.equalsIgnoreCase("success")) {
			changeToActiveStatus();
		}else {
			System.out.println("Invalid OTP");
		}
		
	}
	public static void main(String[] args) {
		activeStatus();
	}

}
