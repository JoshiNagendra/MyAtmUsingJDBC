package atm.functionalities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ChangePin {
	static Connection connection = null;
	static PreparedStatement pstmnt = null;
	static int n = 0;
	static int  finalpin =0;
	static String otpStatus;

	public static int reConform() {
		System.out.println("Enter new Pin [Note :: 4 Digits pin only] ::");
		Scanner sc = new Scanner(System.in);
		
		int a1 = sc.nextInt();
		if (a1 >= 1000 && a1 <= 9999) {
			System.out.println("Renter the Pin ::");
			int a2 = sc.nextInt();
			if (a1 == a2) {
				finalpin = a1;
			} else {

				n++;

				if (n < 3) {
					System.out.println("Please try again. The pin is not matched.");
					System.out.println();
					reConform();
				} else {
					System.out.println("Chances for Entry Completed");
					finalpin = 0;
				}

			}
		} else {
			System.out.println("Please try again, only 4 digits allowed");
			System.out.println();
			n++;
			if (n < 3) {
				reConform();
			} else {
				System.out.println("Chances for Entry Completed");
				finalpin = 0;
			}
		}
		return finalpin;

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

	public static void changePin() {
		System.out.println("*** Welcome to Change Pin Service ***");
		int pin = ChangePin.reConform();

		if (pin != 0) {
			String otpVerify = otpVerification();
			if(otpVerify.equalsIgnoreCase("success")) {
				String query = "update accountdetails set AtmPin = ? where accName = 'Joshi'";
				try {
					connection = atm.util.JDBCUtils.getConnection();
					if (connection != null) {
						pstmnt = connection.prepareStatement(query);
						if (pstmnt != null) {
							pstmnt.setInt(1, pin);
							int reflected = pstmnt.executeUpdate();
							if (reflected == 1) {
								for(int i=0;i<=2;i++) {
									System.out.println("Pin Updating .....");
									Thread.sleep(2000);
								}
								System.out.println();
								System.out.println("Pin Changed SuccessFully");
								System.out.println();
								System.out.println("*** Thank you for using Change Pin Service ***");
							}
						}
					}
				} catch (SQLException es) {
					es.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					atm.util.JDBCUtils.closeConnection(connection, pstmnt, null, null);
				}
			}else {
				System.out.println("*** Thank you for using ChangePin Service ***");
			}
		} else {
			System.out.println("*** Thank you for using Change Pin Service ***");
		}
	}

	public static void main(String[] args) {
		changePin();

	}

}
