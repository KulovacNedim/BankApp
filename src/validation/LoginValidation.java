package validation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import data.User;

public class LoginValidation {

	private FileReaderClass fileReader = new FileReaderClass();
	private AddressValidation addressValidation = new AddressValidation();
	private BankValidation bankValidation = new BankValidation();

	public void validateLogin(String userID, String pass, User user) {
		Path path = Paths.get("users.txt");
		ArrayList<String> records = fileReader.readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];
			String tempPass = array[3];

			if (tempID.equals(userID) && tempPass.equals(pass)) {
				String tempFirstName = array[1];
				String tempLastName = array[2];
				String tempAddress = array[4];
				String tempBank = array[5];
				String tempIsGlobalAdmin = array[6];
				String tempIsAtmOperator = array[7];

				user.setID(tempID);
				user.setFirstName(tempFirstName);
				user.setLastName(tempLastName);
				user.setPassword(tempPass);
				user.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
				user.setBank(bankValidation.setBank(Integer.valueOf(tempBank)));
				user.setGlobalAdmin(tempIsGlobalAdmin.equals("true") ? true : false);
				user.setAtmOperator(tempIsAtmOperator.equals("true") ? true : false);
			}
		}
	}

	public String getNewPassword() {
		Random rnd = new Random();

		String password = "";
		boolean valid = false;
		while (!valid) {
			boolean isNum = rnd.nextBoolean();

			if (isNum) {
				password += (rnd.nextInt(10));
			} else {
				char ch = (char) (rnd.nextInt(26) + 'a');
				boolean isUpperCase = rnd.nextBoolean();
				if (isUpperCase) {
					password += Character.toUpperCase(ch);
				} else {
					password += ch;
				}
			}
			valid = isPasswordValid(password);
		}
		return password;
	}

	private boolean isPasswordValid(String password) {
		int numCounter = 0;
		int uppercaseCounter = 0;
		boolean unique = false;

		for (int i = 0; i < password.length(); i++) {
			if (Character.isDigit(password.charAt(i))) {
				numCounter++;
			} else if (Character.isUpperCase(password.charAt(i))) {
				uppercaseCounter++;
			}
		}

		unique = isPasswordUnique(password);

		if (numCounter >= 3 && uppercaseCounter >= 1 && password.length() == 10 && unique) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isPasswordUnique(String password) {
		boolean unique = true;
		Path path = Paths.get("users.txt");
		ArrayList<String> records = fileReader.readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempPass = array[3];

			if (tempPass.equals(password)) {
				unique = false;
				break;
			}
		}
		return unique;
	}

	public String getNewUserId() {
		Random rnd = new Random();
		String id = "";

		boolean valid = false;
		while (!valid) {
			id += 9999 + (rnd.nextInt(10000));
			valid = isIdUnique(id);
		}
		return id;
	}

	private boolean isIdUnique(String id) {
		boolean unique = true;
		Path path = Paths.get("users.txt");
		ArrayList<String> records = fileReader.readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempId = array[0];

			if (tempId.equals(id)) {
				unique = false;
				break;
			}
		}
		return unique;
	}
}
