package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Bill;
import model.Customer;
import model.Product;
import model.Promotion;
import model.Reward;
import model.SelectSizeQuantity;
import view.MainView;

public class MainController implements ActionListener {
	private MainView view;

	public MainController(MainView view) {
		super();
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == view.bt_sell) {
			resetBackgroundButton();
			view.bt_sell.setForeground(Color.ORANGE);
			
			view.panel_main.removeAll();
			view.panel_main.add(view.panel_sell);
			view.panel_main.setVisible(false);
			view.panel_main.setVisible(true);
			

		} else if (e.getSource() == view.bt_bills) {
			resetBackgroundButton();
			view.bt_bills.setForeground(Color.ORANGE);

		} else if (e.getSource() == view.bt_customers) {
			resetBackgroundButton();
			view.bt_customers.setForeground(Color.ORANGE);

		} else if (e.getSource() == view.bt_statistical) {
			resetBackgroundButton();
			view.bt_statistical.setForeground(Color.ORANGE);

		} else if (e.getSource() == view.bt_employees) {
			resetBackgroundButton();
			view.bt_employees.setForeground(Color.ORANGE);

		} else if (e.getSource() == view.bt_products) {
			resetBackgroundButton();
			view.bt_products.setForeground(Color.ORANGE);

		} else if (e.getSource() == view.bt_promotions) {
			resetBackgroundButton();
			view.bt_promotions.setForeground(Color.ORANGE);
			
			view.panel_main.removeAll();
			view.panel_main.add(view.panel_promotion);
			view.panel_main.setVisible(false);
			view.panel_main.setVisible(true);
		}
		// Lọc SP
		else if (view.myPOS.isType(e.getActionCommand())) {
			Map<String, List<Product>> groupProducts = view.myPOS.groupProducts();
			view.panel_sell.panel_selectProducts.removeAll();
			view.panel_sell.panel_selectProducts.setVisible(false);
			view.panel_sell.panel_selectProducts.setVisible(true);

			view.panel_sell.panel_selectProducts.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 30));
			view.panel_sell.panel_selectProducts
					.setPreferredSize(new Dimension(700, (groupProducts.get(e.getActionCommand()).size() / 3 + 1) * 160
							+ (groupProducts.get(e.getActionCommand()).size() / 3 + 1) * 30));
			view.panel_sell.products(groupProducts.get(e.getActionCommand()));// lọc -> đưa lên JPanel
		}
		// Thêm SP
		else if (view.myPOS.findProductByNameProduct(e.getActionCommand()) != null
				&& (view.panel_sell.lb_idBill.getText().length() > 0)) {
			Product p = view.myPOS.findProductByNameProduct(e.getActionCommand());
			Bill b = view.panel_sell.getCurrentBill(view.panel_sell.lb_idBill.getText());

			addProductEventHandler(p, b);
		}
		// Show Bill
		else if (view.myPOS.findNotSuccessfulPaymentBillById(e.getActionCommand()) != null) {
			Bill b = view.myPOS.findNotSuccessfulPaymentBillById(e.getActionCommand());
			view.panel_sell.restartBillInterface(b);
		}
		// Sử dụng KM
		else if (view.panel_sell.bt_usePromotion == e.getSource()) {
			String code = view.panel_sell.tf_code.getText();
			Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
			view.myPOS.getPromotionManagement().usePromotionCodeForBill(b, code);// đã sử dụng KM
			view.panel_sell.restartBillInterface(b);
		}
		// Tính tiền
		else if (view.panel_sell.bt_calculateRebate == e.getSource()) {
			if (view.panel_sell.tf_moneyCustomer.getText().length() > 0) {
				Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
				double money = Double.parseDouble(view.panel_sell.tf_moneyCustomer.getText());
				b.totalPrice();
				double rebate = money - b.getTotalPrice();
				if (rebate >= 0)
					view.panel_sell.tf_rebate.setText(rebate + "");
				else {
					JOptionPane.showMessageDialog(null, "Số tiền không đủ để thanh toán!", null,
							JOptionPane.INFORMATION_MESSAGE);
					view.panel_sell.tf_moneyCustomer.setText("");
					view.panel_sell.tf_rebate.setText("");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền!", null, JOptionPane.INFORMATION_MESSAGE);
			}
		}
		// Thanh toán
		else if (view.panel_sell.bt_payment == e.getSource()) {
			bt_paymentEventHandler();
		}
		// Them Bill
		else if (e.getSource() == view.panel_sell.bt_addBill) {
			view.myPOS.createBill(view.myPOS.getWorkingEmp());
			view.panel_sell.restartShowBills();

		}
		// xoa Bill
		else if (e.getSource() == view.panel_sell.bt_deleteBill) {
			bt_deleteBillEventHandler();
		}
		// doi diem
		else if (e.getSource() == view.panel_sell.bt_useLoyaltyPoints) {
			if (view.panel_sell.lb_idBill.getText().length() > 0) {
				Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
				view.myPOS.getPromotionManagement().redeemRewardForBill(b,
						view.myPOS.getPromotionManagement().getRewards().get(0));
				view.panel_sell.restartBillInterface(b);
			}
		}
		// Thêm KH
		else if (e.getSource() == view.panel_sell.bt_addCustomer) {
			Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
			if (b.getCustomer().getName().equals("")) {
				Customer c = new Customer(view.panel_sell.tf_phoneNumber.getText(),
						view.panel_sell.tf_nameCustomer.getText());
				boolean a = view.myPOS.addCustomer(c);
				if (a == false) {
					JOptionPane.showMessageDialog(null,
							"Không thể thêm khách hàng vì số điện thoại của khách hàng đã tồn tại", "Thêm khách hàng",
							JOptionPane.WARNING_MESSAGE);
				} else
					b.setCustomer(c);
			}
			view.panel_sell.restartBillInterface(b);
		}
		// TÌm KH
		else if (e.getSource() == view.panel_sell.bt_findCustomer) {
			Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
			if (b.getCustomer().getName().equals("")) {
				Customer c = view.myPOS.findCustomerByPhoneNumber(view.panel_sell.tf_phoneNumber.getText());
				if (c != null) {
					b.setCustomer(c);
				} else {
					JOptionPane.showMessageDialog(null, "Khách hàng không tồn tại!", "Tìm khách hàng",
							JOptionPane.WARNING_MESSAGE);
				}
			}
			view.panel_sell.restartBillInterface(b);
		}
		// note Bill
		else if (e.getSource() == view.panel_sell.bt_saveNote) {
			Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
			if (b != null)
				b.setNote(view.panel_sell.textArea_note.getText());
		}
		// chỉnh sửa đh
		else if (e.getSource() == view.panel_sell.bt_editProducts) {
			if (view.panel_sell.lb_idBill.getText().length() > 0) {
				Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
				if (!b.getProducts_quantity().isEmpty()) {
					JTextField indexRemove = new JTextField();
					JButton remove = new JButton("Loại bỏ sản phẩm theo STT");
					remove.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (e.getSource() == remove) {
								int stt = Integer.parseInt(indexRemove.getText());
								b.removeProducts(nameProductFromTable(stt), sizeProductFromTable(stt));

								view.panel_sell.restartBillInterface(b);

								JOptionPane.showMessageDialog(null, "Đã hủy bỏ SP" + indexRemove.getText(),
										"Loại bỏ sản phẩm", JOptionPane.WARNING_MESSAGE);
							}
						}
					});

					JTextField newQuantity = new JTextField();
					JButton renewQuantity = new JButton("Thay đổi số lượng sản phẩm");
					renewQuantity.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							int stt = Integer.parseInt(indexRemove.getText());
							int q = Integer.parseInt(newQuantity.getText());
							if (q != 0) {
								b.setQuantity(nameProductFromTable(stt), sizeProductFromTable(stt), q);

								view.panel_sell.restartBillInterface(b);

								JOptionPane.showMessageDialog(null, "Thay đổi đã được cập nhật", "Loại bỏ sản phẩm",
										JOptionPane.WARNING_MESSAGE);
							} else
								newQuantity.setText("");
						}
					});
					Object[] message = { "STT:", indexRemove, "Số lượng mới:", newQuantity, renewQuantity, remove };
					int choiceSize = JOptionPane.showConfirmDialog(null, message, "Chỉnh sửa",
							JOptionPane.DEFAULT_OPTION);
				}
			}
		}
		// Shows Khuyến Mãi
				else if (e.getSource() == view.panel_promotion.rb_promotionWithoutConditions) {
					List<Promotion> promotions = view.myPOS.getPromotionManagement().getPromotionWithoutConditions();
					resetTable();
					int hsize = 1;
					for (Promotion p : promotions) {
						String namesizeProducts = "<html>";
						int hsizetemp = 0;
						for (Product product : p.getResultProducts()) {
							hsizetemp++;
							namesizeProducts += product.getNameProduct()
									+ (product.getUnitPriceBySize().keySet().contains(null) ? ""
											: " - " + product.getUnitPriceBySize().keySet())
									+ "<br>";
						}
						namesizeProducts += "</html>";
						view.panel_promotion.model.addRow(
								new Object[] { p.getTitle(), namesizeProducts, p.isStatus() ? "Đang áp dụng" : "Ngưng áp dụng",
										p.getStartDate(), p.getEndDate(), p.isDiscount() ? "Có" : "Không" });
						hsize = (hsize < hsizetemp) ? hsizetemp : hsize;
					}

					view.panel_promotion.table.setRowHeight(hsize * view.panel_promotion.table.getRowHeight());
				} else if (e.getSource() == view.panel_promotion.rb_promotionByCode) {
					resetTable();
					view.panel_promotion.table.setModel(new DefaultTableModel(new Object[][] {},
							new String[] { "Mã KM", "Tiêu đề", "Tên sản phẩm áp dụng - Size", "Trạng thái", "Ngày bắt đầu",
									"Ngày kết thúc", "Thay đổi giá" }));

					view.panel_promotion.table.getColumnModel().getColumn(0).setPreferredWidth(60);
					view.panel_promotion.table.getColumnModel().getColumn(1).setPreferredWidth(120);
					view.panel_promotion.table.getColumnModel().getColumn(2).setPreferredWidth(150);
					view.panel_promotion.table.getColumnModel().getColumn(3).setPreferredWidth(100);
					view.panel_promotion.table.getColumnModel().getColumn(4).setPreferredWidth(80);
					view.panel_promotion.table.getColumnModel().getColumn(5).setPreferredWidth(80);
					view.panel_promotion.table.getColumnModel().getColumn(6).setPreferredWidth(70);
					view.panel_promotion.model = (DefaultTableModel) view.panel_promotion.table.getModel();

					for (Map.Entry<String, List<Promotion>> entry : view.myPOS.getPromotionManagement().getPromotionByCode()
							.entrySet()) {
						String key = entry.getKey();
						List<Promotion> value = entry.getValue();

						int hsize = 1;
						for (Promotion p : value) {
							String namesizeProducts = "<html>";
							int hsizetemp = 0;
							for (Product product : p.getResultProducts()) {
								hsizetemp++;
								namesizeProducts += product.getNameProduct()
										+ (product.getUnitPriceBySize().keySet().contains(null) ? ""
												: " - " + product.getUnitPriceBySize().keySet())
										+ "<br>";
							}
							namesizeProducts += "</html>";
							view.panel_promotion.model.addRow(new Object[] { (value.indexOf(p) == 0) ? key : "", p.getTitle(),
									namesizeProducts, p.isStatus() ? "Đang áp dụng" : "Ngưng áp dụng", p.getStartDate(),
									p.getEndDate(), p.isDiscount() ? "Có" : "Không" });
							hsize = (hsize < hsizetemp) ? hsizetemp : hsize;
						}
						view.panel_promotion.table.setRowHeight(hsize * view.panel_promotion.table.getRowHeight());
					}

				} else if (e.getSource() == view.panel_promotion.rb_rewards) {
					resetTable();
					view.panel_promotion.table.setModel(new DefaultTableModel(new Object[][] {},
							new String[] { "Tiêu đề phần thưởng", "Điểm TL tối thiểu", "Tiêu đề khuyến mãi",
									"Tên sản phẩm áp dụng - Size", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc",
									"Thay đổi giá" }));

					view.panel_promotion.table.getColumnModel().getColumn(0).setPreferredWidth(120);
					view.panel_promotion.table.getColumnModel().getColumn(1).setPreferredWidth(90);
					view.panel_promotion.table.getColumnModel().getColumn(2).setPreferredWidth(120);
					view.panel_promotion.table.getColumnModel().getColumn(3).setPreferredWidth(150);
					view.panel_promotion.table.getColumnModel().getColumn(4).setPreferredWidth(100);
					view.panel_promotion.table.getColumnModel().getColumn(5).setPreferredWidth(80);
					view.panel_promotion.table.getColumnModel().getColumn(6).setPreferredWidth(80);
					view.panel_promotion.table.getColumnModel().getColumn(7).setPreferredWidth(70);
					view.panel_promotion.model = (DefaultTableModel) view.panel_promotion.table.getModel();

					int hsize = 1;
					Collection<Reward> rewards = view.myPOS.getPromotionManagement().getRewards().values();
					for (Reward r : rewards) {
						Promotion p = r.getPromotion();
						String namesizeProducts = "<html>";
						int hsizetemp = 0;
						for (Product product : p.getResultProducts()) {
							hsizetemp++;
							namesizeProducts += product.getNameProduct()
									+ (product.getUnitPriceBySize().keySet().contains(null) ? ""
											: " - " + product.getUnitPriceBySize().keySet())
									+ "<br>";
						}
						namesizeProducts += "</html>";
						view.panel_promotion.model
								.addRow(new Object[] { r.getTitleReward(), r.getDepreciatedConsumptionPoints(), p.getTitle(),
										namesizeProducts, p.isStatus() ? "Đang áp dụng" : "Ngưng áp dụng", p.getStartDate(),
										p.getEndDate(), p.isDiscount() ? "Có" : "Không" });
						hsize = (hsize < hsizetemp) ? hsizetemp : hsize;
					}
					view.panel_promotion.table.setRowHeight(hsize * view.panel_promotion.table.getRowHeight());
				}
	}

	private void resetBackgroundButton() {
		view.bt_sell.setForeground(null);
		view.bt_bills.setForeground(null);
		view.bt_customers.setForeground(null);
		view.bt_statistical.setForeground(null);
		view.bt_employees.setForeground(null);
		view.bt_products.setForeground(null);
		view.bt_promotions.setForeground(null);
	}

	private void addProductEventHandler(Product p, Bill b) {
		String s = "";
		int q = 0;
		// JOptionPane
		String showSize = "<html><b><center>" + p.getNameProduct() + "</center></b><br>";
		for (Map.Entry<String, Double> entry : p.getUnitPriceBySize().entrySet()) {
			if (entry.getKey() != null)
				showSize += entry.getKey() + " : " + entry.getValue() + "<br>";
			else
				showSize += entry.getValue() + "<br>";
		}
		showSize += "--------------------------------------------<br> </html>";
		// Xử lý trên JOptionPane
		JTextField size = new JTextField();
		JTextField quantum = new JTextField();
		if (p.getUnitPriceBySize().containsKey(null)) {
			Object[] message = { showSize, "Số lượng:", quantum };
			int choiceSize = JOptionPane.showConfirmDialog(null, message, "Chọn Size", JOptionPane.OK_CANCEL_OPTION);
			if (choiceSize == JOptionPane.OK_OPTION) {
				if (quantum.getText().length() > 0) {
					q = Integer.parseInt(quantum.getText());

					if (q != 0) {
						List<SelectSizeQuantity> newSizeQuantitys = new ArrayList<>();
						if (b.getProducts_quantity().get(p) != null)
							q += b.getProducts_quantity().get(p).get(0).getQuantity();

						SelectSizeQuantity sq = new SelectSizeQuantity(q);
						newSizeQuantitys.add(sq);
						b.getProducts_quantity().remove(p);
						b.addProductsQuantity(p, newSizeQuantitys);
					}
				}
			}
		} else {
			Object[] message = { showSize, "Size:", size, "Số lượng:", quantum };
			int choiceSize = JOptionPane.showConfirmDialog(null, message, "Chọn Size", JOptionPane.OK_CANCEL_OPTION);
			if (choiceSize == JOptionPane.OK_OPTION) {
				if (quantum.getText().length() > 0 && size.getText().length() > 0) {
					if (p.getUnitPriceBySize().containsKey(size.getText())) {
						s = size.getText();
						q = Integer.parseInt(quantum.getText());

						if (q != 0) {
							List<SelectSizeQuantity> newSizeQuantitys = new ArrayList<>();
							if (b.getProducts_quantity().get(p) != null) {
								newSizeQuantitys = b.getProducts_quantity().get(p);

								for (SelectSizeQuantity temp : b.getProducts_quantity().get(p)) {
									if (temp.getSize().equals(s)) {
										q += temp.getQuantity();
										newSizeQuantitys.remove(temp);
										b.getProducts_quantity().remove(p);
										break;
									}
								}
							}
							newSizeQuantitys.add(new SelectSizeQuantity(s, q));
							b.addProductsQuantity(p, newSizeQuantitys);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Size không phù hợp", null, JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
		view.panel_sell.restartBillInterface(b);
	}

	private void bt_paymentEventHandler() {
		Bill b = view.myPOS.findNotSuccessfulPaymentBillById(view.panel_sell.lb_idBill.getText());
		if (view.panel_sell.tf_moneyCustomer.getText().length() > 0) {
			double money = Double.parseDouble(view.panel_sell.tf_moneyCustomer.getText());
			b.payment(money);
			if (b.isSuccessfulPayment()) {
				JOptionPane.showMessageDialog(null, b.printBill(), "Thanh toán thành công!", JOptionPane.PLAIN_MESSAGE);

//				System.out.println("===============> "+view.myPOS.getBills().size()+" > "+view.myPOS.getNotSuccessfulPaymentBills().size());
				view.panel_sell.restartShowBills();
				if (view.panel_sell.billCodes_panel.getComponentCount() > 0) {
					JButton btBillFirst = (JButton) view.panel_sell.billCodes_panel.getComponent(0);
					view.panel_sell
							.restartBillInterface(view.myPOS.findNotSuccessfulPaymentBillById(btBillFirst.getText()));
				} else
					view.panel_sell.resetBillInterface();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền!", null, JOptionPane.DEFAULT_OPTION);
		}
	}

	private void bt_deleteBillEventHandler() {
		if (view.panel_sell.lb_idBill.getText().length() > 0) {
			view.myPOS.removeNotSuccessfulPaymentBill(view.panel_sell.lb_idBill.getText());
			view.panel_sell.restartShowBills();
			if (view.panel_sell.billCodes_panel.getComponentCount() > 0) {
				JButton btBillFirst = (JButton) view.panel_sell.billCodes_panel.getComponent(0);
				view.panel_sell
						.restartBillInterface(view.myPOS.findNotSuccessfulPaymentBillById(btBillFirst.getText()));
			} else
				view.panel_sell.resetBillInterface();
		}
	}

	private String nameProductFromTable(int stt) {
		String nameProduct = "";
		Object value = view.panel_sell.table.getValueAt(stt - 1, 1);
		if (value != null) {
			nameProduct = value.toString();
		}
		return nameProduct;
	}

	private String sizeProductFromTable(int stt) {
		String sizeProduct = "";
		Object value = view.panel_sell.table.getValueAt(stt - 1, 3);
		if (value != null) {
			sizeProduct = value.toString();
		}
		return sizeProduct;
	}
	private void resetTable() {
		while (view.panel_promotion.model.getRowCount() != 0) {
			view.panel_promotion.model.removeRow(0);
		}
		view.panel_promotion.table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Tiêu đề",
				"Tên sản phẩm áp dụng - Size", "Trạng thái", "Ngày bắt đầu", "Ngày kết thúc", "Thay đổi giá" }));

		view.panel_promotion.table.getColumnModel().getColumn(0).setPreferredWidth(120);
		view.panel_promotion.table.getColumnModel().getColumn(1).setPreferredWidth(150);
		view.panel_promotion.table.getColumnModel().getColumn(2).setPreferredWidth(100);
		view.panel_promotion.table.getColumnModel().getColumn(3).setPreferredWidth(80);
		view.panel_promotion.table.getColumnModel().getColumn(4).setPreferredWidth(80);
		view.panel_promotion.table.getColumnModel().getColumn(5).setPreferredWidth(70);
		view.panel_promotion.model = (DefaultTableModel) view.panel_promotion.table.getModel();
		view.panel_promotion.table.setRowHeight(15);

	}
}
