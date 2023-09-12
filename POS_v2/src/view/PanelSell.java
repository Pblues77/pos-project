package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Bill;
import model.MyPOS;
import model.Product;
import model.SelectSizeQuantity;

public class PanelSell extends JPanel {
	public JTextField tf_phoneNumber;
	public JTextField tf_nameCustomer;
	public JTextField tf_loyaltyPoints;
	public JTextField tf_totalPrice;
	public JTextField tf_code;
	public JTextField tf_descriptionPromotion;
	public JTextField tf_moneyCustomer;
	public JTextField tf_rebate;
	public JPanel panel_selectProducts;

	public MyPOS myPOS;
	public ActionListener actionListener;

	public DefaultTableModel model;
	public JTable table;
	public JLabel lb_idBill;
	public JButton bt_usePromotion;
	public JButton bt_calculateRebate;
	public JButton bt_payment;
	public JButton bt_addBill;
	public JButton bt_deleteBill;
	public JButton bt_useLoyaltyPoints;
	public JButton bt_findCustomer;
	public JButton bt_addCustomer;
	public JTextArea textArea_note;
	public JButton bt_saveNote;
	public JButton bt_editProducts;
	private JPanel products_panel;
	public JPanel billCodes_panel;

	private static PanelSell instance;

	private PanelSell(MyPOS myPOS, ActionListener actionListener, Container parent) {
		this.myPOS = myPOS;
		this.actionListener = actionListener;

		this.setLayout(new BorderLayout());
		products_panel(myPOS.getProducts(), parent);
		bills_panel(parent);
	}

	public static PanelSell getInstance(MyPOS myPOS, ActionListener actionListener, Container parent) {
		if (instance == null)
			instance = new PanelSell(myPOS, actionListener, parent);
		return instance;
	}

	/*
	 * products_panel(BoderLayout): --->NORTH: filterProducts_panel(FlowLayout)
	 * --->CENTER: panel_selectProducts(FlowLayout)
	 */
	public void products_panel(List<Product> products, Container parent) {
		products_panel = new JPanel(new BorderLayout(0, 0));
		products_panel.setBorder(new LineBorder(MyColor.BORDER_COLOR, 5));
		this.add(products_panel, BorderLayout.CENTER);
		products_panel.setPreferredSize(
				new Dimension(HandlerScreenResolution.resizeWidthByScreenResolution(700), parent.getHeight()));
		// filter Products
		filter(myPOS);
		// select products
		panel_selectProducts = new JPanel();
		panel_selectProducts.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
		JScrollPane scrollPane = new JScrollPane(panel_selectProducts);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		products_panel.add(scrollPane, BorderLayout.CENTER);
		products(products);
	}

	public void products(List<Product> products) {
		// panel
		panel_selectProducts.setPreferredSize(new Dimension(HandlerScreenResolution.resizeWidthByScreenResolution(820),
				(HandlerScreenResolution.resizeWidthByScreenResolution((products.size() / 2 + 1) * 200)
						+ (products.size() / 2 + 1) * 30)));

		for (Product p : products) {
			ImageIcon icon_thongke = new ImageIcon(p.getImageFilePath());
			JButton bt_thongke = new JButton(p.getNameProduct());
			bt_thongke.setBorder(BorderFactory.createLineBorder(MyColor.BORDER_ProductButtons_COLOR, 3));
			bt_thongke.setVerticalTextPosition(SwingConstants.BOTTOM);
			bt_thongke.setHorizontalTextPosition(SwingConstants.CENTER);
			bt_thongke.setIcon(icon_thongke);
			bt_thongke.setSize(new Dimension(HandlerScreenResolution.resizeWidthByScreenResolution(240),
					HandlerScreenResolution.resizeWidthByScreenResolution(200)));
			bt_thongke.addActionListener(actionListener);
			panel_selectProducts.add(bt_thongke);
		}
	}

	public void filter(MyPOS myPOS) {
		Map<String, List<Product>> groupProducts = new LinkedHashMap<>();
		groupProducts.put("Tất cả", myPOS.getProducts());
		for (Product p : myPOS.getProducts()) {
			if (groupProducts.containsKey(p.getType())) {
				List<Product> newValue = groupProducts.get(p.getType());
				newValue.add(p);
				groupProducts.put(p.getType(), newValue);
			} else {
				groupProducts.put(p.getType(), new ArrayList<Product>());
			}
		}

		JPanel filterProducts_panel = new JPanel();
		filterProducts_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		filterProducts_panel.setBackground(MyColor.FILTER_BACKGROUND_COLOR);
		int w = HandlerScreenResolution.resizeWidthByScreenResolution(130);
		int h = HandlerScreenResolution.resizeWidthByScreenResolution(50);
		filterProducts_panel.setPreferredSize(new Dimension(groupProducts.size() * w + groupProducts.size() * 20 + 20,
				HandlerScreenResolution.resizeWidthByScreenResolution(80)));

		for (String i : groupProducts.keySet()) {
			JButton bt_filter = new JButton(i);
			bt_filter.setPreferredSize(new Dimension(w, h));
			bt_filter.addActionListener(actionListener);
			filterProducts_panel.add(bt_filter);
		}
		JScrollPane sb = new JScrollPane(filterProducts_panel);
		sb.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sb.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		products_panel.add(sb, BorderLayout.NORTH);
	}

	/*
	 * bills_panel(BoderLayout): --->NORTH: billCodesAndEdit(GridLayout(2,0)):
	 * billCodes_panel and edit_panel --->CENTER: billInfo_panel
	 */
	public void bills_panel(Container parent) {
		JPanel bills_panel = new JPanel();
		bills_panel.setBorder(new LineBorder(MyColor.BORDER_COLOR, 5));
		bills_panel.setLayout(new BorderLayout());
		this.add(bills_panel, BorderLayout.EAST);
		bills_panel.setPreferredSize(
				new Dimension((int) HandlerScreenResolution.resizeWidthByScreenResolution(770), parent.getHeight()));
		// head
		JPanel billCodesAndEdit_panel = new JPanel();
		billCodesAndEdit_panel.setLayout(new GridLayout(2, 0));
		// bill codes
		billCodes_panel = new JPanel();
		billCodes_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		billCodes_panel.setBackground(MyColor.FILTER_BACKGROUND_COLOR);

		JScrollPane sb = new JScrollPane(billCodes_panel);
		sb.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		sb.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		billCodesAndEdit_panel.add(sb);

		restartShowBills();
		// them, xoa, chinh sua
		JPanel edit_panel = new JPanel();
		edit_panel.setLayout(new GridLayout(0, 5));
		edit_panel.add(new JLabel("<html>Working <br>Bill Code:</html>"));
		lb_idBill = new JLabel("");
		lb_idBill.setFont(new Font(getFont().getName(), getFont().BOLD, getFont().getSize()));
		edit_panel.add(lb_idBill);
		bt_addBill = new JButton("Thêm HĐ");
		edit_panel.add(bt_addBill);
		bt_editProducts = new JButton("<html>Chỉnh sửa<br>HĐ</html>");
		edit_panel.add(bt_editProducts);
		bt_deleteBill = new JButton("Xóa HĐ");
		edit_panel.add(bt_deleteBill);
		billCodesAndEdit_panel.add(edit_panel);
		bills_panel.add(billCodesAndEdit_panel, BorderLayout.NORTH);

		//
		JPanel billInfo_panel = new JPanel(new BorderLayout());
		bills_panel.add(billInfo_panel, BorderLayout.CENTER);
		// table
		table = new JTable();
		table.setEnabled(false);
		table.setBorder(null);
		table.setModel(new DefaultTableModel(new String[][] {},
				new String[] { "STT", "Tên SP", "Khuyến mãi áp dụng", "Size", "Đơn Giá", "SL", "Thành Tiền" }));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getColumnModel().getColumn(2)
				.setPreferredWidth(HandlerScreenResolution.resizeWidthByScreenResolution(130));
		table.setRowHeight(20);

		model = (DefaultTableModel) table.getModel();

		JScrollPane sp = new JScrollPane(table);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		billInfo_panel.add(sp, BorderLayout.CENTER);
		//
		JPanel customerAndPayment_Info = new JPanel(new BorderLayout());
		customerAndPayment_Info.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		billInfo_panel.add(customerAndPayment_Info, BorderLayout.SOUTH);

		JPanel customerInfo_panel = new JPanel(new GridLayout(4, 3));
		customerAndPayment_Info.add(customerInfo_panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("SĐT:");
		customerInfo_panel.add(lblNewLabel);

		tf_phoneNumber = new JTextField();
		customerInfo_panel.add(tf_phoneNumber);
		tf_phoneNumber.setColumns(10);

		bt_findCustomer = new JButton("Tìm");
		customerInfo_panel.add(bt_findCustomer);

		JLabel lblNewLabel_2 = new JLabel("Họ tên:");
		customerInfo_panel.add(lblNewLabel_2);

		tf_nameCustomer = new JTextField();
		tf_nameCustomer.setColumns(10);
		customerInfo_panel.add(tf_nameCustomer);

		bt_addCustomer = new JButton("Thêm");
		customerInfo_panel.add(bt_addCustomer);

		JLabel lblNewLabel_3 = new JLabel("Điểm tích lũy:");
		customerInfo_panel.add(lblNewLabel_3);

		tf_loyaltyPoints = new JTextField();
		tf_loyaltyPoints.setEditable(false);
		customerInfo_panel.add(tf_loyaltyPoints);
		tf_loyaltyPoints.setColumns(10);

		bt_useLoyaltyPoints = new JButton("Đổi điểm");
		customerInfo_panel.add(bt_useLoyaltyPoints);

		JLabel lblNewLabel_1_1_1 = new JLabel("Mã khuyến mại:");
		customerInfo_panel.add(lblNewLabel_1_1_1);

		tf_code = new JTextField();
		tf_code.setColumns(10);
		customerInfo_panel.add(tf_code);

		bt_usePromotion = new JButton("Kiểm tra");
		customerInfo_panel.add(bt_usePromotion);
		// center
		JPanel panel_center = new JPanel(new GridBagLayout());

		tf_descriptionPromotion = new JTextField();
		tf_descriptionPromotion.setEditable(false);
		tf_descriptionPromotion.setColumns(10);

		textArea_note = new JTextArea(3, 10);
		JScrollPane scrollPane = new JScrollPane(textArea_note);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Ghi chú hóa đơn"));

		bt_saveNote = new JButton("Lưu");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 10;
		gbc.fill = GridBagConstraints.BOTH;
		panel_center.add(tf_descriptionPromotion, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 3;
		panel_center.add(scrollPane, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		panel_center.add(bt_saveNote, gbc);

		customerAndPayment_Info.add(panel_center, BorderLayout.CENTER);
		// south
		JPanel payment_panel = new JPanel(new GridLayout(3, 3));
		customerAndPayment_Info.add(payment_panel, BorderLayout.SOUTH);

		JLabel lblNewLabel_1_1 = new JLabel("Tổng tiền hóa đơn");
		lblNewLabel_1_1.setHorizontalAlignment(JLabel.CENTER);
		payment_panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_4 = new JLabel("Tiền khách trả");
		lblNewLabel_4.setHorizontalAlignment(JLabel.CENTER);
		payment_panel.add(lblNewLabel_4);

		bt_calculateRebate = new JButton("TÍnh tiền thừa");
		payment_panel.add(bt_calculateRebate);

		tf_totalPrice = new JTextField();
		tf_totalPrice.setEditable(false);
		tf_totalPrice.setColumns(10);
		payment_panel.add(tf_totalPrice);

		tf_moneyCustomer = new JTextField();
		tf_moneyCustomer.setColumns(10);
		payment_panel.add(tf_moneyCustomer);

		tf_rebate = new JTextField();
		tf_rebate.setEditable(false);
		tf_rebate.setColumns(10);
		payment_panel.add(tf_rebate);

		payment_panel.add(new JLabel());
		bt_payment = new JButton("Thanh Toán");
		payment_panel.add(bt_payment);
		payment_panel.add(new JLabel());

	}

	public void restartShowBills() {
		int n = myPOS.getNotSuccessfulPaymentBills().size();
		int w = HandlerScreenResolution.resizeWidthByScreenResolution(100);
		int h = HandlerScreenResolution.resizeWidthByScreenResolution(25);

		billCodes_panel.setMaximumSize(new Dimension(n * w + n * h + 20, 25));
		billCodes_panel.removeAll();
		for (Bill b : myPOS.getNotSuccessfulPaymentBills()) {
			if (!b.isSuccessfulPayment()) {
				JButton bt = new JButton(b.getIdBill());
				bt.addActionListener(actionListener);
				bt.setHorizontalTextPosition(SwingConstants.CENTER);
				billCodes_panel.add(bt);
			}
		}
		billCodes_panel.setVisible(false);
		billCodes_panel.setVisible(true);
	}

	public void resetBillInterface() {
		resetTableBill();
		this.lb_idBill.setText("");
		this.tf_phoneNumber.setText("");
		this.tf_nameCustomer.setText("");
		this.tf_loyaltyPoints.setText("");
		this.tf_code.setText("");
		this.tf_descriptionPromotion.setText("");
		this.tf_totalPrice.setText("");
		this.tf_moneyCustomer.setText("");
		this.tf_rebate.setText("");
		this.textArea_note.setText("");
	}

	private void resetTableBill() {
		while (this.model.getRowCount() != 0) {
			this.model.removeRow(0);
		}
		this.model = (DefaultTableModel) this.table.getModel();
	}

	public void restartBillInterface(Bill b) {
		resetBillInterface();
		// table
		int stt = 0;
		for (Map.Entry<Product, List<SelectSizeQuantity>> entry : b.getProducts_quantity().entrySet()) {
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
					this.model.addRow(
							new Object[] { stt, nameProduct, despromotion, size, unitprice, quantity, totalPrice });

				}
			}
		}
		//
		this.lb_idBill.setText(b.getIdBill());
		this.tf_totalPrice.setText((double) Math.round(b.getTotalPrice() * 1000) / 1000 + "");
		this.tf_phoneNumber.setText(b.getCustomer().getPhoneNumber());
		this.tf_nameCustomer.setText(b.getCustomer().getName());
		this.tf_loyaltyPoints.setText(b.getCustomer().getloyaltyPoints() + "");
		this.textArea_note.setText(b.getNote());
		if (b.getCustomer().getName().length() > 0) {
			tf_phoneNumber.setEditable(false);
			tf_nameCustomer.setEditable(false);
		} else {
			tf_phoneNumber.setEditable(true);
			tf_nameCustomer.setEditable(true);
		}
		this.tf_descriptionPromotion.setText(b.getDescription());
	}

	public Bill getCurrentBill(String idBill) {
		for (Bill b : myPOS.getNotSuccessfulPaymentBills()) {
			if (b.getIdBill().equals(idBill)) {
				return b;
			}
		}
		return null;
	}
}
