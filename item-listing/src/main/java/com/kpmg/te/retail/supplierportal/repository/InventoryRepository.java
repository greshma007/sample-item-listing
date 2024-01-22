package com.kpmg.te.retail.supplierportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpmg.te.retail.supplierportal.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	Optional<Inventory> findByItemName(String itemName);
	
	Optional<Inventory> findByItemNumber(String itemNumber);
}
