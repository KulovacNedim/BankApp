package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import data.Address;
import data.Bank;
import data.User;
import utils.BankAppMenusAndMessages;
import validation.BankValidation;
import validation.FileReaderClass;
import validation.FileWriterClass;
import validation.InputValidation;
import validation.LoginValidation;

public class AdminApp {

	private BankAppMenusAndMessages bankAppMenusAndMessages = new BankAppMenusAndMessages();
	private BankApp bankApp = new BankApp();
	private FileReaderClass fileReader = new FileReaderClass();
	private LoginValidation loginValidation = new LoginValidation();
	private BankValidation bankValidation = new BankValidation();
	private FileWriterClass fileWriter = new FileWriterClass();
	private InputValidation inputValidation = new InputValidation();
	private UserApp userApp = new UserApp();
	private AtmApp atmApp = new AtmApp();

	public void adminWelcomeScreen(User user, Scanner sc) {
		boolean loggedInSpecificBank = false;
		Bank bank = new Bank();

		bankAppMenusAndMessages.printHeader(user, bank, "WELCOME TO BANK ADMIN MENU");
		bankAppMenusAndMessages.printAdminMenu();
		listAdminMainManuChoices(sc, loggedInSpecificBank, bank, user);
	}

	public void listAdminMainManuChoices(Scanner sc, boolean loggedInSpecificBank, Bank bank, User user) {
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
			if (!loggedInSpecificBank) {
				bankAppMenusAndMessages.printHeader(user, bank, "BANK LOGGING MENU");
				bankApp.chooseBank(bank, user, sc);
				loggedInSpecificBank = true;
			} else {
				bankAppMenusAndMessages.printHeader(user, bank, "BANK LOGGING MENU");
				System.out.println("\nYou are already logged in " + bank.getName() + " system.");
				String yesNo = "";
				boolean askInputAgain = true;
				while (askInputAgain) {
					System.out.print("Do you want to log in another bank? Type \"Yes\" or \"No\": ");
					yesNo = sc.next();
					if (yesNo.equalsIgnoreCase("yes") || yesNo.equalsIgnoreCase("no")) {
						askInputAgain = false;
					} else {
						System.out.println("\nWrong input. \"Yes\" or \"No\" permitted. Try again.");
					}
				}

				if (yesNo.equalsIgnoreCase("Yes")) {
					System.out.println();
					bank = new Bank();
					bankApp.chooseBank(bank, user, sc);
				}
			}
			bankAppMenusAndMessages.printHeader(user, bank, "WELCOME TO BANK ADMIN MENU");
			bankAppMenusAndMessages.printAdminMenu();
			listAdminMainManuChoices(sc, loggedInSpecificBank, bank, user);
			break;
		case 2:
			bankApp.bankSettings(sc, loggedInSpecificBank, user);
			break;
		case 3:
			adminSettings(sc, user);
			break;
		case 4:
			if (!loggedInSpecificBank) {
				System.out.println("\nThese are settings for a specific bank and in order to use "
						+ " these settings you have to log in specific bank (opcion 1) first.");
			} else {
				userApp.userSettings(sc, bank, user);
			}
			adminWelcomeScreen(user, sc);
			break;
		case 5:
			if (!loggedInSpecificBank) {/// Jet lip those 2*
				System.out.println("\nThese are settings for a specific bank and in order to use "
						+ " these settings you have to log in specific bank (opcion 1) first.");
			} else {
				atmApp.atmOperatorsSettings(sc, bank, user);
			}
			adminWelcomeScreen(user, sc);
			break;
		case 6:
			System.out.println("LOGGED OUT.\n\n");
			sc.nextLine();
			userApp.loginSystem(sc);
			break;
		default:
			System.out.println("Choice you made does not exist. Try again:");
			bankAppMenusAndMessages.printAdminMenu();
		}

	}

	private void adminSettings(Scanner sc, User user) {

		bankAppMenusAndMessages.printHeader(user, "ADMIN SETTINGS MENU");
		System.out.println();
		bankAppMenusAndMessages.printAdminSettingsOptions();

		ArrayList<User> userObjects = fileReader.getUserObjects();

		boolean print = true;
		int choice = 0;

		while (print) {
			try {
				System.out.print("\nYour choice: ");
				choice = sc.nextInt();
				print = false;
			} catch (InputMismatchException ex) {
				System.err.println("Wrong input. Integers only permitted. Try again.");
				sc.nextLine();
			}
		}

		switch (choice) {
		case 1:
			bankAppMenusAndMessages.printHeader(user, "OVERVIEW OF ADMINISTRATORS");
			System.out.println();
			printListOfAdmins(userObjects);
			adminSettings(sc, user);
			break;
		case 2:
			bankAppMenusAndMessages.printHeader(user, "ADD NEW ADMIN SECTION");
			System.out.println();
			addNewAdmin(sc, userObjects);
			adminSettings(sc, user);
			break;
		case 3:
			bankAppMenusAndMessages.printHeader(user, "DELETE ADMINISTRATOR SECTION");
			System.out.println();
			deleteAdmin(sc, userObjects, user);
			adminSettings(sc, user);
			break;
		case 4:
			bankAppMenusAndMessages.printHeader(user, "CHANGE ADMINISTRATOR ACTIVITY STATUS");
			System.out.println();
			changeAdminActivityStatus(sc, userObjects, user);
			adminSettings(sc, user);
			break;
		case 5:
			adminWelcomeScreen(user, sc);
			break;
		default:
			System.out.println("Choice you made does not exist. Try again:");
			adminSettings(sc, user);
		}

	}

	private void addNewAdmin(Scanner sc, ArrayList<User> userObjects) {

		boolean addNewAdmin = true;
		sc.nextLine();

		while (addNewAdmin) {
			System.out.println("Enter new administrator informations: ");
			String firstName = "";
			String lastName = "";
			String city = "";
			String street = "";
			String zipCode = "";

			while (inputValidation.isStringEmpty(firstName)) {
				System.out.print("First Name: ");
				firstName = sc.nextLine();
				if (inputValidation.isStringEmpty(firstName)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(lastName)) {
				System.out.print("Last Name: ");
				lastName = sc.nextLine();
				if (inputValidation.isStringEmpty(lastName)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(city)) {
				System.out.print("City: ");
				city = sc.nextLine();
				if (inputValidation.isStringEmpty(city)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(street)) {
				System.out.print("Street: ");
				street = sc.nextLine();
				if (inputValidation.isStringEmpty(street)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(zipCode)) {
				System.out.print("Zip code: ");
				zipCode = sc.nextLine();
				if (inputValidation.isStringEmpty(zipCode)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			User user = new User();
			Address address = new Address();
			Path addressPath = Paths.get("addresses.txt");

			user.setID(loginValidation.getNewUserId());
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(loginValidation.getNewPassword());
			user.setAddress(address);
			user.getAddress().setId(fileReader.findNextIdInFile(addressPath, 0));
			user.getAddress().setCity(city);
			user.getAddress().setStreet(street);
			user.getAddress().setPostZip(zipCode);
			user.setBank(bankValidation.setBank(1));
			user.setGlobalAdmin(true);
			user.setAtmOperator(false);
			user.setActive(true);

			userObjects.add(user);

			try {
				fileWriter.writeAddressObjectToFile(addressPath, user.getAddress());
			} catch (IOException e) {
				System.out.print("File does not exist.");
				System.exit(1);
			}

			try {
				fileWriter.writeUserObjectsToFile(userObjects);
			} catch (FileNotFoundException e) {
				System.out.print("File does not exist.");
				System.exit(1);
			}

			System.out.println("New administrator " + user.getFirstName() + " " + user.getLastName() + " added.");

			String yesNo = "";
			boolean askAgain = true;

			while (askAgain) {
				System.out.print("\nDo you want to add new administrator? (\"Yes\" or \"No\"): ");
				yesNo = sc.nextLine();

				if (yesNo.equalsIgnoreCase("No")) {
					addNewAdmin = false;
					askAgain = false;
				} else if (yesNo.equalsIgnoreCase("Yes")) {
					System.out.println();
					askAgain = false;
				} else {
					System.out.println("\nWrong input. \"Yes\" or \"No\" expected. Try again.");
				}
			}
		}
	}

	private void deleteAdmin(Scanner sc, ArrayList<User> userObjects, User user) {

		printListOfAdmins(userObjects);
		System.out.print(
				"Take a look at table above and enter ID of administrator to delete it or enter \"0\" to CANCEL: ");
		int id = -1;
		boolean askAgain = true;
		while (askAgain) {
			try {
				id = sc.nextInt();
				if (inputValidation.doesIdExist(id, "users.txt", 0)) {
					askAgain = false;
				} else if (id == 0) {
					adminSettings(sc, user);
				} else {
					System.out.print("ID you entered does not exist. Try again: ");
				}
			} catch (InputMismatchException ex) {
				System.out.print("Wrong input. Integers only permitted. Try again: ");
				sc.nextLine();
			}
		}

		deleteAdmin(id, userObjects);

		try {
			fileWriter.writeUserObjectsToFile(userObjects);
		} catch (FileNotFoundException e) {
			System.out.print("File does not exist.");
			System.exit(1);
		}
	}

	private void deleteAdmin(int id, ArrayList<User> userObjects) {

		for (int i = 0; i < userObjects.size(); i++) {
			if (Integer.valueOf(userObjects.get(i).getID()) == id) {
				System.out.println("Administrator " + userObjects.get(i).getFirstName() + " "
						+ userObjects.get(i).getLastName() + " deleted.");
				userObjects.remove(i);
			}
		}
	}

	private void changeAdminActivityStatus(Scanner sc, ArrayList<User> userObjects, User user) {
		printListOfAdmins(userObjects);
		System.out.print(
				"Take a look at table above and enter ID of administrator to change activity status or enter \"0\" to CANCEL: ");
		int id = -1;
		boolean askAgain = true;
		while (askAgain) {
			try {
				id = sc.nextInt();
				if (inputValidation.doesIdExist(id, "users.txt", 0)) {
					askAgain = false;
				} else if (id == 0) {
					adminSettings(sc, user);
				} else {
					System.out.print("ID you entered does not exist. Try again: ");
				}
			} catch (InputMismatchException ex) {
				System.out.print("Wrong input. Integers only permitted. Try again: ");
				sc.nextLine();
			}
		}

		changeAdminActivityStatus(id, userObjects);

		try {
			fileWriter.writeUserObjectsToFile(userObjects);
		} catch (FileNotFoundException e) {
			System.out.print("File does not exist.");
			System.exit(1);
		}
	}

	private void printListOfAdmins(ArrayList<User> userObjects) {

		System.out.println("LIST OF GLOBAL SYSTEM ADMINISTRATORS:");
		System.out.println("==========================================");
		System.out.printf("%-5s \t %-20s %-9s\n", "ID", "NAME", "STATUS");
		System.out.println("==========================================");

		for (int i = 0; i < userObjects.size(); i++) {
			if (userObjects.get(i).isGlobalAdmin()) {
				System.out.printf("%-5s \t %-20s %-9s\n", userObjects.get(i).getID(),
						userObjects.get(i).getFirstName() + " " + userObjects.get(i).getLastName(),
						userObjects.get(i).isActive() == true ? "ACTIVE" : "INACTIVE");
			}
		}
		System.out.println("== END OF LIST ===========================\n");
	}

	private void changeAdminActivityStatus(int id, ArrayList<User> userObjects) {

		for (int i = 0; i < userObjects.size(); i++) {
			if (userObjects.get(i).getID().equals(userApp.getNextUserId(id))) {
				if (userObjects.get(i).isActive()) {
					userObjects.get(i).setActive(false);
				} else {
					userObjects.get(i).setActive(true);
				}

				System.out.println(
						"\nActivity status for administrator " + userObjects.get(i).getFirstName().toUpperCase() + " "
								+ userObjects.get(i).getLastName().toUpperCase() + " is changed to "
								+ (userObjects.get(i).isActive() == true ? "ACTIVE." : "INACTIVE."));
			}
		}
	}
}
