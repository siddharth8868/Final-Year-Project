<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <!-- css -->
    <link href="css/menu.css" rel="stylesheet">
    <link href="css/my.css" rel="stylesheet">
    <title>College Portal</title>
    <script type="text/javascript">
    
    function getXmlHttpRequestObject() {
    	if (window.XMLHttpRequest) {
    	return new XMLHttpRequest(); //To support the browsers IE7+, Firefox, Chrome, Opera, Safari
    	} else if(window.ActiveXObject) {
    	return new ActiveXObject("Microsoft.XMLHTTP"); // For the browsers IE6, IE5
    	} else {
    	alert("Error due to old verion of browser upgrade your browser");
    	}
    }
	
    function validate(){
    var oldp = document.getElementById('login').value;
    var newp = document.getElementById('password').value;
			document.getElementById('login').style.border = "";
			document.getElementById('password').style.border = "";


    if (oldp== "") {
		document.getElementById('login').style.border = "solid 1px red";
		return;
    }
 
    if (newp.length =="") {
		document.getElementById('password').style.border = "solid 1px red";
		return;
    }

  if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
    xmlhttp=new XMLHttpRequest();
    }
  else
    {// code for IE6, IE5
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
  xmlhttp.onreadystatechange=function()
    {
    if (xmlhttp.readyState==4 && xmlhttp.status==200)
      {
      document.getElementById("resultval").innerHTML=xmlhttp.responseText;
      }
    };
  xmlhttp.open("GET","<%=getServletContext().getInitParameter("rootdir")%>ChangePasswordServlet?oldp="+oldp+"&newp="+newp,true);
  xmlhttp.send();
  document.getElementById("resultval").innerHTML="<img src=\"img/loading.gif\"/><p style=\"color: green;\">Loading please wait</p>";
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
	else if(position.equals("Faculty")){
		%><jsp:include page="menufaculty.html"/><%
	}
	else if(position.equals("Student")){
		%><jsp:include page="menustudents.html"/><%
	}		
	
%>

<div align="center">
		<br><br><br>
		<h2>Change Password</h2>
		<table>
			<tr>
				<td>Old Password:</td>
				<td><input type="password" name="login" id="login" value=""></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><input type="password" name="password" id="password" value=""></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<button type="submit" onclick="validate();">Change</button>
				</td>
			</tr>
			<tr>
				<td>
					<div id="resultval"></div>
				</td>
			</tr>
		</table>
	
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