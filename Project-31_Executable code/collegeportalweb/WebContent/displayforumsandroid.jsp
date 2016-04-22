<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
a {text-decoration: none !important;}
a {color:#000;}      /* unvisited link */
a:visited {color:#000;}  /* visited link */
a:hover {color:#0000FF;}  /* mouse over link */
a:active {color:#0000FF;}
</style>
<meta name="viewport" content="width=device-width">
<title>College Portal</title>
</head>
<body>
	<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	
	try{
	String branch=(String)request.getParameter("branch");
	String id=request.getParameter("id");
	String su="selected=\"selected\"";
	if(branch==null)
    {
    	branch="All";
    }
	%>
	<form action="displayforumsandroid.jsp">
	<input type="hidden" id="id" name="id" value="<%=id%>"/>
	<select name="branch" id="branch" style="width: 100%" onchange="this.form.submit()">
  	  	<option <% if(branch.equals("All")){%><%=su%><%}%>     value="All">ALL</option>
  	  	<option <% if(branch.equals("General")){%><%=su%><%}%> value="General">General</option>
  	  	<option <% if(branch.equals("Acadamic Branch")){%><%=su%><%}%>     value="Acadamic Branch">Acadamic Branch</option>
  	  	<option <% if(branch.equals("Administration")){%><%=su%><%}%>	   value="Administration">Administration</option>
	  	<option <% if(branch.equals("Sports")){%><%=su%><%}%>  value="Sports">Sports</option>
	  	<option <% if(branch.equals("Library")){%><%=su%><%}%> value="Library">Li</option>
  		<option <% if(branch.equals("CIVIL")){%><%=su%><%}%>   value="CIVIL">CIVIL</option>
		<option <% if(branch.equals("CSE")){%><%=su%><%}%>     value="CSE">CSE</option>
		<option <% if(branch.equals("IT")){%><%=su%><%}%>      value="IT">IT</option>
		<option <% if(branch.equals("ECE")){%><%=su%><%}%>     value="ECE">ECE</option>
		<option <% if(branch.equals("EEE")){%><%=su%><%}%>     value="EEE">EEE</option>
		<option <% if(branch.equals("MECH")){%><%=su%><%}%>    value="MECH">MECH</option>
		<option <% if(branch.equals("AUTO")){%><%=su%><%}%>    value="AUTO">AUTO</option>
		<option <% if(branch.equals("MBA")){%><%=su%><%}%>     value="MBA">MBA</option>
		<option <% if(branch.equals("MCA")){%><%=su%><%}%>     value="MCA">MCA</option>
      </select><br>
      </form>

 <%
      try {
    	    Class.forName("oracle.jdbc.driver.OracleDriver");  
    	    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    	    String query;
    		if(branch.equals("All"))
    		{
    			query="select * from forums order by id desc";
    		}
    		else{
    			query="select * from forums where branch='"+branch+"' order by id desc";
    		}
    	    
    	    Statement st= connection.createStatement();
    	    ResultSet rs=st.executeQuery(query);
    	    int c=0;
    	    while(rs.next())
    	    {
    	    	c++;
    	    	%>
    	    	<p><b><%=c%></b><a href="/collegeportalweb/displayforumsoneandroid.jsp?id=<%=rs.getLong(1)%>&user=<%=id%>"><%=rs.getString(4)%></a><br></p>
    	    	 <p align="right"><span style="color:#0080FF"><%=rs.getString(3)%></span><br><b><%=rs.getDate(6)%></b></p>
    	    	 <hr style="height: 3px; color: gray;">
    			<%
    	    }

    	    /*ResultSet rs= st.executeQuery(strquery);*/
    	  }
    	  catch(Exception e)
    	  {
    	    System.out.println("Could not connect");
    	  }

      %>
 
 <%
	}//try end for back button refresh
	catch(Exception e)
	{
		%>Sorry cannot access<%
	}
	%>
</body>
</html>