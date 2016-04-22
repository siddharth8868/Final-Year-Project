<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>
</head>
<body>
	<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	
	try{
	String position= (String)session.getAttribute("position");
	String s;
	if(position.equals("admin")){
		%><jsp:include page="menuadmin.html"/><br><%
	}
	else if(position.equals("HOD")){
		%><jsp:include page="menuhod.html"/><br><%
	}
		
	
%>
<div align="center"><h2>Accounts</h2></div>
<div class="mycenter">
	<div class="CSSTableGenerator">
	<table>
 <tr><td>ID</td><td>PASSWORD</td><td>BRANCH</td><td>POSITION</td></tr>
	<% 
  try {
    Class.forName("oracle.jdbc.driver.OracleDriver");  
    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    String query,branch;
    branch = (String)session.getAttribute("branch");
	if(position.equals("admin"))
	{
		query="select * from login order by branch,position,id";
	}
	else{
		query="select * from login where branch='"+branch+"' order by branch,position,id";
	}
    
    Statement st= connection.createStatement();
    ResultSet rs=st.executeQuery(query);
    while(rs.next())
    {
    	%>
    	 <tr>
    	 <td><%=rs.getString(1)%></td>
    	 <td><%=rs.getString(2)%></td>
    	 <td><%=rs.getString(3)%></td>
    	 <td><%=rs.getString(4)%></td></tr>
    	<%
    }

    /*ResultSet rs= st.executeQuery(strquery);*/
  }
  catch(Exception e)
  {
    System.out.println("Could not connect");
  }
	%>
	</table>
	</div>
</div>
	<%
	}//try end for back button refresh
	catch(Exception e)
	{
		%>Sorry cannot access<%
	}
	%>
</body>
</html>