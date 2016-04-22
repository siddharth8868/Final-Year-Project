package collegeportalweb.andorid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;

public class DisplayScheduleAndroidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn;
	PreparedStatement ps;
	String branch,output;
	ResultSet rs;
	
	public void init() throws ServletException {
		try {
				conn=MyConnection.getConnection();
			} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		branch=request.getParameter("branch");
		try {
			String s="Select * from schedules where branch="+branch+" order by id desc";
			System.out.println(s);
			ps=conn.prepareStatement("Select * from schedules where branch='"+branch+"' order by id desc");
			rs=ps.executeQuery();
			while(rs.next())
			{
				out.print(rs.getString(2)+
						"/@#"+rs.getDate(3)+
						"/@#"+rs.getDate(4)+
						"/@#"+rs.getString(5)+
						"/@#"+rs.getString(6)+
						"/@#"+rs.getString(7)+
						"##epnd");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			out.print("error");
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
