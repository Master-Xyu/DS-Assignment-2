package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class clientGUI implements MouseListener{

	private JFrame frame;
	int row = 20;
	int col = 20;
	Container container1 = new Container();
	Container container2 = new Container();
	Container container3 = new Container();
	Container container4 = new Container();
	JButton border[][] = new JButton[row][col];
	ArrayList<ArrayList<Integer>> word= new ArrayList();
	ArrayList<Integer> c = new ArrayList();
	char borderKey[][] = new char[row][col];
	String[] wordKey = {"A","B","C","D","E","F","G","H","I","J","K","L"
			,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	JButton wordList[] = new JButton[wordKey.length];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clientGUI window = new clientGUI();
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
	public clientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.red);
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10,10));
		
		showInfo();
		showBoarder();
		showWord();
		showFunc();
	}

	

	private void showInfo() {
		frame.getContentPane().add(container1,BorderLayout.NORTH);
		container1.setLayout(new GridLayout(1,26));
		
	}

	private void showBoarder() {
		container2.setBackground(Color.red);
		frame.getContentPane().add(container2,BorderLayout.CENTER);
		container2.setLayout(new GridLayout(row,col));
		for (int i = 0; i < row ; i++)
			for (int j = 0; j < col ; j++) {
				JButton x = new JButton();
				x.setMargin(new Insets(0,0,0,0));
				x.setBackground(Color.white);
				x.setOpaque(true);
				x.setBorderPainted(true); 
				x.addMouseListener(this);
				border[i][j] = x;
				container2.add(x);
			}
	}
	
	private void showWord() {
		frame.getContentPane().add(container3,BorderLayout.EAST);
		container3.setLayout(new GridLayout(26,5));
		for (int i = 0; i < 26 ; i++) {
			JButton x = new JButton();
			x.setMargin(new Insets(0,0,0,0));
			x.setBackground(Color.WHITE);
			x.setOpaque(true);
			x.setBorderPainted(true); 
			x.addMouseListener(this);
			x.setText(wordKey[i]);
			wordList[i] = x;
			container3.add(x);
		}	
	}
	
	private void showFunc() {
		frame.getContentPane().add(container4,BorderLayout.SOUTH);
		container4.setLayout(new GridLayout(1,3));
		JButton x = new JButton("CANCEL");
		x.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearWord();
				clearChar();
			}
		});
		x.setMargin(new Insets(0,0,0,0));
		x.setBackground(Color.WHITE);
		x.setOpaque(true);
		x.setBorderPainted(true); 
		x.addMouseListener(this);
		container4.add(x);
		JButton y = new JButton("CLAIM");
		y.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(word.size()!=0)
					System.out.println(word);//CLAIM Word£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡
			}
		});
		y.setMargin(new Insets(0,0,0,0));
		y.setBackground(Color.WHITE);
		y.setOpaque(true);
		y.setBorderPainted(true); 
		y.addMouseListener(this);
		container4.add(y);
		JButton z = new JButton("EXIT");
		z.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		z.setMargin(new Insets(0,0,0,0));
		z.setBackground(Color.WHITE);
		z.setOpaque(true);
		z.setBorderPainted(true); 
		z.addMouseListener(this);
		container4.add(z);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton button = (JButton)e.getSource();
		for (int i=0;i<row;i++)
			for (int j=0;j<col;j++)
				if (button.equals(border[i][j])) {
					select(i,j);
					return;
				}
		for (int i=0;i<26;i++)
			if(button.equals(wordList[i])) {
				if(c.size()==2)
				{
					int tx = 0,ty = 0;
					tx = c.get(0);
					ty = c.get(1);
					c.clear();
					border[tx][ty].setEnabled(true);
					border[tx][ty].setBackground(Color.white);
					border[tx][ty].setText(wordKey[i]);
					System.out.println("("+tx+","+ty+")"+"->"+"wordKey[i]");
				}
			}
	}

	private void select(int tx, int ty) {
		if(border[tx][ty].getText().equals("")) {
			clearWord();
			clearChar();
			c.add(tx);
			c.add(ty);
			border[tx][ty].setBackground(Color.red);
			border[tx][ty].setEnabled(false);
		}
		else {
			clearChar();
			ArrayList<Integer> tmp=new ArrayList();
			if(word.size()==0) {
				tmp.add(tx);
				tmp.add(ty);
				word.add(tmp);
				border[tx][ty].setBackground(Color.blue);
				border[tx][ty].setEnabled(false);
			}
			else {
				if(validSelect(tx,ty)) {
					border[tx][ty].setBackground(Color.blue);
					border[tx][ty].setEnabled(false);
					tmp.add(tx);
					tmp.add(ty);
					word.add(tmp);
				}
				else {
					clearWord();
					tmp.add(tx);
					tmp.add(ty);
					word.add(tmp);
					border[tx][ty].setBackground(Color.blue);
					border[tx][ty].setEnabled(false);
				}
			}
		}
	}

	private boolean validSelect(int tx,int ty) {
		int flag=0;
		for(int i=0;i<word.size();i++)
		{
			if(word.get(i).get(0)==tx&&Math.abs(word.get(i).get(1)-ty)<=word.size())
			{
				flag++;
			}
		}
		if(flag==word.size())
			return true;
		flag=0;
		for(int i=0;i<word.size();i++)
		{
			if(word.get(i).get(1)==ty&&Math.abs(word.get(i).get(0)-tx)<=word.size())
			{
				flag++;
			}
		}
		if(flag==word.size())
			return true;
		return false;
	}

	private void clearChar() {
		if(c.size()==2)
		{
			border[c.get(0)][c.get(1)].setEnabled(true);
			border[c.get(0)][c.get(1)].setBackground(Color.WHITE);
			c.clear();
		}
		if(c.size()>2)
			c.clear();
	}

	private void clearWord() {
		for(int i=0;i<word.size();i++)
		{
			border[word.get(i).get(0)][word.get(i).get(1)].setEnabled(true);
			border[word.get(i).get(0)][word.get(i).get(1)].setBackground(Color.white);
		}
		word.clear();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
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

}
