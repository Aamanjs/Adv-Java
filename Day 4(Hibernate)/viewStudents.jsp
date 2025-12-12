<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cdac.Student" %>
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
        .btn {
            display: block;
            width: 200px;
            margin: 30px auto;
            padding: 12px;
            text-align: center;
            background: linear-gradient(to right, #007BFF, #0056b3);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
            transition: background 0.3s ease;
        }
        .btn:hover {
            background: linear-gradient(to right, #0056b3, #003f7f);
        }
        .empty-message {
            text-align: center;
            margin-top: 40px;
            font-size: 18px;
            color: #666;
        }
        .edit-btn {
		    background: linear-gradient(to right, #FFD700, #FFA500); /* Yellow gradient */
		    color: black;
		    border: none;
		    padding: 8px 12px;
		    border-radius: 5px;
		    text-decoration: none;
		    font-weight: bold;
		    transition: background 0.3s ease;
		}

		.edit-btn:hover {
		    background: linear-gradient(to right, #FFA500, #FF8C00);
		}

		.delete-btn {
		    background: linear-gradient(to right, #FF4C4C, #B22222); /* Red gradient */
		    color: white;
		    border: none;
		    padding: 8px 12px;
		    border-radius: 5px;
		    text-decoration: none;
		    font-weight: bold;
		    transition: background 0.3s ease;
		}

		.delete-btn:hover {
		    background: linear-gradient(to right, #B22222, #8B0000);
		}
    </style>
</head>
<body>
    <h2>All Registered Students</h2>

		<table>
		    <tr>
		        <th>ID</th><th>Name</th><th>Email</th><th>Mobile</th><th>Actions</th>
		    </tr>
		
		<%
		    List<Student> list = (List<Student>) request.getAttribute("studentList");
		    if (list != null && !list.isEmpty()) {
		        for (Student s : list) {
		%>
		    <tr>
		        <td><%= s.getId() %></td>
		        <td><%= s.getName() %></td>
		        <td><%= s.getEmail() %></td>
		        <td><%= s.getMobile() %></td>
		        <td>
		            <a href="editStudent?id=<%= s.getId() %>" class="edit-btn">Edit</a>
		            <a href="deleteStudent?id=<%= s.getId() %>" class="delete-btn"
		               onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
		        </td>
		    </tr>
		<%
		        }
		    } else {
		%>
		    <div class="empty-message">No students found or error retrieving data.</div>
		<%
		    }
		%>
		</table>
		
		<br>
		<a href="register.html" class="btn">Register Another Student</a>
</body>
</html>
