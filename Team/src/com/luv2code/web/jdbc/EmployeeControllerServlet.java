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













