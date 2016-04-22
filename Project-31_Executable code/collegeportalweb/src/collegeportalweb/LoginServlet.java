package collegeportalweb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection conn;
	PreparedStatement ps;
	Properties prop;
	
    public void init() {
    	
    	try{
    		conn=MyConnection.getConnection();
    		ps=conn.prepareStatement("select id,branch,position from login where id=? and password=?");

    		}
    		catch(Exception e)
    		{
    			System.out.println("connection failed "+e);
    			e.printStackTrace();
    		}
        
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try {
			String   login=req.getParameter("login");
			String   password=req.getParameter("password");
			
			
			ps.setString(1,login);
			ps.setString(2,password);
			ResultSet rs;
			rs=ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				System.out.println("i'm in if");
				 
				  String rootdir = this.getServletContext().getInitParameter("rootdir"); 
				  res.sendRedirect(rootdir+"loginfail.html");
				  //out.println(pathname);
				}
			
			else{
				while(rs.next())
				{
					out.println("success");
					//creating session
					HttpSession hts=req.getSession(true);
					hts.setAttribute("id",rs.getString(1));
					hts.setAttribute("branch",rs.getString(2));
					hts.setAttribute("position",rs.getString(3));
					
					//request dispatcher for forwarding the request to AccountServlet
					//since it is calling from goPost() in AccountServlet Also doPost() method is called
					RequestDispatcher rd= this.getServletContext().getRequestDispatcher("/displayschedules.jsp");
					req.setAttribute("branch","General");
					rd.forward(req,res);
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("i'm in catch");
			e1.printStackTrace();
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
