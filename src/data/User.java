package data;

public class User {
	
	private String ID; 
	private String firstName;
	private String lastName;
	private String password;
	private Address address;
	private Bank bank;
	private boolean isGlobalAdmin;
	private boolean isAtmOperator;
	private boolean isActive;
	
	public User() {
		
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public boolean isGlobalAdmin() {
		return isGlobalAdmin;
	}
	public void setGlobalAdmin(boolean isGlobalAdmin) {
		this.isGlobalAdmin = isGlobalAdmin;
	}
	public boolean isAtmOperator() {
		return isAtmOperator;
	}
	public void setAtmOperator(boolean isAtmOperator) {
		this.isAtmOperator = isAtmOperator;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", address=" + address + ", bank=" + bank + ", isGlobalAdmin=" + isGlobalAdmin + ", isAtmOperator="
				+ isAtmOperator + ", isActive=" + isActive + "]";
	}
	

}
