package JavaMiniProject.model;

public class Customer {

	private int customerId;
	private String name ;
	private String homePhone;
	private String cellPhone;
	private String workPhone;
	private String street;
	private String city;
	private String state;
	private int zip;
	
	public Customer() {
		this.customerId = 0;
		this.name = null;
		this.homePhone = null;
		this.cellPhone = null;
		this.workPhone = null;
		this.street = null;
		this.city = null;
		this.state = null;
		this.zip = 0;
	}
	
	public Customer(String name, String homePhone, String cellPhone, String workPhone, String street, String city, String state, int zip) {
		super();
		this.name = name;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.workPhone = workPhone;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public Customer(int customerId, String name, String homePhone, String cellPhone, String workPhone, String street, String city, String state, int zip) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.workPhone = workPhone;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getHomePhone() {
		return Long.parseLong(homePhone);
	}
	
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	
	public long getCellPhone() {
		return Long.parseLong(cellPhone);
	}
	
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public long getWorkPhone() {
		return Long.parseLong(workPhone);
	}
	
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public int getZip() {
		return zip;
	}
	
	public void setZip(int zip) {
		this.zip = zip;
	}
}
