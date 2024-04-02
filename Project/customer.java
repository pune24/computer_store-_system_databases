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

public class customer extends JFrame implements ActionListener, WindowListener
{
	connection conn=new connection();
	JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16;
	JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16;
	JButton btnSave,btnCancle,btnAdd;
	JComboBox comBoxItem=null;
	int id=0;
	String itemName="";
	computershop c;
//	int billNo=0;
	
	public customer(computershop c)
	{
		this.c=c;
		Container con=getContentPane();
 		con.setLayout(null);
 		Color r=new Color(157,238,130);
 		con.setBackground(r);
 		l1=new JLabel("Name");	
	 	l1.setBounds(20,20,100,30);
	 	con.add(l1);
	 	
	 	l2=new JLabel("Contact Person");	
	 	l2.setBounds(240,20,100,30);
	 	con.add(l2);
	 	
	 	l3=new JLabel("Address");	
	 	l3.setBounds(20,60,100,30);
	 	con.add(l3);
	 	
	 	l4=new JLabel("PostCode");	
	 	l4.setBounds(240,60,100,30);
	 	con.add(l4);
	 	
	 	l5=new JLabel("Country");	
	 	l5.setBounds(20,100,100,30);
	 	con.add(l5);
	 	
	 	l6=new JLabel("Phone");	
	 	l6.setBounds(240,100,100,30);
	 	con.add(l6);
	 	
	 	l7=new JLabel("Mobile");	
	 	l7.setBounds(20,140,100,30);
	 	con.add(l7);
	 	
	 	l8=new JLabel("Email");	
	 	l8.setBounds(240,140,100,30);
	 	con.add(l8);
	 	
	 	l9=new JLabel("Fax");	
	 	l9.setBounds(20,180,100,30);
	 	con.add(l9);
	 	
	 	l10=new JLabel("VAT Number");	
	 	l10.setBounds(240,180,100,30);
	 	con.add(l10);
	 	
	 	l11=new JLabel("State");	
	 	l11.setBounds(20,220,100,30);
	 	con.add(l11);
	 	
	 	l12=new JLabel("Comment");	
	 	l12.setBounds(240,220,100,30);
	 	con.add(l12);	 	
	 	
	 
		
		String[] item={"Select"};
 		comBoxItem= new JComboBox(item);
        comBoxItem.setEditable(true);
        comBoxItem.setBounds(130,20,100,25);        
        comBoxItem.addActionListener(this);
        FillItem();        
		con.add(comBoxItem);
		
		t2=new JTextField(20);
	 	t2.setBounds(350,20,100,25);	 	
		con.add(t2);
		
		t3=new JTextField(20);
	 	t3.setBounds(130,60,100,25);	 	
		con.add(t3);
		
		t4=new JTextField(20);
	 	t4.setBounds(350,60,100,25);	 	
		con.add(t4);
		
		t5=new JTextField(20);
	 	t5.setBounds(130,100,100,25);	 	
		con.add(t5);
		
		t6=new JTextField(20);
	 	t6.setBounds(350,100,100,25);	 	
		con.add(t6);
		
		t7=new JTextField(20);
	 	t7.setBounds(130,140,100,25);	 	
		con.add(t7);
		
		t8=new JTextField(20);
	 	t8.setBounds(350,140,100,25);	 	
		con.add(t8);
		
		t9=new JTextField(20);
	 	t9.setBounds(130,180,100,25);	 	
		con.add(t9);
		
		t10=new JTextField(20);
	 	t10.setBounds(350,180,100,25);	 	
		con.add(t10);
		
		t11=new JTextField(20);
	 	t11.setBounds(130,220,100,25);	 	
		con.add(t11);
		
		t12=new JTextField(20);
	 	t12.setBounds(350,220,100,25);	 	
		con.add(t12);
		
	
		
		btnAdd=new JButton("Add");
		btnAdd.setBounds(130,250,100,30);
		btnAdd.addActionListener(this);
		btnAdd.setVisible(false);
		con.add(btnAdd);
			
		btnSave=new JButton("Save");
	    btnSave.setBounds(240,250,100,30);
	    btnSave.setVisible(false);
		btnSave.addActionListener(this);
		con.add(btnSave);
		
		
		btnCancle=new JButton("Cancel");
	    btnCancle.setBounds(350,250,100,30);
		btnCancle.addActionListener(this);
		con.add(btnCancle);
		
	//	getBillNo();
		
		setVisible(true);
		setTitle("Customer");
		setSize(550,340);setLocation(300,150);
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
		
	public void FillItem()
	{
		//System.out.println("ok");
		//comBoxItem.addItem("Test");
	//	comBoxItem.removeAllItems();
	//	comBoxItem.addItem("Select");
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

	
	public void actionPerformed(ActionEvent e)
 	{
 		int temp=0;
 		if(e.getSource()==comBoxItem)
 		{
	 	    itemName =""+ comBoxItem.getSelectedItem();
	 	
	 		try
	 		{
				ResultSet rs=conn.ExecuteQuery("Select * From CustomerMaster where Name='"+ itemName +"'");					
				while(rs.next())
				{	
					id=rs.getInt(1);
					t2.setText(rs.getString(3));
					t3.setText(rs.getString(4));
					t4.setText(rs.getString(5));
					t5.setText(rs.getString(6));
					t6.setText(rs.getString(7));
					t7.setText(rs.getString(8));
					t8.setText(rs.getString(9));
					t9.setText(rs.getString(10));
					t10.setText(rs.getString(11));
					t11.setText(rs.getString(12));
					t12.setText(rs.getString(13));										
					temp=1;
					btnSave.setVisible(true);
					btnAdd.setVisible(false);				
					}
					
				conn.closecon();	
				}
				catch(Exception e1){}
				
				if(temp==0)
				{
					btnAdd.setVisible(true);
					btnSave.setVisible(false);
				}
	 					
		    } 
 		
 		if(e.getSource()==btnAdd)
 		{
 			
 			int billNo=0;
			try
			{			
				ResultSet rs=conn.ExecuteQuery("Select Max(id) from CustomerMaster");					
				while(rs.next())
				{	
					billNo=rs.getInt(1);			
					}
					
				if(billNo>0)
					billNo+=1;
				else
					billNo=1;
					
				
			/*	Date today = new Date();
	            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	            String dateString = formatter.format(today);*/
				conn.ExecuteUpdate("Insert into CustomerMaster values("+billNo+",'"+itemName+"','"+t2.getText()+"','"+t3.getText()+"','"+t4.getText()+"','"+t5.getText()+"','"+t6.getText()+"','"+t7.getText()+"','"+t8.getText()+"','"+t9.getText()+"','"+t10.getText()+"','"+t11.getText()+"','"+t12.getText()+"')");
				JOptionPane.showMessageDialog(null,"Customer record added successefully!");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
				t7.setText("");
				t8.setText("");
				t9.setText("");
				t10.setText("");
				t11.setText("");
				t12.setText("");
				
				comBoxItem.setSelectedItem("Select");
				conn.closecon();	
				}
				catch(Exception e1){System.out.println(e1);}
				btnAdd.setVisible(false);
 			}
 		
 		if(e.getSource()==btnSave)
 		{ 		
 			try
			{			
			/*	ResultSet rs=conn.ExecuteQuery("Select Max(id) from CustomerMaster");					
				while(rs.next())
				{	
					billNo=rs.getInt(1);			
					}
					
				if(billNo>0)
					billNo+=1;
				else
					billNo=1;*/		
			
				//conn.ExecuteUpdate("Insert into CustomerMaster values("+billNo+",'"+itemName+"','"+t2.getText()+"','"+t3.getText()+"','"+t4.getText()+"','"+t5.getText()+"','"+t6.getText()+"','"+t7.getText()+"','"+t8.getText()+"','"+t9.getText()+"','"+t10.getText()+"','"+t11.getText()+"','"+t12.getText()+"')");
				conn.ExecuteUpdate("Update CustomerMaster set ContactPerson='"+t2.getText()+"', Address='"+t3.getText()+"', PostCode='"+t4.getText()+"',Country='"+t5.getText()+"',Phone='"+t6.getText()+"',Mobile='"+t7.getText()+"',Email='"+t8.getText()+"',Fax='"+t9.getText()+"',VATNumber='"+t10.getText()+"',PDVNumber='"+t11.getText()+"',Comment='"+t12.getText()+"' where id="+id+"");	
				JOptionPane.showMessageDialog(null,"Customer record updated successefully!");
				t2.setText("");
				t3.setText("");
				t4.setText("");
				t5.setText("");
				t6.setText("");
				t7.setText("");
				t8.setText("");
				t9.setText("");
				t10.setText("");
				t11.setText("");
				t12.setText("");

				comBoxItem.setSelectedItem("Select");
				conn.closecon();	
				}
				catch(Exception e1){System.out.println(e1);}
				btnSave.setVisible(false);		
 			}
 			
 		if(e.getSource()==btnCancle)
 		{
 			c.setVisible(true);
 			this.dispose(); 			
 			}	
 		}
		
	public static void main(String args[])
	{
	//	new customer();
		
		}	
	}