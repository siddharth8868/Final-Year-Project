<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="java.io.StringWriter"%>
<%@page import="oracle.sql.CLOB"%>
<%@page import="org.omg.CORBA.portable.InputStream"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*" import=" java.sql.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<link href="css/table.css" rel="stylesheet">
<link href="css/modal.css" rel="stylesheet" type="text/css" />
<meta name="viewport" content="width=device-width">
<title>College Portal</title>

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


</head>
<body>
	<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	
	try{
		
	String id= request.getParameter("id");
	
%>

<div align="center"><h2>Delete-Forums</h2></div>
<div style="margin-left: 15px; margin-right: 15px;" align="center" >
	<div class="CSSTableGenerator1">
	<form id="myform">
	<table>
		  <col width="10%">
		  <col width="80%">
		  <col width="10%">
 <tr><td>Sl.No</td><td>Forums</td><td>DEL</td></tr>
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
		    	 <td><%=rs.getString(4)%></td>
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
		<form id="sending" action="DeleteForumsAndroidServlet" method="post">
		<input type="hidden" id="myarray" name="myArray1" value="a"/>
		<input type="hidden" id="id" name="id" value="<%=id%>"/>
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