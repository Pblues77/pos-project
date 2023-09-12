package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import util.POSDataUtils;

public class MyPOS implements ObserverPromotion, ObserverBill {
	// data base
	private Employee workingEmp;
	private List<Product> srcProducts;
	private List<Product> products;// danh sách sản phẩm
	private List<Employee> employees;// danh sách nhân viên
	private List<Customer> customers;// danh sách khách hàng
	private PromotionManagement promotionManagement;// danh sách khuyến mãi của cửa hàng
	private List<Bill> bills;// lưu trữ hóa đơn
	private List<Bill> notSuccessfulPaymentBills;
	private POSDataUtils dataUtils;
	//

	public MyPOS(List<Product> srcProducts, List<Employee> employees, List<Customer> customers,
			PromotionManagement promotionManagement, List<Bill> bills, POSDataUtils dataUtils) {
		super();
		this.dataUtils = dataUtils;
		this.srcProducts = srcProducts;
		this.employees = employees;
		this.customers = customers;
		this.promotionManagement = promotionManagement;
		this.bills = bills;
		this.products = dataUtils.loadProducts();
		this.notSuccessfulPaymentBills = new ArrayList<>();
		// Khi KM ko cần đk thay đổi -> set giá lại
		promotionManagement.addObserverPromotion(this);
		// load lại SP nếu có KM
		if (!promotionManagement.getPromotionWithoutConditions().isEmpty())
			promotionManagement.renewProductsByPromotionWithoutConditions(products);
	}

	public MyPOS(POSDataUtils dataUtils) {
		this.dataUtils = dataUtils;
		this.srcProducts = dataUtils.loadProducts();
		this.employees = dataUtils.loadEmployees();
		this.customers = dataUtils.loadCustomers();
		List<Promotion> promotionWithoutConditions = dataUtils.loadPromotionWithoutConditions();
		Map<String, List<Promotion>> promotionByCode = dataUtils.loadPromotionsByCode();
		TreeMap<Integer, Reward> rewards = dataUtils.loadRewards();
		this.promotionManagement = new PromotionManagement(promotionWithoutConditions, promotionByCode, rewards,
				dataUtils);
		this.bills = dataUtils.loadBills();
		this.products = dataUtils.loadProducts();
		this.notSuccessfulPaymentBills = new ArrayList<>();
		// Khi KM ko cần đk thay đổi -> set giá lại
		promotionManagement.addObserverPromotion(this);
		// load lại SP nếu có KM
		if (!promotionManagement.getPromotionWithoutConditions().isEmpty())
			promotionManagement.renewProductsByPromotionWithoutConditions(products);
	}

	public Employee getWorkingEmp() {
		return workingEmp;
	}

	public void setWorkingEmp(Employee workingEmp) {
		this.workingEmp = workingEmp;
	}

	@Override
	public void updateProductPrice() {
		this.products = dataUtils.loadProducts();
		if (!promotionManagement.getPromotionWithoutConditions().isEmpty())
			promotionManagement.renewProductsByPromotionWithoutConditions(products);
	}

	@Override
	public void updateAddBill(Bill bill) {
		if (!bill.getProducts_quantity().isEmpty()) {
			this.bills.add(bill);
			this.dataUtils.writeBills(bills);// lưu data
		}
		this.notSuccessfulPaymentBills.remove(bill);
	}

	public List<Bill> getNotSuccessfulPaymentBills() {
		return notSuccessfulPaymentBills;
	}

	public List<Product> getProducts() {
		return products;
	}

	public List<Product> getSrcProducts() {
		return srcProducts;
	}

	public void setSrcProducts(List<Product> srcProducts) {
		this.srcProducts = srcProducts;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public PromotionManagement getPromotionManagement() {
		return promotionManagement;
	}

	public void setPromotionManagement(PromotionManagement promotionManagement) {
		this.promotionManagement = promotionManagement;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	// Tìm Sản Phẩm
	public Product findProductByNameProduct(String nameProduct) {
		for (Product c : products) {
			if (c.getNameProduct().equals(nameProduct))
				return c;
		}
		return null;
	}

	// Thêm sản phẩm
	public void addProduct(Product p) {
		this.srcProducts.add(p);
		this.dataUtils.writeProducts(srcProducts);// lưu data
		this.products = dataUtils.loadProducts();
		if (!promotionManagement.getPromotionWithoutConditions().isEmpty())
			promotionManagement.renewProductsByPromotionWithoutConditions(products);
	}

	// Xóa sản phẩm
	public void removeProduct(String nameProduct) {
		Product p = findProductByNameProduct(nameProduct);
		if (p != null) {
			this.srcProducts.remove(p);
			this.dataUtils.writeProducts(srcProducts);// lưu data
			this.products = dataUtils.loadProducts();
			if (!promotionManagement.getPromotionWithoutConditions().isEmpty())
				promotionManagement.renewProductsByPromotionWithoutConditions(products);
		}
	}

	// Tìm nhân viên
	public Employee findEmpoyeeById(int idEmployee) {
		for (Employee e : employees) {
			if (e.getIdEmployee() == idEmployee)
				return e;
		}
		return null;
	}

	// Thêm nhân viên
	public void addEmployee(Employee e) {
		this.employees.add(e);
		this.dataUtils.writeEmployee(employees);
	}

	// Xóa nhân viên
	public void removeEmployee(int idEmployee) {
		Employee e = findEmpoyeeById(idEmployee);
		if (e != null) {
			this.employees.remove(e);
			this.dataUtils.writeEmployee(employees);
		}
	}

	// Tìm khách hàng
	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		for (Customer c : customers) {
			if (c.getPhoneNumber().equals(phoneNumber))
				return c;
		}
		return null;
	}

	// Thêm khách hàng
	public boolean addCustomer(Customer c) {
		for (Customer temp : this.customers) {
			if (temp.getPhoneNumber().equals(c.getPhoneNumber()))
				return false;
		}
		this.customers.add(c);
		this.dataUtils.writeCustomers(customers);// lưu data
		return true;
	}

	// Xóa khách hàng
	public void removeCustomer(String phoneNumber) {
		Customer c = findCustomerByPhoneNumber(phoneNumber);
		if (c != null) {
			this.customers.remove(c);
			this.dataUtils.writeCustomers(customers);// lưu data
		}
	}

	// Tìm bill
	public Bill findBillById(String idBill) {
		for (Bill b : bills) {
			if (b.getIdBill().equals(idBill))
				return b;
		}
		return null;
	}

	// Thêm Bill
	public void addBilll(Bill b) {
		this.bills.add(b);
		this.dataUtils.writeBills(bills);// lưu data
	}

	public void removeBill(String idBill) {
		Bill b = findBillById(idBill);
		if (b != null) {
			this.bills.remove(b);
			this.dataUtils.writeBills(bills);// lưu data
		}
	}

	// tim bill chua thanh toan
	public Bill findNotSuccessfulPaymentBillById(String idBill) {
		for (Bill b : notSuccessfulPaymentBills) {
			if (b.getIdBill().equals(idBill))
				return b;
		}
		return null;
	}

	public void removeNotSuccessfulPaymentBill(String idBill) {
		Bill b = findNotSuccessfulPaymentBillById(idBill);
		if (b != null)
			this.notSuccessfulPaymentBills.remove(b);
	}

	// nhóm các sản phẩm cùng Type
	public Map<String, List<Product>> groupProducts() {
		Map<String, List<Product>> groupProducts = new HashMap<>();
		groupProducts.put("Tất cả", getProducts());
		for (Product p : getProducts()) {
			if (groupProducts.containsKey(p.getType())) {
				List<Product> newValue = groupProducts.get(p.getType());
				newValue.add(p);
				groupProducts.put(p.getType(), newValue);
			} else {
				List<Product> newValue = new ArrayList<Product>();
				newValue.add(p);
				groupProducts.put(p.getType(), newValue);
			}
		}
		return groupProducts;
	}

	// kiểm tra type có tồn tại?
	public boolean isType(String type) {
		Map<String, List<Product>> groupProducts = groupProducts();
		if (groupProducts.containsKey(type)) {
			return true;
		}
		return false;
	}

	public Bill createBill(Employee employee, Customer customer) {
		String idBill = getRandomString();
		for (int i = 0; i < bills.size(); i++) {
			if (idBill.equals(bills.get(i).getIdBill())) {
				idBill = getRandomString();
				break;
			}
			if (i == bills.size() - 1)
				i = 0;
		}
		Bill newBill = new Bill(idBill, employee, customer);
		this.notSuccessfulPaymentBills.add(newBill);
		return newBill;
	}

	public Bill createBill(Employee employee) {
		String idBill = getRandomString();
		for (int i = 0; i < bills.size(); i++) {
			if (idBill.equals(bills.get(i).getIdBill())) {
				idBill += bills.size();
			}
		}
		Bill newBill = new Bill(idBill, employee);
		this.notSuccessfulPaymentBills.add(newBill);
		newBill.addObserverBill(this);// i'm fine
		return newBill;
	}

	private String getRandomString() {
		int length = 5;
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
}
