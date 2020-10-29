package JavaMiniProject.service;

import java.util.List;

import JavaMiniProject.dao.CustomerDao;
import JavaMiniProject.model.Customer;

public class CustomerService {

	CustomerDao dao = new CustomerDao();
	
	public void addCustomer(Customer c) {
		dao.addCustomer(c);
	}
	
	public boolean getCust(int customerId) {
		if(getCustomer(customerId) == null)
			return false;
		return true;
	}
	
	public Customer getCustomer(int customerId) {
		return dao.getCustomer(customerId);
	}
	
	public List<Customer> getAllCustomers() {
		return dao.getAllCustomers();
	}
	
	public void deleteCustomer(int customerId) {
		dao.deleteCustomer(customerId);
	}
	
	public void displayCustomer(Customer c) {
		System.out.println("\nCustomer Id: "+c.getCustomerId());
		System.out.println("Customer Name: "+c.getName());
		System.out.println("Home Phone: "+c.getHomePhone());
		System.out.println("Cell Phone: "+c.getCellPhone());
		System.out.println("Work Phone: "+c.getWorkPhone());
		System.out.println("Street: "+c.getStreet());
		System.out.println("City: "+c.getCity());
		System.out.println("State: "+c.getState());
		System.out.println("Zip Code: "+c.getZip());
	}
}
