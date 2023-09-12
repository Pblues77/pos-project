package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import util.POSDataUtils;

public class PromotionManagement {
	private List<ObserverPromotion> observerPromotions;
	private List<Promotion> promotionWithoutConditions;
	private Map<String, List<Promotion>> promotionByCode;
	private TreeMap<Integer, Reward> rewards;// danh sách phần thưởng để đổi
	private POSDataUtils dataUtils;

	public PromotionManagement(List<Promotion> promotionWithoutConditions, Map<String, List<Promotion>> promotionByCode,
			TreeMap<Integer, Reward> rewards, POSDataUtils dataUtils) {
		super();
		this.promotionWithoutConditions = promotionWithoutConditions;
		this.promotionByCode = promotionByCode;
		this.rewards = rewards;
		observerPromotions = new ArrayList<>();
		this.dataUtils = dataUtils;
	}

	public List<ObserverPromotion> getObserverPromotions() {
		return observerPromotions;
	}

	//
	public void addObserverPromotion(ObserverPromotion observerPromotion) {
		observerPromotions.add(observerPromotion);
	}

	public void notifyObserver() {
		for (ObserverPromotion o : observerPromotions) {
			o.updateProductPrice();
		}
	}

	// Thêm KM:
	public void addPromotionWithoutCondition(Promotion p) {
		if (p != null) {
			if (promotionWithoutConditions.contains(p))
				promotionWithoutConditions.remove(p);
			promotionWithoutConditions.add(p);
		}

		dataUtils.writePromotionWithoutConditions(promotionWithoutConditions);//lưu data
		notifyObserver();
	}

	// Xóa KM:
	public void removePromotionWithoutCondition(String titlePromotion) {
		for (Promotion pr : promotionWithoutConditions) {
			if (pr.getTitle().equals(titlePromotion))
				promotionWithoutConditions.remove(pr);
		}
		dataUtils.writePromotionWithoutConditions(promotionWithoutConditions);//lưu data
		notifyObserver();
	}

	// Java tham chiếu nên chỉ cần write lại file
	public void addPromotionByCode(String code, List<Promotion> promotions) {
		if (code.length() > 0) {
			promotionByCode.put(code, promotions);
			dataUtils.writePromotionsByCode(promotionByCode);//lưu data
		}
	}

	public void removePromotionByCode(String code) {
		if (code.length() > 0) {
			promotionByCode.remove(code);
			dataUtils.writePromotionsByCode(promotionByCode);//lưu data
		}
	}

	public void addReward(Reward reward) {
		if (rewards.isEmpty())
			rewards.put(0, reward);
		else {
			Integer newkey = Collections.max(rewards.keySet()) + 1;
			rewards.put(newkey, reward);
		}
		dataUtils.writeRewards(rewards);//lưu data
	}

	public void removeReward(Integer key) {
		rewards.remove(key);
		dataUtils.writeRewards(rewards);//lưu data
	}

	private void renewProduct(Product p, List<Promotion> promotions) {
		if (!promotions.isEmpty()) {
			for (Promotion pr : promotions) {// Duyệt các Khuyến mãi
				// gia duoc giam
				renewProduct(p, pr);
			}
		}
	}

	private void renewProduct(Product p, Promotion promotion) {
		// gia duoc giam
		if (promotion.isStatus()) {
			promotion.discount();
			for (Product productPr : promotion.getResultProducts()) {// Duyệt các sản phẩm sẽ khuyến mãi
				if (p.getNameProduct().equals(productPr.getNameProduct())) {// Nếu các SP đang bán cùng tên
					// discount != 0
					if (promotion.isDiscount()) {
						Map<String, Double> unitPriceBySize = p.getUnitPriceBySize();
						for (Map.Entry<String, Double> entry : unitPriceBySize.entrySet()) {// Duyệt các SP
																							// đó để renew
																							// lại giá từng
																							// Size
							if (productPr.getUnitPriceBySize().containsKey(entry.getKey())) {// Nếu
								unitPriceBySize.put(entry.getKey(), p.getUnitPriceBySize().get(entry.getKey())
										- productPr.getUnitPriceBySize().get(entry.getKey()));
							}
						}
						p.setUnitPriceBySize(unitPriceBySize);
					}
				}
			}
			// mô tả
			String sizes = p.getUnitPriceBySize().keySet() + "";
			if (p.getUnitPriceBySize().keySet().contains(null))
				sizes = "duy nhất";
			String d = promotion.getDescription() + " với size " + sizes;

			if (p.getDescriptionPromotion() == null)
				p.setDescriptionPromotion(d + "; ");
			else
				p.setDescriptionPromotion(p.getDescriptionPromotion() + d + "; ");
		}
	}

	// KM không có đk
	public void renewProductsByPromotionWithoutConditions(List<Product> products) {
		for (Product p : products) {// Duyệt các Sản Phẩm hiện bán
			renewProduct(p, promotionWithoutConditions);
		}
	}

	// Đối với KM có đk -> nên thực hiện trong bill
	private void renewProductsByPromotionCode(Product p, String code) {
		if (promotionByCode.containsKey(code)) {
			renewProduct(p, promotionByCode.get(code));
		}
	}

	// đổi KM có đk
	public void usePromotionCodeForBill(Bill bill, String code) {
		if (!bill.getProducts_quantity().isEmpty()) {
			for (Map.Entry<Product, List<SelectSizeQuantity>> entry : bill.getProducts_quantity().entrySet()) {
				renewProductsByPromotionCode(entry.getKey(), code);
			}
			bill.totalPrice();
			removePromotionByCode(code);
		}
	}

	// Đổi Phần thưởng
	public void redeemRewardForBill(Bill bill, Reward reward) {
		if (reward.checkCustomerCondition(bill.getCustomer())) {
			// set mô tả bill += title phần thưởng
			bill.setDescription(reward.getTitleReward() + ", " + bill.getDescription());
			// set lại giá trong bill
			if (!bill.getProducts_quantity().isEmpty()) {
				for (Map.Entry<Product, List<SelectSizeQuantity>> entry : bill.getProducts_quantity().entrySet()) {
					renewProduct(entry.getKey(), reward.getPromotion());
				}
				bill.totalPrice();
			}
			// trừ điểm KH
			bill.getCustomer()
					.setloyaltyPoints(bill.getCustomer().getloyaltyPoints() - reward.getDepreciatedConsumptionPoints());
			// tính lại tiền
			bill.totalPrice();
		}
	}

	public List<Promotion> getPromotionWithoutConditions() {
		return promotionWithoutConditions;
	}

	public void setPromotionWithoutConditions(List<Promotion> promotionWithoutConditions) {
		this.promotionWithoutConditions = promotionWithoutConditions;
	}

	public Map<String, List<Promotion>> getPromotionByCode() {
		return promotionByCode;
	}

	public void setPromotionByCode(Map<String, List<Promotion>> promotionByCode) {
		this.promotionByCode = promotionByCode;
	}

	public TreeMap<Integer, Reward> getRewards() {
		return rewards;
	}

	public void setRewards(TreeMap<Integer, Reward> rewards) {
		this.rewards = rewards;
	}
}
