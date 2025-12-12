package com.cdac;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditStudentServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		StudentDAO dao = new StudentDAO();
		Student s = dao.getAllStudents().stream().filter(st -> st.getId() == id).findFirst().orElse(null);
		
		req.setAttribute("student", s);
		RequestDispatcher rd = req.getRequestDispatcher("editStudent.jsp");
		rd.forward(req, res);
	}
	
	

}
