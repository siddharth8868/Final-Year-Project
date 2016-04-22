<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>
<style>
	   #container { width:600px; margin:auto; border:1px solid white }
	   #outPopUp{
     position:absolute;
     width:300px;
     height:200px;
     z-index:15;
     top:50%;
     left:50%;
     margin:-100px 0 0 -150px;
     background:white;
}</style>



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
	
    function clear1()
    {
    	alert("cleared");	
    	document.getElementById('id').value="";
        document.getElementById('password').value="";
    }
    
    function disable()
    {
        document.getElementById("resultval").innerHTML="<h4><font color=\"yellow\">Processing</font></h4>";
        document.getElementById('id').readonly=true;
        document.getElementById('password').readonly=true;
        
    	document.getElementById('save').disable=true;
    	document.getElementById('clear').disable=true;
    }
    
    function enable()
    {
        document.getElementById('id').readonly=false;
        document.getElementById('password').readonly=false;
        document.getElementById('position').readonly=false;
        
    	document.getElementById('save').disable=false;
    	document.getElementById('clear').disable=false;
    }
    
    function removehint()
    {
    	document.getElementById("resultval").innerHTML="";
    }
    
    function validate(){
    var id = document.getElementById('id').value;
    var pass = document.getElementById('password').value;
    var position = document.getElementById('position').value;
    var branch1 = document.getElementById('branch').value;

	document.getElementById('id').style.border = "";
	document.getElementById('password').style.border = "";


    if (id== "") {
		document.getElementById('id').style.border = "solid 1px red";
		return;
    }
 
    if (pass== "") {
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
    
    
    
  xmlhttp.open("GET","<%=getServletContext().getInitParameter("rootdir")%>AddAccountServlet?id="+id+"&pass="+pass+"&branch="+branch1+"&position="+position,true);
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
	String branch= (String)session.getAttribute("branch");
	String s;
	if(position.equals("admin")){
		%><jsp:include page="menuadmin.html"/><br><%
	}
	else if(position.equals("HOD")){
		%><jsp:include page="menuhod.html"/><br><%
	}
	
%>
<div align="center"><h2>Create Account</h2></div>
<div id="outPopUp">
	<table cellspacing="6">
	<tbody>
	
      <tr>
      	<td>ID:</td>
      	<td><input type="text" name="id" id="id" value="" size="30" onkeyup="removehint()"></td>
      </tr>
      <tr>
      	<td>Password:</td>
      	<td><input type="password" name="password" id="password" value="" size="30" onkeyup="removehint()"></td>
      </tr>
      <tr>
      	<td>Position:</td>
      	<td><select name=position id="position" style="width: 200px">
      <%
      if(position.equals("admin")){
    		%><option value="HOD">HOD</option>
	  		  <option value="Faculty">Faculty</option>
	  	   	  <option value="Student">Student</option>
	  		<%
   		}
    	else if(position.equals("HOD")){
    		%><option value="Faculty">Faculty</option>
	  	   	  <option value="Student">Student</option>
    		<%
    	}
      %>
	  </select>
	  </td>  
	  </tr>      
      
      <%
      if(position.equals("admin")){
    		%><tr>
    			<td>Branch:</td>
    			<td><select name="branch" id="branch" style="width: 200px">
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
	  		<%
      	}
      else if(position.equals("HOD")){
    	  %><tr>
			<td>Branch:</td>
			<td><select name="branch" id="branch" style="width: 200px">
	  	   	<option value="<%=branch%>"><%=branch%></option></select></td>
		  </tr>
		<%
      }
      

      %>
	  
	  </tbody>
      </table>
		<div align="center">
		<input type="button" id="save" onclick="validate()" value="Save"/>
		<input type="button" id="clear" onclick="clear1()" value="Clear"/>
		</div>
		<div id="resultval" align="center"></div>
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