package data;

public class BankOffice {

	private int bankId;
	private int officeId;
	private String officeName;
	private Address address;
	private boolean isActive;
	
	public BankOffice() {
		
	}
	
	public BankOffice(int bankId, int officeId, String officeName, Address address) {
		this.bankId = bankId;
		this.officeId = officeId;
		this.officeName = officeName;
		this.address = address;
		this.isActive = true;
	}
	
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public int getOfficeId() {
		return officeId;
	}
	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "BankOffice [bankId=" + bankId + ", officeId=" + officeId + ", officeName=" + officeName + ", address="
				+ address + ", isActive=" + isActive + "]";
	}
	
	
}
