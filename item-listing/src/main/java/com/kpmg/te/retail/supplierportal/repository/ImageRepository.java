package com.kpmg.te.retail.supplierportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpmg.te.retail.supplierportal.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	// TODO 
	// Optional<Image> findByItemNumber(String itemNumber);

}
