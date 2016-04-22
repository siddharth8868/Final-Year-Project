package collegeportalweb.andorid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;


public class AuthenticateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn;
	PreparedStatement ps;
	@Override
	public void init() throws ServletException {
		super.init();
		try{
		conn=MyConnection.getConnection();
		ps=conn.prepareStatement("select id from login where id=? and password=?");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = null;
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			String   login=request.getParameter("login");
			String   password=request.getParameter("password");
			String   call=request.getParameter("call");
			
			
			ps.setString(1,login);
			ps.setString(2,password);
			ResultSet rs;
			rs=ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				System.out.println("i'm in if");
				 
				  out.println("<HTML><body>You are no <span style=\"color:blue;\">Authorized</span> to Access these details</body></HTML>");
				  return;
				}
			
			else{
				while(rs.next())
				{
					if(call.equals("library"))
					{
						rd= this.getServletContext().getRequestDispatcher("/displaybooksandroid.jsp");
					}
					else if (call.equals("forums")) {
						rd= this.getServletContext().getRequestDispatcher("/displayforums.jsp");
					}
					else if(call.equals("Schedules"))
					{
						rd= this.getServletContext().getRequestDispatcher("/displayschedules.jsp");
					}
					rd.forward(request,response);
				}
			}
			out.println("finish");
		} catch (SQLException e1) {
			System.out.println("i'm in catch");
			e1.printStackTrace();
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
