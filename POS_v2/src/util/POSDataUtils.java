package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.Bill;
import model.Customer;
import model.Employee;
import model.PercentageOffProductPromotion;
import model.Product;
import model.Promotion;
import model.Reward;
import model.SelectSizeQuantity;

public class POSDataUtils {
	private String pathProductsFile;
	private String pathEmployeesFile;
	private String pathCustomersFile;
	private String pathBillsFile;
	private String pathRewardsFile;
	private String pathPromotionsByCodeFile;
	private String pathPromotionWithoutConditionsFile;

	public POSDataUtils(String pathProductsFile, String pathEmployeesFile, String pathCustomersFile,
			String pathBillsFile, String pathRewardsFile, String pathPromotionsByCodeFile,
			String pathPromotionWithoutConditionsFile) {
		super();
		this.pathProductsFile = pathProductsFile;
		this.pathEmployeesFile = pathEmployeesFile;
		this.pathCustomersFile = pathCustomersFile;
		this.pathBillsFile = pathBillsFile;
		this.pathRewardsFile = pathRewardsFile;
		this.pathPromotionsByCodeFile = pathPromotionsByCodeFile;
		this.pathPromotionWithoutConditionsFile = pathPromotionWithoutConditionsFile;
	}

	public static <T> void write(T objects, String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(objects);
			out.close();
			fileOut.close();

			System.out.println("Serialized data is saved in file " + path);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static <T> T load(String path) {
		T objects = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			objects = (T) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Product class not found");
			c.printStackTrace();
			return null;
		}
		return objects;
	}

	public void writeProducts(List<Product> products) {
		POSDataUtils.write(products, pathProductsFile);
	}

	public List<Product> loadProducts() {
		List<Product> re = POSDataUtils.load(pathProductsFile);
		return (re != null) ? re : new ArrayList<>();
	}

	public void writeEmployee(List<Employee> employees) {
		POSDataUtils.write(employees, pathEmployeesFile);
	}

	public List<Employee> loadEmployees() {
		List<Employee> re = POSDataUtils.load(pathEmployeesFile);
		return (re != null) ? re : new ArrayList<>();
	}

	public void writeCustomers(List<Customer> customers) {
		POSDataUtils.write(customers, pathCustomersFile);
	}

	public List<Customer> loadCustomers() {
		List<Customer> re = POSDataUtils.load(pathCustomersFile);
		return (re != null) ? re : new ArrayList<>();
	}

	public void writeBills(List<Bill> bills) {
		POSDataUtils.write(bills, pathBillsFile);
	}

	public List<Bill> loadBills() {
		List<Bill> re = POSDataUtils.load(pathBillsFile);
		return (re != null) ? re : new ArrayList<>();
	}

	public void writePromotionWithoutConditions(List<Promotion> promotionWithoutConditions) {
		POSDataUtils.write(promotionWithoutConditions, pathPromotionWithoutConditionsFile);
	}

	public List<Promotion> loadPromotionWithoutConditions() {
		List<Promotion> re = POSDataUtils.load(pathPromotionWithoutConditionsFile);
		return (re != null) ? re : new ArrayList<>();
	}

	public void writeRewards(TreeMap<Integer, Reward> rewards) {
		POSDataUtils.write(rewards, pathRewardsFile);
	}

	public TreeMap<Integer, Reward> loadRewards() {
		TreeMap<Integer, Reward> re = POSDataUtils.load(pathRewardsFile);
		return (re != null) ? re : new TreeMap<>();
	}

	public void writePromotionsByCode(Map<String, List<Promotion>> promotionsByCode) {
		POSDataUtils.write(promotionsByCode, pathPromotionsByCodeFile);
	}

	public Map<String, List<Promotion>> loadPromotionsByCode() {
		Map<String, List<Promotion>> re = POSDataUtils.load(pathPromotionsByCodeFile);
		return (re != null) ? re : new HashMap<>();
	}
}
