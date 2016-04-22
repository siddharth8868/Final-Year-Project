<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="collegeportalweb.MyConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Connection conn=null;
	PreparedStatement log,lib,shd,frm;
	try{
		conn=MyConnection.getConnection();
		log=conn.prepareStatement("select * from login");
		lib=conn.prepareStatement("select * from library ");
		shd=conn.prepareStatement("select * from schedules");
		frm=conn.prepareStatement("select * from forums");
		
		ResultSet rs;
		rs=log.executeQuery();
		
		if(rs!=null)
		{
			while(rs.next())
			{
				out.println("insert into login values('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(3)+"','"+rs.getString(4)+"');");
				%><br><%
			}
			%><br><br><%
		}
		

		
		rs=lib.executeQuery();
		if(rs!=null)
		{
			while(rs.next())
			{
				out.println("insert into library('"+rs.getString(1)+"','"+rs.getString(2)+"','"+rs.getString(3)+"','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"','"+rs.getString(7)+"');");
				%><br><%
			}
			%><br><br><%
		}
		
		out.println("\n\n");
		
		rs=shd.executeQuery();
		if(rs!=null)
		{
			while(rs.next())
			{
				out.println("insert into schedules(schedules_sequence.nextval,'"+rs.getString(2)+"','"+rs.getString(3)+"','"+rs.getString(4)+"','"+rs.getString(5)+"','"+rs.getString(6)+"','"+rs.getString(7)+"','"+rs.getString(8)+"','"+rs.getString(9)+"');");
			}
		}

		conn.close();
	}
	catch(Exception e)
	{
		conn.close();
		e.printStackTrace();
	}
	%>
</body>
</html>