package collegeportalweb.andorid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;

public class UpdateForumsServletAndroid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn;
	PreparedStatement ps,ps1;
	Date d;
	@Override
	public void init() throws ServletException {
		super.init();
		try{
		conn = MyConnection.getConnection();
		ps1=conn.prepareStatement("select * from login where id=?");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tid,content,uploader,upbranch = null,upposition = null;
		ResultSet rs;
		tid=request.getParameter("tid");
		uploader=request.getParameter("user");
		
		content = request.getParameter("replaycontent");
		content = content.replace("\n","<br>");
		d = new Date();
		
		try{
			
			ps1.setString(1,uploader);
			rs=ps1.executeQuery();
			while(rs.next()){
				upbranch=rs.getString(3);
				upposition=rs.getString(4);
			}
			
			
		ps=conn.prepareStatement("insert into f"+tid+" VALUES(?,?,?,?,?)");
		ps.setString(1,content);
		ps.setString(2,uploader);
		ps.setDate(3,new java.sql.Date(d.getTime()));
		ps.setString(4,upbranch);
		ps.setString(5,upposition);
		ps.executeUpdate();
		
		RequestDispatcher rd= this.getServletContext().getRequestDispatcher("/displayforumsoneandroid.jsp?id="+tid+"&user="+uploader);
		rd.forward(request,response);
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
