package model;

import java.util.HashMap;

public class SelectSizeQuantity implements Cloneable,java.io.Serializable {
	private String size;
	private Integer quantity;

	public SelectSizeQuantity(String size, Integer quantity) {
		super();
		this.size = size;
		this.quantity = quantity;
	}

	public SelectSizeQuantity(Integer quantity) {
		super();
		this.size = null;
		this.quantity = quantity;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "size: " + size + " - sl: " + quantity;
	}

	@Override
	protected SelectSizeQuantity clone() throws CloneNotSupportedException {
		try {
			SelectSizeQuantity cloned = (SelectSizeQuantity) super.clone();
			return cloned;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
