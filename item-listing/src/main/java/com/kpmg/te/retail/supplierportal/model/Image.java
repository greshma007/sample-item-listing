package com.kpmg.te.retail.supplierportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "image_path")
	private String imagePath;

	// TODO - how to link between Image table and Inventory table ?
//	// foreign key
//	@OneToOne
//	@JoinColumn(name = "inventory_number", unique = true)
//	private Inventory inventory;

	public Long getId() {
		return id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

//	public Inventory getInventory() {
//		return inventory;
//	}
//
//	public void setInventory(Inventory inventory) {
//		this.inventory = inventory;
//	}

	@Override
	public String toString() {
//		return "Image [id=" + id + ", imagePath=" + imagePath + ", inventory=" + inventory + "]";
		return "Image [id=" + id + ", imagePath=" + imagePath + "]";
	}

}
