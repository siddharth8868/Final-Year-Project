package collegeportalweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddAccountServlet
 */
public class AddAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn;
	PreparedStatement ps;
	
	public void init() throws ServletException {
		try {
				conn=MyConnection.getConnection();
				ps=conn.prepareStatement("insert into login values(?,?,?,?)");
			} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		HttpSession hts=request.getSession(true);
		String id,password,branch,position;
		
		            
			id=(String) hts.getAttribute("id");
			branch=(String) hts.getAttribute("branch");
			position=(String) hts.getAttribute("position");
			System.out.println(id);
			
			if(id==null)
			{
				System.out.println("Access denied ");
				out.print("<html><body><font color=\"red\">Access denied</font></body></html>");
				return;
			}
			else{
				
				id=request.getParameter("id");
				password=request.getParameter("pass");
				branch=request.getParameter("branch");
				System.out.println("branch="+branch);
				position= request.getParameter("position");
				
			// start editing your module work here	
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
		    		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");
		    		ps=con.prepareStatement("insert into login values(?,?,?,?)");
					
					ps.setString(1,id);
					ps.setString(2,password);
					ps.setString(3,branch);
					ps.setString(4,position);
					String check="insert into login values('"+id+"','"+password+"','"+branch+"','"+position+"')";
					System.out.println(check);
					
					int n=ps.executeUpdate();
					System.out.println("try1");
						if(n==1)
						{
							System.out.println("try2");
							out.print("<font color=\"green\">Updated successfully</font>");	
						}
						else{
							System.out.println("try2");
							out.print("<font color=\"red\">something went wrong</font>");
						}
					
						ps.close();
						con.close();
				} 	
				catch(SQLException se)
				{
					out.print("<font color=\"red\">already exist</font>");
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
				
			}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
