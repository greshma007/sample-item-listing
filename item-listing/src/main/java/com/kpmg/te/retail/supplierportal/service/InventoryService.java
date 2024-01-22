package com.kpmg.te.retail.supplierportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kpmg.te.retail.supplierportal.model.Inventory;
import com.kpmg.te.retail.supplierportal.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

//	@Autowired
//	private ImageRepository imageRepository;

	/**
	 * Add all to Inventory db
	 * 
	 * @param inventoryEntities
	 */
	public void saveAll(List<Inventory> inventoryEntities) {
		inventoryRepository.saveAll(inventoryEntities);
	}

	/**
	 * Get item by name
	 * 
	 * @param itemName
	 * @return
	 */
	public Inventory getItemByName(String name) {
		Optional<Inventory> inventoryDBOptional = inventoryRepository.findByItemName(name);
		return inventoryDBOptional.orElse(null);
	}

	/**
	 * Get item by item number
	 * 
	 * @param itemNumber
	 * @return
	 */
	public Inventory getItemByItemNumber(String itemNumber) {
		Optional<Inventory> inventoryOptional = inventoryRepository.findByItemNumber(itemNumber);
		return inventoryOptional.orElse(null);
	}

	/**
	 * Remove item from db
	 * 
	 * @param id
	 */
	public void removeByItemName(String name) {
		Optional<Inventory> itemOptional = inventoryRepository.findByItemName(name);
		Inventory item = itemOptional.get();
		inventoryRepository.delete(item);
	}

	public void removeByItemNumber(String itemNumber) {
		Optional<Inventory> itemOptional = inventoryRepository.findByItemNumber(itemNumber);
		Inventory item = itemOptional.get();
		inventoryRepository.delete(item);
	}

	/**
	 * Update item status in db
	 * 
	 * @param name
	 */
	public Inventory updateItemStatus(String itemNumber, String status) {

		Optional<Inventory> itemOptional = inventoryRepository.findByItemNumber(itemNumber);
		Inventory item = itemOptional.get();
		item.setStatus(status);

		return inventoryRepository.save(item);
	}

	/**
	 * TODO 
	 * 
	 * Update image in item in db
	 * 
	 * @param itemNumber
	 * @param imagePath
	 */
	public void updateImage(String itemNumber, String imagePath) {

//		Image img = imageRepository.findByItemNumber(itemNumber).get();
//		Inventory inventory = inventoryRepository.findByItemNumber(itemNumber).get();

	}

}
