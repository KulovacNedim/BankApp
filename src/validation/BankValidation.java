package validation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import data.Bank;
import data.BankOffice;

public class BankValidation {

	private AddressValidation addressValidation = new AddressValidation();
	private FileReaderClass fileReader = new FileReaderClass();

	public Bank setBank(int bankID) {
		Bank bank = new Bank();
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
				String tempIsActive = array[4];

				bank.setId(Integer.valueOf(bankID));
				bank.setName(tempName);
				bank.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
				bank.setOfficies(setOfficies(Integer.valueOf(tempOfficies)));
				bank.setActive(tempIsActive.equals("true") ? true : false);
			}
		}
		return bank;
	}

	public ArrayList<BankOffice> setOfficies(int bankID) {
		Path path = Paths.get("bankOfficies.txt");
		ArrayList<String> records = fileReader.readLinesFromFile(path);
		ArrayList<BankOffice> officies = new ArrayList<>();

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];

			if (Integer.valueOf(tempID) == bankID) {
				BankOffice office = new BankOffice();

				String tempOfficeID = array[1];
				String tempName = array[2];
				String tempAddress = array[3];
				String tempIsActive = array[4];

				office.setBankId(Integer.valueOf(tempID));
				office.setOfficeId(Integer.valueOf(tempOfficeID));
				office.setOfficeName(tempName);
				office.setAddress(addressValidation.getAddress(Integer.valueOf(tempAddress)));
				office.setActive(tempIsActive.equals("true") ? true : false);

				officies.add(office);
			}
		}
		return officies;
	}

	public boolean doesActiveIdExist(int id, Path path, int arrayIndexForId, int arrayIndexForActivity) {
		ArrayList<String> records = fileReader.readLinesFromFile(path);
		boolean flag = false;
		
		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[arrayIndexForId];
			String tempActivity = array[arrayIndexForActivity];

			if (Integer.valueOf(tempID) == id && tempActivity.equals("true")) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public boolean doesExist(ArrayList<Bank> banks, int id) {
		boolean flag = false;

		for (int i = 0; i < banks.size(); i++) {
			if (banks.get(i).isActive() == true && banks.get(i).getId() == id) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
