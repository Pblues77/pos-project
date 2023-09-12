package model;

import java.util.HashMap;
import java.util.Map;

public class Product implements Cloneable, java.io.Serializable {
	private int idProduct;
	private String nameProduct;
	private String type;
	private Map<String, Double> unitPriceBySize;
	private String imageFilePath;
	private String descriptionPromotion;

	public Product(int idProduct, String nameProduct, String type, Map<String, Double> unitPriceBySize,
			String imageFilePath) {
		super();
		this.idProduct = idProduct;
		this.nameProduct = nameProduct;
		this.unitPriceBySize = unitPriceBySize;
		this.type = type;
		this.imageFilePath = imageFilePath;
	}

	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Double> getUnitPriceBySize() {
		return unitPriceBySize;
	}

	public void setUnitPriceBySize(Map<String, Double> unitPriceBySize) {
		this.unitPriceBySize = unitPriceBySize;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getDescriptionPromotion() {
		return descriptionPromotion;
	}

	public void setDescriptionPromotion(String descriptionPromotion) {
		this.descriptionPromotion = descriptionPromotion;
	}

	@Override
	public String toString() {
		return idProduct + ", " + nameProduct + ", " + type + ", unitPriceBySize=" + unitPriceBySize + ", "
				+ imageFilePath + ", " + descriptionPromotion;
	}

	@Override
	public Product clone() {
	    try {
	        Product cloned = (Product) super.clone();
	        cloned.unitPriceBySize = new HashMap<>(this.unitPriceBySize);
	        return cloned;
	    } catch (CloneNotSupportedException e) {
	        throw new AssertionError();
	    }
	}
}
