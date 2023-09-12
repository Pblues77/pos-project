package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

class ClockLabel extends JLabel implements ActionListener {

	String type;
	SimpleDateFormat sdf;

	public ClockLabel(String type) {
		this.type = type;
		setForeground(Color.black);

		switch (type) {
		case "date":
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			break;
		case "time":
			sdf = new SimpleDateFormat("HH:mm:ss");
			break;
		case "day":
			sdf = new SimpleDateFormat("EEEE,");
			break;
		case "day_date":
			sdf = new SimpleDateFormat("EEEE, HH:mm:ss");
			break;
		}

		Timer t = new Timer(1000, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ae) {
		Date d = new Date();
		setText(sdf.format(d));
	}
}