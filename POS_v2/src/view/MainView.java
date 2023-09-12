package view;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import controller.MainController;
import model.Employee;
import model.MyPOS;

public class MainView extends JFrame {
	ImageIcon icon_logo = new ImageIcon("images/logo.png");
	ImageIcon icon_sell = new ImageIcon("images/banhang.png");
	ImageIcon icon_bills = new ImageIcon("images/donhang.png");
	ImageIcon icon_customers = new ImageIcon("images/qlkh.png");
	ImageIcon icon_statistical = new ImageIcon("images/thongke.png");
	ImageIcon icon_employees = new ImageIcon("images/qlnv.png");
	ImageIcon icon_products = new ImageIcon("images/qlsp.png");
	ImageIcon icon_user = new ImageIcon("images/user.png");
	ImageIcon icon_promotions = new ImageIcon("images/khuyenmai.png");

	public JMenuItem mnt_setLogo;
	public JButton bt_sell;
	public JButton bt_bills;
	public JButton bt_customers;
	public JButton bt_statistical;
	public JButton bt_employees;
	public JButton bt_products;
	public JButton bt_promotions;
	public JMenuItem mnItem_logout;
	public JLabel lb_nameUser;
	public String user;

	private String nameShop = " ";
	public JPanel contentPane;

	public MainController controller;
	public MyPOS myPOS;

	public JPanel panel_main;
	public PanelSell panel_sell;
	public PanelPromotion panel_promotion;

	/**
	 * Create the frame.
	 */
	public MainView(MyPOS myPOS) {
		String login_username = JOptionPane.showInputDialog(null, "Username: ", "Login", JOptionPane.DEFAULT_OPTION);
		String login_password = JOptionPane.showInputDialog(null, "Password: ", "Login", JOptionPane.DEFAULT_OPTION);

		Employee emp = null;
		for (Employee e : myPOS.getEmployees()) {
			if (e.getUsername().equals(login_username) && e.getPassword().equals(login_password)) {
				emp = e;
				break;
			}
		}
		if (emp != null) {
			this.nameShop = "Coffee Shop";// set name shop
			myPOS.setWorkingEmp(emp);
			init(myPOS);
		}

	}

	public void init(MyPOS myPOS) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		this.myPOS = myPOS;
		controller = new MainController(this);
		panel_main = new JPanel(new BorderLayout());
		contentPane.add(panel_main, BorderLayout.CENTER);

		this.panel_sell = PanelSell.getInstance(myPOS, controller, panel_main);
		this.panel_promotion = new PanelPromotion();
		
		panel_head();
		panel_function();
		addEvent();
	}

	public void panel_head() {
		JPanel panel_head = new JPanel(new GridBagLayout());
		panel_head.setBackground(Color.CYAN);
		contentPane.add(panel_head, BorderLayout.NORTH);
		panel_head.setPreferredSize(new Dimension(contentPane.getWidth(), 50));
		/*
		 * JPanel panel_center = new JPanel(new GridBagLayout()); textArea_note = new
		 * JTextArea(3, 10); JScrollPane scrollPane = new JScrollPane(textArea_note);
		 * scrollPane.setBorder(BorderFactory.createTitledBorder("Ghi chú hóa đơn"));
		 * 
		 * bt_saveNote = new JButton("Lưu");
		 * 
		 * GridBagConstraints gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy =
		 * 0; gbc.weightx = 3; gbc.fill = GridBagConstraints.BOTH;
		 * panel_center.add(scrollPane, gbc);
		 * 
		 * gbc.gridx = 1; gbc.weightx = 1.0; panel_center.add(bt_saveNote, gbc);
		 */
		ClockLabel daydateLable = new ClockLabel("day_date");
		daydateLable.setFont(new Font("Tahoma", Font.BOLD, 20));
		ClockLabel timeLable = new ClockLabel("time");
		timeLable.setFont(new Font("Tahoma", Font.BOLD, 20));

		JMenuBar mnbar_account = new JMenuBar();
		mnbar_account.setFont(new Font("Tahoma", Font.BOLD, 20));
		mnbar_account.setBackground(Color.CYAN);
		mnbar_account.setBorderPainted(false);
		mnbar_account.setBounds(1139, 0, 45, 40);

		JMenu menu_acount = new JMenu();
		menu_acount.setIcon(icon_user);
		menu_acount.setHorizontalAlignment(SwingConstants.CENTER);
		;
		mnbar_account.add(menu_acount);
		mnItem_logout = new JMenuItem("Đăng xuất");
		menu_acount.add(mnItem_logout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.WEST;
		panel_head.add(daydateLable, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		panel_head.add(timeLable, gbc);
		gbc.gridx = 2;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		panel_head.add(mnbar_account, gbc);
	}

	public void panel_function() {
		JPanel panel_function = new JPanel();
		panel_function.setBackground(Color.CYAN);
		contentPane.add(panel_function, BorderLayout.WEST);
		panel_function.setLayout(new BoxLayout(panel_function, BoxLayout.Y_AXIS));

		Dimension dimensionForFunction = new Dimension(HandlerScreenResolution.resizeWidthByScreenResolution(200), 100);
		int spacing = 20;

		JMenuBar menuBar = new JMenuBar();
		panel_function.add(menuBar);
		JMenu logo = new JMenu();
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setBorder(null);
		logo.setIcon(icon_logo);
		logo.setMaximumSize(new Dimension(200, 200));
		menuBar.add(logo);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_sell = new JButton("<html><b>Bán Hàng<br></b></html>");
		bt_sell.setIcon(icon_sell);
		bt_sell.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_sell.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_sell);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_bills = new JButton("<html><b>Quản Lý<br>Đơn Hàng</b></html>");
		bt_bills.setIcon(icon_bills);
		bt_bills.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_bills.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_bills);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_customers = new JButton("<html><b>Quản Lý<br>Khách Hàng</b></html>");
		bt_customers.setIcon(icon_customers);
		bt_customers.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_customers.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_customers);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_statistical = new JButton("<html><b>Thống Kê<br></b></html>");
		bt_statistical.setIcon(icon_statistical);
		bt_statistical.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_statistical.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_statistical);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_employees = new JButton("<html><b>Quản Lý<br>Nhân Viên</b></html>");
		bt_employees.setIcon(icon_employees);
		bt_employees.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_employees.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_employees);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_products = new JButton("<html><b>Quản Lý<br>Sản Phẩm</b></html>");
		bt_products.setIcon(icon_products);
		bt_products.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_products.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_products);
		panel_function.add(Box.createVerticalStrut(spacing));

		bt_promotions = new JButton("<html><b>Khuyến mại<br></b></html>");
		bt_promotions.setIcon(icon_promotions);
		bt_promotions.setAlignmentX(Component.CENTER_ALIGNMENT);
		bt_promotions.setMaximumSize(dimensionForFunction);
		panel_function.add(bt_promotions);
		panel_function.add(Box.createVerticalStrut(spacing));
	}

	public void addEvent() {
		// MainView
		bt_sell.addActionListener(controller);
		bt_bills.addActionListener(controller);
		bt_customers.addActionListener(controller);
		bt_employees.addActionListener(controller);
		bt_products.addActionListener(controller);
		bt_statistical.addActionListener(controller);
		bt_promotions.addActionListener(controller);
		// Panel_Sell
		panel_sell.bt_usePromotion.addActionListener(controller);
		panel_sell.bt_calculateRebate.addActionListener(controller);
		panel_sell.bt_payment.addActionListener(controller);
		panel_sell.bt_addBill.addActionListener(controller);
		panel_sell.bt_deleteBill.addActionListener(controller);
		panel_sell.bt_useLoyaltyPoints.addActionListener(controller);
		panel_sell.bt_findCustomer.addActionListener(controller);
		panel_sell.bt_addCustomer.addActionListener(controller);
		panel_sell.bt_saveNote.addActionListener(controller);
		panel_sell.bt_editProducts.addActionListener(controller);
		// Panel_Promotion
		panel_promotion.rb_promotionWithoutConditions.addActionListener(controller);
		panel_promotion.rb_promotionByCode.addActionListener(controller);
		panel_promotion.rb_rewards.addActionListener(controller);
	}
}
