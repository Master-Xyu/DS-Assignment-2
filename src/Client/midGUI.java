package Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

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
	public  JTable Tplayer;
	public  DefaultTableModel model;
	private JScrollPane js;
	public  MyRender mr;

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
		JButton button = null ;        
		for(int i=0;i<4;i++)        
		{            
			button = new JButton("°´Å¥"+i) ;            
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
        Tplayer.getColumnModel().getColumn(0).setCellEditor(mr);//ÉèÖÃ±à¼­Æ÷
        Tplayer.getColumnModel().getColumn(0).setCellRenderer(mr);
        Tplayer.getColumnModel().getColumn(1).setCellEditor(mr);//ÉèÖÃ±à¼­Æ÷
        Tplayer.getColumnModel().getColumn(1).setCellRenderer(mr);
        js = new JScrollPane(Tplayer);   
        Cplayer.add(js,BorderLayout.CENTER);

		
	}

	public void addList(String[] player) {
		// TODO Auto-generated method stub
		
	}

	public void addTable(String table, String state, String[] player) {
		// TODO Auto-generated method stub
		
	}
	

}
