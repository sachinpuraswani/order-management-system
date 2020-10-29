package JavaMiniProject.service;

import java.util.List;

import JavaMiniProject.dao.OrderItemsDao;
import JavaMiniProject.model.OrderItems;
import JavaMiniProject.model.StockItem;

public class OrderItemsService {

	OrderItemsDao dao = new OrderItemsDao();
	StockItemService siService = null;
	
	public void addOrder(OrderItems o) {
		dao.addOrder(o);
	}
	
	public List<OrderItems> getAllOrders(int poNumber) {
		return dao.getAllOrders(poNumber);
	}
	
	public void displayOrder(OrderItems o) {
		siService = new StockItemService();
		StockItem si = siService.getStockItem(o.getItemNumber());
		System.out.printf("\n"+si.getItemName()+"\t"+ 
				"| "+o.getNoOfItems()+"\t| "+ si.getItemPrice()+"\t| "+ (o.getNoOfItems()*si.getItemPrice())+"\t    |");
	}
}
