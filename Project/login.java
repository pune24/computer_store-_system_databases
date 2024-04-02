import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.sql.*;

public class login extends JFrame implements ActionListener
{
public int i=1;
Connection conn=new Connection();


JLabel logo=new JLabel(new ImageIcon("C:/New/computershop/Project/logo.jpg"));
JLabel l1 = new JLabel("Username:-");
JTextField t1 = new JTextField(15);

JLabel l2 = new JLabel("Password:-");
JPasswordField p1 = new JPasswordField(20);

JButton b1=new JButton("Login");
JButton b2=new JButton("Cancel");

//JPanel p=new JPanel();

public login()
{Color c=new Color(157,238,130);
Container con = getContentPane();
setLayout(null);
con.setBackground(c);

logo.setBounds(100,20,550,155); 
l1.setBounds(191,124,370,150);

t1.setBounds(374,185,180,27);
t1.setToolTipText("ENTER USERNAME");

l2.setBounds(191,149,370,224);
p1.setBounds(374,247,180,27);
p1.setToolTipText("ENTER password");


b1.setBounds(191,300,125,30);
b2.setBounds(374,300,125,30);
b1.setMnemonic('L');
//getRootPane.setDefaultButton(b1);

con.add(logo);
con.add(l1);
con.add(t1);


con.add(l2);
con.add(p1);

con.add(b1);
con.add(b2);
          
b1.addActionListener(this);
b2.addActionListener(this);
setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
}

public void actionPerformed(ActionEvent e)
	{
	//	System.out.println("OK");
		
		
 			if(e.getSource()==b1)
  			{
  				
  				if(i<3)
  				{
	  				try
	  				{
		  			
		  			    ResultSet rs=conn.ExecuteQuery("Select * From LoginU");		
		  				while(rs.next())
		  				{		 
		  				//	System.out.println("OK"); 					
		  					if(rs.getString("User").equals(""+t1.getText()) && rs.getString("pwd").equals(""+p1.getText()))
		  					{
		  						conn.closecon();
		  						this.setVisible(false);
		  						new computershop();
		  						//l5.setText("ok");
		  						}
		  					
		  					}
		  				conn.closecon();
						++i;
						t1.setText("");
						p1.setText("");
						if(i<3)
						JOptionPane.showConfirmDialog((Component)null,"Please enter correct username & password","confirm dialog",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);			  				
		  			  else
						JOptionPane.showConfirmDialog((Component)null,"You enter Wrong Username or Password more than limit !","confirm dialog",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
	  				}
	  				catch(Exception e1){}	
  				}

  				else{
JOptionPane.showConfirmDialog((Component)null,"Sorry you are not authorised ?","confirm dialog",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
  				  this.dispose();  				 
  				}
  				}
		
		if(e.getSource()==b2)
		{
int result=JOptionPane.showConfirmDialog((Component)null,"Are you sure want to exit ?","confirm dialog ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon("smile.gif"));
	if(result==JOptionPane.YES_OPTION)
		System.exit(0);	
		}
}


public static void main(String args[])
{
login F = new login();
F.setSize(700,400);
F.setVisible(true);
//F.pack();
F.setTitle("Admin_Login");
F.setResizable(true);
F.setLocation(200,100);
}
}