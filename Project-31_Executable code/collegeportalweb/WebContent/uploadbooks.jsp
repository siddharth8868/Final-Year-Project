<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>


<script type="text/javascript" src="js/uploadbooks.js"></script>


</head>
<body style="height:100%" background="img/wood.jpg">

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

<form action="BooksUploadServlet" method="post" enctype="multipart/form-data">

<div class="bookuploadformcontainer" >
<div class="bookuploadformcontainerinnner">
<label>Title</label><br>
<input type="text" name="title" id="title" />
</div>

<div class="bookuploadformcontainerinnner">
<label>Author</label><br>
<input type="text" name="author" id="author" />
</div>

<div class="bookuploadformcontainerinnner">
<label>Subject</label><br>
<input type="text" name="subject" id="subject" />
</div>

<div class="bookuploadformcontainerinnner">
<label>Type</label><br />
    <select name="type" id="type">
    <option value="pdf">pdf</option>
    <option value="doc">doc</option>
    <option value="ppt">ppt</option>
    </select>
</div>
<div class="bookuploadformcontainerinnner">
   <label class="">Branch</label><br />
    <select name="branch" id="branch">
      <option value="GENERAL">GENERAL</option>
    <option value="CIVIL">CIVIL</option>
	<option value="CSE">CSE</option>
	<option value="IT">IT</option>
	<option value="ECE">ECE</option>
	<option value="EEE">EEE</option>
	<option value="MECH">MECH</option>
	<option value="AUTO">AUTO</option>
	<option value="MBA">MBA</option>
	<option value="MCA">MCA</option>
    </select>
</div>


<div class="bookuploadformcontainerinnner">
   <label>Select File :</label><br />
   <input  name="file" type="file" id="file" onchange="return checkfile()"/>
</div>


<div class="bookuploadformcontainerinnner">
  <label>Cover img :</label><br />
   <input  name="cover" type="file" id="cover" onchange="return checkcover()"/>
</div>

<div align="center" style="height:80px; padding-top:20px; padding-right: 30px;">
<input type="submit" style="border-radius:5px; height:40px; width: 70px;" name="upload" value="Upload" onclick="return validate(this);"/>&nbsp;&nbsp;&nbsp;&nbsp;
  <input type="reset" style="border-radius:5px; height:40px; width: 70px;" name="clear" value="Clear" onclick="clears();"/>

</div>
	<div id="errormsg" align="center" style="color: white; margin-top:3px; "></div>
</div>

</form>

	<%
	}//try end for back button refresh
	catch(Exception e)
	{
		%>Sorry cannot access<%
	}
	%>
</body>
</html>