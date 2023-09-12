package test;

import model.Bill;

import model.Customer;
import model.Employee;
import model.MyPOS;
import model.PercentageOffProductPromotion;
import model.Product;
import model.Promotion;
import model.PromotionManagement;
import model.Reward;
import model.SelectSizeQuantity;
import util.POSDataUtils;
import view.MainView;
import view.ThemeMode;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class GUITest {
	public static void main(String[] args) {
		POSDataUtils dataUtils = new POSDataUtils("data/products.data", "data/employees.data", "data/customers.data",
				"data/bills.data", "data/rewards.data", "data/promotionByCode.data",
				"data/promotionWithoutConditions.data");
		/*
		 * Khi file chưa có dữ liệu
		 */
		MyPOS pos = GUITest.load_1(dataUtils);
		/*
		 * Khi file có dữ liệu
		 */
//		MyPOS pos = new MyPOS(dataUtils);
		try {
			ThemeMode.setDefaultTheme();
			MainView frame = new MainView(pos);
			frame.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static MyPOS load_1(POSDataUtils dataUtils) {

		// products
		Map<String, Double> unitPriceS1 = new HashMap<String, Double>();
		unitPriceS1.put("S", 15000.0);
		unitPriceS1.put("M", 20000.0);
		unitPriceS1.put("L", 25000.0);
		Product p1 = new Product(1, "Cà phê đá", "Cà phê", unitPriceS1, "images/cfd.jpg");

		Map<String, Double> unitPriceS2 = new HashMap<String, Double>();
		unitPriceS2.put("S", 20000.0);
		unitPriceS2.put("M", 25000.0);
		unitPriceS2.put("L", 30000.0);
		Product p2 = new Product(2, "Cà phê sữa đá", "Cà phê", unitPriceS2, "images/cfs.jpg");

		Map<String, Double> unitPriceS3 = new HashMap<String, Double>();
		unitPriceS3.put(null, 30000.0);
		Product p3 = new Product(3, "Sinh tố bơ", "Sinh tố", unitPriceS3, "images/stb.jpg");

		Map<String, Double> unitPriceS4 = new HashMap<String, Double>();
		unitPriceS4.put("S", 20000.0);
		unitPriceS4.put("M", 25000.0);
		unitPriceS4.put("L", 30000.0);
		Product p4 = new Product(4, "Bạc xỉu đá", "Cà phê", unitPriceS4, "images/bxd.jpg");

		Map<String, Double> unitPriceS5 = new HashMap<String, Double>();
		unitPriceS5.put("S", 20000.0);
		unitPriceS5.put("M", 25000.0);
		unitPriceS5.put("L", 30000.0);
		Product p5 = new Product(5, "Soda chanh", "Soda", unitPriceS5, "images/sodachanh.jpg");

		Map<String, Double> unitPriceS6 = new HashMap<String, Double>();
		unitPriceS6.put("S", 20000.0);
		unitPriceS6.put("M", 25000.0);
		unitPriceS6.put("L", 30000.0);
		Product p6 = new Product(6, "Soda dâu", "Soda", unitPriceS6, "images/sodadau.jpg");

		Map<String, Double> unitPriceS7 = new HashMap<String, Double>();
		unitPriceS7.put(null, 25000.0);
		Product p7 = new Product(7, "Thơm", "Nước ép", unitPriceS7, "images/thom.jpg");

		Map<String, Double> unitPriceS8 = new HashMap<String, Double>();
		unitPriceS8.put(null, 25000.0);
		Product p8 = new Product(8, "Cam", "Nước ép", unitPriceS8, "images/cam.jpg");

		List<Product> products = new ArrayList<>();
		products.add(p1);
		products.add(p2);
		products.add(p3);
		products.add(p4);
		products.add(p5);
		products.add(p6);
		products.add(p6);
		products.add(p7);
		products.add(p8);
		dataUtils.writeProducts(products);

		Employee e = new Employee(1, "PBlues", "Manager", "admin", "admin", LocalDate.of(2003, 7, 7), "Nam",
				LocalDate.of(2024, 6, 16), 8.0, 25.0);
		List<Employee> employees = new ArrayList<>();
		employees.add(e);
		dataUtils.writeEmployee(employees);

		PromotionManagement promotionManagement = new PromotionManagement(new ArrayList<>(), new HashMap<>(),
				new TreeMap<Integer, Reward>(), dataUtils);
		MyPOS pos = new MyPOS(dataUtils.loadProducts(), dataUtils.loadEmployees(), new ArrayList<>(),
				promotionManagement, new ArrayList<>(), dataUtils);

//		// thêm KM không đk
		Promotion pr1 = new PercentageOffProductPromotion("Giam gia 10% cho tat ca san pham", pos.getProducts(),
				LocalDate.now(), LocalDate.now(), true, 0.1);
		pos.getPromotionManagement().addPromotionWithoutCondition(pr1);

//		// KM theo code
		Promotion pr2 = new PercentageOffProductPromotion("Giảm giá 10% trong tháng nay", pos.getProducts(),
				LocalDate.now(), LocalDate.now(), true, 0.1);
		Promotion pr22 = new PercentageOffProductPromotion("Giảm giá 5% trong tháng nay", pos.getProducts(),
				LocalDate.now(), LocalDate.now(), true, 0.05);
		List<Promotion> pbcode = new ArrayList<>();
		pbcode.add(pr2);
		pbcode.add(pr22);
		pos.getPromotionManagement().addPromotionByCode("LBP", pbcode);
//
//		// Đổi điểm
		List<Product> temp = new ArrayList<>();
		temp.add(pos.getProducts().get(0));// nó tham chiếu -> đã dùng cách add vào bill thì product.clone()
		temp.add(pos.getProducts().get(1));
		Promotion pr3 = new PercentageOffProductPromotion("Giam gia 5%", temp, LocalDate.now(), LocalDate.now(), true,
				0.05);
		Reward r1 = new Reward("Rw", pr3, 2);
		pos.getPromotionManagement().addReward(r1);
		return pos;
	}

}
