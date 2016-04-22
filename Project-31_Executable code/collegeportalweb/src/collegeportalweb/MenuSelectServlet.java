package collegeportalweb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MenuSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hts=request.getSession(true);
		PrintWriter out=response.getWriter();
		String id;
		            
		try{
			id=(String) hts.getAttribute("id");
			System.out.println(id);
			if(id==null)
			{
				System.out.println("Access denied ");
				out.print("<html><body><font color=\"red\">Access denied</font></body></html>");
				
			}
		}catch(Exception e){
			System.out.println("exception ");
			out.print("Access denied");
			
		}
		
		//switch case to redirect to respected servlet from menu item selected
		String val=request.getParameter("val");
		String name=request.getParameter("name");
		RequestDispatcher rd = null;
		System.out.println("1");
	
		
		if(val.equals("Schedules")){
			
			if(name.equals("ViewSchedules")){
				rd=getServletContext().getRequestDispatcher("/displayschedules.jsp");
				request.setAttribute("branch","General");
			}
			else if(name.equals("AddSchedules")){
				rd=getServletContext().getRequestDispatcher("/addschedules.jsp");
			}
			else if(name.equals("DeleteSchedules")){
				rd=getServletContext().getRequestDispatcher("/deleteschedules.jsp");
			}
		}
		
		else if(val.equals("Library")){
			if(name.equals("DisplayBooks")){
				rd=getServletContext().getRequestDispatcher("/displaybooks.jsp");
			}
			else if(name.equals("UploadBooks")){
				rd=getServletContext().getRequestDispatcher("/uploadbooks.jsp");
			}
		}
		
		else if(val.equals("Accounts")){
			if(name.equals("AddAccounts")){
				rd=getServletContext().getRequestDispatcher("/addaccount.jsp");
			}
			else if(name.equals("ViewAccounts")){
				rd=getServletContext().getRequestDispatcher("/viewaccounts.jsp");
			}
			else if(name.equals("DeleteAccounts")){
				rd=getServletContext().getRequestDispatcher("/deleteaccounts.jsp");
			}
		}
		
		else if(val.equals("Forums")){
			if(name.equals("CreateForum")){
				rd=getServletContext().getRequestDispatcher("/AddForum.jsp");
			}
			else if(name.equals("DeleteForums")){
				rd=getServletContext().getRequestDispatcher("/deleteforums.jsp");
			}
			else if(name.equals("AcadamicBranch")){
				rd=getServletContext().getRequestDispatcher("/displayforums.jsp");
				request.setAttribute("type","Acadamic Branch");
			}
			else {
				rd=getServletContext().getRequestDispatcher("/displayforums.jsp");
				request.setAttribute("type",name);
			}
			
		}
		
		else if(val.equals("MyProfile")){
			System.out.println("18");
			if(name.equals("ChangePassword")){
				rd=getServletContext().getRequestDispatcher("/changepassword.jsp");
				System.out.println("19");
			}
			else if(name.equals("SignOut")){
				rd=getServletContext().getRequestDispatcher("/SignOutServlet");
				hts.invalidate();
		 		String rootdir = this.getServletContext().getInitParameter("rootdir"); 
		 		response.sendRedirect(rootdir+"login.html");
		 		System.out.println("12");
		 		return;
				}
		
		}
				
        
		System.out.println("112");
		
   	     rd.forward(request, response);
   	    System.out.println("1");

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		out.print("yes i got it");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
