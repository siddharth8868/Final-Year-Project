package collegeportalweb;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class BooksUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 500 * 1024 * 1000;
	private int maxMemSize = 40 * 1024 * 1000;
	private File file;
	String title, author, subject, type, branch, sesbranch, id;

	Connection conn;
	PreparedStatement ps, ps1;

	public void init() {
		// Get the file location where it would be stored.
		filePath = getServletContext().getInitParameter("file-upload");
		// here i'm changing the file location for local library folder;
		// filePath= getServletContext().getRealPath("/library/");

		try {
			conn = MyConnection.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		HttpSession hts = request.getSession(true);
		id = (String) hts.getAttribute("id");
		java.io.PrintWriter out = response.getWriter();

		String id = (String) hts.getAttribute("id");
		System.out.println(id);

		if (id == null) {
			System.out.println("Access denied ");
			out.print("<html><body><font color=\"red\">Access denied</font></body></html>");
			return;
		}

		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");

		if (!isMultipart) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>No file uploaded</p>");
			out.println("</body>");
			out.println("</html>");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("c:\\"));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);

		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);

			// Process the uploaded file items
			Iterator i = fileItems.iterator();

			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet upload</title>");
			out.println("</head>");
			out.println("<body>");
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (fi.isFormField()) {
					if (fi.getFieldName().equals("title")) {
						title = fi.getString();
					} else if (fi.getFieldName().equals("author")) {
						author = fi.getString();
					} else if (fi.getFieldName().equals("subject")) {
						subject = fi.getString();
					} else if (fi.getFieldName().equals("type")) {
						type = fi.getString();
					} else if (fi.getFieldName().equals("branch")) {
						branch = fi.getString();

						// once got the title and branch check book exit or not
						// already
						boolean b = checkExistAlready(title); // checking does
																// book already
																// exits or not
						if (b) {
							out.print("Book already exits,<br>Still want to insert? <br> try to give a different Author name");
							out.println("</body>");
							out.println("</html>");
							return;
						}
					}

				} else if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fieldName = fi.getFieldName();
					String fileName;
					if (fieldName.equals("file")) {
						fileName = title + "." + type;
						file = new File(filePath + fileName);
						System.out.println(filePath + fileName);
						fi.write(file);

					} else if (fieldName.equals("cover")) {
						String ss = fi.getName();
						ss = ss.substring(ss.lastIndexOf(".") + 1);
						System.out.println(ss);
						fileName = title + "cover";
						file = new File(filePath + fileName + "." + ss);
						System.out.println(fileName);
						fi.write(file);
						int n = updateDatabase(fileName, ss);
						if (n == 1) {
							out.println("book uploaded successfully");
						} else {
							out.println("failed to upload cover");
						}

					}

				} // if- else inside while loop
			}// while
			out.println("</body>");
			out.println("</html>");
		} // try
		catch (Exception ex) {
			System.out.println(ex);
		}

	}

	private boolean checkExistAlready(String tit) {

		boolean result = true;
		try {
			ps = conn
					.prepareStatement("select title from library where title=?");
			ps.setString(1, tit);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	private int updateDatabase(String filename, String ctype) {
		try {
			ps1 = conn
					.prepareStatement("insert into library values(?,?,?,?,?,?,?)");
			ps1.setString(1, title);
			ps1.setString(2, author);
			ps1.setString(3, subject);
			ps1.setString(4, type);
			ps1.setString(5, ctype);
			ps1.setString(6, id);
			ps1.setString(7, branch);
			return ps1.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		try {

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
