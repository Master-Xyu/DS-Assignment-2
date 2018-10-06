package Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class midGUI {

	private JFrame frame;
	private Container Cplayer;
	private Container Ctable;


	public midGUI() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10,10));
		showPlayer();
		showTable();

	}

	private void showTable() {
		frame.getContentPane().add(Ctable,BorderLayout.CENTER);
		
	}

	private void showPlayer() {
		frame.getContentPane().add(Cplayer,BorderLayout.WEST);
		JLabel playerLabel = new JLabel("Players:");
		Cplayer.add(playerLabel,BorderLayout.NORTH);

		
	}

	public void addList(String[] player) {
		// TODO Auto-generated method stub
		
	}

	public void addTable(String table, String state, String[] player) {
		// TODO Auto-generated method stub
		
	}

	public void chatMsg(int num, String string) {
		// TODO Auto-generated method stub
		
	}
	

}
