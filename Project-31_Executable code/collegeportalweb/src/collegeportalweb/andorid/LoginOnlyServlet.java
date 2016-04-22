package collegeportalweb.andorid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;

public class LoginOnlyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	Connection conn;
	PreparedStatement ps;
	@Override
	public void init() throws ServletException {
		super.init();
		try{
		conn=MyConnection.getConnection();
		ps=conn.prepareStatement("select * from login where id=? and password=?");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("i'm called");
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			String   login=request.getParameter("uid");
			String   password=request.getParameter("upass");	
			
			ps.setString(1,login);
			ps.setString(2,password);
			ResultSet rs;
			System.out.print(login+"\n"+password);
			rs=ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				System.out.println("i'm in if");
				 
				  out.print("invalid");
				  return;
				}
			
			else{
				while(rs.next())
				{
					out.print(rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4));
					System.out.print(rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3)+"/"+rs.getString(4));
					return;
				}
			}

			}catch (SQLException e1) {
				out.print("exception");
				return;
			}
		
	}
	
	@Override
	public void destroy() {
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
