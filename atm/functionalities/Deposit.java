package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Deposit {
	static PreparedStatement pstmnt = null;
	static Connection connection = null;
 	static int deposit;
	static int notDeposited;
	
	static int amount;
	static int finalAmount;
	static Scanner sc;

	public static int validate() {
		 sc = new Scanner(System.in);
		System.out.println("Welcome to the deposit service");
		System.out.println("******************************");
		
		System.out.println();
		System.out.println("=================> Instructions <=================");
		System.out.println("1. Deposit amounts range from 100 to 100,000");
		System.out.println("2. The only acceptable notes are 100,200,500,2000");
		System.out.println();
		System.out.println("Enter the amount to deposit ::");
		int userAmount = sc.nextInt();
		System.out.println();
		// 8080
		if (userAmount <= 1000000 && userAmount >= 100) {
			int arr[] = { 2000, 500, 200, 100 };
			amount = userAmount;
			System.out.println("****Denomination****");
			System.out.println();
			for (int i = 0; i < arr.length; i++) {

				int notes = amount / arr[i]; // 4.04 => 16
				if (notes != 0) {
					// 2000 4 2000 * 4
					System.out.println(arr[i] + "\tx\t" + notes + "\t=\t" + (arr[i] * notes));
					amount = amount % arr[i]; // 80
		
				}
			}
			if(amount!=0) {
				System.out.println();
				System.out.println(amount + " : Not Accepted ");
			}
			finalAmount = userAmount - amount;
		}else {
			System.out.println("Your deposit limit is exceeded");
		}
		return finalAmount;

	}
    //To deposit the cash
	public static void deposit() {
		
		//Storing the return value 
		int userDeposit = validate();
		
		//Comparing the user input
		if(userDeposit<=100000) {
			deposit=userDeposit;
		}else if(userDeposit>100000) {
			deposit = 100000;
			notDeposited = userDeposit-100000;
			System.out.println(notDeposited +" :: Please collect money back maximum deposit is upto 1 lakh only");
		}
		
		//We are storing the query in the variable
		String query = "update accountdetails set accBalance = accBalance + ? where accName = 'Joshi'";
		try {
			
			//Getting connection from the database
			connection = atm.util.JDBCUtils.getConnection();
			if (connection != null) {
				
				//Setting auto commit as false
				connection.setAutoCommit(false);
				//Preparing the statement
				pstmnt = connection.prepareStatement(query);
				if (pstmnt != null) {
					pstmnt.setInt(1, deposit);
					
					//Sending the query
					System.out.println("--------------------------------------");
					System.out.println("Total Amount\t\t=\t"+deposit);
					System.out.println();
					System.out.println("Please confirm before making a deposit [Yes/No]");
					String confirm = sc.next();
					int rowsEffec = pstmnt.executeUpdate();
					if(confirm.equalsIgnoreCase("yes")) {
						connection.commit();
						if(rowsEffec ==1) {
							System.out.println(deposit +" ::  Deposited Successfully");
							System.out.println("Thank You for using the services");
							
						}
					}else {
						connection.rollback();
						System.out.println("Money not deposited ");
						System.out.println("Thank you for using the service");
					}
				}
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			//Closing the resources
			atm.util.JDBCUtils.closeConnection(connection, pstmnt, null, sc);
		}

	}
	public static void main(String[] args) {
		deposit();
	}

}
