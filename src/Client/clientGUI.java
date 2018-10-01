package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class clientGUI implements MouseListener{

	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 800;
    public int windowsHeight = 800;
	public JFrame frame;
	int row = 20;
	int col = 20;
	Container container1 = new Container();
	Container container2 = new Container();
	Container container3 = new Container();
	Container container4 = new Container();
	JButton border[][] = new JButton[row][col];
	JButton score[];
	ArrayList<Coordinate> word= new ArrayList();
	ArrayList<Integer> c = new ArrayList();
	String borderKey[][] = new String[row][col];
	boolean blocks[][] = new boolean[row][col];
	String[] wordKey = {"A","B","C","D","E","F","G","H","I","J","K","L"
			,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	JButton wordList[] = new JButton[wordKey.length];
	JButton op[]=new JButton[4];

	/**
	 * Launch the application.
	 */
	public void start() {
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
		for(int p=0;p<20;p++)
			for(int q=0;q<20;q++)
			{
				blocks[p][q]=true;
			}
		frame = new JFrame();
		frame.setBackground(Color.red);
		frame.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(10,10));
		
		showInfo();
		showBoarder();
		showWord();
		showFunc();
		hideBlocks();
	}
	
	public void updateScore(scoreBoard b) {
		for(int i=0;i<b.getNumber();i++)
		{
			JButton x = new JButton();
			score[i].setText(b.getPlayer()[i]+":"+b.getScore()[i]);
		}
	}

	

	private void showInfo() {
		scoreBoard b = pre.myclient.getScore();
		int num = b.getNumber();
		score = new JButton[num];
		String[] players = b.getPlayer();
		int[] scores = b.getScore();
		frame.getContentPane().add(container1,BorderLayout.NORTH);
		container1.setLayout(new GridLayout(num+3,20));
		JLabel label1 = new JLabel("Instruction:"); 
		label1.setForeground(Color.black);
		label1.setOpaque(true);
		container1.add(label1);
		JTextField text1 = new JTextField();
		text1.setEditable(false);
		text1.setForeground(Color.black);
		text1.setOpaque(true);
		container1.add(text1);
		JLabel label2 = new JLabel("Score:"); 
		label2.setForeground(Color.black);
		label2.setOpaque(true);
		container1.add(label2);
		for(int i=0;i<num;i++)
		{
			JButton x = new JButton();
			x.setEnabled(false);
			x.setText(players[i]+":"+scores[i]);
			x.setMargin(new Insets(0,0,0,0));
			x.setBackground(Color.white);
			x.setOpaque(true);
			x.setBorderPainted(true); 
			score[i] = x;
			container1.add(x);
		}
		
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
		container4.setLayout(new GridLayout(1,4));
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
		op[1] = x;
		container4.add(x);
		JButton y = new JButton("CLAIM");
		y.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(word.size()!=0)
				{
					pre.myclient.submitWord(word);
					System.out.println(word);
					hideBlocks();
				}
					
			}
		});
		y.setMargin(new Insets(0,0,0,0));
		y.setBackground(Color.WHITE);
		y.setOpaque(true);
		y.setBorderPainted(true); 
		y.addMouseListener(this);
		op[2] = y;
		container4.add(y);
		JButton k = new JButton("PASS");
		k.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pre.myclient.pass();
				hideBlocks();
			}
		});
		k.setMargin(new Insets(0,0,0,0));
		k.setBackground(Color.WHITE);
		k.setOpaque(true);
		k.setBorderPainted(true); 
		k.addMouseListener(this);
		op[3] = k;
		container4.add(k);
		JButton z = new JButton("EXIT");
		z.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shutdown();
			}
		});
		z.setMargin(new Insets(0,0,0,0));
		z.setBackground(Color.WHITE);
		z.setOpaque(true);
		z.setBorderPainted(true); 
		z.addMouseListener(this);
		op[4] = z;
		container4.add(z);
		
	}
	
	public void shutdown() {
		pre.frame.setVisible(true);
		frame.dispose();
	}
	
	public void offline() {
		JOptionPane.showMessageDialog(null,"Offline!");
		shutdown();
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
					Coordinate set = new Coordinate();
					set.setDx(tx);
					set.setDy(tx);
					set.setLetter(wordKey[i]);
					pre.myclient.submitLetter(set);
					System.out.println("("+tx+","+ty+")"+"->"+"wordKey[i]");
					hideBlocks();
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
			Coordinate tmp=new Coordinate();
			if(word.size()==0) {
				tmp.setDx(tx);
				tmp.setDy(ty);
				tmp.setLetter(border[tx][ty].getText());
				word.add(tmp);
				border[tx][ty].setBackground(Color.blue);
				border[tx][ty].setEnabled(false);
			}
			else {
				if(validSelect(tx,ty)) {
					border[tx][ty].setBackground(Color.blue);
					border[tx][ty].setEnabled(false);
					tmp.setDx(tx);
					tmp.setDy(ty);
					tmp.setLetter(border[tx][ty].getText());
					word.add(tmp);
				}
				else {
					clearWord();
					tmp.setDx(tx);
					tmp.setDy(ty);
					tmp.setLetter(border[tx][ty].getText());
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
			if(word.get(i).getDx()==tx&&Math.abs(word.get(i).getDy()-ty)<=word.size())
			{
				flag++;
			}
		}
		if(flag==word.size())
			return true;
		flag=0;
		for(int i=0;i<word.size();i++)
		{
			if(word.get(i).getDy()==ty&&Math.abs(word.get(i).getDx()-tx)<=word.size())
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
			border[word.get(i).getDx()][word.get(i).getDy()].setEnabled(true);
			border[word.get(i).getDx()][word.get(i).getDy()].setBackground(Color.white);
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
	
	public boolean vote(String word, ArrayList<Coordinate> loc) {
		int number = loc.size();
		for(int i=0;i<number;i++)
			border[loc.get(i).getDx()][loc.get(i).getDy()].setBackground(Color.GRAY);
		if(JOptionPane.showConfirmDialog(null, "vote to "+word+"?", "Vote", JOptionPane.YES_NO_OPTION)==0)
		{
			for(int i=0;i<number;i++)
				border[loc.get(i).getDx()][loc.get(i).getDy()].setBackground(Color.WHITE);
			return true;
		}
		else 
		{
			for(int i=0;i<number;i++)
				border[loc.get(i).getDx()][loc.get(i).getDy()].setBackground(Color.WHITE);
			return false;
		}
	}
	
	public void updateLetter(Coordinate loc) {
		border[loc.getDx()][loc.getDy()].setText(loc.getLetter());
		borderKey[loc.getDx()][loc.getDy()]=loc.getLetter();
	}

	public void updateWord(ArrayList<Coordinate> loc) {
		int number = loc.size();
		for(int i=0;i<number;i++)
		{
			//border[loc.get(i).getDx()][loc.get(i).getDy()].setEnabled(false);
			blocks[loc.get(i).getDx()][loc.get(i).getDy()]=false;
			//updateBlocks();
		}
	}

//	private void updateBlocks() {
//		for(int i=0;i<20;i++)
//			for(int j=0;j<20;j++)
//			{
//				border[i][j].setEnabled(blocks[i][j]);
//			}
//		
//	}
	
	private void hideBlocks() {
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)
			{
				border[i][j].setEnabled(false);
			}
		for(int i=0;i<26;i++)
			wordList[i].setEnabled(false);
		for(int i=0;i<3;i++) {
			op[i].setEnabled(false);
		}
	}
	
	private void showBlocks() {
		for(int i=0;i<20;i++)
			for(int j=0;j<20;j++)
			{
				border[i][j].setEnabled(blocks[i][j]);
			}
		for(int i=0;i<26;i++)
			wordList[i].setEnabled(true);
		for(int i=0;i<3;i++) {
			op[i].setEnabled(true);
		}
	}
	
	public void myTurn() {
		showBlocks();
	}


}
