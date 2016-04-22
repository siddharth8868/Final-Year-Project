package collegeportalweb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteSchedulesServlet
 */
public class DeleteSchedulesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	Connection conn = null;
	PreparedStatement ps;
	Statement st = null;
	
	@Override
	public void init() throws ServletException {
		try {
			conn=MyConnection.getConnection();
			ps=conn.prepareStatement("delete from schedules where id in(?) and uploader=?");
			st=conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession hts=request.getSession(true);
		String id,sp;
		sp=request.getParameter("myArray1");
		id=(String)hts.getAttribute("id");
		System.out.println(sp);
		
		
		try {
			ps.setString(1,sp);
			ps.setString(2,id);
			System.out.println("delete from schedules where id in("+sp+") and uploader="+id+"");
			String quer="delete from schedules where id in("+sp+") and uploader='"+id+"'";
			//ps.executeUpdate();
			st.executeUpdate(quer);
			RequestDispatcher rd= this.getServletContext().getRequestDispatcher("/deleteschedules.jsp");
			rd.forward(request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		
	}
	
	@Override
	public void destroy() {
		
		try {
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
