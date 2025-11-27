package com.cdac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO {
	
	public boolean saveBook(Book B) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms","root","monu@123");
		
		String q = "INSERT INTO books (title, author, genre, year_published) VALUES (?, ?, ?, ?)";
		PreparedStatement pmt = con.prepareStatement(q);
		pmt.setString(1, B.getTitle());
		pmt.setString(2, B.getAuthor());
		pmt.setString(3, B.getGenre());
		pmt.setInt(4, B.getYear_published());
		
		int row = pmt.executeUpdate();
		
		boolean status = false;
		if(row>0) {
			status=true;
		}
		
		return status;
	}

}


