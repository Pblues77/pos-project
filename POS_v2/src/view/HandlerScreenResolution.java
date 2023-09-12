package view;

import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class HandlerScreenResolution {
	public static double getScale() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		return toolkit.getScreenResolution() / 96.0;
	}

	public static int resizeWidthByScreenResolution(double width) {
		return (int) (width * getScale());
	}
	
}
