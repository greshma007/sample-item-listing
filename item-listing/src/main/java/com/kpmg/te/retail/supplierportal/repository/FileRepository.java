package com.kpmg.te.retail.supplierportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kpmg.te.retail.supplierportal.model.File;

public interface FileRepository extends JpaRepository<File, String> {

	Optional<File> findByName(String name);
}
