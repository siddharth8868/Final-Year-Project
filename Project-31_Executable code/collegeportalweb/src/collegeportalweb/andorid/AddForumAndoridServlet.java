package collegeportalweb.andorid;

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

import collegeportalweb.MyConnection;

public class AddForumAndoridServlet extends HttpServlet {
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
			ps3=conn.prepareStatement("select * from login where id=? and password=?");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String content,uploader,pass,branch,subject,upbranch = null,upposition = null;
			ResultSet rs;
		    java.io.PrintWriter out = response.getWriter( );
		    
		   content= request.getParameter("content").replace("\n","<br>");
		   if(content.equals(""))
		   {
			   content="-";
		   }
		   uploader=request.getParameter("id");
		   pass=request.getParameter("pass");
		   subject= (String)request.getParameter("subject").replace("\n","<br>");
		   branch= (String)request.getParameter("branch");
		   cdate = new Date();
		   

		    try{
		    	ps3.setString(1,uploader);
		    	ps3.setString(2,pass);
		    	rs=ps3.executeQuery();
				   System.out.println("try");
				if (!rs.isBeforeFirst()) {
					   System.out.println("if");
						out.print("loginfailed");
						return;
					}
				else{
					while(rs.next())
					{
						   System.out.println("else");
						upbranch=rs.getString(3);
						upposition=rs.getString(4);
						   System.out.println(upbranch+"\n"+upposition);
						
					}
				}
				
		    	
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    
		   
		   try {
			   System.out.println(uploader+"\n"+pass+"\n"+upbranch+"\n"+upposition+"\n"+subject+"\n"+content+"\n"+branch);
			ps.setString(1,content);
			ps.setString(2,uploader);
			ps.setString(3,subject);
			ps.setString(4,branch);
			ps.setDate(5,new java.sql.Date(cdate.getTime()));
			ps.setString(6,upbranch);
			ps.setString(7,upposition);
			
			long n = ps.executeUpdate();
			rs=ps1.executeQuery();
			rs.next();
			n=rs.getLong(1);
				
			
				ps2=conn.prepareStatement("create table F"+n+"(replay clob,uploader varchar2(20),ondate date,upbranch varchar2(20),upposition varchar2(10))");
				n=ps2.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("error");
		    return;

		}
		   out.print("success");
		   return;
		
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
