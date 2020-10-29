package JavaMiniProject.model;

import java.sql.Date;

public class PurchaseOrder {

	private int poNumber;
	private int customerId;
	private Date orderDate;
	private Date shipDate;
	private int status;
	
	public PurchaseOrder() {
		this.poNumber = 0;
		this.customerId = 0;
		this.orderDate = null;
		this.shipDate = null;
		this.status = 0;
	}
	
	public PurchaseOrder(int poNumber, int customerId, Date orderDate, Date shipDate, int status) {
		this.poNumber = poNumber;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.shipDate = shipDate;
		this.status = status;
	}
	
	public int getPoNumber() {
		return poNumber;
	}
	
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public Date getShipDate() {
		return shipDate;
	}
	
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
}
