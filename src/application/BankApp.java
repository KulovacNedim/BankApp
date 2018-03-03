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
import data.BankOffice;
import data.User;
import utils.BankAppMenusAndMessages;
import validation.AddressValidation;
import validation.BankValidation;
import validation.FileReaderClass;
import validation.FileWriterClass;
import validation.InputValidation;

public class BankApp {

	private BankValidation bankValidation = new BankValidation();
	private BankAppMenusAndMessages bankAppMenusAndMessages = new BankAppMenusAndMessages();
	private FileWriterClass fileWriter = new FileWriterClass();
	private AddressValidation addressValidation = new AddressValidation();
	private FileReaderClass fileReader = new FileReaderClass();
	private InputValidation inputValidation = new InputValidation();

	public void chooseBank(Bank bank, User user, Scanner sc) {
		fileReader.listBanks();
		setBankObject(bank, sc);
		System.out.println("\n--------------------------------------------------");
		System.out.println("You are logged in " + bank.getName().toUpperCase() + " system.");
		System.out.println("--------------------------------------------------");
	}

	public void setBankObject(Bank bank, Scanner sc) {

		int bankID = 0;
		boolean print = true;
		while (print) {
			try {
				System.out.println("\nSelect bank (ID) you want to administrate: ");
				System.out.print("Your choice: ");
				bankID = sc.nextInt();

				boolean exist = bankValidation.doesActiveIdExist(bankID, Paths.get("banks.txt"), 0, 4);

				if (exist) {
					print = false;
				} else {
					System.out
							.println("\nWrong input. Bank ID you entered does not exist or is not active. Try again.");
				}
			} catch (InputMismatchException ex) {
				System.err.println("Wrong input! Integers only permitted. Try again!");
				sc.nextLine();
			}
		}

		Path path = Paths.get("banks.txt");
		ArrayList<String> records = fileReader.readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];

			if (Integer.valueOf(tempID) == bankID) {
				String tempName = array[1];
				String tempAddress = array[2];
				String tempOfficies = array[3];

				bank.setId(Integer.valueOf(tempID));
				bank.setName(tempName);
				bank.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
				bank.setOfficies(bankValidation.setOfficies(Integer.valueOf(tempOfficies)));
			}
		}
	}

	public void bankSettings(Scanner sc, boolean loggedInSpecificBank, User user) {
		AdminApp adminApp = new AdminApp();
		bankAppMenusAndMessages.printHeader(user, "BANK SETTINGS MENU");
		System.out.println();
		bankAppMenusAndMessages.printBankSettingsOptions();
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
			bankAppMenusAndMessages.printHeader(user, "BANK AND OFFICIES OVERVIEW MENU");
			System.out.println("\n");
			fileReader.listBanksAndOfficies();
			bankSettings(sc, loggedInSpecificBank, user);
			break;
		case 2:
			bankAppMenusAndMessages.printHeader(user, "ADD NEW BANK AND OFFICIES MENU");
			System.out.println("");
			addNewBank(sc);
			bankSettings(sc, loggedInSpecificBank, user);
			break;
		case 3:
			bankAppMenusAndMessages.printHeader(user, "ADD NEW OFFICE TO EXISTING BANK MENU");
			System.out.println("You can add bank office to some of active banks.\n");
			addNewOffice(user, sc);
			bankSettings(sc, loggedInSpecificBank, user);
			break;
		case 4:
			bankAppMenusAndMessages.printHeader(user, "CHANGE BANKS AND OFFICIES ACTIVITY STATUS");
			changeBankActivityStatus(sc, loggedInSpecificBank, user);
			break;
		case 5:
			adminApp.adminWelcomeScreen(user, sc);
			break;
		default:
			System.err.println("\nYou made choice that does not exist. Try again:");
			bankSettings(sc, loggedInSpecificBank, user);
		}

	}

	private void addNewBank(Scanner sc) {
		sc.nextLine();
		String name = "";
		boolean validString = true;

		while (validString) {
			System.out.print("Enter new bank name: ");
			name = sc.nextLine();

			validString = inputValidation.isStringEmpty(name);

			if (validString) {
				System.err.println("New bank name can not be empty. Try again.");
			}
		}

		System.out.println("\nEnter headquater address info: ");
		System.out.print("City: ");
		String city = sc.nextLine();

		System.out.print("Street: ");
		String street = sc.nextLine();

		System.out.print("Zip Code: ");
		String zipCode = sc.nextLine();

		Path addressesPath = Paths.get("addresses.txt");
		int addressId = fileReader.findNextIdInFile(addressesPath, 0);
		Address address = new Address(addressId, city, street, zipCode);

		try {
			fileWriter.writeAddressObjectToFile(addressesPath, address);
		} catch (IOException e) {
			System.err.println("Error. File does not exist.");
		}

		Path banksPath = Paths.get("banks.txt");
		int bankId = fileReader.findNextIdInFile(banksPath, 0);

		Bank addBank = new Bank(bankId, name, address);
		try {
			fileWriter.writeBankObjectToFile(banksPath, addBank);
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean addNewOffice = true;

		do {
			System.out.print("\nAdd new office for created bank? Enter Yes or No: ");
			String yesNoOpt = sc.nextLine();

			if (yesNoOpt.equalsIgnoreCase("No")) {
				addNewOffice = false;
			} else if (yesNoOpt.equalsIgnoreCase("Yes")) {
				Path officePath = Paths.get("bankOfficies.txt");
				int officeId = fileReader.findNextIdInFile(officePath, 1);

				System.out.print("\nEnter bank office name: ");
				String officeName = sc.nextLine();

				System.out.println("\nEnter office address info: ");
				System.out.print("City: ");
				String cityOffice = sc.nextLine();

				System.out.print("Street: ");
				String streetOffice = sc.nextLine();

				System.out.print("Zip Code: ");
				String zipCodeOffice = sc.nextLine();

				Path addressesOfficePath = Paths.get("addresses.txt");
				int addressOfficeId = fileReader.findNextIdInFile(addressesOfficePath, 0);

				Address officeAddress = new Address(addressOfficeId, cityOffice, streetOffice, zipCodeOffice);
				try {
					fileWriter.writeAddressObjectToFile(addressesPath, officeAddress);
				} catch (IOException e) {
					e.printStackTrace();
				}

				BankOffice bankOffice = new BankOffice(bankId, officeId, officeName, officeAddress);
				try {
					fileWriter.writeObjectToFile(officePath, bankOffice);
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println(
						"\nAdded office" + bankOffice.getOfficeName() + " for bank " + addBank.getName() + ".");
			} else {
				System.out.print("\nWrong input. \"Yes\" or \"No\" expected. Try again.");
			}
		} while (addNewOffice);
	}

	private void addNewOffice(User user, Scanner sc) {
		ArrayList<Bank> banks = fileReader.createListOfBanks();
		fileReader.listActiveBanks(banks);

		int choice = 0;
		boolean print = true;
		do {
			try {
				System.out.print("\nEnter ID of bank to add office to it: ");
				choice = sc.nextInt();
				boolean exist = bankValidation.doesExist(banks, choice);
				if (exist) {
					print = false;
				} else {
					System.out.println("You made choice that does not exist. Try again.");
				}
			} catch (InputMismatchException e) {
				System.out.print("Wrong input. Integers only permited. Try Again.\n ");
				sc.nextLine();
			}
		} while (print);

		boolean printAgain = true;

		do {
			sc.nextLine();
			System.out.print("\nEnter office name: ");
			String officeName = sc.nextLine();

			Path officePath = Paths.get("bankOfficies.txt");
			int officeId = fileReader.findNextIdInFile(officePath, 1);

			System.out.println("Enter address details: ");
			System.out.print("City: ");
			String city = sc.nextLine();
			System.out.print("Street ");
			String street = sc.nextLine();
			System.out.print("Code zip: ");
			String zipCode = sc.nextLine();

			Path addressPath = Paths.get("addresses.txt");
			int addressId = fileReader.findNextIdInFile(addressPath, 0);

			Address officeAddress = new Address(addressId, city, street, zipCode);
			try {
				fileWriter.writeAddressObjectToFile(addressPath, officeAddress);
			} catch (IOException e) {
				System.err.println("Error. File does not exist.");
			}

			BankOffice office = new BankOffice(choice, officeId, officeName, officeAddress);
			try {
				fileWriter.writeObjectToFile(officePath, office);
			} catch (IOException e) {
				System.err.println("Error. File does not exist.");
			}

			System.out.println("\nOffice " + office.getOfficeName() + " added.");

			System.out.print("\nDo you want to add new office to this bank? Enter \"Yes\" or \"No\": ");
			String yesNo = sc.next();

			while (!(yesNo.equalsIgnoreCase("yes") || yesNo.equalsIgnoreCase("no"))) {
				System.out.println("\nWrong input. \"Yes\" or \"No\" expected. Try again.");

				System.out.print("\nDo you want to add new office to this bank? Enter \"Yes\" or \"No\": ");
				yesNo = sc.next();
			}
			if (yesNo.equalsIgnoreCase("yes")) {
				System.out.println();
				addNewOffice(user, sc);
			}
			printAgain = false;
		} while (printAgain);
	}

	private void changeBankActivityStatus(Scanner sc, boolean loggedInSpecificBank, User user) {
		System.out.println("Take a look at list and then choose what to do.\n");
		fileReader.listBanksAndOfficies();

		System.out.println("What do you want to do: ");
		System.out.println("   1) Change bank activity");
		System.out.println("   2) Change office activity");
		System.out.println("   3) Cancel");

		System.out.print("\nYour choice: ");
		int choiceSub = 0;
		boolean askForInput = true;
		while (askForInput) {
			try {
				choiceSub = sc.nextInt();
				if (choiceSub == 1 || choiceSub == 2 || choiceSub == 3) {
					askForInput = false;
				} else {
					System.out.println("You made choice that does not exist. Try again." + "\nEnter your choice: ");
				}
			} catch (InputMismatchException ex) {
				System.err.println("Wrong input. integers only permitted. Try again.");
				System.out.print("\nYour choice:");
				sc.nextLine();
			}
		}

		switch (choiceSub) {
		case 1:
			changeSpecificBankActivityStatus(sc, loggedInSpecificBank, user);
			bankSettings(sc, loggedInSpecificBank, user);
			break;
		case 2:
			changeSpecificBankOfficeActivityStatus(sc, loggedInSpecificBank, user);
			bankSettings(sc, loggedInSpecificBank, user);
			break;
		case 3:
			System.out.println("\nOperation canceled. Back to Bank Settings Menu.");
			bankSettings(sc, loggedInSpecificBank, user);
			break;
		default:

		}
	}

	private void changeSpecificBankActivityStatus(Scanner sc, boolean loggedInSpecificBank, User user) {
		System.out.print("Enter bank ID from list above to make change to it or enter \"0\" to CANCEL: ");
		int bankId = 0;
		boolean askForInputCba = true;
		while (askForInputCba) {
			try {
				bankId = sc.nextInt();
				if (inputValidation.doesIdExist(bankId, "banks.txt", 0)) {
					askForInputCba = false;
				} else if (bankId == 0) {
					bankSettings(sc, loggedInSpecificBank, user);
				} else {
					System.out.print("You made choice that does not exist. Try again." + "\nEnter your choice: ");
				}

			} catch (InputMismatchException ex) {
				System.err.println("Wrong input. integers only permitted. Try again.");
				System.out.print("\nYour choice: ");
				sc.nextLine();
			}
		}

		ArrayList<Bank> allBankObjects = fileReader.getAllBankOBjects();
		ArrayList<BankOffice> officies = fileReader.getAllBankOfficeObjects();

		System.out.println("\nYou have changed " + fileReader.getSpecificBankName(allBankObjects, bankId).toUpperCase()
				+ " status to "
				+ (fileReader.getSpecificBankStatus(allBankObjects, bankId) == true ? "INACTIVE" : "ACTIVE") + ". ");
		System.out.println(fileReader.getSpecificBankStatus(allBankObjects, bankId) == true
				? ("All oficies " + "are set to INACTIVE too.") : "");

		changeBankActivityStatus(allBankObjects, officies, bankId);

		try {
			fileWriter.writeBankObjectsToFile(allBankObjects);
		} catch (FileNotFoundException e) {
			System.err.println("Error. File not found.");
		}
		try {
			fileWriter.writeBankOfficeObjectsToFile(officies);
		} catch (FileNotFoundException e) {
			System.err.println("Error. File not found.");
		}

		if (fileReader.getSpecificBankObject(allBankObjects, bankId).isActive()
				&& fileReader.getSpecificBankObject(allBankObjects, bankId).getOfficies().size() != 0) {
			changeBankOfficeObjectActivityStatus(allBankObjects, officies, user, loggedInSpecificBank, bankId, sc);
		}
	}

	private void changeBankOfficeObjectActivityStatus(ArrayList<Bank> allBankObjects, ArrayList<BankOffice> officies,
			User user, boolean loggedInSpecificBank, int bankId, Scanner sc) {
		System.out.print(
				"Do you want to change activity status of " + fileReader.getSpecificBankName(allBankObjects, bankId)
						+ " some office?  \nEnter \"Yes\" or \"No\": ");

		String officeCba = sc.next();

		while (!(officeCba.equalsIgnoreCase("yes") || officeCba.equalsIgnoreCase("no"))) {
			System.out.println("\nWrong input. \"Yes\" or \"No\" expected. Try again.");

			System.out.print("\nDo you want to add new office to this bank? Enter \"Yes\" or \"No\": ");
			officeCba = sc.next();
		}

		if (officeCba.equalsIgnoreCase("yes")) {
			System.out.println();
			int officeID = 0;
			boolean askForOffice = true;
			while (askForOffice) {
				try {
					System.out.print("Enter office ID to change activity status of: ");
					officeID = sc.nextInt();
					if (inputValidation.doesIdExist(bankId, officeID, "bankOfficies.txt", 0, 1)) {
						askForOffice = false;
					} else {
						System.out.println("Office ID " + officeID + " does not exist for this bank. Try again.");
					}
				} catch (InputMismatchException ex) {
					System.out.println("Wrong input. Integer expected.");
					sc.nextLine();
				}
			}
			changeBankOfficeActivityStatus(officies, bankId, officeID);
			try {
				fileWriter.writeBankOfficeObjectsToFile(officies);
			} catch (FileNotFoundException e) {
				System.err.println("Error. File not found.");
			}

			System.out
					.println(
							"\nYou have changed " + fileReader.getSpecificBankObject(allBankObjects, bankId).getName()
									+ ", "
									+ fileReader
											.getSpecificBankOfficeObject(
													fileReader.getSpecificBankObject(allBankObjects, bankId), officeID)
											.getOfficeName()
									+ " to "
									+ (fileReader
											.getSpecificBankOfficeObject(
													fileReader.getSpecificBankObject(allBankObjects, bankId), officeID)
											.isActive() == true ? "INACTIVE" : "ACTIVE")
									+ ".");
			bankSettings(sc, loggedInSpecificBank, user);
		} else {
			System.out.println("\nChanges saved. Exiting to BANK SETTINGS MENU.");
			bankSettings(sc, loggedInSpecificBank, user);
		}

	}

	private void changeBankActivityStatus(ArrayList<Bank> allBankObjects, ArrayList<BankOffice> officies, int bankID) {

		for (int i = 0; i < allBankObjects.size(); i++) {
			if (allBankObjects.get(i).getId() == bankID) {
				if (allBankObjects.get(i).isActive() == true) {
					allBankObjects.get(i).setActive(false);
					fileReader.setAllBankOfficiesActivityStatusToFalse(officies, bankID);
				} else {
					allBankObjects.get(i).setActive(true);
				}
			}
		}

	}

	private void changeBankOfficeActivityStatus(ArrayList<BankOffice> officies, int bankID, int officeID) {
		for (int i = 0; i < officies.size(); i++) {
			if (officies.get(i).getBankId() == bankID && officies.get(i).getOfficeId() == officeID) {
				if (officies.get(i).isActive()) {
					officies.get(i).setActive(false);
				} else {
					officies.get(i).setActive(true);
				}
			}
		}

	}

	private void changeSpecificBankOfficeActivityStatus(Scanner sc, boolean loggedInSpecificBank, User user) {

		ArrayList<Bank> allBankObjects = fileReader.getAllBankOBjects();
		ArrayList<BankOffice> officies = fileReader.getAllBankOfficeObjects();

		int bankId = 0;
		boolean flag = true;
		while (flag) {
			try {
				System.out.print("\nEnter bank ID to make activity status change for it's office: ");
				bankId = sc.nextInt();
				if (inputValidation.doesIdExist(bankId, "banks.txt", 0)) {
					flag = false;
				} else {
					System.out.println("Bank ID " + bankId + " does not exist. Try again.");
				}

			} catch (InputMismatchException ex) {
				System.out.println("Wrong input. Integer expected. Try again.");
				sc.nextLine();
			}
		}

		int officeId = 0;
		boolean flag2 = true;
		while (flag2) {
			try {
				System.out.print("\nEnter office ID from list above to make change to it or enter \"0\" to CANCEL: ");
				officeId = sc.nextInt();
				if (inputValidation.doesIdExist(bankId, officeId, "bankOfficies.txt", 0, 1)) {
					flag2 = false;
				} else if (officeId == 0) {
					System.out.println("Operation canceled. Back to Bank Settings Menu.");
					changeBankActivityStatus(sc, loggedInSpecificBank, user);
				} else {
					System.out.println("Office ID " + officeId + " does not exist for bank "
							+ fileReader.getSpecificBankObject(allBankObjects, bankId).getName() + ". Try again.");
				}
			} catch (InputMismatchException ex) {
				System.out.println("Wrong input. Integer expected.");
			}
		}

		changeBankOfficeActivityStatus(officies, bankId, officeId);
		try {
			fileWriter.writeBankOfficeObjectsToFile(officies);
		} catch (FileNotFoundException e) {
			System.err.println("Error. File not found.");
		}

		System.out.println(
				"\nYou have changed " + fileReader.getSpecificBankObject(allBankObjects, bankId).getName() + ", "
						+ fileReader.getSpecificBankOfficeObject(
								fileReader.getSpecificBankObject(allBankObjects, bankId), officeId).getOfficeName()
						+ " to "
						+ (fileReader.getSpecificBankOfficeObject(
								fileReader.getSpecificBankObject(allBankObjects, bankId), officeId).isActive() == true
										? "INACTIVE" : "ACTIVE")
						+ ".");
	}
}
