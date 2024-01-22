package com.kpmg.te.retail.supplierportal.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kpmg.te.retail.supplierportal.message.ResponseMessage;
import com.kpmg.te.retail.supplierportal.model.Inventory;
import com.kpmg.te.retail.supplierportal.service.InventoryService;
import com.kpmg.te.retail.supplierportal.util.ItemListingUtils;

/**
 * 
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	// TODO - changeImage() API
	
	
	@Autowired
	private InventoryService inventoryService;

	/**
	 * Add inventory to db on click action of "Add Inventory" button from UI
	 */
	@PostMapping("/save")
	public ResponseEntity<ResponseMessage> addFileContentsToDb() {

		// TODO - need to add only selected items from UI to db
		// for now - reading all items stored in file from utils class - ItemListingUtils.getInventoryList()

		List<Inventory> inventoryEntities = new ArrayList<Inventory>();
		String message = "Failed to save contents !";
		HttpStatus status = HttpStatus.EXPECTATION_FAILED;

		if (ItemListingUtils.getInventoryList() != null & ItemListingUtils.getInventoryList().size() > 0) {

			// reading from util class
			ItemListingUtils.getInventoryList().forEach(i -> {

				Inventory inventoryEntity = new Inventory();
				inventoryEntity.setItemNumber(i.getItemNumber());
				inventoryEntity.setItemName(i.getItemName());
				inventoryEntity.setPrice(i.getPrice());
				inventoryEntity.setSku(i.getSku());
				inventoryEntity.setStatus(i.getStatus());

				inventoryEntities.add(inventoryEntity);
			});

			// run validations on the stored values
			boolean result = ItemListingUtils.runValidations(inventoryEntities);

			// save to db
			if (result) {
				inventoryService.saveAll(inventoryEntities);
				status = HttpStatus.OK;
				message = "Validations are successful. Items uploaded successfully! ";

			} else {
				status = HttpStatus.NOT_ACCEPTABLE;
				message = "Validations failed !! Did not upload items. Please recheck and re-upload";

			}
		}

		return ResponseEntity.status(status).body(new ResponseMessage(message));
	}

	/**
	 * Update status for item/(s) from item list
	 * 
	 * @param itemList
	 */
	@PutMapping("/update")
	public void updateStatus(@RequestBody List<Inventory> itemList) {

		for (Inventory item : itemList) {
			inventoryService.updateItemStatus(item.getItemNumber(), item.getStatus());
		}

	}

	/**
	 * Remove item/(s) from item list
	 * 
	 * @param itemNumber
	 */
	@DeleteMapping("remove")
	public void removeItem(@RequestBody List<Inventory> itemList) {

		for (Inventory item : itemList) {
			inventoryService.removeByItemNumber(item.getItemNumber());
		}

	}

}
