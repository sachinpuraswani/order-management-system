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

import JavaMiniProject.model.OrderItems;
import JavaMiniProject.util.ConnectionManager;

public class OrderItemsDao {

	Connection cn = ConnectionManager.getConnection();
	
	private static final Logger logger = Logger.getLogger(OrderItemsDao.class.getName());
	
	static {
		try {
			File dir = new File("logs/");
			if(!dir.exists())
				dir.mkdirs();
			logger.addHandler(new FileHandler("logs/OrderItemsLogs"));
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Add new Orders to Database
	public void addOrder(OrderItems o) {
		try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO OrderItems values(?,?,?)");) {
			pstmt.setInt(1, o.getPoNumber());
			pstmt.setInt(2, o.getItemNumber());
			pstmt.setInt(3, o.getNoOfItems());
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nOrder Item "+o.getItemNumber()+" added...!");
				logger.info("\nOrder Item added successfully...! \nOrder Item details : " + o);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Get All Orders items under Purchase-Order Number
	public List<OrderItems> getAllOrders(int poNumber) {
		List<OrderItems> oList = new ArrayList<OrderItems>();
		OrderItems o = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from OrderItems where PONumber=?");) {
			pstmt.setInt(1, poNumber);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				o = new OrderItems();
				o.setPoNumber(rs.getInt(1));
				o.setItemNumber(rs.getInt(2));
				o.setNoOfItems(rs.getInt(3));
				oList.add(o);
			}
			logger.info("\nFetched all Order Items for Purchase Order Number "+poNumber+" ...! \nOrder Items list : "+oList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return oList;
	}
	
}
