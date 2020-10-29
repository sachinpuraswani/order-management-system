package JavaMiniProject.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import JavaMiniProject.model.Customer;
import JavaMiniProject.util.ConnectionManager;

public class CustomerDaoTest {
	
	CustomerDao target = null;
	static Connection cn = null;
	Customer c = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cn = ConnectionManager.getConnection();
		assertEquals(cn != null, true);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ConnectionManager.closeConnection();
	}

	@Before
	public void setUp() throws Exception {
		target = new CustomerDao();
		c =  new Customer(10,"Test Customer", "9898989898", "9898989898", "9898989898", "Test Street",
				"Test City", "Test State", 989898);
	}

	@After
	public void tearDown() throws Exception {
		target = null;
		deleteTestData();
		c = null;
	}
	
	private int getRecordCountMethod() throws SQLException {
		Statement stmt = cn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT count(*) from Customer");
		rs.next();
		return rs.getInt(1);
	}
	
	private int deleteTestData() throws SQLException {
		Statement stmt = cn.createStatement();
		int i = stmt.executeUpdate("DELETE FROM `Customer` WHERE `CustomerId`='"+c.getCustomerId()+"'");
		return i;
	}

	@Test
	public void testAddCustomerWithId() throws SQLException {
		int totalRecordsBefore = getRecordCountMethod() + 1;
		target.addCustomerWithId(c);
		int totalRecordsAfter = getRecordCountMethod();
		assertTrue("Record size not updated...!", totalRecordsAfter == totalRecordsBefore);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetCustomer() throws SQLException {
		Customer cust = target.getCustomer(11);
		assertNotNull(cust);
		assertEquals("Rushi",cust.getName());
		//fail("Not yet implemented");
	}
	
	@Test
	public void testGetAllCustomers() throws SQLException {
		List<Customer> custList = target.getAllCustomers();
		assertNotNull(custList);
		assertTrue("Fetched Wrong Number of Customers...!", custList.size()==getRecordCountMethod());
	}

}
