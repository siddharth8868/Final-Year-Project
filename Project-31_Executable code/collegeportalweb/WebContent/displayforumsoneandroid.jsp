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
<meta name="viewport" content="width=device-width">
<title>College Portal</title>

<script type="text/javascript">
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
	String ss=request.getParameter("id");
	String user=request.getParameter("user");
	long tid=Long.parseLong(ss);
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
 			<p align="right">
 			<B>Asked:</B><%=rs.getDate(6)%>&nbsp;
 			<B>By:</B><span style="color:#0080FF"><%=rs.getString(3)%> </span><br>
 			<B>Branch:</B><%=rs.getString(7)%> &nbsp;&nbsp;&nbsp;
 			<B>Pos:</B><%=rs.getString(8)%>			
 			</p>
 </div>
 
<div class="forumstyle" align="center">
<h3 align="left">Answers</h3>
 <hr style=" margin-left: 2%; margin-right: 2%;">
</div> 
 
 <%
}
	ps=connection.prepareStatement(query2);
 	rs=ps.executeQuery(query2);
 	while(rs.next())
 	{
 		%><div class="forumreplays"><%=rs.getString(1)%><br><br><br>
 			<p align="right">
 				<B>Answered:</B><%=rs.getDate(3)%>&nbsp;
 				<B>By:</B><span style="color:#0080FF"><%=rs.getString(2)%> </span><br>
 				<B>Branch:</B><%=rs.getString(4)%> &nbsp;&nbsp;&nbsp;
 				<B>Pos:</B><%=rs.getString(5)%>
 			</p>
		  </div>
		  <hr style="margin-left:5%; margin-right: 5%; height:3px;"/>
		<%
 	}
 %>
 
<p style="margin-left: 5%; margin-right: 5%">
	<input align="left" type="button" id="atq" onclick="atq()" value="Answer This Question"/>
	<div align="center" id="replayme"  style="visibility: hidden;margin-right: 5%; margin-left: 5%;">
	<form action="UpdateForumsServletAndroid" method="post">
		<input type="hidden" name="tid" id="tid" value="<%=tid%>" />
		<input type="hidden" id="user" name="user" value="<%=user%>"/>
		<textarea rows="20" style="width:100%;" name="replaycontent" id="replaycontent"></textarea><br>
		<input type="submit" width="15%" height="100px" value="Post Your Answer"/>
	</form>
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