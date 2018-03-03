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
import validation.FileReaderClass;
import validation.FileWriterClass;
import validation.InputValidation;
import validation.LoginValidation;

public class UserApp {

	private LoginValidation loginValidation = new LoginValidation();
	private BankAppMenusAndMessages bankAppMenusAndMessages = new BankAppMenusAndMessages();
	private FileReaderClass fileReader = new FileReaderClass();
	private InputValidation inputValidation = new InputValidation();
	private FileWriterClass fileWriter = new FileWriterClass();

	public void loginSystem(Scanner sc) {
		AdminApp adminApp = new AdminApp();
		bankAppMenusAndMessages.printHeader();

			User user = new User();
			login(sc, user);
			
			if (user.isGlobalAdmin()) {
				adminApp.adminWelcomeScreen(user, sc);
			} else {
				//LOG AS USER
			}
	}

	public void login(Scanner sc, User user) {

		int wrongLoginCounter = 0;	
		System.out.println("\nIn order to log in application, enter your ID and password.");
			
		while (user.getID() == null && wrongLoginCounter < 3) {
			System.out.print("\nEnter your ID: ");
			String id = sc.nextLine();

			System.out.print("Enter your password: ");
			String pass = sc.nextLine();

			loginValidation.validateLogin(id, pass, user);
			
			if (user.getID() == null) {
				System.out.println("\nWrong ID/password combination. Try again.");
				wrongLoginCounter++;
			}
			if (wrongLoginCounter == 3) {
				System.err.println("You made three attempt to login with wrong ID/password combination. \n" +
									"Your card is blocked.\n" +
									"Please visit our nearlest office.");
				System.exit(1);
			} 
		}
	}

	public String getNextUserId(int id) {
		String idOutput = "" + id;
		while (idOutput.length() < 5) {
			idOutput = "0" + idOutput;
		}
		return idOutput;
	}
	
	public void userSettings(Scanner sc, Bank bank, User user) {
		bankAppMenusAndMessages.printHeader(user, bank, "USERS SETTINGS MENU");
		bankAppMenusAndMessages.printUserSettingsMenu();
		listUserSettingsChoices(sc, bank, user);
	}
	
	private void listUserSettingsChoices(Scanner sc, Bank bank, User user) {
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
			bankAppMenusAndMessages.printHeader(user, bank, "BANK USERS OVERVIEW SECTION");
			System.out.println();
			printListOfUsers(userObjects, bank);
			userSettings(sc, bank, user);
			break;
		case 2:
			bankAppMenusAndMessages.printHeader(user, bank, "ADD NEW USER SECTION");
			System.out.println();
			addNewUser(sc, userObjects, bank);
			userSettings(sc, bank, user);
			break;
		case 3:
			bankAppMenusAndMessages.printHeader(user, bank, "DELETE USER SECTION");
			System.out.println();
			deleteUser(sc, userObjects, bank, user);
			userSettings(sc, bank, user);
			break;
		case 4:
			bankAppMenusAndMessages.printHeader(user, bank, "CHANGE USER ACTIVITY STATUS SECTION");
			System.out.println();
			changeUserActivityStatus(sc, userObjects, bank, user);
			userSettings(sc, bank, user);
			break;
		case 5:
			adminApp.adminWelcomeScreen(user, sc);
			break;
		default:
			System.out.println("Choice you made does not exist. Try again:");
			userSettings(sc, bank, user);
		}
	}
	
	private void printListOfUsers(ArrayList<User> userObjects, Bank bank) {
		
		System.out.println("LIST OF USER FOR " + bank.getName().toUpperCase() + " BANK:");
		System.out.println("==========================================");
		System.out.printf("%-5s \t %-20s %-9s\n", "ID", "NAME", "STATUS");
		System.out.println("==========================================");

		for (int i = 0; i < userObjects.size(); i++) {
			if (userObjects.get(i).getBank().getId() == bank.getId() && !userObjects.get(i).isGlobalAdmin()){
				System.out.printf("%-5s \t %-20s %-9s\n", userObjects.get(i).getID(), userObjects.get(i).getFirstName() + " " + userObjects.get(i).getLastName(),
						userObjects.get(i).isActive() == true ? "ACTIVE" : "INACTIVE");
			}
		}
		System.out.println("== END OF LIST ===========================\n");
	}
	
	private void addNewUser(Scanner sc, ArrayList<User> userObjects, Bank bank) {
		FileWriterClass fileWriter = new FileWriterClass();
		boolean addNewUser = true;
		sc.nextLine();
		
		String firstName = "";
		String lastName = "";
		String city = "";
		String street = "";
		String zipCode = "";

		while (addNewUser) {
			
			System.out.println("Enter new user informations: ");
			
			while (inputValidation.isStringEmpty(firstName)){
				System.out.print("First Name: ");
				firstName = sc.nextLine();
				if (inputValidation.isStringEmpty(firstName)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}
			
			while (inputValidation.isStringEmpty(lastName)){
				System.out.print("Last Name: ");
				lastName = sc.nextLine();
				if (inputValidation.isStringEmpty(lastName)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(city)){
				System.out.print("City: ");
				city = sc.nextLine();
				if (inputValidation.isStringEmpty(city)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(street)){
				System.out.print("Street: ");
				street = sc.nextLine();
				if (inputValidation.isStringEmpty(street)) {
					System.err.println("New bank name can not be empty. Try again.");
				}
			}

			while (inputValidation.isStringEmpty(zipCode)){
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
			user.setBank(bank);;
			user.setGlobalAdmin(false);
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

			System.out.println("New user " + user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase() + " added.");

			String yesNo = "";
			boolean askAgain = true;

			while (askAgain) {
				System.out.print("\nDo you want to add new user? (\"Yes\" or \"No\"): ");
				yesNo = sc.nextLine();

				if (yesNo.equalsIgnoreCase("No")) {
					addNewUser = false;
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
	
	private void deleteUser(Scanner sc, ArrayList<User> userObjects, Bank bank, User user) {
		
		printListOfUsers(userObjects, bank);
		System.out.print("Take a look at table above and enter user ID to delete it or enter \"0\" to CANCEL: ");
		int id = -1;
		boolean askAgain = true;
		while (askAgain) {
			try {
				id = sc.nextInt();
				if (inputValidation.doesIdExist(id, bank.getId(), "users.txt", 0, 5)) {
					askAgain = false;
				} else if (id == 0) {
					userSettings(sc, bank, user);
				} else {
					System.out.print("ID you entered does not exist for " + bank.getName() + ". Try again: ");
				}
			} catch (InputMismatchException ex) {
				System.out.print("Wrong input. Integers only permitted. Try again: ");
				sc.nextLine();
			}
		}

		deleteUser(id, userObjects);

		try {
			fileWriter.writeUserObjectsToFile(userObjects);
		} catch (FileNotFoundException e) {
			System.out.print("File does not exist.");
			System.exit(1);
		}
		
	}
	
	private void deleteUser(int id, ArrayList<User> userObjects) {
		for (int i = 0; i < userObjects.size(); i++) {
			if (Integer.valueOf(userObjects.get(i).getID()) == id) {
				System.out.println("User " + userObjects.get(i).getFirstName().toUpperCase() + " "
						+ userObjects.get(i).getLastName().toUpperCase() + " deleted.");
				userObjects.remove(i);
			}
		}
	}
	
	private void changeUserActivityStatus(Scanner sc, ArrayList<User> userObjects, Bank bank, User user) {
		printListOfUsers(userObjects, bank); 
		System.out.print("Take a look at table above and enter user ID to change activity status or enter \"0\" to CANCEL: ");
		int id = -1;
		boolean askAgain = true;
		while (askAgain) {
			try {
				id = sc.nextInt();
				if (inputValidation.doesIdExist(id, bank.getId(), "users.txt", 0, 5)) {
					askAgain = false;
				} else if (id == 0) {
					userSettings(sc, bank, user);
				} else {
					System.out.print("ID you entered does not exist for " + bank.getName() + ". Try again: ");
				}
			} catch (InputMismatchException ex) {
				System.out.print("Wrong input. Integers only permitted. Try again: ");
				sc.nextLine();
			}
		}

		changeUserActivityStatus(id, userObjects);

		try {
			fileWriter.writeUserObjectsToFile(userObjects);
		} catch (FileNotFoundException e) {
			System.out.print("File does not exist.");
			System.exit(1);
		}
		
	}
	
	private void changeUserActivityStatus(int id, ArrayList<User> userObjects) {
		
		for (int i = 0; i < userObjects.size(); i++) {
			if (userObjects.get(i).getID().equals(getNextUserId(id))) {
				if (userObjects.get(i).isActive()) {
					userObjects.get(i).setActive(false);
				} else {
					userObjects.get(i).setActive(true);
				}
				
				System.out.println(
						"\nActivity status for user " + userObjects.get(i).getFirstName().toUpperCase() + " "
								+ userObjects.get(i).getLastName().toUpperCase() + " is changed to "
								+ (userObjects.get(i).isActive() == true ? "ACTIVE." : "INACTIVE."));
			}
		}
	}
}
