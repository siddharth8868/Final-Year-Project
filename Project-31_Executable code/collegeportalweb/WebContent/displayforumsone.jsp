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
<link href="css/displayforumsone.css" rel="stylesheet" type="text/css" />
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


function atq() {

	var div = document.getElementById('replayme');
	var atq = document.getElementById('atq');
	div.style.visibility = 'visible';
	atq.style.visibility = 'hidden';
	
}

function atqcancel() {
	alert("atq cancled called");
	var div = document.getElementById('replayme');
	var atq = document.getElementById('atq');
	div.style.visibility = 'hidden';
	atq.style.visibility = 'visible';
	
}

</script>

</head>
<body>
	<%
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	
	try{
	response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.addHeader("Expires", "0"); // Proxies.
	
	String position= (String)session.getAttribute("position");
	String ss=request.getParameter("id");
	long tid=Long.parseLong(ss);
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

<%
Class.forName("oracle.jdbc.driver.OracleDriver");  
Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","tiger");  
String query1,query2;

	query1= "select * from forums where id="+tid;
	query2 = "select * from F"+tid; 

	PreparedStatement ps=connection.prepareStatement(query1);
	ResultSet rs=ps.executeQuery();
if(rs.next()){
%>


<div class="forumstyle" align="center">
<h2><%=rs.getString(4)%></h2>
 <hr/>
 </div>
 <div class="forumreplays"><%=rs.getString(2)%><br><br><br>
 		<div class="identity">
 			<B>Asked:</B><%=rs.getDate(6)%>&nbsp;
 			<B>By:</B><span style="color:#0080FF"><%=rs.getString(3)%> </span><br>
 			<B>Branch:</B><%=rs.getString(7)%> &nbsp;&nbsp;&nbsp;
 			<B>Pos:</B><%=rs.getString(8)%>
 		</div>
 </div>
 
<div class="forumstyle" align="center">
<h3 align="left">Answers</h3>
 <hr/>
</div> 
 
 <%
}
	ps=connection.prepareStatement(query2);
 	rs=ps.executeQuery(query2);
 	while(rs.next())
 	{
 		%><div class="forumreplays"><%=rs.getString(1)%><br><br><br>
 			<div class="identity">
 				<B>Answered:</B><%=rs.getDate(3)%>&nbsp;
 				<B>By:</B><span style="color:#0080FF"><%=rs.getString(2)%> </span><br>
 				<B>Branch:</B><%=rs.getString(4)%> &nbsp;&nbsp;&nbsp;
 				<B>Pos:</B><%=rs.getString(5)%>
 			</div>
		  </div>
		  <hr style="margin-left:5%; margin-right: 5%"/>
		<%
 	}
 %>
<div class="forumstyle" align="center" style="margin-left: 7%; margin-right: 7%">
	<h3 align="left"><input align="left" type="button" width="15%" id="atq" onclick="atq()" value="Answer This Question"/></h3>
	<div id="replayme"  style="visibility: hidden;">
	<form action="UpdateForumsServlet" method="post">
	<input type="hidden" name="tid" id="tid" value="<%=tid%>" />
	<textarea rows="6" style="width:100%;" name="replaycontent" id="replaycontent"></textarea><br>
	<input type="submit" width="15%" value="Post Your Answer"/>
	<input type="button" width="15%" id="atqcancel" onclick="atqcancel()" value="Cancel This Post"/>
	</form></div>
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