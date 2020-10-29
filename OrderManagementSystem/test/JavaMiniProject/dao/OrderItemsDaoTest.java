package JavaMiniProject.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import JavaMiniProject.model.OrderItems;
import JavaMiniProject.util.ConnectionManager;

public class OrderItemsDaoTest {
	
	OrderItemsDao target = null;
	static Connection cn = null;

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
		target = new OrderItemsDao();
	}

	@After
	public void tearDown() throws Exception {
		target = null;
	}

	@Test
	public void testGetAllOrders() {
		List<OrderItems> oList = target.getAllOrders(1);
		assertNotNull(oList);
		assertTrue("Fetched Wrong Number of Order Items...!", oList.size()==1);
		//fail("Not yet implemented");
	}

}
