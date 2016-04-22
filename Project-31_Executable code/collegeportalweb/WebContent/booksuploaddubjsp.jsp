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
	else if(position.equals("Faculty")){
		%><jsp:include page="menufaculty.html"/><%
	}

	
	%>
	
	<div align="center"><h2>Accounts</h2></div>
<div class="mycenter">
	<div class="CSSTableGenerator">
	<table>
 
	<% 
  try {
	  System.out.println("/11");
    Class.forName("oracle.jdbc.driver.OracleDriver");  
    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    String query,loc,path;
    String cat,searchby,search,q1=null,q2=null,q3;
    loc= getServletContext().getInitParameter("file-upload");
    cat= (String)request.getAttribute("cat");
    searchby= (String)request.getAttribute("searchby");
    search= (String)request.getAttribute("search");
    q3="libgeneral,libcivil,libcse,libit,libece,libeee,libmech,libauto,libmba,libmca";
    q3="libgeneral";
    System.out.println("/1");
    if(cat.equals("All"))
    {
    	q1= "select * from "+q3+" where "+searchby+" LIKE '%"+search+"%' order by "+searchby+""; 
    	System.out.println(q1);
    }
    else {
    	q2 =  "select * from "+cat+" where "+searchby+" LIKE '%"+search+"%' order by "+searchby+"";
    	
    	System.out.println(q1);
    }
    
    
	if(position.equals("STUDENT"))
	{
		query=q2;
	}
	else{
			query=q1;
	}
    
    Statement st= connection.createStatement();
    ResultSet rs=st.executeQuery(query);
    
    if(position.equals("STUDENT"))
	{
    	System.out.println("/18");
    	%><tr><td>TITLE</td><td>AUTHOR</td><td>SUBJECT</td><td>TYPE</td></tr>
    	<%
    	while(rs.next())
        {
    		path=loc+rs.getString(5);
        	%>
        	 <tr>
        	 <td><a href="<%=path%>"><%=rs.getString(1)%></a></td>
        	 
        	 <td><%=rs.getString(2)%></td>
        	 <td><%=rs.getString(3)%></td>
        	 <td><%=rs.getString(4)%></td></tr>
        	<%
        }
	}
	else{
		
		%><tr><td>TITLE</td><td>AUTHOR</td><td>SUBJECT</td><td>TYPE</td><td>UPLOADER</td></tr>
    	<%
    	while(rs.next())
        {
    		path=loc+rs.getString(5);
        	%>
        	 <tr>
        	 
        	 <td><a href="/collegeportalweb/SendBookServlet"><%=rs.getString(1)%></a></td>
        	 <td><%=rs.getString(2)%></td>
        	 <td><%=rs.getString(3)%></td>
        	 <td><%=rs.getString(4)%></td>
        	 <td><%=rs.getString(6)%></td></tr>
        	<%
        }
			
	}
    
    
    
  }
  catch(Exception e)
  {
	  e.printStackTrace();
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