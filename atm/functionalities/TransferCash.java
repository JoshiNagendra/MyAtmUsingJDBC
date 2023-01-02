package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransferCash {
	static Connection connection = null;
	static PreparedStatement pstmnt = null;
	static Statement statement = null;
	static ResultSet result = null;
	static String finalName = null;
	static int finalAtmId;
	static int atmid;
	static String userOption;
	static Scanner sc = new Scanner(System.in);
	public static void validateDetails() {
		System.out.println("Enter the ATM Id to transfer money to another account");
		
		atmid = sc.nextInt();
		System.out.println("Enter the accountant's name here");
		String name = sc.next();
		String query = "select * from transferacc where accName = 'jagan'";
		 try {
			 connection =atm.util.JDBCUtils.getConnection();
			 if(connection!= null) {
				 pstmnt =connection.prepareStatement(query);
				 if(pstmnt != null) {
					 result =pstmnt.executeQuery();
					 if(result!= null) {
						 if(result.next()) {
							  finalName =result.getString(1);
							  finalAtmId =result.getInt(3);
						 }
					 }
				 }
			 }
		 }catch(SQLException es) {
			 es.printStackTrace();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 atm.util.JDBCUtils.closeConnection(connection, pstmnt, result, null);
		 }
		 if(atmid == finalAtmId && name.equalsIgnoreCase(finalName)) {
			 System.out.println("Account information verified");
			 System.out.println("============================");
			 userOption = "yes";
		 }else {
			 System.out.println("Account information not exists");
			 System.out.println();
			 System.out.println("Do you wish to try once more? [Yes/No]");
			 System.out.println("--------------------------------------");
			 userOption = sc.next();
			 if(userOption.equalsIgnoreCase("yes")) {
				 validateDetails(); 
			 }else if(userOption.equalsIgnoreCase("no")) {
				 
			 }else {
				 System.out.println("** Invalid Input **");
				 
			 }
			 
		 }
	}
	
	public static int verifyAmount() {
		int acBalance = 0;
		String query ="select * from accountdetails where accName = 'Joshi'";
		try {
			connection =atm.util.JDBCUtils.getConnection();
			if(connection!=null) {
				pstmnt =connection.prepareStatement(query);
				if(pstmnt !=null) {
					result =pstmnt.executeQuery();
					if(result!=null) {
						if(result.next()) {
							acBalance =result.getInt(3);
						}
						
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			atm.util.JDBCUtils.closeConnection(connection, pstmnt, result, null);
		}
		return acBalance;
	}
	
	public static void transferCash() {
		java.util.Date date = new java.util.Date();
		System.out.println("*** Welcome to Transfer Service ***");
		validateDetails();
		int acBal = verifyAmount();
		if(userOption.equalsIgnoreCase("yes")){
			System.out.println("Enter the amount to transfer ::");
			int transfer = sc.nextInt();
			if(acBal>transfer ) {
				if(transfer<=50000) {
					String query = "update transferacc set accBalance = accBalance +"+ transfer+" where accName ='Jagan'";
					String query1 = "update accountdetails set accBalance = accBalance - "+transfer+" where accName = 'Joshi'";
					try {
						connection =atm.util.JDBCUtils.getConnection();
						if(connection!=null) {
							statement =connection.createStatement();
							if(statement!= null) {
								int row1effec =statement.executeUpdate(query);
								int row2effec = statement.executeUpdate(query1);
								if(row1effec ==1) {
									System.out.println(transfer+" :: Amount Succesfully transferred to Jagan on "+date);
		                            System.out.println();
								}
								if(row2effec==1) {
									System.out.println("=> Msg :: A/c Joshi Debited for "+transfer+" on "+date+" <=");
									System.out.println();
									System.out.println("***Thank you for using the Transfer Service***");
								}
							}
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("Transfer amount limit exceeds");
					System.out.println();
					System.out.println("***Thank you for using transfer service***");
				}
				
			}else {
				System.out.println("Insuficient Balance ");
				System.out.println();
				System.out.println("***Thank you for using Transfer Service***");
			}
		}else {
			System.out.println("***Thank you for using the Transfer Service***");
		}
		
	}
	public static void main(String[] args) {
		
     	transferCash();
		
	}

}
