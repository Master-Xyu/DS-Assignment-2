package Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.GridLayout;

public class midGUI {

	private JFrame frame;
	private Container Cplayer = new Container();
	private Container Ctable = new Container();
	private Container[] Ct = new Container[4];
	private JButton[][] seat = new JButton[4][4];
	private JLabel[] img = new JLabel[4];
	public  JTable Tplayer;
	public  DefaultTableModel model;
	private JScrollPane js;
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					midGUI window = new midGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}
	
	

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
			ImageIcon pic1=new ImageIcon("img/p1.png");
			seat[i][0] = new JButton("",pic1);
			seat[i][0].setPreferredSize(new Dimension(100, 100));
			t1.add(seat[i][0], BorderLayout.CENTER);
			t1.add(t3, BorderLayout.EAST);
			t1.add(t4, BorderLayout.WEST);
			Ct[i].add(t1,BorderLayout.NORTH);
			ImageIcon pic2=new ImageIcon("img/p2.png");
			seat[i][1] = new JButton("",pic2);
			seat[i][1].setPreferredSize(new Dimension(100, 100));
			Ct[i].add(seat[i][1],BorderLayout.EAST);
			ImageIcon pic3=new ImageIcon("img/p3.png");
			seat[i][2] = new JButton("",pic3);
			seat[i][2].setPreferredSize(new Dimension(100, 100));
			t2.add(seat[i][2], BorderLayout.CENTER);
			t2.add(t5, BorderLayout.EAST);
			t2.add(t6, BorderLayout.WEST);
			Ct[i].add(t2,BorderLayout.SOUTH);
			ImageIcon pic4=new ImageIcon("img/p4.png");
			seat[i][3] = new JButton("",pic4);
			seat[i][3].setPreferredSize(new Dimension(100, 100));
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
		model = new DefaultTableModel(2,2);
		model.setColumnIdentifiers(coloums);
        Tplayer = new JTable(model);
        Tplayer.getColumnModel().getColumn(0).setCellEditor(pre.window.mr);//…Ë÷√±‡º≠∆˜
        Tplayer.getColumnModel().getColumn(0).setCellRenderer(pre.window.mr);
        Tplayer.getColumnModel().getColumn(1).setCellEditor(pre.window.mr);//…Ë÷√±‡º≠∆˜
        Tplayer.getColumnModel().getColumn(1).setCellRenderer(pre.window.mr);
        js = new JScrollPane(Tplayer);   
        Cplayer.add(js,BorderLayout.CENTER);

		
	}
	

}
