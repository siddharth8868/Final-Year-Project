package collegeportalweb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AccountServlet
 */
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	java.sql.Connection conn;
	
	public void init() throws ServletException {
		try {
			//conn=MyConnection.getConnection();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hts=request.getSession(true);
		PrintWriter out=response.getWriter();
		String id;
		            
			id=(String) hts.getAttribute("id");
			System.out.println(id);
			if(id==null)
			{
				System.out.println("Access denied ");
				out.print("<html><body><font color=\"red\">Access denied</font></body></html>");	
			}
			else{
				
				out.print("success");
				
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
