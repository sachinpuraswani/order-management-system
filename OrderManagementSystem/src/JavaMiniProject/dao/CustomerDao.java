package JavaMiniProject.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import JavaMiniProject.model.Customer;
import JavaMiniProject.util.ConnectionManager;

public class CustomerDao {

	Connection cn = ConnectionManager.getConnection();
	
	private static final Logger logger = Logger.getLogger(CustomerDao.class.getName());
	
	static {
		try {
			File dir = new File("logs/");
			if(!dir.exists())
				dir.mkdirs();
			logger.addHandler(new FileHandler("logs/CustomerLogs"));
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Add Customer to Database
	public void addCustomer(Customer c) {
		try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO Customer(Name,HomePhone,CellPhone,WorkPhone,Street,City,State,Zip) values(?,?,?,?,?,?,?,?)");) {
			pstmt.setString(1, c.getName());
			pstmt.setLong(2, c.getHomePhone());
			pstmt.setLong(3, c.getCellPhone());
			pstmt.setLong(4, c.getWorkPhone());
			pstmt.setString(5, c.getStreet());
			pstmt.setString(6, c.getCity());
			pstmt.setString(7, c.getState());
			pstmt.setInt(8, c.getZip());
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nCustomer "+c.getName()+" added...!");
				logger.info("\nCustomer added successfully...! \nCustomer details : " + c);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Add Customer to Database(Customer ID explicitly given)
	public void addCustomerWithId(Customer c) {
		try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO Customer values(?,?,?,?,?,?,?,?,?)");) {
			pstmt.setInt(1, c.getCustomerId());
			pstmt.setString(2, c.getName());
			pstmt.setLong(3, c.getHomePhone());
			pstmt.setLong(4, c.getCellPhone());
			pstmt.setLong(5, c.getWorkPhone());
			pstmt.setString(6, c.getStreet());
			pstmt.setString(7, c.getCity());
			pstmt.setString(8, c.getState());
			pstmt.setInt(9, c.getZip());
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nCustomer "+c.getName()+" added...!");
				logger.info("\nCustomer added successfully...! \nCustomer details : " + c);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Get Customer details
	public Customer getCustomer(int customerId) {
		String qry="SELECT * from Customer where CustomerId=?";
		Customer c = null;
		try (PreparedStatement pstmt = cn.prepareStatement(qry)) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				c = new Customer();		
				c.setCustomerId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setHomePhone(String.valueOf(rs.getLong(3)));
				c.setCellPhone(String.valueOf(rs.getLong(4)));
				c.setWorkPhone(String.valueOf(rs.getLong(5)));
				c.setStreet(rs.getString(6));
				c.setCity(rs.getString(7));
				c.setState(rs.getString(8));
				c.setZip(rs.getInt(9));
				logger.info("\nCustomer fetched successfully...! \nCustomer details : " + c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
	
	// Get All Customers
	public List<Customer> getAllCustomers() {
		List<Customer> custList = new ArrayList<Customer>();
		Customer c = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from Customer");) {
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				c = new Customer();
				c.setCustomerId(rs.getInt(1));
				c.setName(rs.getString(2));
				c.setHomePhone(String.valueOf(rs.getLong(3)));
				c.setCellPhone(String.valueOf(rs.getLong(4)));
				c.setWorkPhone(String.valueOf(rs.getLong(5)));
				c.setStreet(rs.getString(6));
				c.setCity(rs.getString(7));
				c.setState(rs.getString(8));
				c.setZip(rs.getInt(9));
				custList.add(c);
			}
			logger.info("\nFetched all customers...! \nCustomer list : "+custList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return custList;
	}
	
	//Delete Customer
	public void deleteCustomer(int customerId) {
		try(PreparedStatement pstmt = cn.prepareStatement("DELETE from Customer where CustomerId=?");) {
			pstmt.setInt(1, customerId);
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nCustomer "+customerId+" Deleted (Along with all its Purchase Orders and Order Items)...!");
				logger.info("\nCustomer "+customerId+" deleted successfully...!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
