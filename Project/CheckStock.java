import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.sql.*;

public class CheckStock extends JFrame implements ActionListener, TableModelListener, WindowListener
{
	
	connection conn=new connection();
	JComboBox comBoxItem=null;
	JComboBox comSubItem=null;	
	JLabel l1,l2,l3,l4,l5;
	JTextField txtQuantity,txtWastage,txtFree;
	JButton btnAdd,btnCancel;
	String itemName="",subItem="";
	JTable table;
	Vector rows,columns;
	DefaultTableModel tabModel;
	JScrollPane scrollPane;
	computershop c;
	
	public CheckStock(computershop c)
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
	 	
	 	String[] item1={"Select"};
	 	comSubItem= new JComboBox(item1);
	 	comSubItem.setBounds(160,80,150,25);
	 	comSubItem.addActionListener(this);
	    FillSubItem();
		con.add(comSubItem);
		
		rows=new Vector();
		columns= new Vector();
		String[] columnNames = 
		{ 
			"Name",
			"SubItem",
			"Stock",
			"Wastage",
			"Free"
			};
		addColumns(columnNames);		
		tabModel=new DefaultTableModel();
		tabModel.setDataVector(rows,columns);		
		table = new JTable(tabModel);
		scrollPane= new JScrollPane(table);//ScrollPane		
		table.setRowSelectionAllowed(false);		
		table.getModel().addTableModelListener(this);
		table.getParent().setBackground(Color.white);
		scrollPane.setBounds(25,110,350,200);
		con.add(scrollPane);	
		
		setVisible(true);
		setTitle("Check Stock");
		setSize(450,350);setLocation(300,150);
		addWindowListener(this);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
	public void windowClosing(WindowEvent e) {
       c.setVisible(true);
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
		t.addElement((String) item[3]);
		t.addElement((String) item[4]);
	
		rows.addElement(t);
		table.addNotify();		
		}
		
	public void FillTable(String s)
	{
		try
			{	
				String q="";
				String[] item=new String[5];
				addRow(item);
				rows.clear();
				
				if(s.equals("All"))
				  q="Select ItemSubName,Stock,Wastage,Free From StockMaster where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"')";	
				else
				  q="Select ItemSubName,Stock,Wastage,Free From StockMaster where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ s +"' ";	
				  
				  
				
								
				ResultSet rs=conn.ExecuteQuery(q);					
				while(rs.next())
				{							
					item[0]=itemName;
					item[1]=rs.getString("ItemSubName");
					item[2]=rs.getString("Stock");
					item[3]=rs.getString("Wastage");
					item[4]=rs.getString("Free");
					addRow(item);				
					}
					
				conn.closecon();	
				}
				catch(Exception e1){}
		}			
		
	public void FillItem()
	{
		//System.out.println("ok");		
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
		
	public void FillSubItem()
	{
			comSubItem.removeAllItems();
			comSubItem.addItem("Select");
			comSubItem.addItem("All");
			try
			{			
				ResultSet rs=conn.ExecuteQuery("Select ItemSubName From ItemDetails where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"')");					
				while(rs.next())
				{								
					comSubItem.addItem(rs.getString("ItemSubName"));				
				//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
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
	 		FillSubItem();	 	 		 			
		    }
		   
		if(e.getSource()==comSubItem)
 		{
 			subItem=""+ comSubItem.getSelectedItem(); 
 			FillTable(subItem);			
 		//	System.out.println(subItem);
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
 			
	public static void main(String[] args)
	{
	//	new CheckStock();
		}	
	}