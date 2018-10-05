package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class midGUI {

	private JFrame frame;

	public midGUI() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
