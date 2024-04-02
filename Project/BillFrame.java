import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class BillFrame extends JFrame implements ActionListener, FocusListener, WindowListener
{
	connection conn=new connection();
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16;
	JButton btnSave,btnCancle;
	computershop c;
	int custid=0,billNo=0;
	JComboBox comBoxItem=null;
	
	public BillFrame(computershop c,String s)
	{
		this.c=c;
		Container con=getContentPane();
 		con.setLayout(null);
 		Color r=new Color(157,238,130);
 		con.setBackground(r);
 		l1=new JLabel("Name");	
	 	l1.setBounds(20,20,100,30);
	 	con.add(l1);
	 	
	 	String[] item={"Select"};
 		comBoxItem= new JComboBox(item);
        comBoxItem.setEditable(false);
        comBoxItem.setBounds(130,20,100,25);        
        comBoxItem.addActionListener(this);
        FillItem();        
		con.add(comBoxItem);
	 	
	 
	 	
	 	l13=new JLabel("Total Amount");	
	 	l13.setBounds(240,20,100,30);
	 	con.add(l13);
	 	
	 	l14=new JLabel("Discount");	
	 	l14.setBounds(20,60,100,30);
	 	con.add(l14);
	 	
	 	l15=new JLabel("Final Amount");	
	 	l15.setBounds(240,60,100,30);
	 	con.add(l15);
	 	
	
		
		t13=new JTextField(s,20);
	 	t13.setBounds(350,20,100,25);
	 	t13.enable(false);	 	
		con.add(t13);
		
		t14=new JTextField(20);
	 	t14.setBounds(130,60,100,25);	
	 	t14.addFocusListener(this); 	
		con.add(t14);
		
		t15=new JTextField(20);
	 	t15.setBounds(350,60,100,25);
	 	t15.enable(false);	 	
		con.add(t15);
		
		btnSave=new JButton("Save");
	    btnSave.setBounds(130,100,100,25);
		btnSave.addActionListener(this);
		con.add(btnSave);
		
		btnCancle=new JButton("Cancel");
	    btnCancle.setBounds(300,100,100,30);
		btnCancle.addActionListener(this);
		con.add(btnCancle);
		
	
		
		setVisible(true);
		setTitle("Bill");
		setSize(550,200);setLocation(300,150);
		addWindowListener(this);


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
			ResultSet rs=conn.ExecuteQuery("Select Name From CustomerMaster");					
			while(rs.next())
			{								
				comBoxItem.addItem(rs.getString("Name"));				
			//	JOptionPane.showMessageDialog(null,"Combo has already this item.");
				}
				
			conn.closecon();	
			}
			catch(Exception e1){}
		} 			
		
	public void getCustid()
	{
		String itemName =""+ comBoxItem.getSelectedItem();
		try
		{			
			ResultSet rs=conn.ExecuteQuery("Select id from CustomerMaster where Name='"+itemName+"' ");					
			while(rs.next())
			{	
				custid=rs.getInt(1);			
				}
			conn.closecon();	
			}
			catch(Exception e1){System.out.println(e1);}
		}	
		
	public void getBillNo()
	{
		getCustid();
		billNo=0;
		try
		{			
			ResultSet rs=conn.ExecuteQuery("Select Max(BillNo) from SalesMaster");					
			while(rs.next())
			{	
				billNo=rs.getInt(1);			
				}
				
			if(billNo>0)
				billNo+=1;
			else
				billNo=1;
				
			
			Date today = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = formatter.format(today);
			conn.ExecuteUpdate("Insert into SalesMaster values("+billNo+","+custid+","+Integer.parseInt(t13.getText())+","+Integer.parseInt(t14.getText())+","+Integer.parseInt(t15.getText())+",'"+dateString+"')");
				
			conn.closecon();	
			}
			catch(Exception e1){System.out.println(e1);}
		}	
		
	public void focusLost(FocusEvent e){
		if(e.getSource() instanceof JTextField) {
		//	((JTextField)e.getSource()).setBackground(Color.BLACK);
		Integer t=Integer.parseInt(t13.getText()) - Integer.parseInt(t14.getText());		
		t15.setText(t.toString());
		}
	}	
	
	public void focusGained(FocusEvent e){
		if(e.getSource() instanceof JTextField) {
			((JTextField)e.getSource()).setBackground(Color.yellow);
		//	txtAmount.setText(""+ (Integer.parseInt(txtQuantity.getText())*Integer.parseInt(txtRate.getText())));
		}
	}		
	
	
	public void StockMain(String itemName,String subName,int quan)
	{
		try
		{			
			ResultSet rs=conn.ExecuteQuery("Select Stock from StockMaster where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+subName+"'");					
			while(rs.next())
			{
				conn.ExecuteUpdate("Update StockMaster set Stock="+(rs.getInt(1)-quan)+" where ItemID=(Select ItemID from ItemMaster where ItemName='"+ itemName +"') and ItemSubName='"+subName+"'");
				}
			conn.closecon();	
			}
			catch(Exception e1){}
		}
	
	public void StockMaintain()
	{
		String itemName="",subName="";
		int quan=0;
		
		try
		{			
			ResultSet rs=conn.ExecuteQuery("Select * from TempSalesDetails");					
			while(rs.next())
			{	
				itemName=rs.getString(1);
				subName=rs.getString(2);
				quan=rs.getInt(3);
				StockMain(itemName,subName,quan);
				conn.ExecuteUpdate("Insert into FinalSalesDetails values("+billNo+",'"+itemName+"','"+subName+"',"+quan+","+rs.getInt(4)+","+rs.getInt(5)+")");			
				}
				conn.ExecuteUpdate("Delete From TempSalesDetails");
			conn.closecon();	
			}
			catch(Exception e1){}
		}
	
	public void actionPerformed(ActionEvent e)
 	{
 		if(e.getSource()==btnSave)
 		{			
 			//System.out.println("OK");
				if(comBoxItem.getSelectedItem()=="Select")
					JOptionPane.showMessageDialog(null,"Please select the customer name first!");
				else{
 				getBillNo();
 			
 			StockMaintain();
JOptionPane.showMessageDialog(null,"Bill information saved successefully!");
 			
 			c.setVisible(true);
            this.dispose();
 			 }		
 			}
 			
 		if(e.getSource()==btnCancle)
 		{
 			c.setVisible(true);
 			this.dispose(); 			
 			}	
 		}
		
	public static void main(String args[])
	{
		//new BillFrame("100");
		}	
	}