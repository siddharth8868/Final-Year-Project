package collegeportalweb.andorid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;

public class ChangePasswordAndroidServlet extends HttpServlet {
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
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out=response.getWriter();
		String oldp,newp,id;
		    
		try {
			id=request.getParameter("pid");
			ResultSet rs;
			oldp=request.getParameter("oldp");
			newp=request.getParameter("newp");
			
			
					ps.setString(1,id);
					System.out.println(id);
					rs=ps.executeQuery();
					//rs=ps.executeQuery();
							rs.next();
							System.out.println(rs.getString(1));
							System.out.println(oldp);
					if(rs.getString(1).equals(oldp))
					{
						System.out.println("matched");
						ps1.setString(1,newp);
						ps1.setString(2,id);
						int n=ps1.executeUpdate();
						if(n==1)
						{
							System.out.println("Updated successfully");
							out.print("Updated successfully");
							return;
						}
						else{
							System.out.println("something went wrong");
							out.print("something went wrong");
							return;
						}
					}
					else{
						System.out.println("password didnotmatched");
						out.print("Password not matched");
						return;
					}
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					out.print("problem at server, try after some time");
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
