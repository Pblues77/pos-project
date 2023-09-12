package view;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class ThemeMode {
	public static void setDarkMode() {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
			// Apply system colors to the UI
			UIManager.put("Panel.background",  Color.CYAN);
//			UIManager.put("OptionPane.background", new Color(128, 128, 128));
//			UIManager.put("Label.foreground", Color.GRAY);
//			UIManager.put("Label.background",  new Color( 105, 105, 105, 1 ));
//			UIManager.put("TextField.background", Color.white);
//			UIManager.put("TextField.foreground", Color.white);
//			UIManager.put("TextArea.background", Color.white);
//			UIManager.put("TextArea.foreground", Color.GRAY);
//			UIManager.put("List.background", Color.GRAY);
//			UIManager.put("List.foreground", Color.GRAY);
//			UIManager.put("ComboBox.background", Color.white);
//			UIManager.put("ComboBox.foreground", Color.GRAY);
//			UIManager.put("MenuBar.background", Color.GRAY);
//			UIManager.put("MenuBar.foreground", Color.GRAY);
//			UIManager.put("MenuItem.background", Color.GRAY);
//			UIManager.put("MenuItem.foreground", Color.GRAY);
//			UIManager.put("CheckBoxMenuItem.background", Color.GRAY);
//			UIManager.put("CheckBoxMenuItem.foreground", Color.GRAY);
//			UIManager.put("RadioButtonMenuItem.background", Color.GRAY);
//			UIManager.put("RadioButtonMenuItem.foreground", Color.GRAY);
//			UIManager.put("Button.background", new Color(105, 105, 105, 1));
//			UIManager.put("Button.foreground", Color.WHITE);
//			UIManager.put("TextArea.selectionbackground", Color.GRAY);
//			UIManager.put("TextArea.selectionForeground", Color.GRAY);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void setDefaultTheme() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
