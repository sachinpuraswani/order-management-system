package JavaMiniProject.service;

import java.util.List;

import JavaMiniProject.dao.StockItemDao;
import JavaMiniProject.model.StockItem;

public class StockItemService {

	StockItemDao dao = new StockItemDao();
	
	public void addStockItem(StockItem si) {
		dao.addStockItem(si);
	}
	
	public boolean getStock(int itemNumber) {
		if(getStockItem(itemNumber) == null)
			return false;
		return true;
	}
	
	public StockItem getStockItem(int itemNumber) {
		return dao.getStockItem(itemNumber);
	}
	
	public List<StockItem> getAllStockItems() {
		return dao.getAllStockItems();
	}
	
	public void updateQuantityInStock(int itemNumber, int updatedQuantity) {
		dao.updateQuantityInStock(itemNumber, updatedQuantity);
	}
	
	public void addMoreStockQuantity(int itemNumber, int extraQuantity) {
		dao.addMoreStockQuantity(itemNumber, dao.getStockItem(itemNumber).getQtyInStock()+extraQuantity);
	}
	
	public void deleteStockItem(int itemNumber) {
		dao.deleteStockItem(itemNumber);
	}
	
	public void displayStockItem(StockItem si) {
		System.out.printf("%-15s%-15s%-15s%-20s%-5s\n", si.getItemNumber(), "| "+si.getItemName(), "| "+si.getItemPrice(), "| "+si.getQtyInStock(), " |");
		System.out.println("------------------------------------------------------------------");
	}
}
