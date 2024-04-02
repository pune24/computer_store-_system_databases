import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.sql.*;

public class AddStock extends JFrame implements ActionListener, WindowListener
{
	
	connection conn=new connection();
	JComboBox comBoxItem=null;
	JComboBox comSubItem=null;
	JRadioButton Active,Deactive;
	JLabel l1,l2,l3,l4,l5;
	JTextField txtQuantity,txtWastage,txtFree;
	JButton btnAdd,btnCancel;
	String itemName="",subItem="";
	computershop c;
	
	public AddStock(computershop c)
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
		
		l3=new JLabel("Quantity");	
	 	l3.setBounds(50,120,100,30);
	 	con.add(l3);
	 	
	 	txtQuantity=new JTextField("0",20);
	 	txtQuantity.setBounds(160,120,150,25);
		con.add(txtQuantity);
	 	
	 	l4=new JLabel("Wastage Item");	
	 	l4.setBounds(50,155,100,30);
	 	con.add(l4);
	 	
	 	txtWastage=new JTextField("0",20);
	 	txtWastage.setBounds(160,155,150,25);
		con.add(txtWastage);
	 	
	 	l5=new JLabel("Free Item");	
	 	l5.setBounds(50,190,100,30);
	 	con.add(l5);
		
		txtFree=new JTextField("0",20);
	 	txtFree.setBounds(160,190,150,25);
		con.add(txtFree);
		
		btnAdd=new JButton("Add");
	    btnAdd.setBounds(50,230,100,30);
		 btnAdd.addActionListener(this);
		 con.add(btnAdd);
		 
		 btnCancel=new JButton("Cancel");
		 btnCancel.setBounds(160,230,100,30);
		 btnCancel.addActionListener(this);
		 con.add(btnCancel);
		
		setVisible(true);
		setTitle("Add Stock");
		setSize(350,310);setLocation(300,150);
		addWindowListener(this);
	//	setDefaultCloseOperation(EXIT_ON_CLOSE);
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
 		//	System.out.println(subItem);
 			}
 			
 		if(e.getSource()==btnAdd)
 		{
 			int oldStock=0;
 			int oldwast=0;
 			int oldfree=0;
 			ResultSet rs=null;
 			try
			{
				rs=conn.ExecuteQuery("Select Stock,Wastage,Free from StockMaster where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ subItem +"'");					
				while(rs.next())
				{
					//System.out.println(""+oldStock);
						oldStock=rs.getInt("Stock");
						oldwast=rs.getInt("Wastage");
 			            oldfree=rs.getInt("Free");
					}
						
				//System.out.println(""+oldStock);		
				
				conn.ExecuteUpdate("Update StockMaster set Stock="+ ((oldStock+Integer.parseInt(txtQuantity.getText())+Integer.parseInt(txtFree.getText()))-Integer.parseInt(txtWastage.getText())) +",Wastage="+ (oldwast+Integer.parseInt(txtWastage.getText())) +",Free="+ (oldfree+Integer.parseInt(txtFree.getText())) +" where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ subItem +"'");    		
				JOptionPane.showMessageDialog(null,"Stock added successefully!");
				txtQuantity.setText("");
				txtWastage.setText("");
				txtFree.setText("");
				comBoxItem.setSelectedItem("Select");
				comSubItem.setSelectedItem("Select"); 		
				conn.closecon();	
				}
			catch(Exception e1){System.out.println(""+e1);}
 			}
 				
 		if(e.getSource()==btnCancel)
 		{
 			c.setVisible(true);
            this.dispose();
 			}	   	
 		}	
 			
	public static void main(String[] args)
	{
	//	new AddStock();
		}	
	}