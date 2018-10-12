package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class pre {
	
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 600;
    public int windowsHeight = 800;
	public static JFrame frame;
	private JTextField textField;
	public static client myclient;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnConnect;
	public static pre window;
	public static clientGUI gui;
	private int isReady=0;
	public int myId;
	public static midGUI mid;
	private String[] tableState;
	private String[][] seatState;
	public  MyRender mr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new pre();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public pre() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, 600, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		
		JLabel lblInput = new JLabel("SCRABBLE");
		lblInput.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.gridwidth = 2;
		gbc_lblInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblInput.gridx = 1;
		gbc_lblInput.gridy = 1;
		frame.getContentPane().add(lblInput, gbc_lblInput);
		
		JLabel lblServerAddress = new JLabel("Server address:");
		lblServerAddress.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.gridwidth = 2;
		gbc_lblServerAddress.anchor = GridBagConstraints.SOUTH;
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.gridx = 1;
		gbc_lblServerAddress.gridy = 2;
		frame.getContentPane().add(lblServerAddress, gbc_lblServerAddress);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.fill = GridBagConstraints.BOTH;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 3;
		frame.getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.gridwidth = 2;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.SOUTH;
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 4;
		frame.getContentPane().add(lblPort, gbc_lblPort);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.BOTH;
		gbc_textField_3.gridwidth = 2;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		frame.getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		
		
		JLabel lblPlayerName = new JLabel("Player Name:");
		lblPlayerName.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_lblPlayerName = new GridBagConstraints();
		gbc_lblPlayerName.gridwidth = 2;
		gbc_lblPlayerName.anchor = GridBagConstraints.SOUTH;
		gbc_lblPlayerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayerName.gridx = 1;
		gbc_lblPlayerName.gridy = 6;
		frame.getContentPane().add(lblPlayerName, gbc_lblPlayerName);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 7;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnConnect = new JButton("CONNECT");
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 1;
		gbc_btnConnect.gridy = 9;
		frame.getContentPane().add(btnConnect, gbc_btnConnect);
		btnConnect.setVisible(true);
		btnConnect.setVisible(true);
		
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_2.getText().equals("")) {
					JOptionPane.showMessageDialog(null,"Please input server address!");
				}
				else {
					if(textField_3.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Please input port number!");
					}
					else {
						if(textField.getText().equals("")) {
							JOptionPane.showMessageDialog(null,"Please input player name!");
						}
						else {
							String address = textField_2.getText();
							int portnum = Integer.parseInt(textField_3.getText());
							myclient = new client(address,portnum);
							String state = myclient.connect(textField.getText());
							btnConnect.setEnabled(false);
						}
					}
				}
			}
		});
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnClose.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.insets = new Insets(0, 0, 5, 5);
		gbc_btnClose.gridx = 2;
		gbc_btnClose.gridy = 9;
		frame.getContentPane().add(btnClose, gbc_btnClose);
	}
	public void startgame(int id) {
		myId = id;
		frame.setVisible(false);
		window.gui = new clientGUI();
		
	}
	
	public void enterGame() {
		frame.setVisible(false);
		window.mid = new midGUI();
	}

	public void disconnect() {
		initial();
	}
	

	public void initial() {
		textField_2.setText("");
		textField_3.setText("");
		textField.setText("");
	}
	public void addList(String[] p) {
		mr = new MyRender(p);
	}
	
	public void addInfo(String state,String[] a) {
		if(tableState == null)
		{
			tableState = new String[2];
			seatState = new String[2][4];
			tableState[0] = state;
			for(int i=0;i<a.length;i++)
			{
				seatState[0][i] = a[i];
			}
		}
		else 
		{
			tableState[1] = state;
			for(int i=0;i<a.length;i++)
			{
				seatState[1][i] = a[i];
			}
		}
	}
	public void invalidName() {
		JOptionPane.showMessageDialog(null,"Name exist! please change your name!");
		btnConnect.setEnabled(true);
	}
	
//	public void ready() {
//		textField_1.setText("Wait for the game to start!");
//		String state = myclient.ready();
//		if(state.equals("online")) {
//			textField_1.setText("Ready!");
//			btnReady.setEnabled(false);
//			isReady=1;
//		}
//	}
}
