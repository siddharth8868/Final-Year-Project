<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	String cat,searchby,search;
	String su="selected=\"selected\"";
    
    cat= (String)request.getParameter("cat");
    if(cat==null)
    {
    	cat="ALL";
    }
    searchby= (String)request.getParameter("searchby");
    if(searchby==null)
    {
    	searchby="Title";
    }
    search= (String)request.getParameter("search");
    if(search==null)
    {
    	search="";
    }
    
	%>
	
	<div align="center">
		<h2>Accounts</h2><br>
	<form action="displaybooks.jsp">
	  <label class="">Category</label><br />
  	  <select name="cat" id="cat">
  	  	<option <% if(cat.equals("ALL")){%><%=su%><%}%>     value="ALL">ALL</option>
  	    <option <% if(cat.equals("GENERAL")){%><%=su%><%}%> value="GENERAL">GENERAL</option>
  		<option <% if(cat.equals("CIVIL")){%><%=su%><%}%>   value="CIVIL">CIVIL</option>
		<option <% if(cat.equals("CSE")){%><%=su%><%}%>     value="CSE">CSE</option>
		<option <% if(cat.equals("IT")){%><%=su%><%}%>      value="IT">IT</option>
		<option <% if(cat.equals("ECE")){%><%=su%><%}%>     value="ECE">ECE</option>
		<option <% if(cat.equals("EEE")){%><%=su%><%}%>     value="EEE">EEE</option>
		<option <% if(cat.equals("MECH")){%><%=su%><%}%>    value="MECH">MECH</option>
		<option <% if(cat.equals("AUTO")){%><%=su%><%}%>    value="AUTO">AUTO</option>
		<option <% if(cat.equals("MBA")){%><%=su%><%}%>     value="MBA">MBA</option>
		<option <% if(cat.equals("MCA")){%><%=su%><%}%>     value="MCA">MCA</option>
      </select>
      
      <label class="">SearchBy</label><br />
  	  <select name="searchby" id="searchby">
  	  	<option <% if(searchby.equals("Title")){%><%=su%><%}%> value="Title">Title</option>
  	    <option <% if(searchby.equals("Author")){%><%=su%><%}%> value="Author">Author</option>
  		<option <% if(searchby.equals("Subject")){%><%=su%><%}%> value="Subject">Subject</option>
		<option <% if(searchby.equals("Uploader")){%><%=su%><%}%>value="Uploader">Uploader</option>
	  </select>
      <input type="text" name="search" id="search" value="<%=search%>"/>
      <input type="submit" name="searchbutton" id="searchbutton" value="GO"/>
   </form>
	</div>
<div class="mycenter">
	<div class="libdisplaycontainer">
	<table border="0" cellspacing="0" cellpadding="0">	

	<% 
  try {
	  System.out.println("/11");
    Class.forName("oracle.jdbc.driver.OracleDriver");  
    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    String query,loc,path;
    String q1=null,q2=null,q3;
    loc= getServletContext().getInitParameter("file-upload");
    
    
    
    path = getServletContext().getInitParameter("file-upload");
    q3="libgeneral,libcivil,libcse,libit,libece,libeee,libmech,libauto,libmba,libmca";
    q3="libgeneral";
    if(cat.equals("ALL"))
    {
    	query = "select * from "+q3+" where "+searchby+" LIKE '%"+search+"%' order by "+searchby+""; 
    	System.out.println(query);
    }
    else {
    	query =  "select * from "+cat+" where "+searchby+" LIKE '%"+search+"%' order by "+searchby+"";
    	
    	System.out.println(query);
    }
    
    
    Statement st= connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet rs=st.executeQuery(query);
	
    int ip=1,cal=0;
    while(rs.next()){
    	
    		%>
    		<tr>
    		<td valign="bottom">
    		<div class="tdinnerframe">
    		<div class="titles"><b><%=rs.getString(1)%></b></div>
    		<div class="innframeleft"><a href="library/<%=rs.getString(1)+"."+rs.getString(4) %>"><img src="library/<%=rs.getString(1)+"cover."+rs.getString(5) %>" height=""/></a></div>
    		<div class="innframeRight"><BR>&nbsp;<%=rs.getString(2)%><BR>&nbsp;<%=rs.getString(3)%><BR>&nbsp;<%=rs.getString(4)%><BR>&nbsp;<%=rs.getString(6)%></div>
    		</div>
    		</td>
    		</tr>
    		<%
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
	
</body>
</html>