package validation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import data.Bank;
import data.BankOffice;
import data.User;

public class FileReaderClass {

	public ArrayList<String> readLinesFromFile(Path path) {
		ArrayList<String> records = new ArrayList<String>();

		BufferedReader reader;
		try {
			reader = Files.newBufferedReader(path);
			String line;
			
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();

			return records;
		} catch (IOException e) {
			System.err.println("File " + path.toString() + " does not exist. \nContact your system administrator.");
			System.exit(1);
			return null;
		}
	}

	public void listBanks() {
		Path path = Paths.get("banks.txt");
		ArrayList<String> records = readLinesFromFile(path);

		System.out.println("\n\nLIST OF BANKS IN SYSTEM:");
		System.out.println("--------------------------------------------------");
		System.out.printf("%-10s \t %-20s \t %-9s \n", "Bank ID", "Bank Name", "IN/ACTIVE");
		System.out.println("--------------------------------------------------");

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];
			String tempName = array[1];
			String tempActivity = array[4];

			System.out.printf("%-10s \t %-20s \t %-9s \n", tempID, tempName,
					tempActivity.equals("true") ? "active" : "inactive");
		}
		System.out.println("--------------------------------------------------");
	}

	public void listBanksAndOfficies() {
		Path path = Paths.get("banks.txt");
		ArrayList<String> records = readLinesFromFile(path);

		System.out.println("LIST OF BANKS IN SYSTEM:");
		System.out.println("======================================================================");
		System.out.printf("%-10s \t %-20s %-9s\n", "BANK ID", "BANK NAME", "STATUS");
		System.out.printf("\n%-3s   %-9s  %-20s %-24s  %-9s\n", "", "OFFICE ID", "OFFICE NAME", "ADDRESS", "STATUS");
		System.out.println("======================================================================");

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];
			String tempName = array[1];
			String tempIsActive = array[4];

			System.out.println("----------------------------------------------------------------------");
			System.out.printf("%-10s \t %-20s %-9s\n\n", tempID, tempName.toUpperCase(),
					tempIsActive.equals("true") ? "ACTIVE" : "INACTIVE");

			listOfficies(Integer.valueOf(tempID));
		}
		System.out.println("\n== END OF LIST =======================================================\n");
	}

	public void listOfficies(int bankID) {
		Path path = Paths.get("bankOfficies.txt");
		ArrayList<String> records = readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];
			String tempOfficeID = array[1];
			String tempOffName = array[2];
			String tempAddress = array[3];
			String tempIsActive = array[4];

			if (Integer.valueOf(tempID) == bankID) {
				System.out.printf("%-3s   %-9s  %-20s %-24s  %-9s\n", "", tempOfficeID, tempOffName,
						getAddress(Integer.valueOf(tempAddress)), tempIsActive.equals("true") ? "active" : "inactive");
			}
		}
	}

	public String getAddress(int addressID) {
		String str = "";
		Path path = Paths.get("addresses.txt");
		ArrayList<String> records = readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];
			String tempCity = array[1];
			String tempStreet = array[2];

			if (Integer.valueOf(tempID) == addressID) {
				str = str + tempCity + ", " + tempStreet;
			}
		}
		return str;
	}

	public int findNextIdInFile(Path path, int arrayIndex) {
		ArrayList<String> records = readLinesFromFile(path);
		int id = 0;

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[arrayIndex];

			if (Integer.valueOf(tempID) > id) {
				id = Integer.valueOf(tempID);
			}
		}
		return id + 1;
	}

	public ArrayList<Bank> createListOfBanks() {
		BankValidation bankValidation = new BankValidation();
		AddressValidation addressValidation = new AddressValidation();

		ArrayList<Bank> banks = new ArrayList<>();
		Path path = Paths.get("banks.txt");

		ArrayList<String> records = readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {

			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];
			String tempName = array[1];
			String tempAddress = array[2];
			String tempOfficies = array[3];
			String tempIsActive = array[4];

			Bank bank = new Bank();

			bank.setId(Integer.valueOf(tempID));
			bank.setName(tempName);
			bank.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
			bank.setOfficies(bankValidation.setOfficies(Integer.valueOf(tempOfficies)));
			bank.setActive(tempIsActive.equals("true") ? true : false);

			banks.add(bank);
		}
		return banks;
	}

	public void listActiveBanks(ArrayList<Bank> banks) {

		System.out.println("LIST OF ACTIVE BANKS:");
		System.out.println("====================================================================");
		System.out.printf("%-8s \t %-25s \t %-10s \n", "BANK ID", "BANK NAME", "STATUS");
		System.out.println("--------------------------------------------------------------------");
		for (int i = 0; i < banks.size(); i++) {

			if (banks.get(i).isActive() == true) {

				System.out.printf("%-8s \t %-25s \t %-10s \n", banks.get(i).getId(), banks.get(i).getName(),
						(banks.get(i).isActive()) == true ? "active" : "inactive");
			}
		}
		System.out.println("===== END OF LIST ==================================================");

	}

	public ArrayList<Bank> getAllBankOBjects() {

		AddressValidation addressValidation = new AddressValidation();
		BankValidation bankValidation = new BankValidation();
		Path path = Paths.get("banks.txt");

		ArrayList<String> records = readLinesFromFile(path);
		ArrayList<Bank> allBankObjects = new ArrayList<>();

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			Bank bank = new Bank();

			String tempID = array[0];
			String tempName = array[1];
			String tempAddress = array[2];
			String tempOfficies = array[3];
			String tempIsActive = array[4];

			bank.setId(Integer.valueOf(Integer.valueOf(tempID)));
			bank.setName(tempName);
			bank.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
			bank.setOfficies(bankValidation.setOfficies(Integer.valueOf(tempOfficies)));
			bank.setActive(tempIsActive.equals("true") ? true : false);

			allBankObjects.add(bank);
		}
		return allBankObjects;
	}

	public String getSpecificBankName(ArrayList<Bank> allBankObjects, int bankID) {//nepotrebno ce biti
		String bankName = "";
		for (int i = 0; i < allBankObjects.size(); i++) {
			if (allBankObjects.get(i).getId() == bankID) {
				bankName = bankName + allBankObjects.get(i).getName();
			}
		}
		return bankName;
	}

	public boolean getSpecificBankStatus(ArrayList<Bank> allBankObjects, int bankID) {// nepotrebno ce biti
		
		boolean activityStatus = false;

		for (int i = 0; i < allBankObjects.size(); i++) {
			if (allBankObjects.get(i).getId() == bankID && allBankObjects.get(i).isActive()) {
				activityStatus = true;
			}
		}
		return activityStatus;
	}

	public Bank getSpecificBankObject(ArrayList<Bank> allBankObjects, int bankID) {
		Bank bank = new Bank();
		for (int i = 0; i < allBankObjects.size(); i++) {
			if (allBankObjects.get(i).getId() == bankID) {
				bank = allBankObjects.get(i);
			}
		}
		return bank;
	}

	public BankOffice getSpecificBankOfficeObject(Bank bank, int officeID) {
		BankOffice bankOffice = new BankOffice();
		for (int i = 0; i < bank.getOfficies().size(); i++) {
			if (bank.getOfficies().get(i).getOfficeId() == officeID) {
				bankOffice = bank.getOfficies().get(i);
			}
		}
		return bankOffice;
	}

	public Bank getSpecificBankObject(int bankId) {
		AddressValidation addressValidation = new AddressValidation();
		BankValidation bankValidation = new BankValidation();
		Path path = Paths.get("banks.txt");
		ArrayList<String> records = readLinesFromFile(path);
		Bank bank = new Bank();
		
		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");
			
			String tempId = array[0];
			
			if (Integer.valueOf(tempId) == bankId) {
				
				String tempName = array[1];
				String tempAddress = array[2];
				String tempOfficies = array[3];
				String tempIsActive = array[4];
	
				bank.setId(Integer.valueOf(Integer.valueOf(tempId)));
				bank.setName(tempName);
				bank.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
				bank.setOfficies(bankValidation.setOfficies(Integer.valueOf(tempOfficies)));
				bank.setActive(tempIsActive.equals("true") ? true : false);
			}
		}
		return bank;
	}

	public void setAllBankOfficiesActivityStatusToFalse(ArrayList<BankOffice> officies, int bankID) {
		
		for (int i = 0; i < officies.size(); i++) {
			if (officies.get(i).getBankId() == bankID) {
				officies.get(i).setActive(false);
			}
		}
	}

	public ArrayList<BankOffice> getAllBankOfficeObjects() {
		ArrayList<BankOffice> officies = new ArrayList<>();
		AddressValidation addressValidation = new AddressValidation();
		
		Path path = Paths.get("bankOfficies.txt");
		ArrayList<String> records = readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");
			
			String tempID = array[0];
			String tempOfficeID = array[1];
			String tempName = array[2];
			String tempAddress = array[3];
			String tempIsActive = array[4];
			
			BankOffice office = new BankOffice();
			
			office.setBankId(Integer.valueOf(tempID));
			office.setOfficeId(Integer.valueOf(tempOfficeID));
			office.setOfficeName(tempName);
			office.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
			office.setActive(tempIsActive.equals("true") ? true : false);

			officies.add(office);
		}
		return officies;
	}
	
	public ArrayList<User> getUserObjects() {
		
		ArrayList<User> userObjects= new ArrayList<>();
		AddressValidation addressValidation = new AddressValidation();
		BankValidation bankValidation = new BankValidation();
		
		Path path = Paths.get("users.txt");
		ArrayList<String> records = readLinesFromFile(path);
		
		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");
			
			String tempId = array[0];
			String tempFirstName = array[1];
			String tempLastName = array[2];
			String tempPassword = array[3];
			String tempAddress = array[4];
			String tempBank = array[5];
			String tempIsGlobalAdmin = array[6];
			String tempIsAtmOperator = array[7];
			String tempIsActive = array[8];
			
			User user = new User();
			
			user.setID(tempId);
			user.setFirstName(tempFirstName);
			user.setLastName(tempLastName);
			user.setPassword(tempPassword);
			user.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
			user.setBank(bankValidation.setBank(Integer.valueOf(tempBank)));
			user.setGlobalAdmin(tempIsGlobalAdmin.equals("true") ? true : false);
			user.setAtmOperator(tempIsAtmOperator.equals("true") ? true : false);
			user.setActive(tempIsActive.equals("true") ? true : false);

			userObjects.add(user);
		}
		return userObjects;
	}
}
