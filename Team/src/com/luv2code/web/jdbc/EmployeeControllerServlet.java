package com.luv2code.web.jdbc;



import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/EmployeeControllerServlet")
public class EmployeeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeDbUtil employeeDbUtil;
	
	@Resource(name="jdbc/web_employee_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			employeeDbUtil = new EmployeeDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// list the students ... in mvc fashion
		try {
			
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) 
			{
				theCommand = "LIST";
			}
			
			switch (theCommand)
			{
			
			case "LIST" :
			             listEmployees(request, response);
			             break;
			
			case "ADD" :
				        addEmployee(request, response);
	                    break;
	             
	         default :    
	        	 listEmployees(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}			
		
	}
	private void addEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		// read student info from form data
		        
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String email = request.getParameter("email");
				String state = request.getParameter("state");
				
				// create a new student object
				Employee theEmployee = new Employee(firstName, lastName, email,state);
				
				// add the student to the database
				employeeDbUtil.addEmployee(theEmployee);
						
				// send back to main page (the student list)
				// SEND AS REDIRECT to avoid multiple-browser reload issue
				listEmployees(request, response);
		
	}

	private void listEmployees(HttpServletRequest request, HttpServletResponse response) 
=======
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// list the students ... in mvc fashion
		try {
			listStudents(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 

		throws Exception {

		// get students from db util
		List<Employee> employee = employeeDbUtil.getEmployees();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", employee);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list_Employees.jsp");
		dispatcher.forward(request, response);
	}

}













