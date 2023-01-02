package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

//Creating user defined Exception
class InvalidUserException extends Exception{
	InvalidUserException(String msg){
		super(msg);
	}
	
}

//Login class
public class Login {
	static int atmId;
	static int atmPin;
	static Connection connection=null;
	static PreparedStatement pstmnt = null;
	static ResultSet result = null;
	static int id;
	static int pin;
	
	//Method 
	public static void inputFromUser() {
		Scanner sc = new Scanner(System.in);
		
		//Taking the ATM id input from the user
		System.out.println("Enter the ATM ID  ::");
		atmId = sc.nextInt();
		
		//Taking the Atm pin from the user
		System.out.println("Enter the ATM Pin ::");
		atmPin = sc.nextInt();
	}
	
	//Method
	public static void userInfo() {
		
		//Query for retreiving data from database
		String query = "select * from accountdetails";
		try {
			//Connecting to the database
			connection =atm.util.JDBCUtils.getConnection();
			if(connection != null) {
				//Preparing the statement
				pstmnt =connection.prepareStatement(query);
				if(pstmnt!=null) {
					//Executing the query and storing the data into result
					result =pstmnt.executeQuery();
					if(result != null) {
						if(result.next()) {
							 //Retreiving data and storing into the variables
							 id = result.getInt(4);
							 pin = result.getInt(5);
						}
						
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//Method
	public static void validation() throws InvalidUserException {
		//calling the inputFrom user method
		inputFromUser();
		
		//Calling the userInfo Method
		userInfo();
		
		//Comparing the user input credentials and database credentials
		if(atmId == id && atmPin == pin) {
			String status =CardStatus.statusCheck();
			if(status.equalsIgnoreCase("Active")) {
				System.out.println("Login Successful");
			}else {
				System.out.println("--->  Card is blocked !! <---");
				System.out.println("Please type [Yes] if you want to unblock the card");
				Scanner sc = new Scanner(System.in);
				String userOption = sc.next();
				if(userOption.equalsIgnoreCase("yes")) {
					CardStatus.activeStatus();
				}else {
			    	System.out.println();
			    	System.out.println("                 *****Thank you for using My Atm App*****");
			    	System.out.println("                 =========================================");
				}
			}
	    //if not matched then it will execute else block
		}else {
			throw new InvalidUserException("Invalid Credentials");
		}
	}
	

	public static int  verify() {
		int a =0;
		try {
			validation();
			a=1;
		}catch(InvalidUserException e) {
			  System.out.println(e.getMessage());
			  a=0;
			try {
				validation();
				a=1;
			}catch(InvalidUserException f) {
				  System.out.println(f.getMessage());
				  a=0;
				try {
					validation();
					a=1;
				}catch(InvalidUserException g) {
					a=0;
					System.out.println(g.getMessage());
					System.out.println();
					CardStatus.changeToBlockStatus();
					System.out.println(" Tata Tata Bye Bye .. .. .. .");
				}
			}
		}
		return a;
	}
	public static void main(String[] args) {
		verify();
 }
}
