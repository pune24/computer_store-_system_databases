import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.*;
import java.sql.*;

public class DeleteItem extends JFrame  implements ActionListener, WindowListener
{
	connection conn=new connection();
	JComboBox comBoxItem=null;
	JComboBox comSubItem=null;
	JRadioButton Active,Deactive;
	JLabel l1,l2;
	String itemName="",subItem="";
	computershop c;
	
	public DeleteItem(computershop c)
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
		
		Active=new JRadioButton("Active");
		Active.setBounds(50,120,100,30);
		Active.addActionListener(this);
		con.add(Active);
		
		Deactive=new JRadioButton("Deactive");
		Deactive.setBounds(160,120,100,30);
		Deactive.addActionListener(this);
		con.add(Deactive);
	
		ButtonGroup group = new ButtonGroup();
        group.add(Active);
        group.add(Deactive);

		
		
		setVisible(true);
		setTitle("Delete Item");
		setSize(350,200);setLocation(300,150);
		setLocation(300,150);
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
 			
 		if(e.getSource()==Active)
 		{
 			//System.out.println("Yes  "+ subItem); 			
 			try
			{
				conn.ExecuteUpdate("Update ItemDetails set Status=YES where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ subItem +"'");    
				JOptionPane.showMessageDialog(null,"Item Activeted!");
				conn.closecon();	
				}
			catch(Exception e1){}	
 			}  	
		     
		if(e.getSource()==Deactive)
 		{			
 			//System.out.println("No  "+ subItem);
 			try
			{
				conn.ExecuteUpdate("Update ItemDetails set Status=NO where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+ subItem +"'");    
				JOptionPane.showMessageDialog(null,"Item Deactiveted!");
				conn.closecon();	
				}
			catch(Exception e1){}	 			
 			}       
 		}	
		
	public static void main(String[] args)
	{
	//	new DeleteItem();
		}	
	}