<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<style type="text/css">
a {text-decoration: none !important;}
a {color:#000;}      /* unvisited link */
a:visited {color:#000;}  /* visited link */
a:hover {color:#0000FF;}  /* mouse over link */
a:active {color:#0000FF;}
</style>
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
	String type=(String)request.getAttribute("type");
	if(position.equals("admin")){
		%><jsp:include page="menuadmin.html"/><br><%
	}
	else if(position.equals("HOD")){
		%><jsp:include page="menuhod.html"/><br><%
	}
	else if(position.equals("Faculty")){
		%><jsp:include page="menufaculty.html"/><%
	}
	else if(position.equals("Student")){
		%><jsp:include page="menustudents.html"/><%
	}	
%>
<div align="center"><h2>Forums-<%=type%></h2></div>
<div class="mycenter">
	<div class="CSSTableGenerator">
	<table>
	<col width="5%">
  	<col width="75%">
  	<col width="10%">
  	<col width="10%">
	
 <tr><td>SL</td><td>SUBJECT</td><td>BY</td><td>ON</td></tr>
	<% 
  try {
    Class.forName("oracle.jdbc.driver.OracleDriver");  
    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    String query,branch;
	if(type.equals("All"))
	{
		query="select * from forums order by id desc";
	}
	else{
		query="select * from forums where branch='"+type+"' order by id desc";
	}
    
    Statement st= connection.createStatement();
    ResultSet rs=st.executeQuery(query);
    int c=0;
    while(rs.next())
    {
    	c++;
    	%>
    	 <tr>
    	 <td><%=c%></td>
    	 <td><a href="/collegeportalweb/displayforumsone.jsp?id=<%=rs.getLong(1)%>"><%=rs.getString(4)%></a></td>
    	 <td><%=rs.getString(3)%></td>
    	 <td><%=rs.getDate(6)%></td>
    	 </tr>
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