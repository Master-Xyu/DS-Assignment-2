package Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class midGUI implements MouseListener {

	public JFrame frame;
	private Container Cplayer = new Container();
	private Container Ctable = new Container();
	private Container Cmenu = new Container();
	private Container[] Ct = new Container[2];
	private JButton[][] seat = new JButton[2][4];
	private JButton btnReady;
	private JButton btnLeave;
	private JButton btnExit;
	private JLabel[] img = new JLabel[4];
	public  JTable Tplayer;
	public  DefaultTableModel model;
	private JScrollPane js;
	public int myState = 0;
	public String myName;
	

	public midGUI() {
		initialize();
		frame.setVisible(true);
		myName = pre.window.myName;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 927, 1265);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10,10));
		showPlayer();
		showTable();
		showMenu();

	}

	private void showTable() {
		frame.getContentPane().add(Ctable,BorderLayout.CENTER);
		Ctable.setLayout(new GridLayout(2,2,13,13)) ;              
		for(int i=0;i<2;i++)        
		{   Ct[i] = new Container(); 
			Ct[i].setLayout(new BorderLayout()) ;
			Ct[i].setPreferredSize(new Dimension(400,400));
			ImageIcon icon = new ImageIcon("img/2.png");
			img[i] = new JLabel();
			img[i].setIcon(icon);
			img[i].setPreferredSize(new Dimension(300, 300));
			Ct[i].add(img[i],BorderLayout.CENTER);
			Container t1=new Container();
			Container t2=new Container();
			Container t3=new Container();
			Container t4=new Container();
			Container t5=new Container();
			Container t6=new Container();
			t2.setLayout(new BorderLayout());
			t1.setLayout(new BorderLayout());
			t3.setPreferredSize(new Dimension(100,100));
			t4.setPreferredSize(new Dimension(100,100));
			t5.setPreferredSize(new Dimension(100,100));
			t6.setPreferredSize(new Dimension(100,100));
			if(pre.window.seatState[i][0].equals(""))
			{
				ImageIcon pic1=new ImageIcon("img/p1.png");
				seat[i][0] = new JButton("",pic1);
			}
			else 
			{
				seat[i][0] = new JButton(pre.window.seatState[i][0]);
			}
			seat[i][0].setPreferredSize(new Dimension(100, 100));
			seat[i][0].addMouseListener(this);
			t1.add(seat[i][0], BorderLayout.CENTER);
			t1.add(t3, BorderLayout.EAST);
			t1.add(t4, BorderLayout.WEST);
			Ct[i].add(t1,BorderLayout.NORTH);
			if(pre.window.seatState[i][1].equals(""))
			{
				ImageIcon pic2=new ImageIcon("img/p2.png");
				seat[i][1] = new JButton("",pic2);
			}
			else 
			{
				seat[i][1] = new JButton(pre.window.seatState[i][1]);
			}
			seat[i][1].setPreferredSize(new Dimension(100, 100));
			seat[i][1].addMouseListener(this);
			Ct[i].add(seat[i][1],BorderLayout.EAST);
			if(pre.window.seatState[i][2].equals(""))
			{
				ImageIcon pic3=new ImageIcon("img/p3.png");
				seat[i][2] = new JButton("",pic3);
			}
			else 
			{
				seat[i][2] = new JButton(pre.window.seatState[i][2]);
			}
			seat[i][2].setPreferredSize(new Dimension(100, 100));
			seat[i][2].addMouseListener(this);
			t2.add(seat[i][2], BorderLayout.CENTER);
			t2.add(t5, BorderLayout.EAST);
			t2.add(t6, BorderLayout.WEST);
			Ct[i].add(t2,BorderLayout.SOUTH);
			if(pre.window.seatState[i][3].equals(""))
			{
				ImageIcon pic4=new ImageIcon("img/p4.png");
				seat[i][3] = new JButton("",pic4);
			}
			else 
			{
				seat[i][3] = new JButton(pre.window.seatState[i][3]);
			}
			seat[i][3].setPreferredSize(new Dimension(100, 100));
			seat[i][3].addMouseListener(this);
			Ct[i].add(seat[i][3],BorderLayout.WEST);
			Ctable.add(Ct[i]) ;        
		}      

		
	}

	private void showPlayer() {

		frame.getContentPane().add(Cplayer,BorderLayout.WEST);
		Cplayer.setPreferredSize(new Dimension(300, 800));
		Cplayer.setLayout(new BorderLayout());
		JLabel playerLabel = new JLabel("Players:",JLabel.CENTER);
		playerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
		Cplayer.add(playerLabel,BorderLayout.NORTH);
		String[] coloums = {"Player name:",""};
		model = new DefaultTableModel(pre.window.mr.num,2);
		model.setColumnIdentifiers(coloums);
        Tplayer = new JTable(model);
        Tplayer.getColumnModel().getColumn(0).setCellEditor(pre.window.mr);
        Tplayer.getColumnModel().getColumn(0).setCellRenderer(pre.window.mr);
        Tplayer.getColumnModel().getColumn(1).setCellEditor(pre.window.mr);
        Tplayer.getColumnModel().getColumn(1).setCellRenderer(pre.window.mr);
        js = new JScrollPane(Tplayer);   
        Cplayer.add(js,BorderLayout.CENTER);

		
	}
	
	private void showMenu() {
		Cmenu.setLayout(new GridLayout(1,3,13,13)) ;
		btnReady = new JButton("Ready");
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ready();
			}
		});
		btnReady.setEnabled(false);
		btnLeave = new JButton("Leave");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pre.window.myclient.leave();
			}
		});
		btnLeave.setEnabled(false);
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pre.window.myclient.disconnect();
				System.exit(0);
			}
		});
		Cmenu.add(btnReady);
		Cmenu.add(btnLeave);
		Cmenu.add(btnExit);
		Cplayer.add(Cmenu,BorderLayout.SOUTH);
		Cplayer.setPreferredSize(new Dimension(300, 50));
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		JButton button = (JButton)e.getSource();
		for(int i=0;i<2;i++)
			for(int j=0;j<4;j++)
			{
				if(button.equals(seat[i][j]))
				{
					pre.window.myclient.join("table"+(i+1));
				}
			}
		
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	public void updateList(String[] player) {
		for(int i=0;i<player.length;i++)
		{
			if(player[i].equals(myName))
			{
				btnReady.setText("Ready");
				btnLeave.setEnabled(false);
				btnReady.setEnabled(false);
				break;
			}
		}
		String[] coloums = {"Player name:",""};
		model.getDataVector().removeAllElements();
		model = new DefaultTableModel(player.length,2);
		model.setColumnIdentifiers(coloums);
        Tplayer.setModel(model);
        pre.window.mr.update(player);
	}



	public void updateTable(String state, String table, String[] player) {
		int len = player.length;
		int tnum = Integer.parseInt(table.substring(5))-1;
		int loc=0;
		while(len>0)
		{
			seat[tnum][loc].setIcon(null);
			seat[tnum][loc].setText(player[loc]);
			if(player[loc].equals(myName))
			{
				myState = 1;
				btnLeave.setEnabled(true);
				btnReady.setEnabled(true);
			}
				
			len--;
			loc++;
		}
		for(int k=loc;k<4;k++)
		{
			String imgPath = "img/p"+(k+1)+".png";
			ImageIcon pic=new ImageIcon(imgPath);
			seat[tnum][k].setIcon(pic);
		}
		
	}
	
	public void ready() {
		pre.window.myclient.ready();
		btnReady.setText("Unready");
	}
	
	public void unready() {
		pre.window.myclient.unready();
		btnReady.setText("ready");
	}
	
}
