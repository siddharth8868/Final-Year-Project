package collegeportalweb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Testservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		Connection conn=MyConnection.getConnection();
		PreparedStatement ps=conn.prepareStatement("select * from forums");
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getLong(1)+"\n"+rs.getString(2)+"\n"+rs.getString(3)+"\n"+rs.getString(4)+"\n"+rs.getString(5)+"\n"+rs.getDate(6)+"\n"+rs.getString(7)+"\n"+rs.getString(8));
			System.out.print("\n\n\n");
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	
	

}
