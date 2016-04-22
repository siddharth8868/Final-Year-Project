<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<%
Class.forName("oracle.jdbc.driver.OracleDriver");  
String url="jdbc:mysql://localhost:3306/userdbase";
Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
PreparedStatement ps=connection.prepareStatement("delete from login where id=?");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>

 <script type="text/javascript">
    
 window.onpageshow = function(evt) {
	    // If persisted then it is in the page cache, force a reload of the page.
	    if (evt.persisted) {
	        document.body.style.display = "none";
	        location.reload();
	    }
	};
	
 
    function validate()
    {
    	var myform = document.getElementById('myform');
    	var inputTags = myform.getElementsByTagName('input');
    	var checkboxCount = 0;
    	var arr=null;
    	for (var i=0, length = inputTags.length; i<length; i++) {
    	     if (inputTags[i].checked == 1) {
    	    	 if(inputTags[i].id == "admin")
    	    		 {
    	    				alert("cannot delete admin");
    	    				return false;
    	    		 }
    	    	 else{
    	    		 checkboxCount++;
        	         if(i==0)
        	        	 {
        	        	 	arr="'"+inputTags[i].id+"'";
        	        	 }
        	         else{
        	         		arr=arr+","+"'"+inputTags[i].id+"'";
        	         	 }
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
	
	String position= (String)session.getAttribute("position");
	String s;
	if(position.equals("admin")){
		%><jsp:include page="menuadmin.html"/><br><%
	}
	else if(position.equals("HOD")){
		%><jsp:include page="menuhod.html"/><br><%
	}
		
	
%>
<div align="center"><h2>Delete Accounts</h2></div>
<div class="mycenter">
	<div class="CSSTableGenerator">
	<form id="myform">
	<table>
 <tr>
 <td>S.NO</td>
 <td>ID</td>
 <td>BRANCH</td>
 <td>POSITION</td>
 <td>SELECT</td>
 </tr>
 <% 
  try {
   
    String query,branch;
    branch = (String)session.getAttribute("branch");
	if(position.equals("admin"))
	{
		query="select id,branch,position from login order by branch,position,id";
	}
	else{
		query="select id,branch,position from login where branch='"+branch+"' order by branch,position,id";
	}
    
    Statement st= connection.createStatement();
    ResultSet rs=st.executeQuery(query);
    int n=0;
    while(rs.next())
    {
    	n++;
    	%>
    	 <tr>
    	 <td><%=n%></td>
    	 <td><%=rs.getString(1)%></td>
    	 <td><%=rs.getString(2)%></td>
    	 <td><%=rs.getString(3)%></td>
    	 <td><input type="checkbox" id="<%=rs.getString(1)%>" name="check"/></td>
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
	</div> <!--CSSTableGenerator ending-->
	<div align="center"><h4></h4>
		<form id="sending" action="DeleteAccountServlet" method="post">
		<input type="hidden" id="myarray" name="myArray1" value="a"/>
		<input type="submit" id="delete" onclick="return validate();" value="Delete"/>
		</form>
	</div>
</div>  <!--mycenter ending-->
 
 	<%
	}//try end for back button refresh
	catch(Exception e)
	{
		%>Sorry cannot access<%
	}
	%>
</body>
</html>