package com.kpmg.te.retail.supplierportal.controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.HttpHeaders;
import com.kpmg.te.retail.supplierportal.message.ResponseMessage;
import com.kpmg.te.retail.supplierportal.model.File;
import com.kpmg.te.retail.supplierportal.model.Inventory;
import com.kpmg.te.retail.supplierportal.service.FileService;
import com.kpmg.te.retail.supplierportal.util.ItemListingUtils;

/**
 * Controller to handle all file related operations
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileStorageService;

	/**
	 * Upload file description into db (description - file id, file name, file type)
	 */
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFileToDb(@RequestParam("file") MultipartFile file) {

		String message = "";
		HttpStatus status;

		try {
			// save file to db
			fileStorageService.saveFile(file);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			status = HttpStatus.OK;
			LOGGER.info(message + " " + status.toString());

		} catch (Exception e) {
			message = "Could not upload file: " + file.getOriginalFilename() + " !!";
			status = HttpStatus.EXPECTATION_FAILED;
			LOGGER.info(message + " " + status.toString());
		}

		return ResponseEntity.status(status).body(new ResponseMessage(message));

	}

	/**
	 * Read the uploaded file from db and populate items into table
	 * 
	 * @throws FileNotFoundException
	 */
	@GetMapping("/populate/{fileName}")
	public List<Inventory> populateItemsfromFile(@PathVariable String fileName) throws FileNotFoundException {

		List<Inventory> inventoryItems = new ArrayList<>();

		// retrieve file from db
		File fileDB = fileStorageService.getFileByName(fileName);

		// convert to bytestream to read data from file
		byte[] data = fileDB.getData();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

		try (BufferedReader br = new BufferedReader(new InputStreamReader(byteArrayInputStream))) {

			// issue : headers getting read while parsing and throwing exception
			br.readLine(); // fix : to skip 1st row line - headers
			
			String line = "";

			while ((line = br.readLine()) != null) {

				// Split the CSV line into individual values
				String[] values = line.split(",");

				// Create Item object from the values
				Inventory item = new Inventory(values[0].trim(), // item number
						values[1].trim(), // item name
						values[2].trim(), // SKU
						Float.parseFloat(values[3].trim()), // price
						values[4].trim() // active status
				);

				// Add the item to the list
				inventoryItems.add(item);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		// common place to save list of items
		ItemListingUtils.setInventoryList(inventoryItems);

		return inventoryItems;
	}

	/**
	 * Download file from db into local sys
	 */
	@GetMapping("/download/{name}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String name) {

		// get the file from db
		File fileDb = fileStorageService.getFileByName(name);

		// download file to local sys
		byte[] data = fileDb.getData();

		// remove commas
		String csvContent = new String(data, StandardCharsets.UTF_8);
		csvContent = csvContent.replaceAll(",", "");

		// Convert back to byte array
		byte[] modifiedData = csvContent.getBytes(StandardCharsets.UTF_8);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDb.getName() + "\"")
				.body(modifiedData);

	}

	/**
	 * Delete file from db
	 * 
	 * @param id
	 */
	@DeleteMapping("delete/{fileName}")
	public void deleteFile(@PathVariable String fileName) {
		File fileDb = fileStorageService.getFileByName(fileName);
		fileStorageService.deleteFileByName(fileDb.getName());
	}

}
