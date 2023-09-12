package model;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Giảm giá trực tiếp
 * Mua 1 tặng 1
 * Đồng giá sản phẩm
 */
public abstract class Promotion implements java.io.Serializable{
	protected String title;
	protected boolean isDiscount;
	protected List<Product> promotionalProducts;
	protected List<Product> resultProducts;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected boolean status;

	public Promotion(String title, List<Product> promotionalProducts, LocalDate startDate, LocalDate endDate,
			boolean status, boolean isDiscount) {
		super();
		this.title = title;
		this.isDiscount = isDiscount;
		this.promotionalProducts = promotionalProducts;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		copyProducts();
		checkStatus();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isDiscount() {
		return isDiscount;
	}

	public void setDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	public List<Product> getResultProducts() {
		return resultProducts;
	}

	public String getDescription() {
		return title;
	}

	public abstract void discount();// discount() = true -> có giảm giá -> child class tự triển khai lại danh sách

	private void checkStatus() {
		if (status == true) {
			LocalDate today = LocalDate.now();
			long cons1 = ChronoUnit.DAYS.between(this.startDate, today);
			long cons2 = ChronoUnit.DAYS.between(today, this.startDate);
			if (!(cons1 >= 0 && cons2 >= 0))
				status = false;
		}
	}

	public List<Product> copyProducts() {
		resultProducts = new ArrayList<>();
		if (!promotionalProducts.isEmpty()) {
			for (Product p : promotionalProducts) {
				resultProducts.add(p.clone());
			}
		}
		return resultProducts;
	}
}
