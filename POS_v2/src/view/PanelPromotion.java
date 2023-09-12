package view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView.TableCell;

import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class PanelPromotion extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_2;
	public JTable table;
	public DefaultTableModel model;
	public JRadioButton rb_promotionWithoutConditions;
	public JRadioButton rb_promotionByCode;
	public JRadioButton rb_rewards;

	/**
	 * Create the panel.
	 */
	public PanelPromotion() {
		this.setSize(1185, 722);
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Mã khuyến mại:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(50, 126, 148, 29);
		add(lblNewLabel);

		JLabel lblTnKhuynMi = new JLabel("Tên khuyến mại:");
		lblTnKhuynMi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTnKhuynMi.setBounds(50, 178, 148, 29);
		add(lblTnKhuynMi);

		JLabel lblLoiKhuynMi = new JLabel("Loại khuyến mại:");
		lblLoiKhuynMi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLoiKhuynMi.setBounds(50, 73, 148, 29);
		add(lblLoiKhuynMi);

		JLabel lblNgyBtu = new JLabel("Ngày bắt đầu:");
		lblNgyBtu.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNgyBtu.setBounds(50, 335, 148, 29);
		add(lblNgyBtu);

		JLabel lblNgyKtThc = new JLabel("Ngày kết thúc:");
		lblNgyKtThc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNgyKtThc.setBounds(50, 397, 148, 29);
		add(lblNgyKtThc);

		JLabel lblTrngThi = new JLabel("Trạng thái:");
		lblTrngThi.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTrngThi.setBounds(50, 454, 148, 29);
		add(lblTrngThi);

		textField = new JTextField();
		textField.setBounds(225, 125, 191, 26);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(225, 180, 191, 26);
		add(textField_1);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(225, 334, 191, 26);
		add(textField_3);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(225, 396, 191, 26);
		add(textField_4);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(225, 459, 191, 21);
		add(comboBox);

		JButton btnNewButton = new JButton("Tìm");
		btnNewButton.setBounds(426, 125, 57, 27);
		add(btnNewButton);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(225, 78, 191, 21);
		add(comboBox_1);

		JButton btnNewButton_1 = new JButton("Cập nhật");
		btnNewButton_1.setBounds(388, 512, 95, 34);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Thêm");
		btnNewButton_2.setBounds(288, 512, 90, 34);
		add(btnNewButton_2);

		JLabel lblSnPhmc = new JLabel("Sản phẩm áp dụng:");
		lblSnPhmc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSnPhmc.setBounds(50, 224, 165, 29);
		add(lblSnPhmc);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Tất cả");
		rdbtnNewRadioButton.setBounds(225, 230, 65, 21);
		add(rdbtnNewRadioButton);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Qu\u1EA3n l\u00FD danh s\u00E1ch khuy\u1EBFn m\u00E3i", TitledBorder.CENTER, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel.setBounds(548, 10, 627, 702);
		panel.setLayout(null);
		add(panel);

		JLabel lblNewLabel_1 = new JLabel("Tìm khuyến mại");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 25, 156, 19);
		panel.add(lblNewLabel_1);

		textField_2 = new JTextField();
		textField_2.setBounds(153, 26, 133, 19);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton_3 = new JButton("Tìm");
		btnNewButton_3.setBounds(296, 25, 57, 21);
		panel.add(btnNewButton_3);

		JLabel lblNewLabel_3 = new JLabel("Trạng thái:");
		lblNewLabel_3.setBounds(385, 93, 63, 13);
		panel.add(lblNewLabel_3);

		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(442, 89, 175, 21);
		panel.add(comboBox_3);

		JButton btnNewButton_2_1 = new JButton("Tùy chọn");
		btnNewButton_2_1.setBounds(326, 230, 90, 23);
		add(btnNewButton_2_1);

		table = new JTable();
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tiêu đề", "Tên sản phẩm áp dụng - Size",
				"Trạng thái", "Ngày bắt đầu", "Ngày kết thúc", "Thay đổi giá" }));

		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(70);
		model = (DefaultTableModel) table.getModel();

		// Cấu hình renderer để cho phép nhóm các cột

		//
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 120, 607, 573);
		panel.add(scrollPane);

		ButtonGroup g = new ButtonGroup();
		rb_promotionWithoutConditions = new JRadioButton("Khuyến mãi không điều kiện");
		g.add(rb_promotionWithoutConditions);
		rb_promotionWithoutConditions.setBounds(10, 50, 156, 21);
		panel.add(rb_promotionWithoutConditions);

		rb_promotionByCode = new JRadioButton("Khuyến mãi theo mã");
		rb_promotionByCode.setBounds(10, 70, 156, 21);

		g.add(rb_promotionByCode);
		panel.add(rb_promotionByCode);

		rb_rewards = new JRadioButton("Khuyến mãi đổi phần thưởng");
		rb_rewards.setBounds(10, 89, 156, 21);
		g.add(rb_rewards);
		panel.add(rb_rewards);
	}
}
