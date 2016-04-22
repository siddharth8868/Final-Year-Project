package collegeportalweb.andorid;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import collegeportalweb.MyConnection;

public class AddSchedulesAndroidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn;
	PreparedStatement ps,ps1;
	String subject,branch,validity,content,id,upbranch,pass;

	@Override
	public void init() throws ServletException {
		try {
			conn=MyConnection.getConnection();
			ps=conn.prepareStatement("select * from login where id=? and password=?");
			ps1 = conn.prepareStatement("insert into schedules values(schedules_sequence.nextval,?,?,?,?,?,?,?,?)");

		} catch (Exception e) {
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		id=request.getParameter("id");
		pass=request.getParameter("pass");
		upbranch=request.getParameter("pbranch");
		subject=request.getParameter("subject");
		branch=request.getParameter("branch");
		validity=request.getParameter("validity");
		content=request.getParameter("content");
		if(content.equals(""))
		{
			
			content="-";
		}
		System.out.print(id+"\n"+pass+"\n"+subject+"\n"+branch+"\n"+validity+"\n"+content+"\n"+upbranch);
		try {
			
			ps.setString(1,id);
			ps.setString(2,pass);
			ResultSet rs;
			rs=ps.executeQuery();
			if (!rs.isBeforeFirst()) {
					out.print("failed");
					return;
				}
			else{
				int n=saveindatabase();
				if(n==1)
	  	 		  {
	  	 			System.out.println("completed");
	  	 			out.print("success");
	  	 			System.out.println(out);
	  	 		  }
	  	 		  else{
	  	 			out.println("failed to add schedule");
	  	 		  }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("fail"); 
			return;
		}
		
		return;
	}
	
	public int saveindatabase() {
		
		Date cdate = new Date();
		long cdateLong = cdate.getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
		Date vdate = null;
		try {
			vdate = sdf1.parse(validity);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		   
		 try {
			   
			   ps1.setString(1,subject.replace("\n","<br>"));
			   ps1.setDate(2,new java.sql.Date(cdateLong));
			   ps1.setDate(3,new java.sql.Date(vdate.getTime()));
			   ps1.setString(4,"");
			   ps1.setString(5,content.replace("\n","<br>"));
			   ps1.setString(6,id);
			   ps1.setString(7,branch);
			   ps1.setString(8,upbranch);
				System.out.println("successfully updated");
			   int n = ps1.executeUpdate();
  			   return n;
			   
		   } catch (SQLException e) {
		 	e.printStackTrace();
		 	return 0;
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
