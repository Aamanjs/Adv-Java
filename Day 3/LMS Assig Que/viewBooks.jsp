<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cdac.Book" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>All Books</title>
	<style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f4f8;
            padding: 40px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 80%;
            margin: auto;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
	    th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007BFF;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
<h2>Book List</h2>
<table>
	<tr>
		<th>Title</th>
		<th>Author</th>
		<th>Genre</th>
		<th>Published Year</th>
	</tr>
	<% 
	   List<Book> list = (List<Book>)request.getAttribute("bookList");
	   for(Book b : list) {
	%>
	<tr>
		<td><%= b.getTitle() %></td>
		<td><%= b.getAuthor() %></td>
		<td><%= b.getGenre() %></td>
		<td><%= b.getYear_published() %></td>
	</tr>
	<% } %>
</table>
</body>
</html>
