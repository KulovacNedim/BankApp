package validation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import data.Address;

public class AddressValidation {
	
	private FileReaderClass fileReader = new FileReaderClass();

	public Address getAddress(int addressID) {
		Address address = new Address();
		Path path = Paths.get("addresses.txt");
		ArrayList<String> records = fileReader.readLinesFromFile(path);

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[0];

			if (Integer.valueOf(tempID) == addressID) {
				String tempCity = array[1];
				String tempStreet = array[2];
				String tempPostZip = array[3];

				address.setId(Integer.valueOf(addressID));
				address.setCity(tempCity);
				address.setStreet(tempStreet);
				address.setPostZip(tempPostZip);
			}
		}
		return address;
	}
}
