package JavaMiniProject.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import JavaMiniProject.model.PurchaseOrder;
import JavaMiniProject.service.CustomerService;
import JavaMiniProject.util.ConnectionManager;

public class PurchaseOrderDao {

	Connection cn = ConnectionManager.getConnection();
	CustomerService customerService = null;
	
	private static final Logger logger = Logger.getLogger(PurchaseOrderDao.class.getName());
	
	static {
		try {
			File dir = new File("logs/");
			if(!dir.exists())
				dir.mkdirs();
			logger.addHandler(new FileHandler("logs/PurchaseOrderLogs"));
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Add new Purchase Order to Database
	public void addPurchaseOrder(PurchaseOrder po) {
		try(PreparedStatement pstmt = cn.prepareStatement("INSERT INTO PurchaseOrder values(?,?,?,?,?)");) {
			pstmt.setInt(1, po.getPoNumber());
			pstmt.setInt(2, po.getCustomerId());
			pstmt.setDate(3, po.getOrderDate());
			pstmt.setDate(4, po.getShipDate());
			pstmt.setInt(5, po.getStatus());
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nPurchase Order "+po.getPoNumber()+" created...!\nAdd required Order Items...!\n");
				logger.info("\nPurchase Order added successfully...! \nPurchase Order details : " + po);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Get specific Purchase Order details
	public PurchaseOrder getPurchaseOrder(int poNumber) {
		String qry="SELECT * from PurchaseOrder where PONumber=?";
		PurchaseOrder po = null;
		try (PreparedStatement pstmt = cn.prepareStatement(qry)) {
			pstmt.setInt(1, poNumber);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				po = new PurchaseOrder();		
				po.setPoNumber(rs.getInt(1));
				po.setCustomerId(rs.getInt(2));
				po.setOrderDate(rs.getDate(3));
				po.setShipDate(rs.getDate(4));
				po.setStatus(rs.getInt(5));
				logger.info("\nPurchase Order fetched successfully...! \nPurchase Order details : " + po);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return po;
	}
	
	// Get All Purchase Orders
	public List<PurchaseOrder> getAllPurchaseOrders() {
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();
		PurchaseOrder po = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from PurchaseOrder");) {
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				po = new PurchaseOrder();
				po.setPoNumber(rs.getInt(1));
				po.setCustomerId(rs.getInt(2));
				po.setOrderDate(rs.getDate(3));
				po.setShipDate(rs.getDate(4));
				po.setStatus(rs.getInt(5));
				poList.add(po);
			}
			logger.info("\nFetched all Purchase orders...! \nPurchase Orders list : "+poList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return poList;
	}
	
	// Get Purchase Orders for specific Customer
	public List<PurchaseOrder> getPurchaseOrdersForSpecificCustomer(int customerId) {
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();
		PurchaseOrder po = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from PurchaseOrder where CustomerId=?");) {
			pstmt.setInt(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				po = new PurchaseOrder();
				po.setPoNumber(rs.getInt(1));
				po.setCustomerId(rs.getInt(2));
				po.setOrderDate(rs.getDate(3));
				po.setShipDate(rs.getDate(4));
				po.setStatus(rs.getInt(5));
				poList.add(po);
			}
			logger.info("\nFetched all Purchase orders for Customer "+customerId+" ...! \nPurchase Orders list : "+poList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return poList;
	}
	
	// Get Purchase Orders for specific Duration (Between From and To date)
	public List<PurchaseOrder> getPurchaseOrdersForSpecificDuration(Date fromDate, Date toDate) {
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();
		PurchaseOrder po = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from PurchaseOrder where OrderDate between ? and ?");) {
			pstmt.setDate(1, fromDate);
			pstmt.setDate(2, toDate);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				po = new PurchaseOrder();
				po.setPoNumber(rs.getInt(1));
				po.setCustomerId(rs.getInt(2));
				po.setOrderDate(rs.getDate(3));
				po.setShipDate(rs.getDate(4));
				po.setStatus(rs.getInt(5));
				poList.add(po);
			}
			logger.info("\nPurchase Orders list from "+fromDate+" to "+toDate+" : "+poList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return poList;
	}
	
	// Get Purchase Orders for specific Date
	public List<PurchaseOrder> getPurchaseOrdersForSpecificDate(Date orderDate) {
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();
		PurchaseOrder po = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from PurchaseOrder where OrderDate=?");) {
			pstmt.setDate(1, orderDate);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				po = new PurchaseOrder();
				po.setPoNumber(rs.getInt(1));
				po.setCustomerId(rs.getInt(2));
				po.setOrderDate(rs.getDate(3));
				po.setShipDate(rs.getDate(4));
				po.setStatus(rs.getInt(5));
				poList.add(po);
			}
			logger.info("\nPurchase Orders list for Date "+orderDate+" : "+poList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return poList;
	}
	
	//Update ship date and status
	public void updateShipDate(int poNumber, Date shipDate) {
		try(PreparedStatement pstmt = cn.prepareStatement("UPDATE PurchaseOrder SET ShipDate=?, Status=? where PONumber=?");) {
			pstmt.setDate(1, shipDate);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, poNumber);
			
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nShip Date updated for Purchase-Order Number "+poNumber+"...!");
				logger.info("\nPurchase Order "+poNumber+" Shipped successfully...!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Get Delayed Purchase Orders
	public List<PurchaseOrder> getDelayedPurchaseOrders() {
		List<PurchaseOrder> poList = new ArrayList<PurchaseOrder>();
		PurchaseOrder po = null;
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT * from PurchaseOrder where DATEDIFF(ShipDate, OrderDate)>=4");) {
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				po = new PurchaseOrder();
				po.setPoNumber(rs.getInt(1));
				po.setCustomerId(rs.getInt(2));
				po.setOrderDate(rs.getDate(3));
				po.setShipDate(rs.getDate(4));
				po.setStatus(rs.getInt(5));
				poList.add(po);
			}
			logger.info("\nList of Delayed Orders : " + poList);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return poList;
	}
	
	//Get Month-Wise total orders shipped
	public void getMonthWiseTotalOrdersShipped() {
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT count(PONumber) as Order_Count,month(ShipDate) as Month,year(ShipDate) as Year from PurchaseOrder where Status=1 GROUP BY Month,Year");) {
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("\nTotal Orders\tMonth\tYear");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t\t"+rs.getInt(2)+"\t"+rs.getInt(3));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//Get monthly total amount collected
	public void getMonthlyTotalAmountCollected() {
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT sum(o.NoOfItems*s.ItemPrice) as Amount_Collected,month(p.ShipDate) as Month,year(p.ShipDate) as Year from OrderItems o,StockItem s,PurchaseOrder p where p.PONumber=o.PONumber and o.ItemNumber=s.ItemNumber group by Month,Year");) {
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("\nAmount Collected\tMonth\tYear");
			while(rs.next()) {
				System.out.println(rs.getDouble(1)+"\t\t\t"+rs.getInt(2)+"\t"+rs.getInt(3));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Get Customer who has made maximum orders
	public void getCustomerWithMaximumOrders() {
		customerService = new CustomerService();
		try(PreparedStatement pstmt = cn.prepareStatement("SELECT CustomerId,count(PONumber) as Orders_Count from PurchaseOrder group by CustomerId order by Orders_Count desc limit 1");) {
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("\nCustomer Id\tCustomer Name\tOrders Count");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t\t"+customerService.getCustomer(rs.getInt(1)).getName()+"\t\t"+rs.getInt(2));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Delete Purchase Order
	public void deletePurchaseOrder(int poNumber) {
		try(PreparedStatement pstmt = cn.prepareStatement("DELETE from PurchaseOrder where PONumber=?");) {
			pstmt.setInt(1, poNumber);
			if(pstmt.executeUpdate() == 1) {
				System.out.println("\nPurchase Order "+poNumber+" Deleted...!");
				logger.info("\nPurchase Order "+poNumber+" deleted successfully...!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
