package data;

import java.util.ArrayList;

public class Bank {
	
	private int id;
	private String name;
	private Address address;
	private ArrayList<BankOffice> officies;
	private boolean isActive;
	
	public Bank() {
		
	}
	
	public Bank(int id, String name, Address address) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.isActive = true;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ArrayList<BankOffice> getOfficies() {
		return officies;
	}
	public void setOfficies(ArrayList<BankOffice> officies) {
		this.officies = officies;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "Bank [id=" + id + ", name=" + name + ", address=" + address + ", officies=" + officies + ", isActive="
				+ isActive + "]";
	}
	

}
