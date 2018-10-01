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
    public int windowsWedth = 800;
    public int windowsHeight = 650;
	public static JFrame frame;
	private JTextField textField_1;
	private JTextField textField;
	public static client myclient;
	private JTextField textField_2;
	private JTextField textField_3;
	public static pre window;
	public static clientGUI gui;
	private JButton btnReady;
	private int isReady=0;

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
                (height - windowsHeight) / 2, 878, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.05, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		
		JLabel lblInput = new JLabel("SCRABBLE");
		lblInput.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.gridwidth = 7;
		gbc_lblInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblInput.gridx = 1;
		gbc_lblInput.gridy = 1;
		frame.getContentPane().add(lblInput, gbc_lblInput);
		
		JLabel lblServerAddress = new JLabel("Server address:");
		lblServerAddress.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblServerAddress = new GridBagConstraints();
		gbc_lblServerAddress.anchor = GridBagConstraints.WEST;
		gbc_lblServerAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddress.gridx = 1;
		gbc_lblServerAddress.gridy = 2;
		frame.getContentPane().add(lblServerAddress, gbc_lblServerAddress);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 3;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 3;
		frame.getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.gridx = 4;
		gbc_lblPort.gridy = 3;
		frame.getContentPane().add(lblPort, gbc_lblPort);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.BOTH;
		gbc_textField_3.gridx = 5;
		gbc_textField_3.gridy = 3;
		frame.getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JButton btnConnect = new JButton("CONNECT");
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 7;
		gbc_btnConnect.gridy = 3;
		frame.getContentPane().add(btnConnect, gbc_btnConnect);
		btnConnect.setVisible(true);
		btnConnect.setVisible(true);
		
		
		
		JLabel lblPlayerName = new JLabel("Player Name:");
		lblPlayerName.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblPlayerName = new GridBagConstraints();
		gbc_lblPlayerName.anchor = GridBagConstraints.WEST;
		gbc_lblPlayerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayerName.gridx = 1;
		gbc_lblPlayerName.gridy = 4;
		frame.getContentPane().add(lblPlayerName, gbc_lblPlayerName);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblState = new JLabel("STATE:");
		lblState.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.anchor = GridBagConstraints.WEST;
		gbc_lblState.insets = new Insets(0, 0, 5, 5);
		gbc_lblState.gridx = 1;
		gbc_lblState.gridy = 6;
		frame.getContentPane().add(lblState, gbc_lblState);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.RED);
		textField_1.setEditable(false);
		textField_1.setFont(new Font("Arial", Font.PLAIN, 30));
		textField_1.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 5;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 7;
		frame.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		btnReady = new JButton("READY");
		btnReady.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_btnReady = new GridBagConstraints();
		gbc_btnReady.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReady.insets = new Insets(0, 0, 0, 5);
		gbc_btnReady.gridx = 1;
		gbc_btnReady.gridy = 9;
		frame.getContentPane().add(btnReady, gbc_btnReady);
		btnReady.setVisible(false);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isReady==1) {
					myclient.disconnect();
				}
				System.exit(0);
			}
		});
		btnClose.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 7;
		gbc_btnClose.gridy = 9;
		frame.getContentPane().add(btnClose, gbc_btnClose);
		
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_2.getText().equals("")) {
					textField_1.setText("Please input server address!");
				}
				else {
					if(textField_3.getText().equals("")) {
						textField_1.setText("Please input port number!");
					}
					else {
						if(textField.getText().equals("")) {
							textField_1.setText("Please input player name!");
						}
						else {
							String address = textField_2.getText();
							int portnum = Integer.parseInt(textField_3.getText());
							myclient = new client(address,portnum);
							String state = myclient.connect(textField.getText());
							textField_1.setText(state);
							if(state.equals("Ready required!")) {
								btnReady.setVisible(true);
							}
						}
					}
				}
			}
		});
		
		
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("Wait for the game to start!");
				String state = myclient.ready();
				if(state.equals("online")) {
					textField_1.setText("Ready!");
					btnReady.setEnabled(false);
					isReady=1;
				}
			}
		});
	}
	public void startgame() {
		frame.setVisible(false);
		window.gui = new clientGUI();
		
	}

	public void disconnect() {
		setWarning("Server Disconnect!");
		textField_2.setText("");
		textField_3.setText("");
		btnReady.setVisible(false);
	}
	
	public void setWarning(String mes) {
		textField_1.setText(mes);
	}
}
