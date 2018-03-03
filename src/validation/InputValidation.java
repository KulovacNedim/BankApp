package validation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InputValidation {
	private FileReaderClass fileReader = new FileReaderClass();
	
	public boolean isStringEmpty(String string) {
		if (string.equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean doesIdExist(int id, String file, int index) {
		Path path = Paths.get(file);
		ArrayList<String> records = fileReader.readLinesFromFile(path);
		boolean flag = false;

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String tempID = array[index];

			if (Integer.valueOf(tempID) == id) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public boolean doesIdExist(int idSuper, int idSub, String file, int indexSuper, int indexSub) {
		Path path = Paths.get(file);
		ArrayList<String> records = fileReader.readLinesFromFile(path);
		boolean flag = false;

		for (int i = 0; i < records.size(); i++) {
			String tempLine = records.get(i);
			String[] array = tempLine.split("<>");

			String superID = array[indexSuper];
			String subID = array[indexSub];

			if (Integer.valueOf(superID) == idSuper && Integer.valueOf(subID) == idSub) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
