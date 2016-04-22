<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<title>College Portal</title>

<style type="text/css">
a {text-decoration: none !important;}
a {color:#000;}      /* unvisited link */
a:visited {color:#000;}  /* visited link */
a:hover {color:#0000FF;}  /* mouse over link */
a:active {color:#0000FF;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
function validate()
{
	var myform = document.getElementById('myform');
	var inputTags = myform.getElementsByTagName('input');
	var checkboxCount = 0;
	var arr;
	for (var i=0, length = inputTags.length; i<length; i++) {
	     if (inputTags[i].checked == 1) {
	    		 checkboxCount++;
    	         if(checkboxCount==1)
    	        	 {
    	        	 	arr=inputTags[i].id;
    	        	 }
    	         else{
    	         		arr=arr+","+inputTags[i].id;
    	         	 }
	     }
	}
	if(checkboxCount==0)
		{
			alert("no row selected");
			return false;
		}
	else{
			document.getElementById("myarray").value= arr;
			return true;
		}
}


</script>

<title>Insert title here</title>
</head>
<body>
	<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	try{

	String position= (String)session.getAttribute("position");
	String id= (String)session.getAttribute("id");
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
<div align="center"><h2>Delete-Forums</h2></div>
<div class="mycenter">
	<div class="CSSTableGenerator">
	<form id="myform">
	<table>
	<col width="5%">
  	<col width="70%">
  	<col width="10%">
  	<col width="10%">
  	<col width="5%">
	
 <tr><td>SL</td><td>SUBJECT</td><td>BY</td><td>ON</td><td>DEL</td></tr>
	<% 
  try {
    Class.forName("oracle.jdbc.driver.OracleDriver");  
    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    String query,branch;
	query="select * from forums where uploader='"+id+"'";
    Statement st= connection.createStatement();
    ResultSet rs=st.executeQuery(query);
    int c=0;
    while(rs.next())
    {
    	c++;
    	%>
    	 <tr>
    	 <td><%=c%></td>
    	 <td><a href="/collegeportalweb/displayformsone.jsp?id=<%=rs.getLong(1)%>"><%=rs.getString(4)%></a></td>
    	 <td><%=rs.getString(3)%></td>
    	 <td><%=rs.getDate(6)%></td>
    	 <td><input type="checkbox" id="F<%=""+rs.getLong(1)%>" name="check"/></td>
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
	</form>
	</div>
	
	<div align="center"><h4></h4>
		<form id="sending" action="DeleteForumsServlet" method="post">
		<input type="hidden" id="myarray" name="myArray1" value="a"/>
		<input type="submit" id="delete" onclick="return validate();" value="Delete"/>
		</form>
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