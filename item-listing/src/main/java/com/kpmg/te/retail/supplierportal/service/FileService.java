package com.kpmg.te.retail.supplierportal.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kpmg.te.retail.supplierportal.model.File;
import com.kpmg.te.retail.supplierportal.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;
	

	/**
	 * Save the file in db
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public File saveFile(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		File fileDB = new File(fileName, file.getContentType(), file.getBytes());
		return fileRepository.save(fileDB);
	}
	
	/**
	 * Get file by name
	 * 
	 * @param fileName
	 * @return
	 */
	public File getFileByName(String fileName) {
		Optional<File> fileOptional = fileRepository.findByName(fileName);
        return fileOptional.orElse(null);
	}

	/**	
	 * Get File id
	 * 
	 * @param id
	 * @return
	 */
	public File getFileById(String id) {
		Optional<File> findById = fileRepository.findById(id);
		return findById.orElse(null);
	}

	/**
	 * Remove file from db
	 * 
	 * @param id
	 */
	public void deleteFileByName(String name) {
		Optional<File> fileOptional = fileRepository.findByName(name);
		File fileDB = fileOptional.get();
		fileRepository.deleteById(fileDB.getId());
	}

}
