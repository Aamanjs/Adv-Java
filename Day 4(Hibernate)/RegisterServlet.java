package com.cdac;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		
		Student s = new Student();
		s.setName(name);
		s.setEmail(email);
		s.setMobile(mobile);
		
		StudentDAO sd = new StudentDAO();
		
		sd.writeStudent(s);
		
		res.sendRedirect("success.jsp");
		
	}
	
	

}
