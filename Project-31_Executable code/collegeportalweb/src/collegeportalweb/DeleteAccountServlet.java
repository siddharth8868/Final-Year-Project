package collegeportalweb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteAccountServlet
 */
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Connection conn = null;
	Statement st = null;
	@Override
	public void init() throws ServletException {
		try{
		conn=MyConnection.getConnection();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sp=request.getParameter("myArray1");
		System.out.println(sp);
		
		
		try {
			
			st=conn.createStatement();
			String q="delete from login where id in("+sp+")";
			System.out.println(q);
			st.executeUpdate(q);
			RequestDispatcher rd= this.getServletContext().getRequestDispatcher("/deleteaccounts.jsp");
			rd.forward(request,response);
			st.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				st.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		try{
		
		conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
