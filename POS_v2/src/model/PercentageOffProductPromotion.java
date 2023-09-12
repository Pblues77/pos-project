package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PercentageOffProductPromotion extends Promotion {
	private double percentage;

	public PercentageOffProductPromotion(String title, List<Product> promotionalProducts, LocalDate startDate,
			LocalDate endDate, boolean status, double percentage) {
		super(title, promotionalProducts, startDate, endDate, status, true);
		this.percentage = percentage;
	}

	@Override
	public void discount() {
		copyProducts();
		if (isDiscount())
			for (Product p : resultProducts) {
				for (Map.Entry<String, Double> entry : p.getUnitPriceBySize().entrySet()) {
					Double newValueUP = (entry.getValue() * percentage);
					p.getUnitPriceBySize().put(entry.getKey(), newValueUP);
				}
			}
	}

	@Override
	public String getDescription() {
		return "Giảm giá " + percentage*100 + " % ";
	}
}
