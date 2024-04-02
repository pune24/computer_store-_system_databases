import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.sql.*;

public class addNewItem extends JFrame implements ActionListener, TableModelListener, WindowListener
{
	connection conn=new connection();
	JComboBox comBoxItem=null;
	JLabel l1,l2;
	JTextField txtsubItem,txtRate;
	JTable table;
	Vector rows,columns;
	DefaultTableModel tabModel;
	JScrollPane scrollPane;
	JButton btnAdd,btnCancel;
	String itemName="";
	computershop c;
	
	
	public addNewItem(computershop c)
	{	
		this.c=c;
		Container con=getContentPane();
 		con.setLayout(null);
 		Color r=new Color(157,238,130);
 		con.setBackground(r);
 		l1=new JLabel("Item");	
	 	l1.setBounds(50,50,100,30);
	 	con.add(l1);
 		
 		String[] item={"Select"};
 		comBoxItem= new JComboBox(item);
        comBoxItem.setEditable(true);
        comBoxItem.setBounds(160,50,150,25);        
        comBoxItem.addActionListener(this);
        FillItem();        
		con.add(comBoxItem);
		
		l2=new JLabel("SubItem");	
	 	l2.setBounds(50,80,100,30);
	 	con.add(l2);
		
		txtsubItem=new JTextField(20);
	 	txtsubItem.setBounds(160,80,150,25);
		con.add(txtsubItem);
		
		l2=new JLabel("Rate");	
	 	l2.setBounds(50,110,100,30);
	 	con.add(l2);
		
		txtRate=new JTextField(20);
	 	txtRate.setBounds(160,110,150,25);
		con.add(txtRate);
		
		 btnAdd=new JButton("Add");
		 btnAdd.setBounds(50,150,100,30);
		 btnAdd.addActionListener(this);
		 con.add(btnAdd);
		 
		 btnCancel=new JButton("Cancel");
		 btnCancel.setBounds(160,150,100,30);
		 btnCancel.addActionListener(this);
		 con.add(btnCancel);
		
		rows=new Vector();
		columns= new Vector();
		String[] columnNames = 
		{ 
			"Name",
			"SubItem",
			"Rate"
			};
		addColumns(columnNames);		
		tabModel=new DefaultTableModel();
		tabModel.setDataVector(rows,columns);		
		table = new JTable(tabModel);
		scrollPane= new JScrollPane(table);//ScrollPane		
		table.setRowSelectionAllowed(false);		
		table.getModel().addTableModelListener(this);
		table.getParent().setBackground(Color.white);
		scrollPane.setBounds(50,200,250,100);
		con.add(scrollPane);
		
		
		setVisible(true);
		setTitle("Add New Item");
		setSize(450,410);setLocation(300,150);
		addWindowListener(this);
	//	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		}	
		
	public void windowClosing(WindowEvent e) {
            c.setVisible(true);
 			c.comBoxItem.removeAllItems();
 			c.comBoxItem.addItem("Select");
 			c.FillItem();
       		this.dispose();
    }

    public void windowClosed(WindowEvent e) {
      
    }

    public void windowOpened(WindowEvent e) {
        
    }

    public void windowIconified(WindowEvent e) {
       
    }

    public void windowDeiconified(WindowEvent e) {
      
    }

    public void windowActivated(WindowEvent e) {
        
    }

    public void windowDeactivated(WindowEvent e) {
       
    }
		
	public void addColumns(String[] colName)//Table Columns
	{
		for(int i=0;i<colName.length;i++)
		columns.addElement((String) colName[i]);
		}	
	
	public void addRow(String[] item)
	{		
		Vector t = new Vector();
		t.addElement((String) item[0]);
		t.addElement((String) item[1]);
		t.addElement((String) item[2]);
	
		rows.addElement(t);
		table.addNotify();		
		}
	
	public void FillItem()
	{
		//System.out.println("ok");
		comBoxItem.removeAllItems();
		comBoxItem.addItem("Select");
		try
		{			
			ResultSet rs=conn.ExecuteQuery("Select ItemName From ItemMaster where Status=YES");					
			while(rs.next())
			{								
				comBoxItem.addItem(rs.getString("ItemName"));				
			//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
				}
				
			conn.closecon();	
			}
			catch(Exception e1){}
		} 
		
	public void FillTable(String s)
	{
		try
			{	
				String[] item=new String[3];
				addRow(item);
				rows.clear();				
				ResultSet rs=conn.ExecuteQuery("Select ItemSubName,ItemRate From ItemDetails where ItemID=(Select ItemID from ItemMaster where ItemName='"+ s +"')");					
				while(rs.next())
				{							
					item[0]=itemName;
					item[1]=rs.getString("ItemSubName");
					item[2]=rs.getString("ItemRate");
					addRow(item);				
					}
					
				conn.closecon();	
				}
				catch(Exception e1){}
		}	
		
	public void actionPerformed(ActionEvent e)
 	{
 		if(e.getSource()==comBoxItem)
 		{
	 		itemName =""+ comBoxItem.getSelectedItem();
	 		FillTable(itemName); 			
		    } 
 		if(e.getSource()==btnAdd)
 		{
 			int itemID=0;
 			ResultSet rs=null;
 			try
			{
	 			rs=conn.ExecuteQuery("Select ItemID from ItemMaster where ItemName='"+ itemName +"'");					
				while(rs.next())
						itemID=rs.getInt("ItemID");
						
					//	System.out.println("ok" +""+itemID);
				if(itemID>0)
				{
					//System.out.println("ok"+""+itemID);
					conn.ExecuteUpdate("Insert into ItemDetails values("+itemID+",'"+txtsubItem.getText()+"',"+Integer.parseInt(txtRate.getText())+",YES)");
					conn.ExecuteUpdate("Insert into StockMaster values("+itemID+",'"+txtsubItem.getText()+"',0,0,0)");    
					FillTable(itemName);
					JOptionPane.showMessageDialog(null,"Item added successefully!");
					txtsubItem.setText("");
					txtRate.setText("");
					comBoxItem.setSelectedItem("Select");
					//conn.closecon();
					}
				else
				{
					//System.out.println("ok");
					//conn.closecon();
					itemID=0;
					rs=conn.ExecuteQuery("Select max(ItemID) from ItemMaster");					
				    while(rs.next())
						itemID=rs.getInt(1);
						
				//	System.out.println("ok"+itemID);	
					if(itemID>0)	
						itemID+=1;
					else
					    itemID=1;				
					
					
					conn.ExecuteUpdate("Insert into ItemMaster values("+itemID+",'"+itemName+"',YES)");    
					conn.ExecuteUpdate("Insert into ItemDetails values("+itemID+",'"+txtsubItem.getText()+"',"+Integer.parseInt(txtRate.getText())+",YES)");    
					conn.ExecuteUpdate("Insert into StockMaster values("+itemID+",'"+txtsubItem.getText()+"',0,0,0)");    
					//System.out.println("ok");
											
					FillTable(itemName);
					txtsubItem.setText("");
					txtRate.setText("");
					comBoxItem.setSelectedItem("Select");
					JOptionPane.showMessageDialog(null,"Item added successefully!");					
					FillItem();
					
					System.out.println("ok");
					}				
				conn.closecon();	
				}
			catch(Exception e1){}		
 			}
 		if(e.getSource()==btnCancel)
 		{
 			c.setVisible(true);
 			c.comBoxItem.removeAllItems();
 			c.comBoxItem.addItem("Select");
 			c.FillItem();
       		this.dispose();
 		    /*try{
 		    
 			table.print();
 		}catch(Exception e1){}	*/
 			}	
 		 }
 			
	 public void tableChanged(javax.swing.event.TableModelEvent source)
	 {
                 String msg="";
                 TableModel tabMod = (TableModel)source.getSource();
          switch (source.getType())
                   {
                       case TableModelEvent.UPDATE:
                       msg="Table Value Updated for  cell "+table.getSelectedRow()+","+table.getSelectedColumn()+"\nWhich is "+table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()).toString();
              JOptionPane.showMessageDialog(null,msg,"Table Example",JOptionPane.INFORMATION_MESSAGE);
                break;

                   }

   		 }//Table Changed Method

		
 		
	public static void main(String args[])
	{
		//new addNewItem();
		}
	}