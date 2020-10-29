package JavaMiniProject.model;

public class OrderItems {

	private int poNumber;
	private int itemNumber;
	private int noOfItems;
	
	public OrderItems() {
        this.poNumber = 0;
        this.itemNumber = 0;
        this.noOfItems = 0;
    }
	
	public OrderItems(int poNumber, int itemNumber, int numberOfItems) {
		this.poNumber = poNumber;
        this.itemNumber = itemNumber;
        this.noOfItems = numberOfItems;
    }
    
    public int getNoOfItems() {
		return noOfItems;
	}
    
    public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}
    
    public int getItemNumber() {
		return itemNumber;
	}
    
    public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
    
    public int getPoNumber() {
		return poNumber;
	}
    
    public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
    
}
