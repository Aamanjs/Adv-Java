<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Success</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e0eafc, #cfdef3);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .success-container {
            background-color: white;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        h2 {
            color: green;
            margin-bottom: 25px;
        }

        .btn {
            display: block;
            width: 100%;
            padding: 12px;
            margin-top: 15px;
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
    </style>
</head>
<body>
    <div class="success-container">
        <h2>Book Added Successfully!</h2>

        <a href="ViewBooksServlet" class="btn">View All Books</a>
        <a href="addBook.html" class="btn">Add Another Book</a>
    </div>
</body>
</html>
