package JavaMiniProject;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import JavaMiniProject.model.Customer;
import JavaMiniProject.model.OrderItems;
import JavaMiniProject.model.PurchaseOrder;
import JavaMiniProject.model.StockItem;
import JavaMiniProject.service.CustomerService;
import JavaMiniProject.service.OrderItemsService;
import JavaMiniProject.service.PurchaseOrderService;
import JavaMiniProject.service.StockItemService;

public class TestOrderManagementSystem {
	
	private static final Logger logger = Logger.getLogger(TestOrderManagementSystem.class.getName());
	
	static {
		try {
			File dir = new File("logs/");
			if(!dir.exists())
				dir.mkdirs();
			logger.addHandler(new FileHandler("logs/TestOrderManagementSystemLogs"));
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		
		try {
			
			logger.info("Welcome to Order Management System...!");
			
			CustomerService custService = new CustomerService();
			PurchaseOrderService poService = new PurchaseOrderService();
			OrderItemsService ordersService = new OrderItemsService();
			StockItemService siService = new StockItemService();
			
			int ch = 0;
			while(ch != 15) {
				System.out.println("\n\t***Order Management System***\n");
				System.out.println("1. Add new Customer");
				System.out.println("2. Add new Purchase Order");
				System.out.println("3. Add new Stock Item");
				System.out.println("4. Fetch Customer based on ID");
				System.out.println("5. Fetch Orders placed by specific Customers");
				System.out.println("6. Fetch Orders placed for specific Duration");
				System.out.println("7. Update Ship Date");
				System.out.println("8. Fetch delayed Orders");
				System.out.println("9. Fetch all Stock Items");
				System.out.println("10. Find month-wise total orders shipped");
				System.out.println("11. Find total amount collected based on months");
				System.out.println("12. Find the customer who has made maximum orders");
				System.out.println("13. Generate bill for customer for specific order");
				System.out.println("14. Exit");
				
				System.out.print("\nEnter your choice: ");
				ch = sc.nextInt();
				
				switch(ch) {
					case 1: Customer c = new Customer();
					
							sc.nextLine();
							System.out.print("\nEnter Customer Name: ");
							c.setName(sc.nextLine());
							System.out.print("Home Phone: ");
							c.setHomePhone(sc.next());
							System.out.print("Cell Phone: ");
							c.setCellPhone(sc.next());
							System.out.print("Work Phone: ");
							c.setWorkPhone(sc.next());
							sc.nextLine();
							System.out.print("Street: ");
							c.setStreet(sc.nextLine());
							System.out.print("City: ");
							c.setCity(sc.nextLine());
							System.out.print("State: ");
							c.setState(sc.nextLine());
							System.out.print("Zip Code: ");
							c.setZip(sc.nextInt());
							
							custService.addCustomer(c);
							
							break;
							
					case 2: 
							System.out.print("\nEnter Purchase-Order Number: ");
							int poNumber = sc.nextInt();
							System.out.print("Enter Customer Id: ");
							int customerId = sc.nextInt();
							System.out.print("Order Date: ");
							String orderDate = sc.next();
							System.out.print("Ship Date: ");
							String shipDate = sc.next();
							
							PurchaseOrder po = new PurchaseOrder(poNumber,customerId, Date.valueOf(orderDate), Date.valueOf(shipDate), 0);
							
							poService.addPurchaseOrder(po);
							
							int itemCount = 0;
							while(true) {
								System.out.print("Order Item Number: ");
								int itemNumber = sc.nextInt();
								System.out.print("Quantity required: ");
								int noOfItems = sc.nextInt();
								
								if(!siService.getStock(itemNumber))
									System.out.println("\nInvalid StockItem Id...!");
								else {
									StockItem stock = siService.getStockItem(itemNumber);
									
									if(noOfItems <= stock.getQtyInStock()) {
										OrderItems o = new OrderItems(poNumber, itemNumber, noOfItems);
										ordersService.addOrder(o);
										itemCount++;
										siService.updateQuantityInStock(itemNumber, stock.getQtyInStock()-noOfItems);								
									}
									else {
										System.out.println("\nSorry, Item out of Stock...!");
									}
								}
								
								System.out.print("\nWant to add more items? (Press Y/y if yes and n/N if No) : ");
								char Char = sc.next().charAt(0);
								if(Char == 'N' || Char == 'n')
									break;
							}
							
							if(itemCount == 0) {
								System.out.println("\nPurchase Order "+poNumber+" doesn't contain any item, Cancelling it...!");
								poService.deletePurchaseOrder(poNumber);
							}
							
							break;
							
					case 3: StockItem si = new StockItem();
							
							sc.nextLine();
							System.out.print("\nStockItem Name: ");
							si.setItemName(sc.nextLine());
							System.out.print("Item Price: ");
							si.setItemPrice(sc.nextDouble());
							System.out.print("Stock Quantity: ");
							si.setQtyInStock(sc.nextInt());
							
							siService.addStockItem(si);
							
							break;
							
					case 4: System.out.print("\nEnter Customer Id: ");
							int custId = sc.nextInt();
							
							if(!custService.getCust(custId)) {
								//System.out.println("\nInvalid Customer Id...!");
								logger.info("\nException Occured : Invalid Customer ID "+custId+" ...!");
								throw new Exception("\nException: Invalid Customer Id...!");
							}
							else
								custService.displayCustomer(custService.getCustomer(custId));
							
							break;
							
					case 5: System.out.print("\nEnter Customer Id: ");
							int custId1 = sc.nextInt();
							
							if(!custService.getCust(custId1)) {
								//System.out.println("\nInvalid Customer Id...!");
								logger.info("\nException Occured : Invalid Customer ID "+custId1+" ...!");
								throw new Exception("\nException: Invalid Customer Id...!");
							}
							else {
								List<PurchaseOrder> poList = poService.getPurchaseOrdersForSpecificCustomer(custId1);
								
								if(poList.isEmpty())
									System.out.println("\nNo Purchase orders found, Please place your first order...!");
								else {
									for(PurchaseOrder p : poList)
										poService.displayPurchaseOrder(p);
								}
							}
							
							break;
					
					case 6: int ch1 = 0;
							while(ch1 != 3) {
								System.out.println("\n1. Placed between From and To date");
								System.out.println("2. Placed for specific date");
								System.out.println("3. Back");
								
								System.out.print("\nYour choice: ");
								ch1 = sc.nextInt();
								
								switch(ch1) {
									case 1: System.out.print("\nFrom date: ");
											String fromDate = sc.next();
											System.out.print("To date: ");
											String toDate = sc.next();
											
											List<PurchaseOrder> poList1 = poService.getPurchaseOrdersForSpecificDuration(Date.valueOf(fromDate), Date.valueOf(toDate));
											
											if(poList1.isEmpty())
												System.out.println("\nNo Purchase orders found for given duration...!");
											else {
												for(PurchaseOrder p : poList1)
													poService.displayPurchaseOrder(p);
											}
											
											break;
									
									case 2: System.out.print("\nEnter date: ");
											String orderDate1 = sc.next();
											
											List<PurchaseOrder> poList2 = poService.getPurchaseOrdersForSpecificDate(Date.valueOf(orderDate1));
											
											if(poList2.isEmpty())
												System.out.println("\nNo purchase orders found for given date...!");
											else {
												for(PurchaseOrder p : poList2)
													poService.displayPurchaseOrder(p);
											}
											
											break;
									
									case 3: 
											break;
								}
							}
							
							break;
								
					case 7: System.out.println("\nEnter Purchase-Order Number whose ship date is to be updated: ");
							int PONumber = sc.nextInt();
							
							if(!poService.getPurchOrder(PONumber)) {
								 //System.out.println("\nInvalid Purchase-Order Number...!");
								 logger.info("\nException Occured : Invalid Purchase Order Number "+PONumber+" ...!"); 
								 throw new Exception("\nException: Invalid Purchase-Order Number...!");
							}
							else {
								System.out.print("Enter Updated Ship Date: ");
								String updatedShipDate = sc.next();
								
								poService.updateShipDate(PONumber, Date.valueOf(updatedShipDate));
							}
							
							break;
						
					case 8: List<PurchaseOrder> poList3 = poService.getDelayedPurchaseOrders();
					
							if(poList3.isEmpty())
								System.out.println("\nThere is no delayed order...!");
							else {
								for(PurchaseOrder p : poList3)
									poService.displayPurchaseOrder(p);
							}
							
							break;
						
					case 9: List<StockItem> siList = siService.getAllStockItems();
					
							if(siList.isEmpty())
								System.out.println("\nNo available stock...! Add stock items...!");
							else {
								System.out.println("\n------------------------------------------------------------------");
								System.out.printf("%-15s%-15s%-15s%-20s%-5s\n", "Item Number", "| Item Name", "| Item Price", "| Quantity in Stock", " |");
								System.out.println("------------------------------------------------------------------");
								
								for(StockItem s : siList)
									siService.displayStockItem(s);
							}
							
							break;
						
					case 10: poService.getMonthWiseTotalOrdersShipped();
							 break;
							
					case 11: poService.getMonthlyTotalAmountCollected();
							 break;
						
					case 12: poService.getCustomerWithMaximumOrders();
							 break;
						
					case 13: System.out.print("\nEnter Purchase-Order Number: ");
							 int poNumber1 = sc.nextInt();
							 
							 if(!poService.getPurchOrder(poNumber1)) {
								 //System.out.println("\nInvalid Purchase-Order Number...!");
								 logger.info("\nException Occured : Invalid Purchase Order Number "+poNumber1+" ...!");
								 throw new Exception("\nException: Invalid Purchase-Order Number...!");
							 }
							 else
								 poService.generateBill(poService.getPurchaseOrder(poNumber1));
							 
							 break;
							
					case 14: System.out.println("\nThank you for using the \"Order Management System\"!!!");
							 logger.info("\nThank you for using \"Order Management System\"!!!");
							 System.exit(0);
							 break;
							
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
		}
		
		sc.close();
	}

}
