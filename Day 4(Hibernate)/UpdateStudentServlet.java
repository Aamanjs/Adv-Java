package com.cdac;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStudentServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");

        Student s = new Student();
        s.setId(id);
        s.setName(name);
        s.setEmail(email);
        s.setMobile(mobile);
        
        StudentDAO dao = new StudentDAO();
        dao.updateStudent(s);
        
        res.sendRedirect("view");
	}

}
