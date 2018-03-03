package data;

public class Address {
	
	private int id;
	private String city;
	private String street;
	private String postZip;
	
	public Address() {
		
	}
	
	public Address(int id, String city, String street, String zipCode) {
		this.id = id;
		this.city = city;
		this.street = street;
		this.postZip = zipCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostZip() {
		return postZip;
	}

	public void setPostZip(String postZip) {
		this.postZip = postZip;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", street=" + street + ", postZip=" + postZip + "]";
	}
	
	
}
