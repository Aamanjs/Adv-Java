<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cdac.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Student</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; padding: 40px; }
        .form-container { background: white; padding: 30px; border-radius: 10px; width: 400px; margin: auto; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        label { display: block; margin-top: 15px; font-weight: bold; }
        input[type="text"] { width: 100%; padding: 10px; margin-top: 5px; border: 1px solid #ccc; border-radius: 5px; }
        .btn { margin-top: 20px; width: 100%; padding: 12px; background: linear-gradient(to right, #007BFF, #0056b3); color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; text-decoration: none; text-align: center; }
        .btn:hover { background: linear-gradient(to right, #0056b3, #003f7f); }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Edit Student</h2>
        <form action="updateStudent" method="post">
            <input type="hidden" name="id" value="<%= ((Student)request.getAttribute("student")).getId() %>">
            <label>Name</label>
            <input type="text" name="name" value="<%= ((Student)request.getAttribute("student")).getName() %>" required>
            <label>Email</label>
            <input type="text" name="email" value="<%= ((Student)request.getAttribute("student")).getEmail() %>" required>
            <label>Mobile</label>
            <input type="text" name="mobile" value="<%= ((Student)request.getAttribute("student")).getMobile() %>" required>
            <input type="submit" value="Update Student" class="btn">
        </form>
    </div>
</body>
</html>
