
package collegeportalweb.andorid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;

public class DeleteForumsAndroidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	PreparedStatement ps;
	Statement st = null;
	
	@Override
	public void init() throws ServletException {
		try {
			conn=MyConnection.getConnection();
			conn.setAutoCommit(false);
			ps=conn.prepareStatement("delete from forums where id in(?) and uploader=?");
			st=conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id,sp,ar[];
		int n;
		Savepoint savepoint1 = null;
		sp=request.getParameter("myArray1");
		id=request.getParameter("id");
		ar= sp.split(",");
		System.out.println(sp);
		
		try{
			savepoint1 = conn.setSavepoint("Savepoint1");
			ps.setString(1,sp.replace("F",""));
			ps.setString(2,id);
			n=ps.executeUpdate();
			System.out.println("deleted");		
			for(n=0;n<ar.length;n++)
			{
				st.executeUpdate("drop table "+ar[n]+"");
				System.out.println("deleted="+ar[n]);
				
			}
			conn.commit();
			RequestDispatcher rd= this.getServletContext().getRequestDispatcher("/deleteforumsandroid.jsp?id="+id);
			rd.forward(request,response);
			
		} catch (Exception e) {
			e.printStackTrace();
			   try {
				conn.rollback(savepoint1);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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
