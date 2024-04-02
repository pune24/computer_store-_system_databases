import java.io.*;
import java.sql.*;

public class connection
{	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	int i=0;
	
	public Connection()
	{
		
		}
		
	
	public void opencon()
	{
		try
		  {
		  	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 		 	con=DriverManager.getConnection("jdbc:odbc:computershop","","");	
		  	stmt=con.createStatement();
		  }
		  catch(Exception e){System.out.println(e);}
		}
		
		public void closecon()
		{
			try
			  {
		  	
				stmt.close();
	  			con.close();
	  			}
	  			catch(Exception e){System.out.println(e);}
			}
	
		public ResultSet ExecuteQuery(String query)
		{
		//	ResultSet rs=null;
			try
			{
				opencon();
				rs=stmt.executeQuery(query);			
				}
				catch(Exception e){
					 System.out.println(e);
					closecon();
					}
				return rs;
			}
			public int ExecuteUpdate(String query)
			{				
				try
				{
					opencon();
					i=stmt.executeUpdate(query);
					closecon();
					}
					catch(Exception e){
						System.out.println(e);
						closecon();
						}
					
					return i;
					
				}
	}