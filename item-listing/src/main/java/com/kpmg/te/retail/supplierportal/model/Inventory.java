package com.kpmg.te.retail.supplierportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "item_number")
	private String itemNumber;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "sku")
	private String sku;

	@Column(name = "price")
	private double price;

	@Column(name = "status")
	private String status;
	
	
	public Inventory(String itemNumber, String itemName, String sku, double price, String status) {
		this.itemNumber = itemNumber;
		this.itemName = itemName;
		this.sku = sku;
		this.price = price;
		this.status = status;
	}

	public Inventory() {

	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemId) {
		this.itemNumber = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Override
	public String toString() {
		return "Inventory [id=" + id + ", itemNumber=" + itemNumber + ", itemName=" + itemName + ", sku=" + sku
				+ ", price=" + price + ", status=" + status + "]";
	}

}
