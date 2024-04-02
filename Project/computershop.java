import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.sql.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class computershop extends JFrame implements ActionListener, TableModelListener, FocusListener
{
	connection conn=new connection();
	public JComboBox comBoxItem=null;
	JComboBox comSubItem=null;
	JLabel l1,l2,l3,l4,l5,l6;
	JTextField txtQuantity,txtRate,txtAmount,txtTotalAmount;
	String itemName="",subItem="";
	JButton btnAdd,btnDelete,btnBill;
	JMenuBar menuBar;
	JTable table;
	Vector rows,columns;
	DefaultTableModel tabModel;
	JScrollPane scrollPane;
	int itemID=0;
	JMenuItem additem,deleteitem,addstock,checkstock,addcust,logout;
	
	public computershop()
	{
		Container con=getContentPane();
		Color c=new Color(157,238,130);
 		con.setBackground(c);
		con.setLayout(null);
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
		
		l3=new JLabel("Quantity");	
	 	l3.setBounds(50,120,100,30);
	 	con.add(l3);
	 	
	 	txtQuantity=new JTextField("0",20);
	 	txtQuantity.setBounds(160,120,150,25);
	 	txtQuantity.addActionListener(this);
	 	txtQuantity.addFocusListener(this);
		con.add(txtQuantity);
	 	
	 	l4=new JLabel("Rate");	
	 	l4.setBounds(50,155,100,30);
	 	con.add(l4);
	 	
	 	txtRate=new JTextField("0",20);
	 	txtRate.setBounds(160,155,150,25);
	 	txtRate.enable(false);
		con.add(txtRate);
	 	
	 	l5=new JLabel("Amount");	
	 	l5.setBounds(50,190,100,30);
	 	con.add(l5);
		
		txtAmount=new JTextField("0",20);
	 	txtAmount.setBounds(160,190,150,25);
	 	txtAmount.enable(false);
		con.add(txtAmount);
		
		l6=new JLabel("Amount");	
	 	l6.setBounds(50,475,100,30);
	 	con.add(l6);
		
		txtTotalAmount=new JTextField("0",20);
	 	txtTotalAmount.setBounds(160,475,150,25);
	 	txtTotalAmount.enable(false);
		con.add(txtTotalAmount);
		
		btnAdd=new JButton("Add");
	    btnAdd.setBounds(50,230,100,30);
		btnAdd.addActionListener(this);
		con.add(btnAdd);
		
		btnDelete=new JButton("Delete");
		btnDelete.setBounds(160,230,100,30);
		btnDelete.addActionListener(this);
		con.add(btnDelete);
		
		btnBill=new JButton("Bill");
		btnBill.setBounds(270,230,100,30);
		btnBill.addActionListener(this);
		con.add(btnBill);
		
		rows=new Vector();
		columns= new Vector();
		String[] columnNames = 
		{ 
			"Name",
			"SubItem",
			"Quantity",
			"Rate",
			"TotalAmount"
			};
		addColumns(columnNames);		
		tabModel=new DefaultTableModel();
		tabModel.setDataVector(rows,columns);		
		table = new JTable(tabModel);
		scrollPane= new JScrollPane(table);//ScrollPane		
		table.setRowSelectionAllowed(false);		
		table.getModel().addTableModelListener(this);
		table.getParent().setBackground(Color.white);
		table.setRowSelectionAllowed(true);
		scrollPane.setBounds(50,270,350,200);
		con.add(scrollPane);	
		
		setVisible(true);
		setTitle("computershop");
		setSize(450,600);setLocation(300,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
		menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
        
        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("Item");
        JMenu editMenu = new JMenu("Stock");
        JMenu CustMenu = new JMenu("Customer");
	//JMenu LogoutMenu = new JMenu("Logout");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(CustMenu);
	//menuBar.add(LogoutMenu);
        
        // Create and add simple menu item to one of the drop down menu
        additem = new JMenuItem("Add Item");
        deleteitem = new JMenuItem("Delete Item");
        addstock = new JMenuItem("Add Stock");
        checkstock = new JMenuItem("Check Stock");
        addcust = new JMenuItem("Add Customer");
	logout = new JMenuItem("Logout");
       
        
        fileMenu.add(additem);
        additem.addActionListener(this);
        fileMenu.add(deleteitem);
        deleteitem.addActionListener(this);
	 fileMenu.addSeparator();
	 fileMenu.add(logout);
	logout.addActionListener(this);

        editMenu.add(addstock);
        addstock.addActionListener(this);
        editMenu.add(checkstock);
        checkstock.addActionListener(this);
        CustMenu.add(addcust);
        addcust.addActionListener(this);
	//LogoutMenu.add(Logout);
	//LogoutMenu.addActionListener(this);
       
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
		
	public void FillTable()
	{
		try
			{					
				String[] item=new String[5];
				addRow(item);
				rows.clear();						  
				
								
				ResultSet rs=conn.ExecuteQuery("Select * from TempSalesDetails");					
				while(rs.next())
				{							
					item[0]=rs.getString(1);
					item[1]=rs.getString(2);
					item[2]=rs.getString(3);
					item[3]=rs.getString(4);
					item[4]=rs.getString(5);
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
			try
			{			
				ResultSet rs=conn.ExecuteQuery("Select ItemID,ItemSubName From ItemDetails where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and Status=YES");					
				while(rs.next())
				{	
					itemID=rs.getInt("ItemID");							
					comSubItem.addItem(rs.getString("ItemSubName"));				
				//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
					}
					
				conn.closecon();	
				}
				catch(Exception e1){}
		}
		
	public void focusLost(FocusEvent e){
		if(e.getSource() instanceof JTextField) {
		//	((JTextField)e.getSource()).setBackground(Color.White);
		Integer t=Integer.parseInt(txtQuantity.getText()) * Integer.parseInt(txtRate.getText());		
		txtAmount.setText(t.toString());
		}
	}	
	
	public void focusGained(FocusEvent e){
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		//	txtAmount.setText(""+ (Integer.parseInt(txtQuantity.getText())*Integer.parseInt(txtRate.getText())));
		}
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
 		//	FillTable(subItem);	
 			try
			{			
				ResultSet rs=conn.ExecuteQuery("Select ItemRate From ItemDetails where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ subItem +"'");					
				while(rs.next())
				{	
					txtRate.setText(rs.getString("ItemRate"));							
				//	comBoxItem.addItem(rs.getString("ItemRate"));				
				//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
					}
					
				conn.closecon();	
				}
				catch(Exception e1){}
 			}		
 		if(e.getSource()==btnAdd)
 		{
 			//System.out.println("ok");
 			int stock=0;
 			try
			{			
				ResultSet rs=conn.ExecuteQuery("Select Stock From StockMaster where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ subItem +"'");					
				while(rs.next())
				{	
					stock=rs.getInt("Stock");			
				//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
					}
					
				if(stock>=Integer.parseInt(txtQuantity.getText()))
				{
					conn.ExecuteUpdate("Insert into TempSalesDetails values('"+itemName+"','"+ subItem +"',"+ Integer.parseInt(txtQuantity.getText()) +","+ Integer.parseInt(txtRate.getText()) +","+ Integer.parseInt(txtAmount.getText()) +")");    
					JOptionPane.showMessageDialog(null,"Item added successefully!");
					txtQuantity.setText("");
					txtRate.setText("");
					txtAmount.setText("");
					comBoxItem.setSelectedItem("Select");
					comSubItem.setSelectedItem("Select"); 


					}
				else
				 	JOptionPane.showMessageDialog(null,"Stock not available .");
					
					
				conn.closecon();
				FillTable();
				
				FillTotalAmount();	
				}
				catch(Exception e1){}
 			}
 			
 		if(e.getSource()==btnDelete)
 		{
 			
 			//System.out.println( table.getSelectedRow());
 			Object obj1 = GetData(table, table.getSelectedRow(), 0);
 			Object obj2 = GetData(table, table.getSelectedRow(), 1);
 //			System.out.println(""+obj1); 
 			try
			{
				conn.ExecuteUpdate("Delete From TempSalesDetails where ItemName='"+ obj1.toString() +"' and ItemSubName='"+ obj2.toString() +"'");
				conn.closecon();
				}
			catch(Exception e1){}
			FillTable();
			JOptionPane.showMessageDialog(null,"Item deleted successefully!");
 			}
 			
 		if(e.getSource()==btnBill)
 		{
 		//	System.out.println("ok");
 		    this.setVisible(false);
			BillFrame b=new BillFrame(this,txtTotalAmount.getText());
 		//	JOptionPane.showMessageDialog(b,""); 
 		//	this.setVisible(true);					
 			}		
 		 			 			
 		if(e.getSource()==additem)
 		{
 			this.setVisible(false);
 			new addNewItem(this);
 			
 			}
 		if(e.getSource()==deleteitem)
 		{
 			this.setVisible(false);
 			new DeleteItem(this);
 			}
 		if(e.getSource()==addstock)
 		{
 			this.setVisible(false);
 			new AddStock(this);
 			}
 		if(e.getSource()==checkstock)
 		{
 			this.setVisible(false);
 			new CheckStock(this);
 			}
 			
 			
 		if(e.getSource()==addcust)
 		{
 			this.setVisible(false);
 			new customer(this);
 			}
		if(e.getSource()==logout)
 			{
 			System.exit(0);
 			
 			}	
 		}	
 	
 	public Object GetData(JTable table1, int row_index, int col_index){
    return table1.getModel().getValueAt(row_index, col_index);
    }  
 		
 	public void FillTotalAmount()
 	{
 		Integer t=0;
 		try
			{			
				ResultSet rs=conn.ExecuteQuery("Select Sum(TotalAmount) From TempSalesDetails ");					
				while(rs.next())
				{	
					t=rs.getInt(1);
					txtTotalAmount.setText(t.toString());
				//	txtTotalAmount.setText(rs.getString(1));
				//	txtRate.setText(rs.getString("ItemRate"));							
				//	comBoxItem.addItem(rs.getString("ItemRate"));				
				//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
					}
					
				conn.closecon();	
				}
				catch(Exception e1){}
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
	//	new computershop();
		}
	}