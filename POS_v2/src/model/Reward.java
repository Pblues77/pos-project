package model;

import java.util.List;

/*
 * Khi khách hàng tới quán uống trà sữa 10 lần sẽ được nhận ưu đãi giảm giá 10% cho những lần mua tiếp theo
 * => Nhận ưu đãi giảm giá đến 30% cho lần mua sắm thứ 5
 * Khi tích lũy đủ 100 điểm bạn sẽ nhận được quà tặng đặc biệt
 * Khi tích lũy điểm đạt mốc 1000 điểm sẽ nhận được thẻ vàng với chiết khấu 5% cho tất cả các lần mua sau.
 * Quy đổi 1 điểm 1000đ. Khách hàng sẽ được tích số điểm tương ứng với tổng giá trị hóa đơn. 
 * => Ở lần mua sau, khách hàng có thể chọn thanh toán đổi điểm thành tiền để giảm trừ thanh toán với tỉ lệ quy đổi là 1 điểm = 1000đ.
 */
public class Reward implements java.io.Serializable{
	private String titleReward;
	private Promotion promotion;
	private long depreciatedConsumptionPoints;

	public Reward(String titleReward, Promotion promotion, long depreciatedConsumptionPoints) {
		super();
		this.titleReward = titleReward;
		this.promotion = promotion;
		this.depreciatedConsumptionPoints = depreciatedConsumptionPoints;
	} 

	public String getTitleReward() {
		return titleReward;
	}

	public void setTitleReward(String titleReward) {
		this.titleReward = titleReward;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public long getDepreciatedConsumptionPoints() {
		return depreciatedConsumptionPoints;
	}

	public void setDepreciatedConsumptionPoints(long depreciatedConsumptionPoints) {
		this.depreciatedConsumptionPoints = depreciatedConsumptionPoints;
	}
	
	public boolean checkCustomerCondition(Customer c) {
		if (c.getloyaltyPoints() >= this.depreciatedConsumptionPoints)
			return true;
		return false;
	}
}
