package collegeportalweb;

import java.sql.*;

public class MyConnection {
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection conn = null;
		try{
			    Class.forName("oracle.jdbc.driver.OracleDriver");
	    		conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
			}
		catch(Exception e){
			e.printStackTrace();
			System.out.print("error myconnection");
		}
		return conn;
	}
	
	

}
