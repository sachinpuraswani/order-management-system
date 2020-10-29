package JavaMiniProject.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import JavaMiniProject.model.PurchaseOrder;
import JavaMiniProject.util.ConnectionManager;

public class PurchaseOrderDaoTest {
	
	PurchaseOrderDao target = null;
	static Connection cn = null;
	PurchaseOrder po = null;

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
		target = new PurchaseOrderDao();
		po = new PurchaseOrder(1050,3,Date.valueOf("2020-10-28"),Date.valueOf("2020-10-30"),0);
	}

	@After
	public void tearDown() throws Exception {
		target = null;
		deleteTestData();
		po = null;
	}
	
	private int getRecordCountMethod() throws SQLException {
		Statement stmt = cn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT count(*) from PurchaseOrder");
		rs.next();
		return rs.getInt(1);
	}
	
	private int deleteTestData() throws SQLException {
		Statement stmt = cn.createStatement();
		int i = stmt.executeUpdate("DELETE FROM `PurchaseOrder` WHERE `PONumber`='"+po.getPoNumber()+"'");
		return i;
	}
	
	@Test
	public void testAddPurchaseOrder() throws SQLException {
		int totalRecordsBefore = getRecordCountMethod() + 1;
		target.addPurchaseOrder(po);
		int totalRecordsAfter = getRecordCountMethod();
		assertTrue("Record size not updated...!", totalRecordsAfter == totalRecordsBefore);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPurchaseOrder() {
		PurchaseOrder pur = target.getPurchaseOrder(1004);
		assertNotNull(pur);
		assertEquals(1004,pur.getPoNumber());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetAllPurchaseOrders() throws SQLException {
		List<PurchaseOrder> poList = target.getAllPurchaseOrders();
		assertNotNull(poList);
		assertTrue("Fetched Wrong Number of Purchase Orders...!", poList.size()==getRecordCountMethod());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPurchaseOrdersForSpecificCustomer() {
		List<PurchaseOrder> poList = target.getPurchaseOrdersForSpecificCustomer(2);
		assertNotNull(poList);
		assertTrue("Fetched Wrong Number of Purchase Orders...!", poList.size()==2);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPurchaseOrdersForSpecificDuration() {
		List<PurchaseOrder> poList = target.getPurchaseOrdersForSpecificDuration(Date.valueOf("2020-10-8"),Date.valueOf("2020-11-10"));
		assertNotNull(poList);
		assertTrue("Fetched Wrong Number of Purchase Orders...!", poList.size()==4);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPurchaseOrdersForSpecificDate() {
		List<PurchaseOrder> poList = target.getPurchaseOrdersForSpecificDate(Date.valueOf("2020-10-8"));
		assertNotNull(poList);
		assertTrue("Fetched Wrong Number of Purchase Orders...!", poList.size()==1);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetDelayedPurchaseOrders() {
		List<PurchaseOrder> poList = target.getDelayedPurchaseOrders();
		assertNotNull(poList);
		assertTrue("Fetched Wrong Number of Purchase Orders...!", poList.size()==2);
		//fail("Not yet implemented");
	}

}
