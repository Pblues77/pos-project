package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Bill implements Cloneable, java.io.Serializable {
	private LocalDateTime datetime;
	private String idBill;
	private Employee employee;
	private Customer customer;
	private Map<Product, List<SelectSizeQuantity>> products_quantity;// danh sách sản phẩm kh chọn: Size của Product sẽ
																		// KH chọn
	private double totalPrice;// tiền thanh toán
	private double moneyCustomer;// tiền khách đưa
	private double rebate;// tiền thừa
	private boolean isSuccessfulPayment;// kiểm tra thanh toán chưa?
	private boolean orderCompletion;// kiểm tra hoàn thanh sản phẩm?
	private String description = "";// mô tả khi đổi điểm
	private String note;//cho chế biến
	private List<ObserverBill> observerBills;//

	public Bill(String idBill, Employee employee, Customer customer) {
		super();
		this.datetime = LocalDateTime.now();
		this.idBill = idBill;
		this.employee = employee;
		this.customer = customer;
		this.products_quantity = new LinkedHashMap<>();
		moneyCustomer = 0;
		rebate = 0;
		totalPrice();
		observerBills = new ArrayList<>();
	}

	public Bill(String idBill, Employee employee) {
		super();
		this.datetime = LocalDateTime.now();
		this.idBill = idBill;
		this.employee = employee;
		this.customer = new Customer("", "");
		this.products_quantity = new LinkedHashMap<>();
		moneyCustomer = 0;
		rebate = 0;
		totalPrice();
		observerBills = new ArrayList<>();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Map<Product, List<SelectSizeQuantity>> getProducts_quantity() {
		return products_quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getMoneyCustomer() {
		return moneyCustomer;
	}

	public void setMoneyCustomer(double moneyCustomer) {
		this.moneyCustomer = moneyCustomer;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public String getIdBill() {
		return idBill;
	}

	public void setIdBill(String idBill) {
		this.idBill = idBill;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setProducts_quantity(Map<Product, List<SelectSizeQuantity>> products_quantity) {
		this.products_quantity = products_quantity;
	}

	public boolean isSuccessfulPayment() {
		return isSuccessfulPayment;
	}

	public void setSuccessfulPayment(boolean isSuccessfulPayment) {
		this.isSuccessfulPayment = isSuccessfulPayment;
	}

	public boolean isOrderCompletion() {
		return orderCompletion;
	}

	public void setOrderCompletion(boolean orderCompletion) {
		this.orderCompletion = orderCompletion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addObserverBill(ObserverBill observerBill) {
		this.observerBills.add(observerBill);
	}

	public void notifyObserver() {
		for (ObserverBill o : observerBills) {
			o.updateAddBill(this.clone());// phải clone mới ok
		}
	}

	/*
	 * Tính tổng giá tiền
	 */
	public void totalPrice() {
		double re = 0;
		for (Map.Entry<Product, List<SelectSizeQuantity>> entry : products_quantity.entrySet()) {
			Product k = entry.getKey();
			List<SelectSizeQuantity> v = entry.getValue();
			for (Map.Entry<String, Double> prices : k.getUnitPriceBySize().entrySet()) {
				for (SelectSizeQuantity sq : v) {
					if (sq.getSize() != null) {
						if (sq.getSize().equals(prices.getKey())) {
							re += prices.getValue() * sq.getQuantity();
						}
					} else {
						re += prices.getValue() * sq.getQuantity();
					}
				}
			}
		}
		this.totalPrice = re;
	}

	/*
	 * Thanh toán
	 */
	public boolean payment(double money) {
		totalPrice();
		this.moneyCustomer = money;
		this.rebate = money - totalPrice;
		isSuccessfulPayment = (this.rebate >= 0) ? (true) : false;
		if (isSuccessfulPayment == true) {
			customer.setloyaltyPoints(customer.getloyaltyPoints() + 1);
			customer.setnumberPurchases(customer.getnumberPurchases() + 1);
			this.notifyObserver();
		}
		return isSuccessfulPayment;
	}

	/*
	 * Thêm sản phẩm: size, số lượng
	 */
	public double addProductsQuantity(Product p, List<SelectSizeQuantity> sizeQuantity) {
		this.products_quantity.put(p.clone(), sizeQuantity);
		totalPrice();
		return totalPrice;
	}

	/*
	 * Tìm sản phẩm đã chọn mua
	 */
	private Product findProduct(String nameP) {
		for (Product p : products_quantity.keySet()) {
			if (p.getNameProduct().equals(nameP))
				return p;
		}
		return null;
	}

	/*
	 * Xóa sản phẩm đã chọn mua
	 */
	public void removeProducts(String nameP, String size) {
		Product p = findProduct(nameP);
		if (p != null && products_quantity.containsKey(p)) {
			List<SelectSizeQuantity> newValue = new ArrayList<>();
			for (SelectSizeQuantity s : products_quantity.get(p)) {
				if (s.getSize() == null && size.equals(""))
					;
				else if (!s.getSize().equals(size))
					newValue.add(s);
			}
			products_quantity.put(p, newValue);
			totalPrice();
		}
	}

	/*
	 * Thay đổi số lượng mua của 1 sản phẩm đã chọn của bill
	 */
	public void setQuantity(String nameP, String size, int q) {
		if (q != 0) {
			Product p = findProduct(nameP);
			if (p != null && products_quantity.containsKey(p)) {
				List<SelectSizeQuantity> newValue = new ArrayList<>();
				for (SelectSizeQuantity s : products_quantity.get(p)) {
					if (s.getSize() == null && size.equals("")) {
						newValue.add(new SelectSizeQuantity(s.getSize(), q));
					} else if (s.getSize().equals(size)) {
						newValue.add(new SelectSizeQuantity(s.getSize(), q));
					}
				}
				products_quantity.put(p, newValue);
				totalPrice();
			}
		}
	}

	/*
	 * In hóa đơn
	 */
	public String printBill() {
		String re = "<html>" + "<style>" + "table, th, td {" + "  border:1px solid black;" + "}" + "</style>"
				+ "<body>";

		// datatime
		re += "<p> Số hóa đơn: <b>" + this.getIdBill() + "</b><br>Ngày lập HĐ: <b>" + this.getDatetime().getDayOfMonth()
				+ "/" + this.getDatetime().getMonthValue() + "/" + this.getDatetime().getYear()
				+ "</b><br>Thời gian: <b>" + this.getDatetime().getHour() + ":" + this.getDatetime().getMinute() + ":"
				+ this.getDatetime().getSecond() + "</b><br>Nhân viên: <b>" + this.getEmployee().getNameEmployee()
				+ "</b><br>Khách hàng: <b>" + this.getCustomer().getName() + "</b>" + "<br></p>";
		// table
		re += "<table style=\"width:100%\">" + "    <th>STT</th>" + "    <th>Tên</th>" + "    <th>Size</th>"
				+ "    <th>Đơn giá</th>" + "    <th>Số lượng</th>" + "    <th>Thành tiền</th>" + "  </tr>";
		int stt = 0;
		for (Map.Entry<Product, List<SelectSizeQuantity>> entry : this.getProducts_quantity().entrySet()) {
			Product k = entry.getKey();
			List<SelectSizeQuantity> v = entry.getValue();
			for (SelectSizeQuantity sq : v) {
				stt++;
				if (k.getUnitPriceBySize().containsKey(sq.getSize())) {
					String idProduct = k.getIdProduct() + "";
					String nameProduct = k.getNameProduct();
					String despromotion = k.getDescriptionPromotion();
					String size = (sq.getSize() != null) ? sq.getSize() : "";
					String unitprice = (double) Math.round(k.getUnitPriceBySize().get(sq.getSize()) * 1000) / 1000 + "";
					String quantity = sq.getQuantity() + "";
					String totalPrice = (double) Math
							.round(k.getUnitPriceBySize().get(sq.getSize()) * sq.getQuantity() * 1000) / 1000 + "";
					String note = "";

					re += " <tr>" + "    <td>" + stt + "</td>" + "    <td>" + nameProduct + "</td>" + "    <td>" + size
							+ "</td>" + "    <td>" + unitprice + "</td>" + "    <td>" + quantity + "</td>" + "    <td>"
							+ totalPrice + "</td>" + "  </tr>";
				}
			}
		}
		re += "</table>";
		re += "<h4 style=\"text-align: right;\">Tổng giá: " + this.getTotalPrice() + "</h4>";
		re += "<br><hr><p>" + ((getNote() != null) ? getNote() : "") + "</p>";
		re += "</body>" + "</html>";
		return re;
	}

	@Override
	public Bill clone() {
			Bill clonedBill = new Bill(idBill, employee, customer);
			clonedBill.products_quantity = new LinkedHashMap<>();
			for (Map.Entry<Product, List<SelectSizeQuantity>> entry : products_quantity.entrySet()) {
				List<SelectSizeQuantity> newValue = new ArrayList<SelectSizeQuantity>();
				for (SelectSizeQuantity s : entry.getValue()) {
					newValue.add(s);
				}
				clonedBill.products_quantity.put(entry.getKey().clone(), newValue);
			}
			return clonedBill;
	}
}
