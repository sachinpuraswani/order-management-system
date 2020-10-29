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

import JavaMiniProject.model.StockItem;
import JavaMiniProject.util.ConnectionManager;

public class StockItemDao {

	Connection cn = ConnectionManager.getConnection();
	
	private static final Logger logger = Logger.getLogger(StockItemDao.class.getName());
	
	static {
		try {
			File dir = new File("logs/");
			if(!dir.exists())
				dir.mkdirs();
			logger.addHandler(new FileHandler("logs/StockItemLogs"));
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Add new Stock Item to Database
	public void addStockItem(StockItem si) {
		try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO StockItem(ItemName,ItemPrice,QtyInStock) values(?,?,?)");) {
			pstmt.setString(1, si.getItemName());
			pstmt.setDouble(2, si.getItemPrice());
			pstmt.setInt(3, si.getQtyInStock());
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nStock Item "+si.getItemName()+" added...!");
				logger.info("\nStock Item added successfully...! \nStock Item details : " + si);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Add new Stock Item to Database(StockItem Number explicitly given)
	public void addStockItemWithId(StockItem si) {
		try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO StockItem values(?,?,?,?)");) {
			pstmt.setInt(1, si.getItemNumber());
			pstmt.setString(2, si.getItemName());
			pstmt.setDouble(3, si.getItemPrice());
			pstmt.setInt(4, si.getQtyInStock());
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nStock Item "+si.getItemName()+" added...!");
				logger.info("\nStock Item added successfully...! \nStock Item details : " + si);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Get Stock Item details
	public StockItem getStockItem(int itemNumber) {
		String qry="SELECT * from StockItem where ItemNumber=?";
		StockItem si = null;
		try (PreparedStatement pstmt = cn.prepareStatement(qry)) {
			pstmt.setInt(1, itemNumber);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				si = new StockItem();		
				si.setItemNumber(rs.getInt(1));
				si.setItemName(rs.getString(2));
				si.setItemPrice(rs.getDouble(3));
				si.setQtyInStock(rs.getInt(4));
				logger.info("\nStock Item fetched successfully...! \nStock Item details : "+si);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return si;
	}
	
	// Get All Stock Items
	public List<StockItem> getAllStockItems() {
		List<StockItem> siList = new ArrayList<StockItem>();
		StockItem si = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from StockItem");) {
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				si = new StockItem();
				si.setItemNumber(rs.getInt(1));
				si.setItemName(rs.getString(2));
				si.setItemPrice(rs.getDouble(3));
				si.setQtyInStock(rs.getInt(4));
				siList.add(si);
			}
			logger.info("\nFetched all Stock Items...! \nStock Items list : "+siList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return siList;
	}
	
	
	//update Quantity in stock(After placing a new order)
	public void updateQuantityInStock(int itemNumber, int updatedQuantity) {
		try(PreparedStatement pstmt = cn.prepareStatement("UPDATE StockItem SET QtyInStock=? where ItemNumber=?");) {
			pstmt.setInt(1, updatedQuantity);
			pstmt.setInt(2, itemNumber);
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("Stock Updated...!");
				logger.info("\nQuantity Updated for Stock Item " + itemNumber + " : " + updatedQuantity);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Add more quantity for stock items
	public void addMoreStockQuantity(int itemNumber, int newQuantity) {
		try(PreparedStatement pstmt = cn.prepareStatement("UPDATE StockItem SET QtyInStock=? where ItemNumber=?");) {
			pstmt.setInt(1, newQuantity);
			pstmt.setInt(2, itemNumber);
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("Stock updated with more stock quantity...!");
				logger.info("\nMore Stock Quantity added for Stock Item " + itemNumber + "...! \nAvailable Quantity : " + newQuantity);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Delete Stock Item
	public void deleteStockItem(int itemNumber) {
		try(PreparedStatement pstmt = cn.prepareStatement("DELETE from StockItem where ItemNumber=?");) {
			pstmt.setInt(1, itemNumber);
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nStock Item "+itemNumber+" Deleted (Along with the respective Items Ordered)...!");
				logger.info("\nStock Item "+itemNumber+" deleted successfully...!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
