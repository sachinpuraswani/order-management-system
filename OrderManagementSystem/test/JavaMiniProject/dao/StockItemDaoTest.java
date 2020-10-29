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

import JavaMiniProject.model.StockItem;
import JavaMiniProject.util.ConnectionManager;

public class StockItemDaoTest {
	
	StockItemDao target = null;
	static Connection cn = null;
	StockItem si = null;

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
		target = new StockItemDao();
		si =  new StockItem(60,"Test StockItem", 50, 20);
	}

	@After
	public void tearDown() throws Exception {
		target = null;
		deleteTestData();
		si = null;
	}
	
	private int getRecordCountMethod() throws SQLException {
		Statement stmt = cn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT count(*) from StockItem");
		rs.next();
		return rs.getInt(1);
	}
	
	private int deleteTestData() throws SQLException {
		Statement stmt = cn.createStatement();
		int i = stmt.executeUpdate("DELETE FROM `StockItem` WHERE `ItemNumber`='"+si.getItemNumber()+"'");
		return i;
	}

	@Test
	public void testAddStockItemWithId() throws SQLException {
		int totalRecordsBefore = getRecordCountMethod() + 1;
		target.addStockItemWithId(si);
		int totalRecordsAfter = getRecordCountMethod();
		assertTrue("Record size not updated...!", totalRecordsAfter == totalRecordsBefore);
		//fail("Not yet implemented");
	}

	@Test
	public void testGetStockItem() {
		StockItem stock = target.getStockItem(52);
		assertNotNull(stock);
		assertEquals("Eraser",stock.getItemName());
		//fail("Not yet implemented");
	}

	@Test
	public void testGetAllStockItems() throws SQLException {
		List<StockItem> siList = target.getAllStockItems();
		assertNotNull(siList);
		assertTrue("Fetched Wrong Number of Stock Items...!", siList.size()==getRecordCountMethod());
		//fail("Not yet implemented");
	}

}
