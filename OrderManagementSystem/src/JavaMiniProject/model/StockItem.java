package JavaMiniProject.model;

public class StockItem {

	private int itemNumber;
	private String itemName;
	private double itemPrice;
	private int qtyInStock;
	
	public StockItem() {
		this.itemNumber = 0;
		this.itemName = null;
		this.itemPrice = 0;
		this.qtyInStock = 0;
	}
	
	public StockItem(String itemName, double itemPrice, int qtyInStock) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.qtyInStock = qtyInStock;
	}
	
	public StockItem(int itemNumber, String itemName, double itemPrice, int qtyInStock) {
		this.itemNumber = itemNumber;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.qtyInStock = qtyInStock;
	}
	
	public int getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public double getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public int getQtyInStock() {
		return qtyInStock;
	}
	
	public void setQtyInStock(int qtyInStock) {
		this.qtyInStock = qtyInStock;
	}

}
