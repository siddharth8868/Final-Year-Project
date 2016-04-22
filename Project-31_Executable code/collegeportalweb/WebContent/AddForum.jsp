<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>

<script type="text/javascript" src="js/addforums.js"></script>

</head>
<body >

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
	else if(position.equals("Student")){
		%><jsp:include page="menustudents.html"/><%
	}		
%>

<div align="center"><h2>Create Forum</h2></div>

<div style="margin-left:20px;">

<form action="AddForumServlet" method="post" >	
<table cellspacing="6">

	<tbody>
      <tr>
      	<td>Subject:</td>
      	<td><input type="text" name="subject" id="subject" value="" style="width:200px;"></td>
      </tr>
      
      <tr>
    			<td>Branch:</td>
    			<td><select name="branch" id="branch" style="width: 200px">
    		  <option value="General">General</option>
    		  <option value="Acadamic Branch">Acadamic Branch</option>
    		  <option value="Administration">Administration</option>
	  		  <option value="Sports">Sports</option>
	  	   	  <option value="Library">Library</option>
	  	   	  <option value="CIVIL">CIVIL</option>
	  	   	  <option value="CSE">CSE</option>
	  	   	  <option value="IT">IT</option>
	  	   	  <option value="ECE">ECE</option>
	  	   	  <option value="EEE">EEE</option>
	  	   	  <option value="MECH">MECH</option>
	  	   	  <option value="AUTO">AUTO</option>
	  	   	  <option value="MBA">MBA</option>
	  	   	  <option value="MCA">MCA</option></select></td>
	  </tr>
			  
	  <tr>
      	<td>Content:</td>
      	<td><textarea rows="5" cols="70" id="content" name="content"></textarea></td>
      </tr>
	  </tbody>
      </table>
      <div align="center" style="width: 200px; margin-left: 20px">
		<input type="submit" id="save" onclick="return validate()" value="Save"/>
		<input type="reset" id="clear" onclick="clear1()" value="Clear"/>
	  </div>
      <div align="center" id="errormsg" style="width: 200px; margin-left: 20px;color: red;" ></div>
      </form>
		

</div>
	<%	}

	catch(Exception e)
	{
		%>Sorry cannot access<%
	} %>
</body>
</html>