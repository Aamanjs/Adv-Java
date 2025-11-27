package com.cdac;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddBookServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String btitle = request.getParameter("title");
		String aname = request.getParameter("author");
		String bgenre = request.getParameter("genre");
		String pyearStr = request.getParameter("year_published");
		
		int pyear = 0;
        try {
            pyear = Integer.parseInt(pyearStr);   // convert String to int
        } catch(NumberFormatException e) {
            out.println("<h3>Invalid Year Format!</h3>");
            return;  // stop execution if year is invalid
        }

		Book b = new Book(btitle, aname, bgenre, pyear);
		
		BookDAO bdao = new BookDAO();
		
		boolean status = false;
		try {
			status = bdao.saveBook(b);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			out.println("<h3>Database Error: "+e.getMessage()+"</h3>");
			e.printStackTrace();
		}
		
		if(status) {
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } else {
            out.println("<h2>Error Adding Book</h2>");
        }

		
		
	}
	

}
