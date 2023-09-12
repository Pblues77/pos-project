package model;

public class Customer implements java.io.Serializable {
	private String phoneNumber;
	private String name;
	private long loyaltyPoints;
	private long numberPurchases;

	
	
	public Customer(String phoneNumber, String name, long loyaltyPoints, long numberPurchases) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.loyaltyPoints = loyaltyPoints;
		this.numberPurchases = numberPurchases;
	}

	public Customer(String phoneNumber, String name) {
		super();
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.loyaltyPoints = 0;
		this.numberPurchases = 0;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getloyaltyPoints() {
		return loyaltyPoints;
	}

	public void setloyaltyPoints(long loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public long getnumberPurchases() {
		return numberPurchases;
	}

	public void setnumberPurchases(long numberPurchases) {
		this.numberPurchases = numberPurchases;
	}
}
