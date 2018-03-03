package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import data.Bank;
import data.User;
import utils.BankAppMenusAndMessages;
import validation.FileReaderClass;
import validation.FileWriterClass;
import validation.InputValidation;

public class AtmApp {

	private BankAppMenusAndMessages bankAppMenusAndMessages = new BankAppMenusAndMessages();
	private FileReaderClass fileReader = new FileReaderClass();
	private UserApp userApp = new UserApp();
	private InputValidation inputValidation = new InputValidation();
	private FileWriterClass fileWriter = new FileWriterClass();

	public void atmOperatorsSettings(Scanner sc, Bank bank, User user) {

		bankAppMenusAndMessages.printHeader(user, bank, "ATM OPERATORS SETTINGS");
		bankAppMenusAndMessages.printAtmOperatorsMenu();
		listAtmOperatorsSettingsChoices(sc, bank, user);

	}

	private void listAtmOperatorsSettingsChoices(Scanner sc, Bank bank, User user) {
		AdminApp adminApp = new AdminApp();
		ArrayList<User> userObjects = fileReader.getUserObjects();

		boolean print = true;
		int choice = 0;
		while (print) {
			try {
				System.out.print("\nYour choice: ");
				choice = sc.nextInt();
				print = false;
			} catch (InputMismatchException ex) {
				System.err.println("Wrong input! Integer only permitted. Try again!");
				sc.nextLine();
			}
		}

		switch (choice) {
		case 1:
			bankAppMenusAndMessages.printHeader(user, bank, "ATM OPERATORS OVERVIEW SECTION");
			System.out.println();
			printListOfAtmOperators(userObjects, bank);
			atmOperatorsSettings(sc, bank, user);
			break;
		case 2:
			bankAppMenusAndMessages.printHeader(user, bank, "SET OR DELETE ATM OPERATORS SECTION");
			System.out.println();
			setOrDeleteAtmOperators(sc, userObjects, bank, user);
			atmOperatorsSettings(sc, bank, user);
			break;
		case 3:
			adminApp.adminWelcomeScreen(user, sc);
			break;
		default:
			System.out.println("Choice you made does not exist. Try again:");
			atmOperatorsSettings(sc, bank, user);
		}
	}

	private void printListOfAtmOperators(ArrayList<User> userObjects, Bank bank) {

		System.out.println("LIST OF ATM OPERATORS FOR " + bank.getName().toUpperCase() + " BANK:");
		System.out.println("==========================================");
		System.out.printf("%-5s \t %-20s %-9s\n", "ID", "NAME", "STATUS");
		System.out.println("==========================================");

		for (int i = 0; i < userObjects.size(); i++) {
			if (userObjects.get(i).getBank().getId() == bank.getId() && userObjects.get(i).isAtmOperator()
					&& !userObjects.get(i).isGlobalAdmin()) {
				System.out.printf("%-5s \t %-20s %-9s\n", userObjects.get(i).getID(),
						userObjects.get(i).getFirstName() + " " + userObjects.get(i).getLastName(),
						userObjects.get(i).isActive() == true ? "ACTIVE" : "INACTIVE");
			}
		}
		System.out.println("== END OF LIST ===========================\n");
	}

	private void setOrDeleteAtmOperators(Scanner sc, ArrayList<User> userObjects, Bank bank, User user) {

		printListOfUsersAtmStatus(userObjects, bank);
		System.out.print(
				"Take a look at table above and enter user ID to change ATM operator status or enter \"0\" to CANCEL: ");
		int id = -1;
		boolean askAgain = true;
		while (askAgain) {
			try {
				id = sc.nextInt();
				if (inputValidation.doesIdExist(id, bank.getId(), "users.txt", 0, 5)) {
					askAgain = false;
				} else if (id == 0) {
					atmOperatorsSettings(sc, bank, user);
				} else {
					System.out.print("ID you entered does not exist for " + bank.getName() + ". Try again: ");
				}
			} catch (InputMismatchException ex) {
				System.out.print("Wrong input. Integers only permitted. Try again: ");
				sc.nextLine();
			}
		}

		changeAtmOperatorStatus(id, userObjects);

		try {
			fileWriter.writeUserObjectsToFile(userObjects);
		} catch (FileNotFoundException e) {
			System.out.print("File does not exist.");
			System.exit(1);
		}
	}

	private void printListOfUsersAtmStatus(ArrayList<User> userObjects, Bank bank) {

		System.out.println("LIST OF USER FOR " + bank.getName().toUpperCase() + " BANK:");
		System.out.println("========================================================");
		System.out.printf("%-5s \t %-20s %-9s %-15s\n", "ID", "NAME", "STATUS", "ATM OPERATOR");
		System.out.println("========================================================");

		for (int i = 0; i < userObjects.size(); i++) {
			if (userObjects.get(i).getBank().getId() == bank.getId() && !userObjects.get(i).isGlobalAdmin()) {
				System.out.printf("%-5s \t %-20s %-9s %-15s\n", userObjects.get(i).getID(),
						userObjects.get(i).getFirstName() + " " + userObjects.get(i).getLastName(),
						userObjects.get(i).isActive() == true ? "ACTIVE" : "INACTIVE",
						userObjects.get(i).isAtmOperator() == true ? "YES" : "NO");
			}
		}
		System.out.println("== END OF LIST =========================================\n");

	}

	private void changeAtmOperatorStatus(int id, ArrayList<User> userObjects) {

		for (int i = 0; i < userObjects.size(); i++) {
			boolean valid = isUserValidAsAtmOperator(userObjects.get(i));
			
				if (valid && userObjects.get(i).getID().equals(userApp.getNextUserId(id))) {
					if (userObjects.get(i).isAtmOperator()) {
						userObjects.get(i).setAtmOperator(false);
					} else {
						userObjects.get(i).setAtmOperator(true);
					}

					System.out.println("\n" + userObjects.get(i).getFirstName().toUpperCase() + " "
							+ userObjects.get(i).getLastName().toUpperCase()
							+ ((userObjects.get(i).isAtmOperator() == true ? " is set as ATM operator."
									: " is delated as ATM operator.")));
				} else if (!valid && userObjects.get(i).getID().equals(userApp.getNextUserId(id))){
				System.out.println("\nIt is not allowed to set inactive user or global administrator as ATM operator.");
			}

		}

	}

	private boolean isUserValidAsAtmOperator(User user) {

		if (user.isActive() && !user.isGlobalAdmin()) {
			return true;
		} else {
			return false;
		}
	}
}
