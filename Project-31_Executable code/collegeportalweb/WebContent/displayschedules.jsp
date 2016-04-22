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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>

<script type="text/javascript">
function bigdata(c) {
	var sub=document.getElementById("s"+c).innerText;
	var cont=document.getElementById("c"+c).innerHTML;
	var allAnchors = document.getElementsByTagName('A');
    var numberOfAnchors = allAnchors.length;
    for (var i = 0; i < numberOfAnchors; i++) {
        if (allAnchors[i].href.indexOf("#join_form") >= 0) {
        	document.getElementById("popsubject").innerHTML=sub;
        	document.getElementById("popcontent").innerHTML=cont;

        	allAnchors[i].click();
            return;
        }
    }
}

</script>
<style type="text/css">
.dingdong{
position:absolute;
left:0px;
top:0px;
z-index:-1;
}
</style>

</head>
<body>
	<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	
	try{
	String position= (String)session.getAttribute("position");
	String branch=(String)request.getAttribute("branch");
	if(branch==null)
	{
		branch=request.getParameter("branch");
	}
	String su="selected=\"selected\"";
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

<div align="center"><h2>Schedules</h2><br>

  	  <form action="displayschedules.jsp">
	  <select name="branch" id="branch">
  	    <option <% if(branch.equals("General")){%><%=su%><%}%> value="General">GENERAL</option>
  	    <option <% if(branch.equals("Acadamic Branch")){%><%=su%><%}%> value="Acadamic Branch">Acadamic Branch</option>
	  	<option <% if(branch.equals("Administration")){%><%=su%><%}%> value="Administration">Administration</option>
	  	<option <% if(branch.equals("Sports")){%><%=su%><%}%> value="Sports">Sports</option>
	  	<option <% if(branch.equals("Library")){%><%=su%><%}%> value="Library">Library</option>
  		<option <% if(branch.equals("CIVIL")){%><%=su%><%}%> value="CIVIL">CIVIL</option>
		<option <% if(branch.equals("CSE")){%><%=su%><%}%> value="CSE">CSE</option>
		<option <% if(branch.equals("IT")){%><%=su%><%}%> value="IT">IT</option>
		<option <% if(branch.equals("ECE")){%><%=su%><%}%> value="ECE">ECE</option>
		<option <% if(branch.equals("EEE")){%><%=su%><%}%> value="EEE">EEE</option>
		<option <% if(branch.equals("MECH")){%><%=su%><%}%> value="MECH">MECH</option>
		<option <% if(branch.equals("AUTO")){%><%=su%><%}%> value="AUTO">AUTO</option>
		<option <% if(branch.equals("MBA")){%><%=su%><%}%> value="MBA">MBA</option>
		<option <% if(branch.equals("MCA")){%><%=su%><%}%> value="MCA">MCA</option>
      </select>
      <input type="submit"/>
      </form> 
</div>
<div style="margin-left: 15px; margin-right: 15px;" align="center" >
	<div class="CSSTableGenerator1">
	<table>
	<col width="5%">
  <col width="60%">
  <col width="10%">
  <col width="10%">
  <col width="5%">
  <col width="10%">
 <tr><td>Sl.No</td><td>SUBJECT</td><td>POSTED</td><td>VALID</td><td>LINK</td><td>UPLOADER</td></tr>
	<%! public String htmlString(String s)
		{
			s=s.replace("\n","<br>");
			return s;
		}
	%>
	<% 
  try {
    Class.forName("oracle.jdbc.driver.OracleDriver");  
    Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
    String query,q1;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
    InputStream in;
    CLOB cb;
    StringWriter sr;
    StringWriter w = new StringWriter();
    query="select * from schedules where branch='"+branch+"' order by id desc";
	System.out.println(query);
    
    Statement st= connection.createStatement();
    ResultSet rs=st.executeQuery(query);
   
    
    int count=0;
    while(rs.next())
    {
    	count++;
    	
    	%>
    	 <tr>
    	 <td><%=count%></td>
    	 <td onclick="bigdata(<%=count%>);" id="s<%=count%>"><%=rs.getString(2)%></td>
    	 <td><%=df.format(rs.getDate(3))%></td>
    	 <td><%=df.format(rs.getDate(4))%></td>
    	  <%
    	  		//System.out.println(rs.getString(1)+"////"+rs.getString(5));	 

    	  	if(rs.getString(5)==null){
    	  		%> <td></td> <%
    	  	}
    	  	else{
    	  		%> <td><a href="schedules/<%=rs.getString(5)%>">V</a></td> <%	
    	  	}
    	  	
    	  %>
    	  <td id="u<%=count%>"><%=rs.getString(7)%></td>
    	 <td style = "display:none" id="c<%=count%>"><%=htmlString(rs.getString(6))%></td>
    	 
    	 
    	 </tr>
    <%	
    	
    }

    /*ResultSet rs= st.executeQuery(strquery);*/
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

<!-- popup form #1 -->
<a href="#join_form" id="join_pop"></a>
        <a href="#x" class="overlay" id="join_form"></a>
        <div class="popup" style="width:600px;">
            
            
            <h3 id="popsubject"></h3>
            <p id="popcontent"></p>
            <a class="close" href="#close"></a>
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