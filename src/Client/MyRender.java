package Client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

class MyRender extends AbstractCellEditor implements TableCellRenderer,ActionListener, TableCellEditor{
	 
    private static final long serialVersionUID = 1L;
    private JLabel[] name;
    private JButton[] aa;
    public int num;
    public MyRender(String[] names){
    	num = names.length;
    	aa = new JButton[num];
    	name = new JLabel[num];
    	for(int i=0;i<num;i++) {
    		aa[i] = new JButton("Invite");
    		name[i] = new JLabel(names[i],JLabel.LEFT);
    		aa[i].addActionListener(this);
    		aa[i].setEnabled(false);
    	}
    }
    
    public void update(String[] names) {
    	name = null;
    	aa = null;
    	num = names.length;
    	aa = new JButton[num];
    	name = new JLabel[num];
    	for(int i=0;i<num;i++) {
    		aa[i] = new JButton("Invite");
    		name[i] = new JLabel(names[i],JLabel.LEFT);
    		aa[i].addActionListener(this);
    		aa[i].setEnabled(false);
    	}
    	pre.window.mid.Tplayer.getColumnModel().getColumn(0).setCellEditor(pre.window.mr);
		pre.window.mid.Tplayer.getColumnModel().getColumn(0).setCellRenderer(pre.window.mr);
		pre.window.mid.Tplayer.getColumnModel().getColumn(1).setCellEditor(pre.window.mr);
		pre.window.mid.Tplayer.getColumnModel().getColumn(1).setCellRenderer(pre.window.mr);
		pre.window.mid.model.fireTableDataChanged();
    }
    
    public void setRender(int a) {
    	aa[a].setText("Invited!");
    	aa[a].setEnabled(false);
    	pre.window.myclient.invite(name[a].getText());
    }
 
    @Override
    public Object getCellEditorValue() {
        // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // TODO Auto-generated method stub
    	if(column==1)
    		return aa[row];
    	else
    		return name[row];
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	JButton button = (JButton)e.getSource();
    	for(int i=0;i<num;i++)
    	{
    		if(button.equals(aa[i])&&aa[i].isEnabled())
    		{
    			System.out.println(i);
    			pre.window.mr.setRender(i);
    			pre.window.mid.Tplayer.getColumnModel().getColumn(0).setCellEditor(pre.window.mr);
    			pre.window.mid.Tplayer.getColumnModel().getColumn(0).setCellRenderer(pre.window.mr);
    			pre.window.mid.Tplayer.getColumnModel().getColumn(1).setCellEditor(pre.window.mr);
    			pre.window.mid.Tplayer.getColumnModel().getColumn(1).setCellRenderer(pre.window.mr);
    			pre.window.mid.model.fireTableDataChanged();
    		}
    	} 
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
		if(column==1)
			return aa[row];
		else
			return name[row];
    }
     
}
