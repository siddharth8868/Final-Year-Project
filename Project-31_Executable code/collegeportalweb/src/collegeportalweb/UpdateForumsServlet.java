package collegeportalweb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateForumsServlet
 */
public class UpdateForumsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn;
	PreparedStatement ps;
	Date d;
	@Override
	public void init() throws ServletException {
		super.init();
		try{
		conn = MyConnection.getConnection();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tid,content,uploader,upbranch,upposition;
		HttpSession hts=request.getSession();
		tid=request.getParameter("tid");
		
		uploader=(String) hts.getAttribute("id");
		upbranch=(String) hts.getAttribute("branch");
		upposition=(String) hts.getAttribute("position");;
		content = request.getParameter("replaycontent");
		content = content.replace("\n","<br>");
		d = new Date();
		
		try{
			
		ps=conn.prepareStatement("insert into f"+tid+" VALUES(?,?,?,?,?)");
		ps.setString(1,content);
		ps.setString(2,uploader);
		ps.setDate(3,new java.sql.Date(d.getTime()));
		ps.setString(4,upbranch);
		ps.setString(5,upposition);
		
		ps.executeUpdate();
		/*
		String q="insert into f"+tid+" values('"+content+"','"+uploader+"','"+new java.sql.Date(d.getTime())+"','"+upbranch+"','"+upposition+"'";
		System.out.println(q);
		st=conn.createStatement();
		st.executeUpdate("insert into f"+tid+" values('"+content+"','"+uploader+"','"+new java.sql.Date(d.getTime())+"','"+upbranch+"','"+upposition+"'");
		*/
		
		RequestDispatcher rd= this.getServletContext().getRequestDispatcher("/displayforumsone.jsp?id="+tid+"");
		rd.forward(request,response);
		return;
		}
		catch(Exception e){
			e.printStackTrace();
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
