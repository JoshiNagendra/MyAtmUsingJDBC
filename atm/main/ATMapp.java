package atm.main;

import java.util.Scanner;

public class ATMapp {
	public static void FirstService() throws InterruptedException {
	   	System.out.println("Please Choose below options ");
			System.out.println("1 - WithDrawl       ||    2 - Deposit");
			System.out.println("3 - BalanceEnquiry  ||    4 - TransferAmount");
			System.out.println("5 - Change Pin      ||    6 - To Know Account Details");
			System.out.println("                 7 - Exit  ");
			Scanner sc = new Scanner(System.in);
			int userInput = sc.nextInt();
			if(userInput==1) {
				atm.functionalities.CashWithDrawl.cashWithDrawl();
			}else if(userInput ==2) {
				atm.functionalities.Deposit.deposit();
			}else if(userInput == 3) {
				atm.functionalities.BalanceEquiry.balanceEnquiry();
			}else if(userInput ==4 ) {
				atm.functionalities.TransferCash.transferCash();
				
			}else if(userInput ==5 ) {
				atm.functionalities.ChangePin.changePin();
				
			}else if(userInput ==6 ) {
				atm.functionalities.AccountDetails.accountDetails();
			}else if(userInput == 7) {
				          
				
			}else {
				System.out.println("Wrong input ");
			}
			
}

    public static void main(String[] args) throws InterruptedException {
    	System.out.println("                  *****Welcome to My Atm App*****");
    	System.out.println("                  ================================");
    	System.out.println();
    	int verify =atm.functionalities.Login.verify();
    	if(verify ==1) {
    		FirstService();
    	}
    	System.out.println();
    	System.out.println("                 *****Thank you for using My Atm App*****");
    	System.out.println("                 =========================================");	
	}
}
