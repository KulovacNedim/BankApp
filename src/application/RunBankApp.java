package application;

import java.util.Scanner;

public class RunBankApp {
	
	public static void main(String[] args) {
	
		UserApp userApp = new UserApp();
		
		Scanner sc = new Scanner(System.in);
		
		userApp.loginSystem(sc);
	}
}
