package JavaMiniProject.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import JavaMiniProject.dao.PurchaseOrderDao;
import JavaMiniProject.model.OrderItems;
import JavaMiniProject.model.PurchaseOrder;
import JavaMiniProject.model.StockItem;
import JavaMiniProject.util.OrderStatus;

public class PurchaseOrderService {

	PurchaseOrderDao dao = new PurchaseOrderDao();
	OrderItemsService ordersService = null;
	CustomerService customerService = null;
	StockItemService siService = null;
	
	private static final Logger logger = Logger.getLogger(PurchaseOrderService.class.getName());
	
	static {
		try {
			File dir = new File("logs/");
			if(!dir.exists())
				dir.mkdirs();
			logger.addHandler(new FileHandler("logs/PurchaseServiceLogs"));
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void addPurchaseOrder(PurchaseOrder po) {
		dao.addPurchaseOrder(po);
	}
	
	public boolean getPurchOrder(int PONumber) {
		if(getPurchaseOrder(PONumber) == null)
			return false;
		return true;
	}
	
	public PurchaseOrder getPurchaseOrder(int PONumber) {
		return dao.getPurchaseOrder(PONumber);
	}
	
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return dao.getAllPurchaseOrders();
	}
	
	public List<PurchaseOrder> getPurchaseOrdersForSpecificCustomer(int customerId) {
		return dao.getPurchaseOrdersForSpecificCustomer(customerId);
	}
	
	public List<PurchaseOrder> getPurchaseOrdersForSpecificDuration(Date fromDate, Date toDate) {
		return dao.getPurchaseOrdersForSpecificDuration(fromDate, toDate);
	}
	
	public List<PurchaseOrder> getPurchaseOrdersForSpecificDate(Date orderDate) {
		return dao.getPurchaseOrdersForSpecificDate(orderDate);
	}
	
	public void updateShipDate(int poNumber, Date shipDate) {
		dao.updateShipDate(poNumber, shipDate);
	}
	
	public List<PurchaseOrder> getDelayedPurchaseOrders() {
		return dao.getDelayedPurchaseOrders();
	}
	
	public void getMonthWiseTotalOrdersShipped() {
		dao.getMonthWiseTotalOrdersShipped();
	}
	
	public void getMonthlyTotalAmountCollected() {
		dao.getMonthlyTotalAmountCollected();
	}
	
	public void getCustomerWithMaximumOrders() {
		dao.getCustomerWithMaximumOrders();
	}
	
	public void generateBill(PurchaseOrder po) {
		String filename="bills/"+po.getCustomerId()+"/"+po.getPoNumber()+".txt";
		File file1 = new File(filename);
		if(!file1.exists()) {
			file1.getParentFile().mkdirs();
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try(FileWriter out = new FileWriter(filename);) {	
			
			out.write(String.valueOf("*** Invoice for Purchase Order Number: "+po.getPoNumber()+" ***")+" \n\n");
			out.write(String.valueOf("Customer Id: "+po.getCustomerId())+" \n");
			
			customerService = new CustomerService();
			out.write(String.valueOf("Customer Name: "+customerService.getCustomer(po.getCustomerId()).getName())+" \n");
			out.write(String.valueOf("Order Date: "+po.getOrderDate())+" \n");
			out.write(String.valueOf("Ship Date: "+po.getShipDate())+" \n\n");
			
			out.write(String.valueOf("*** Order Details ***")+" \n\n");
			
			ordersService = new OrderItemsService();
			List<OrderItems> ordersList = ordersService.getAllOrders(po.getPoNumber());
			
			double Total = 0;
			for(OrderItems o : ordersList) {
				siService = new StockItemService();
				StockItem si = siService.getStockItem(o.getItemNumber());
				out.write(String.valueOf("Item Name: "+si.getItemName())+" \n");
				out.write(String.valueOf("Item Price: "+si.getItemPrice())+" \n");
				out.write(String.valueOf("No. of Items: "+o.getNoOfItems())+" \n");
				out.write(String.valueOf("SubTotal: "+(o.getNoOfItems()*si.getItemPrice()))+" \n\n");
				Total += o.getNoOfItems()*si.getItemPrice();
			}
			out.write(String.valueOf("Total: "+Total));
			
			System.out.println("\nBill generated!..");
			logger.info("\nBill generated for Purchase Order "+po.getPoNumber()+"...!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deletePurchaseOrder(int PONumber) {
		dao.deletePurchaseOrder(PONumber);
	}
	
	public void displayPurchaseOrder(PurchaseOrder po) {
		System.out.println("\nPurchase Order Number: "+po.getPoNumber());
		System.out.println("Customer Id: "+po.getCustomerId());
		System.out.println("Order Date: "+po.getOrderDate());
		System.out.println("Ship Date: "+po.getShipDate());
		System.out.println("Order status: "+OrderStatus.values()[po.getStatus()]);
		
		ordersService = new OrderItemsService();
		List<OrderItems> ordersList = ordersService.getAllOrders(po.getPoNumber());
		
		System.out.println("\n\t***Order Items Details***\n------------------------------------");
		System.out.printf("Name\t"+"| Count\t"+ "| Price\t"+"| SubTotal  |\n------------------------------------");
		for(OrderItems o : ordersList)
			ordersService.displayOrder(o);
		
		System.out.println("\n------------------------------------");
		
	}
}
