package com.cdac;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewBookServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		List<Book> list = new ArrayList<>();
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","monu@123");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM books");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Book b = new Book(rs.getString("title"), rs.getString("author"), rs.getString("genre"), rs.getInt("year_published"));
                list.add(b);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
		request.setAttribute("bookList", list);
		RequestDispatcher rd = request.getRequestDispatcher("viewBooks.jsp");
		rd.forward(request, response);
		
	}

}
