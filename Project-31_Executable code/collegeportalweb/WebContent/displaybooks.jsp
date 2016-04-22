<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page language="java"%>
<%@page import="java.lang.*" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College Portal</title>
</head>
<body>
	<%
		response.addHeader("Cache-Control",
				"no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.addHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.addHeader("Expires", "0"); // Proxies.
		try {
			String position = (String) session.getAttribute("position");
			String cat, searchby, search;
			String su = "selected=\"selected\"";
			if (position.equals("admin")) {
	%><jsp:include page="menuadmin.html" /><br>
	<%
		} else if (position.equals("HOD")) {
	%><jsp:include page="menuhod.html" /><br>
	<%
		} else if (position.equals("Faculty")) {
	%><jsp:include page="menufaculty.html" />
	<%
		} else if (position.equals("Student")) {
	%><jsp:include page="menustudents.html" />
	<%
		}

			cat = (String) request.getParameter("cat");
			if (cat == null) {
				cat = "ALL";
			}
			searchby = (String) request.getParameter("searchby");
			if (searchby == null) {
				searchby = "Title";
			}
			search = (String) request.getParameter("search");
			if (search == null) {
				search = "";
			}
	%>

	<div align="center">
		<h2>Library</h2>
		<form action="displaybooks.jsp">
			<label class="">Category</label> <select name="cat" id="cat">
				<option <%if (cat.equals("ALL")) {%> <%=su%> <%}%> value="ALL">ALL</option>
				<option <%if (cat.equals("GENERAL")) {%> <%=su%> <%}%>
					value="GENERAL">GENERAL</option>
				<option <%if (cat.equals("CIVIL")) {%> <%=su%> <%}%> value="CIVIL">CIVIL</option>
				<option <%if (cat.equals("CSE")) {%> <%=su%> <%}%> value="CSE">CSE</option>
				<option <%if (cat.equals("IT")) {%> <%=su%> <%}%> value="IT">IT</option>
				<option <%if (cat.equals("ECE")) {%> <%=su%> <%}%> value="ECE">ECE</option>
				<option <%if (cat.equals("EEE")) {%> <%=su%> <%}%> value="EEE">EEE</option>
				<option <%if (cat.equals("MECH")) {%> <%=su%> <%}%> value="MECH">MECH</option>
				<option <%if (cat.equals("AUTO")) {%> <%=su%> <%}%> value="AUTO">AUTO</option>
				<option <%if (cat.equals("MBA")) {%> <%=su%> <%}%> value="MBA">MBA</option>
				<option <%if (cat.equals("MCA")) {%> <%=su%> <%}%> value="MCA">MCA</option>
			</select> <br> <label class="">SearchBy</label> <select name="searchby"
				id="searchby">
				<option <%if (searchby.equals("Title")) {%> <%=su%> <%}%>
					value="Title">Title</option>
				<option <%if (searchby.equals("Author")) {%> <%=su%> <%}%>
					value="Author">Author</option>
				<option <%if (searchby.equals("Subject")) {%> <%=su%> <%}%>
					value="Subject">Subject</option>
				<option <%if (searchby.equals("Uploader")) {%> <%=su%> <%}%>
					value="Uploader">Uploader</option>
			</select> <input type="text" name="search" id="search" value="<%=search%>" />
			<input type="submit" name="searchbutton" id="searchbutton" value="GO" />
		</form>
	</div>
	<div class="mycenter">
		<div class="libdisplaycontainer">
			<table border="0" cellspacing="0" cellpadding="0">

				<%
					Connection connection = null;
						try {
							search = search.toUpperCase();
							System.out.println("/11");
							Class.forName("oracle.jdbc.driver.OracleDriver");
							connection = DriverManager.getConnection(
									"jdbc:oracle:thin:@localhost:1521:xe", "system",
									"tiger");
							String query, loc, path;
							String q1 = null, q2 = null, q3;
							loc = getServletContext().getInitParameter("file-upload");

							path = getServletContext().getInitParameter("file-upload");
							if (cat.equals("ALL")) {
								query = "select * from library where upper(" + searchby
										+ ") LIKE '%" + search + "%' order by "
										+ searchby + "";
								System.out.println(query);
							} else {
								query = "select * from library where upper(" + searchby
										+ ") LIKE '%" + search + "%' and branch='"
										+ cat + "' order by " + searchby + "";

								System.out.println(query);
							}

							Statement st = connection.createStatement(
									ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);
							ResultSet rs = st.executeQuery(query);

							int ip = 1, cal = 0, pp = 0;
							while (rs.next()) {
								System.out.println(++pp);
								if (ip == 1) {
				%><tr>
					<%
						rs.previous();
										 
									}

									//1st column

									if (rs.next()) {
					%>
					<td valign="bottom">
						<div class="tdinnerframe">
							<div class="titles">
								<b><%=rs.getString(1)%></b>
							</div>
							<div class="innframeleft">
								<a href="library/<%=rs.getString(1) + "." + rs.getString(4)%>"><img
									src="library/<%=rs.getString(1) + "cover."
									+ rs.getString(5)%>"
									height="" /></a>
							</div>
							<div class="innframeRight">
								<BR>&nbsp;<%=rs.getString(2)%><BR>&nbsp;<%=rs.getString(3)%><BR>&nbsp;<%=rs.getString(4)%><BR>&nbsp;<%=rs.getString(6)%></div>
						</div>
					</td>
					<%
						} else {
										cal = 1;
										break;
									}

									//2nd column

									if (rs.next()) {
					%>
					<td valign="bottom">
						<div class="tdinnerframe">
							<div class="titles">
								<b><%=rs.getString(1)%></b>
							</div>
							<div class="innframeleft">
								<a href="library/<%=rs.getString(1) + "." + rs.getString(4)%>"><img
									src="library/<%=rs.getString(1) + "cover."
									+ rs.getString(5)%>"
									height="" /></a>
							</div>
							<div class="innframeRight">
								<BR>&nbsp;<%=rs.getString(2)%><BR>&nbsp;<%=rs.getString(3)%><BR>&nbsp;<%=rs.getString(4)%><BR>&nbsp;<%=rs.getString(6)%></div>
						</div>
					</td>
					<%
						} else {
										cal = 1;
										break;
									}

									//3rd column 

									if (rs.next()) {
					%>
					<td valign="bottom">
						<div class="tdinnerframe">
							<div class="titles">
								<b><%=rs.getString(1)%></b>
							</div>
							<div class="innframeleft">
								<a href="library/<%=rs.getString(1) + "." + rs.getString(4)%>"><img
									src="library/<%=rs.getString(1) + "cover."
									+ rs.getString(5)%>"
									height="" /></a>
							</div>
							<div class="innframeRight">
								<BR>&nbsp;<%=rs.getString(2)%><BR>&nbsp;<%=rs.getString(3)%><BR>&nbsp;<%=rs.getString(4)%><BR>&nbsp;<%=rs.getString(6)%></div>
						</div>
					</td>
					<%
						} else {
										cal = 1;
										break;
									}

									//4th column

									if (rs.next()) {
					%>
					<td valign="bottom">
						<div class="tdinnerframe">
							<div class="titles">
								<b><%=rs.getString(1)%></b>
							</div>
							<div class="innframeleft">
								<a href="library/<%=rs.getString(1) + "." + rs.getString(4)%>"><img
									src="library/<%=rs.getString(1) + "cover."
									+ rs.getString(5)%>"
									height="" /></a>
							</div>
							<div class="innframeRight">
								<BR>&nbsp;<%=rs.getString(2)%><BR>&nbsp;<%=rs.getString(3)%><BR>&nbsp;<%=rs.getString(4)%><BR>&nbsp;<%=rs.getString(6)%></div>
						</div>
					</td>
					<%
						} else {
										cal = 1;
										break;
									}
					%>
				</tr>
				<%
					}

							connection.close();
						} catch (Exception e) {
							connection.close();
							e.printStackTrace();
							System.out.println("Could not connect");
						}
				%>
			</table>
		</div>
	</div>
	<%
		}//try end for back button refresh
		catch (Exception e) {
	%>Sorry cannot access<%
		}
	%>
</body>
</html>