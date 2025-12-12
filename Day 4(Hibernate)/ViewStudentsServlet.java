package com.cdac;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewStudentsServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		StudentDAO sd = new StudentDAO();
		
		List<Student> lst = null;
		lst = sd.getAllStudents();
		
		req.setAttribute("studentList", lst);
		
		RequestDispatcher rd = req.getRequestDispatcher("viewStudents.jsp");
		rd.forward(req, res);
	}
	

}
