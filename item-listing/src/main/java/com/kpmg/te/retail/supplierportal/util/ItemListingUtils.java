package com.kpmg.te.retail.supplierportal.util;

import java.util.ArrayList;
import java.util.List;

import com.kpmg.te.retail.supplierportal.model.Inventory;

/**
 * Common code
 */
public class ItemListingUtils {

	static List<Inventory> inventoryList = new ArrayList<>();

	// GETTER
	public static List<Inventory> getInventoryList() {
		return inventoryList;
	}

	// SETTER
	public static void setInventoryList(List<Inventory> finalInventoryList) {
		inventoryList.clear();
		inventoryList = finalInventoryList;
	}

	/**
	 * Run validations on the excel contents
	 * 
	 * @param inventoryEntities
	 */
	public static boolean runValidations(List<Inventory> inventoryEntities) {

		// mandatory fields - item number, item name, sku, price
		return inventoryEntities.stream().anyMatch(item -> isEmptyOrNull(item.getItemNumber())
				&& isEmptyOrNull(item.getItemName()) && isEmptyOrNull(item.getSku()) && item.getPrice() >= 0);
	}

	/**
	 * Check if the fields are empty or null
	 * 
	 * @param value
	 * @return
	 */
	private static boolean isEmptyOrNull(String value) {
		return value != null && value.trim().isEmpty();
	}

}
