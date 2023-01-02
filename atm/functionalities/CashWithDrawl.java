package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CashWithDrawl {
	static Connection connection = null;
	static PreparedStatement pstmnt = null;
	static Statement statement = null;
	static ResultSet result = null;
	static int amount;
	static int finalAmount;
	static int availableBalance;
	static Scanner sc;

	
	
	public static  void cashWithDrawl() {
		System.out.println( "Welcome to WithDrawl Services ");
		System.out.println("********************************");
		
		System.out.println("Enter the withdrawal amount :: ");
		System.out.println("--------------------------------");
		sc=new Scanner(System.in);
		int balWithdrawl = 0;
		int withdrawl = sc.nextInt();
		if(withdrawl>=100 && withdrawl<=20000 ) {
			if(withdrawl%100!=0) {
				System.out.println();
				System.out.println("Unable to proceed with the entered amount [only 2000, 500, 200, 100 available]");
				System.out.println();
				System.out.println("***Thank You for using WithDrawl service***");
	
			}else {
				balWithdrawl=withdrawl;
			}
			
		}else {
			System.out.println("Amount is not in the range");
			System.out.println();
			System.out.println("***Thank you for using WithDrawl Service***");
		}
		String query = "update accountdetails set accBalance = accBalance - ? where accName ='Joshi'";
		try {
			connection = atm.util.JDBCUtils.getConnection();
			if(connection != null) {
				pstmnt =connection.prepareStatement(query);
				statement =connection.createStatement();
				if(statement !=null) {
					String query2 ="select * from accountdetails where accName = 'Joshi'";
					result =statement.executeQuery(query2);
					if(result!=null) {
						if(result.next()) {
							availableBalance = result.getInt(3);
						}
					}
				}
				if(balWithdrawl<=availableBalance) {
					if(pstmnt!=null) {
						pstmnt.setInt(1, balWithdrawl);
						int rowsEffec = pstmnt.executeUpdate();
						if(rowsEffec==1) {
							if(balWithdrawl!=0) {
								System.out.println(balWithdrawl+" :: Successful withdrawal; please collect money");
								System.out.println();
								System.out.println("***Thank You for using the Withdrawl Service***");
							}
						}
					}
				}else {
					System.out.println("Insufficient Balance ");
					System.out.println("Thank you for using Withdrawl Service ");
				}
				
			}
		}catch(SQLException es) {
			es.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			atm.util.JDBCUtils.closeConnection(connection, pstmnt, null, sc);
		}
		
	}
	public static void main(String[] args) {
		cashWithDrawl();
	}
	
}
