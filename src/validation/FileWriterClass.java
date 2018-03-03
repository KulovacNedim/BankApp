package validation;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import data.Address;
import data.Bank;
import data.BankOffice;
import data.User;

public class FileWriterClass {

	public void writeAddressObjectToFile(Path path, Address address) throws IOException {
		if (!Files.exists(path)) {
			Files.createFile(path);
		}

		try (PrintWriter output = new PrintWriter(new FileWriter(path.toString(), true))) {
			output.println(address.getId() + "<>" + address.getCity() + "<>" + address.getStreet() + "<>"
					+ address.getPostZip());
		} catch (Exception e) {
			System.err.println("Error. File does not exist.");
		}
	}

	public void writeBankObjectToFile(Path path, Bank bank) throws IOException {
		if (!Files.exists(path)) {
			Files.createFile(path);
		}

		try (PrintWriter output = new PrintWriter(new FileWriter(path.toString(), true))) {
			output.println(bank.getId() + "<>" + bank.getName() + "<>" + bank.getAddress().getId() + "<>" + bank.getId()
					+ "<>" + bank.isActive());
		} catch (Exception e) {
			System.err.println("Error. File does not exist.");
		}
	}

	public void writeObjectToFile(Path path, BankOffice office) throws IOException {
		if (!Files.exists(path)) {
			Files.createFile(path);
		}

		try (PrintWriter output = new PrintWriter(new FileWriter(path.toString(), true))) {
			output.println(office.getBankId() + "<>" + office.getOfficeId() + "<>" + office.getOfficeName() + "<>"
					+ office.getAddress().getId() + "<>" + office.isActive());
		} catch (Exception e) {
			System.err.println("Error. File does not exist.");
		}
	}

	public void writeBankObjectsToFile(ArrayList<Bank> allBankObjects) throws FileNotFoundException {
		String file = "banks.txt";
		Path path = Paths.get(file);

		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				System.err.println("Error. File not found.");
			}
		}

		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();

		for (int i = 0; i < allBankObjects.size(); i++) {
			try (PrintWriter output = new PrintWriter(new FileWriter(path.toString(), true))) {
				output.println(allBankObjects.get(i).getId() + "<>" + allBankObjects.get(i).getName() + "<>"
						+ allBankObjects.get(i).getAddress().getId() + "<>" + allBankObjects.get(i).getId() + "<>"
						+ allBankObjects.get(i).isActive());
			} catch (Exception e) {
				System.err.println("Error. File does not exist.");
			}
		}
	}

	public void writeBankOfficeObjectsToFile(ArrayList<BankOffice> officies) throws FileNotFoundException {
		String file = "bankOfficies.txt";
		Path path = Paths.get(file);

		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				System.err.println("Error. File not found.");
			}
		}

		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
		
		for (int i = 0; i < officies.size(); i++) {
			try (PrintWriter output = new PrintWriter(new FileWriter(path.toString(), true))) {
				output.println(officies.get(i).getBankId() + "<>" + officies.get(i).getOfficeId() + "<>" + officies.get(i).getOfficeName() + "<>"
						+ officies.get(i).getAddress().getId() + "<>" + officies.get(i).isActive());
			} catch (Exception e) {
				System.err.println("Error. File does not exist.");
			}
		}
	}

	public void writeUserObjectsToFile(ArrayList<User> userObjects) throws FileNotFoundException {
		String file = "users.txt";
		Path path = Paths.get(file);

		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				System.err.println("Error. File not found.");
			}
		}

		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();
		
		for (int i = 0; i < userObjects.size(); i++) {
			try (PrintWriter output = new PrintWriter(new FileWriter(path.toString(), true))) {
				output.println(userObjects.get(i).getID() + "<>" + userObjects.get(i).getFirstName() + "<>" + userObjects.get(i).getLastName() + "<>"
						+ userObjects.get(i).getPassword() + "<>" + userObjects.get(i).getAddress().getId() + "<>"
						+ userObjects.get(i).getBank().getId() + "<>" + userObjects.get(i).isGlobalAdmin() + "<>"
						+ userObjects.get(i).isAtmOperator() + "<>" + userObjects.get(i).isActive());
			} catch (Exception e) {
				System.err.println("Error. File does not exist.");
			}
		}
	}
}
