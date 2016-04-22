package collegeportalweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChangePasswordServlet
 */
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	java.sql.Connection conn;
	PreparedStatement ps,ps1;
	
	public void init() throws ServletException {
		try {
				conn=MyConnection.getConnection();
				ps=conn.prepareStatement("select password from login where id=?");
				ps1=conn.prepareStatement("update  login set password=? where id=?");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		HttpSession hts=request.getSession(true);
		String id;
		            
			id=(String) hts.getAttribute("id");
			String oldp,newp;
			ResultSet rs;
			oldp=request.getParameter("oldp");
			newp=request.getParameter("newp");
			System.out.println(id);
			System.out.println(oldp);
			System.out.println(newp);
			
			if(id==null)
			{
				System.out.println("Access denied ");
				out.print("<html><body><font color=\"red\">Access denied</font></body></html>");
				return;
			}
			else{
				
				try {
					ps.setString(1,id);
					System.out.println(id);
					Statement st=conn.createStatement();
							rs=st.executeQuery("select password from login where id='"+id+"'");
					//rs=ps.executeQuery();
							rs.next();
					System.out.println(rs.getString(1));
					if(rs.getString(1).equals(oldp))
					{
						ps1.setString(1,newp);
						ps1.setString(2,id);
						int n=ps1.executeUpdate();
						if(n==1)
						{
							out.print("<html><body><font color=\"green\">Updated successfully</font></body></html>");
							return;
						}
						else{
							out.print("<html><body><font color=\"red\">something went wrong</font></body></html>");
							return;
						}
					}
					else{
						out.print("<html><body><font color=\"red\">Password didnot matched</font></body></html>");
						return;
					}
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					out.print("<html><body><font color=\"red\">problem at server, try after some time</font></body></html>");
					return;
				}
				
			}

		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
