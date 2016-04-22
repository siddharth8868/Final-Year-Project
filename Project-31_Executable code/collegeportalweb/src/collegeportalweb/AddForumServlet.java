package collegeportalweb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddForumServlet
 */
public class AddForumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn;
	PreparedStatement ps,ps1,ps2,ps3;
	DateFormat dateFormat;
	Date cdate;
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			conn=MyConnection.getConnection();
			ps=conn.prepareStatement("insert into forums values(forum_sequence.nextval,?,?,?,?,?,?,?)");
			ps1=conn.prepareStatement("SELECT id FROM forums where id=(select max(id) from forums)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String content,uploader,subject,branch,upbranch,upposition;
			HttpSession hts=request.getSession(true);
		    java.io.PrintWriter out = response.getWriter( );
		    
		    String id = (String) hts.getAttribute("id");
		    if(id==null)
			{
				System.out.println("Access denied ");
				out.print("<html><body><font color=\"red\">Access denied</font></body></html>");
				return;
			}
		   
		    out.println("<html><body>");
		   content= request.getParameter("content").replace("\n","<br>");
		   if(content.equals(""))
		   {
			   content="-";
		   }
		   uploader=(String) hts.getAttribute("id");
		   upbranch=(String) hts.getAttribute("branch");
		   upposition=(String) hts.getAttribute("position");
		   
		   subject= (String)request.getParameter("subject").replace("\n","<br>");
		   branch= (String)request.getParameter("branch");
		   cdate = new Date();
		   
		   try {
			ps.setString(1,content);
			ps.setString(2,uploader);
			ps.setString(3,subject);
			ps.setString(4,branch);
			ps.setDate(5,new java.sql.Date(cdate.getTime()));
			ps.setString(6,upbranch);
			ps.setString(7,upposition);
			
			long n = ps.executeUpdate();
			ResultSet rs=ps1.executeQuery();
			rs.next();
			n=rs.getLong(1);
				
			
				ps2=conn.prepareStatement("create table F"+n+"(replay clob,uploader varchar2(20),ondate date,upbranch varchar2(20),upposition varchar2(10))");
				n=ps2.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("error in creating forum table");
		    out.println("<html><body>");
		    return;

		}
		   
		 
		   out.println("forum has been created successfully");
		   out.println("<html><body>");
		   return;
		
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
