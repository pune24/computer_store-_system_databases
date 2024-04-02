import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.sql.*;

public class LoginU extends JFrame implements ActionListener
{
	JButton btnLogin,btnCancel;
 	JTextField txtUser,txtPwd;
 	JLabel l1,l2,l3,l4,l5;
 	connection conn=new connection();
 	int i=0;
 	
 	JList myList=null;
	
	public LoginU()
	{
		Container con=getContentPane();
 		con.setLayout(null);
 		
 		
 		l1=new JLabel("CMS");
		Font f=new Font("Georgia",Font.BOLD,48);
	 	l1.setFont(f); 
	 	l1.setForeground(Color.red);
	 	l1.setBounds(165,25,200,50);
	 	con.add(l1);
	 	
	 	l2=new JLabel("Computer shop management system");
		Font f1=new Font("Georgia",Font.BOLD,14);
	 	l2.setFont(f1); 
	 	l2.setForeground(Color.BLUE);
	 	l2.setBounds(110,80,300,50);
	 	con.add(l2);
	 	
	 	l3=new JLabel("User");
		//l3.setFont(f1); 
	 	//l3.setForeground(Color.BLUE);
	 	l3.setBounds(110,120,300,30);
	 	con.add(l3);
	 	
	 	l5=new JLabel("User");
		//l3.setFont(f1); 
	 	//l3.setForeground(Color.BLUE);
	 	l5.setBounds(330,330,300,30);
	 	con.add(l5);
	 	
	 	txtUser=new JTextField(20);
	 	txtUser.setBounds(200,120,150,25);
		con.add(txtUser);
	 	
	 	l4=new JLabel("Password");
		//l4.setFont(f1); 
	 	//l4.setForeground(Color.BLUE);
	 	l4.setBounds(110,150,300,30);
	 	con.add(l4);
	 	
	 	txtPwd=new JTextField(20);
	 	txtPwd.setBounds(200,150,150,25);
		con.add(txtPwd);
		
		 btnLogin=new JButton("Login");
		 btnLogin.setBounds(110,200,100,30);
		 btnLogin.addActionListener(this);
		 con.add(btnLogin);
		 
		 btnCancel=new JButton("Cancel");
		 btnCancel.setBounds(220,200,100,30);
		 btnCancel.addActionListener(this);
		 con.add(btnCancel);
		 
		/* String[] data = {"one", "two", "three", "four"};
		 myList=new JList(data);
		 myList.setLayoutOrientation(JList.VERTICAL_WRAP);
		 myList.setBounds(110,200,100,30);
		 con.add(myList);*/
		
		setVisible(true);
		setTitle("Login");
		setSize(450,410);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		public void actionPerformed(ActionEvent e)
 		{
 			int counter=0;
 			if(e.getSource()==btnLogin)
  			{
  				
  				if(i<2)
  				{
	  				try
	  				{
		  			
		  			    ResultSet rs=conn.ExecuteQuery("Select * From LoginU");		
		  				while(rs.next())
		  				{		  					
		  					if(rs.getString("User").equals(""+txtUser.getText()) && rs.getString("pwd").equals(""+txtPwd.getText()))
		  					{
		  						conn.closecon();
		  						
		  						//l5.setText("ok");
		  						}
		  					
		  					}
		  				conn.closecon();			  				
		  			++i;
	  				}
	  				catch(Exception e1){}	
  				}
  				else
  				  this.dispose();  				 
  				
  				}
 			}
		
		public static void main(String args[])
		{
			new LoginU();
			}
	}