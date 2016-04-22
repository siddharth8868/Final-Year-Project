package collegeportalweb;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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


public class AddScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	   private String filePath,fileName;
	   private int maxFileSize = 500 * 1024 * 1000;
	   private int maxMemSize = 40 * 1024 * 1000;
	   private File file ;
	   SimpleDateFormat formatter;
	   Date vdate,cdate;
	   String validity,branch,id,upbranch,subject,content;

	   Connection conn;
	   PreparedStatement ps;
	   public void init( ){
	      // Get the file location where it would be stored.
	      filePath = getServletContext().getInitParameter("schedule-upload");
	      formatter = new SimpleDateFormat("yyyy-MM-dd");
	      
	      // here i'm changing the file location for local library folder;
	      //filePath= getServletContext().getRealPath("/library/");

	      try {
			conn = MyConnection.getConnection();
			ps = conn.prepareStatement("insert into schedules values(schedules_sequence.nextval,?,?,?,?,?,?,?,?)");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	      
	   }
	        

	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  System.out.println("entered");
		   HttpSession hts=request.getSession(true);
		   id=(String) hts.getAttribute("id");
		   upbranch=(String) hts.getAttribute("branch");
		   java.io.PrintWriter out = response.getWriter( );
		   DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		   cdate = new Date();
		   String fname=dateFormat.format(cdate);
		   
		   String id=(String) hts.getAttribute("id");
			System.out.println(id);
			
			if(id==null)
			{
				System.out.println("Access denied ");
				out.print("<html><body><font color=\"red\">Access denied</font></body></html>");
				return;
			}
			
			// Check that we have a file upload request 
		      isMultipart = ServletFileUpload.isMultipartContent(request);
		      response.setContentType("text/html");
		      
		      if( !isMultipart ){
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
		      upload.setSizeMax( maxFileSize );

		      try{ 
		      // Parse the request to get file items.
		      List fileItems = upload.parseRequest(request);
			
		      // Process the uploaded file items
		      Iterator i = fileItems.iterator();

		      out.println("<html>");
		      out.println("<head>");
		      out.println("<title>Servlet upload</title>");  
		      out.println("</head>");
		      out.println("<body>");
	    	  System.out.println("above while");
		      while ( i.hasNext () ) 
		      {
		         FileItem fi = (FileItem)i.next();
		         if(fi.isFormField())
		         {
		        	 if(fi.getFieldName().equals("subject"))
		        	 {
		        		 subject= fi.getString();
		        	 }
		        	 else if(fi.getFieldName().equals("validity"))
		        	 {
		        		 vdate=formatter.parse(fi.getString());
		        		 System.out.println(vdate);
		        	 }
		        	 else if(fi.getFieldName().equals("branch"))
		        	 {
		        		 branch= fi.getString();
		        	 }
		        	 else if(fi.getFieldName().equals("content"))
		        	 {
		        		 if(fi.getString()!=null)
		        			 content= fi.getString();
		        		 else{
		        			 content= "-";
		        		 }
		        		 if(fi.getString().equals(""))
		        		 {
		        			 content= "-";
		        		 }
		        	 }
		        	 System.out.println("if");
		         }
		         
		         
		         else //it is an file	
		         {
		        	 	 fileName=fi.getName();
		        	 	 if(fileName.equals(""))
		        	 	 {
		        	 		System.out.println("hello");
		        	 		fileName ="";
		        	 	 }
		        	 	 else{
		        	 		 fileName = fileName.replace(" ","_");
		        	 		if( fileName.lastIndexOf("\\") >= 0 ){
		        	 			fileName=fname + fileName.substring( fileName.lastIndexOf("\\"));
		        	               file = new File(filePath+fileName) ;
		        	            }else{
		        	            	fileName=fname + fileName.substring( fileName.lastIndexOf("\\")+1);
		        	               file = new File(filePath+fileName) ;
		        	            }

		        	 		fi.write( file ) ;
		        	 		System.out.println("in else");
		        	 	 }
		         }
		         
		        
		      }//while
		      
		      int n=updateDatabase(fileName);
		      if(n==1)
  	 		  {
  	 			out.println("schedule added successfully");
  	 		  }
  	 		  else{
  	 			out.println("failed to add schedule");
  	 		  }
		      out.println("</body>");
		      out.println("</html>");
		      
		      }//try
		      catch(Exception e){
		    	  e.printStackTrace();
		    	  System.out.println(e.getMessage());
		      }
		      
		      

	}
	
	
	private int updateDatabase(String filename) {
		   

		   long cdateLong = cdate.getTime();
		   long vdateLong = vdate.getTime();  
		   try {
			   
			   ps.setString(1,subject.replace("\n","<br>"));
			   ps.setDate(2,new java.sql.Date(cdateLong));
			   ps.setDate(3,new java.sql.Date(vdateLong));
			   ps.setString(4,filename);
			   ps.setString(5,content.replace("\n","<br>"));
			   ps.setString(6,id);
			   ps.setString(7,branch);
			   ps.setString(8,upbranch);
			   int n = ps.executeUpdate();
			   return n;
			   
		   } catch (SQLException e) {
		 	e.printStackTrace();
		 	return 0;
		   }
		}
	   
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		try{
		
		conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
