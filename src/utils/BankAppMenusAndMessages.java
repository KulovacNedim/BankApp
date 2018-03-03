package utils;

import data.Bank;
import data.User;
import validation.BankValidation;

public class BankAppMenusAndMessages {

	BankValidation bankValidation = new BankValidation();
	
	public void printHeader(User user, Bank bank, String string) {
		int stringLenght = string.length();
		int numberOfBlanksAfter = ((68 - stringLenght) / 2);
		int numberOfBlanksBefore = 68 - numberOfBlanksAfter - stringLenght;
		
		String infoLine = "You are logged as " + user.getLastName().toUpperCase() + " " 
						+ user.getFirstName().toUpperCase()
						+ (bank.getId() != 0 ? (" in " + bank.getName().toUpperCase() + " system") : "");
		
		int stringLenght1 = infoLine.length();
		int numberOfBlanksAfter1 = ((68 - stringLenght1) / 2);
		int numberOfBlanksBefore1 = 68 - numberOfBlanksAfter1 - stringLenght1;
		
		System.out.println("\n+--------------------------------------------------------------------+");
		System.out.println("+                                                                    +");
		System.out.print("+");
		for (int i = 1; i <= numberOfBlanksBefore; i++) {
			System.out.print(" ");
		}
		System.out.print(string);
		for (int i = 1; i <= numberOfBlanksAfter; i++) {
			System.out.print(" ");
		}
		System.out.println("+");
		System.out.println("+--------------------------------------------------------------------+");
		System.out.print("+");
		for (int i = 1; i <= numberOfBlanksBefore1; i++) {
			System.out.print(" ");
		}
		System.out.print(infoLine);
		for (int i = 1; i <= numberOfBlanksAfter1; i++) {
			System.out.print(" ");
		}
		System.out.println("+");
		System.out.println("+--------------------------------------------------------------------+");
	}
	
	public void printHeader(User user, String string) {
		int stringLenght = string.length();
		int numberOfBlanksAfter = ((68 - stringLenght) / 2);
		int numberOfBlanksBefore = 68 - numberOfBlanksAfter - stringLenght;
		
		String infoLine = "You are logged as " + user.getLastName().toUpperCase() + " " 
						+ user.getFirstName().toUpperCase()
						+ ". Not logged in any bank system.";
						
		int stringLenght1 = infoLine.length();
		int numberOfBlanksAfter1 = ((68 - stringLenght1) / 2);
		int numberOfBlanksBefore1 = 68 - numberOfBlanksAfter1 - stringLenght1;
		
		System.out.println("\n+--------------------------------------------------------------------+");
		System.out.println("+                                                                    +");
		System.out.print("+");
		for (int i = 1; i <= numberOfBlanksBefore; i++) {
			System.out.print(" ");
		}
		System.out.print(string);
		for (int i = 1; i <= numberOfBlanksAfter; i++) {
			System.out.print(" ");
		}
		System.out.println("+");
		System.out.println("+--------------------------------------------------------------------+");
		System.out.print("+");
		for (int i = 1; i <= numberOfBlanksBefore1; i++) {
			System.out.print(" ");
		}
		System.out.print(infoLine);
		for (int i = 1; i <= numberOfBlanksAfter1; i++) {
			System.out.print(" ");
		}
		System.out.println("+");
		System.out.println("+--------------------------------------------------------------------+");
	}

	public void printHeader() {

		System.out.println("+--------------------------------------------------------------------+");
		System.out.println("+                                                                    +");
		System.out.println("+                 WELCOME TO BANK APPLICATION MENU                   +");
		System.out.println("+                                                                    +");
		System.out.println("+--------------------------------------------------------------------+");
		System.out.println("+                                                                    +");
		System.out.println("+--------------------------------------------------------------------+");
	}

	public void printAdminMenu() {
		System.out.println("\nSelect what do you want to do next: \n");
		System.out.println("    1) Login to specific bank");
		System.out.println("    2) Bank settings");
		System.out.println("    3) Admin setting");
		System.out.println("    4) User settings");
		System.out.println("    5) ATM operators settings");
		System.out.println("    6) Log out of system");
	}

	public void printBankSettingsOptions() {
		System.out.println("Select your choice: \n");
		System.out.println("    1) Banks and officies overview");
		System.out.println("    2) Add new bank and officies");
		System.out.println("    3) Add new bank office to existing bank");
		System.out.println("    4) Change bank and officies activity status");
		System.out.println("    5) Back to MAIN ADMIN MENU");
	}

	public void printAdminSettingsOptions() {
		System.out.println("Select your choice: \n");
		System.out.println("    1) Overview list of global administrators");
		System.out.println("    2) Add new administrator");
		System.out.println("    3) Delete administrator");
		System.out.println("    4) Change administrators activity status");
		System.out.println("    5) Back to MAIN ADMIN MENU");
	}

	public void printUserSettingsMenu() {
		System.out.println("Select your choice: \n");
		System.out.println("    1) Overview list of users for this bank");
		System.out.println("    2) Add new user");
		System.out.println("    3) Delete user");
		System.out.println("    4) Change users activity status");
		System.out.println("    5) Back to MAIN ADMIN MENU");
	}

	public void printAtmOperatorsMenu() {
		System.out.println("Select your choice: \n");
		System.out.println("    1) Overview list of ATM operators for this bank");
		System.out.println("    2) Set or delete ATM operator");
		System.out.println("    3) Back to MAIN ADMIN MENU");
	}
}
