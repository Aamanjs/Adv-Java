# Question
## Problem Statement: Session Management with Shopping Cart

Develop a small web application using Servlet, JSP, and JDBC/Hibernate to demonstrate basic session handling.

Requirements (Very Simple)
1. User Login

Create a login page.

Validate user credentials from the database.

On success, create a session and store the username.

2. Show Products

After login, display a static or database-based product list.

Each product has an “Add to Cart” button.

3. Add to Cart Using Session

When the user clicks “Add to Cart”, store the product in the session (cart object).

Cart should remain available as long as the session is active.

4. View Cart

Display the list of cart items from the session.

5. Logout

On logout, invalidate the session and redirect to login.


# Solution

## User.java
```java
package com.cdac;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")

public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	public User() {}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

```

## UserDAO.java
```java
package com.cdac;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserDAO {
	
	public boolean validateUser(String uname, String pass) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		
		User user = session.createQuery(
			    "SELECT u FROM User u WHERE u.username = :uname AND u.password = :pass", User.class)
			    .setParameter("uname", uname.trim())
			    .setParameter("pass", pass.trim())
			    .uniqueResult();
		
		session.close();
		
		return user != null;
	}

}

```
## LoginServlet
```java
package com.cdac;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String uname = req.getParameter("username");
        String pass = req.getParameter("password");

        UserDAO dao = new UserDAO();
        if(dao.validateUser(uname, pass)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", uname);
            res.sendRedirect("products");
        } else {
            res.sendRedirect("error.jsp");
        }

	}

}
```
## Product.java
```java
package com.cdac;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")

public class Product {
	@Id
	private int id;
    private String name;
    private double price;

    public Product() {}
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	
}
```

## ProductDAO.java
```java
package com.cdac;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ProductDAO {
	
	public List<Product> getAllProducts() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		
		List<Product> list = session.createQuery("from Product",Product.class).list();
		session.close();
		
		return list;
	}
	
	public Product getProductId(int id) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		
		Session session = factory.openSession();
		
		Product p = session.find(Product.class, id);

		session.close();

		return p;
	}

}
```
## ProductServlet.java
```java
package com.cdac;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ProductDAO dao = new ProductDAO();
        List<Product> products = dao.getAllProducts();

        req.setAttribute("productList", products);
        RequestDispatcher rd = req.getRequestDispatcher("products.jsp");
        rd.forward(req, res);
    }


}
```
## AddToCartServlet.java
```java
package com.cdac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddToCartServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        ProductDAO dao = new ProductDAO();
        Product p = dao.getProductId(id);

        HttpSession session = req.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");
        if(cart == null) cart = new ArrayList<>();
        cart.add(p);

        session.setAttribute("cart", cart);
        res.sendRedirect("viewCart");
    }

}
```
##  ViewCartServlet.java
```java
package com.cdac;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewCartServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        List<Product> cart = (session != null) ? (List<Product>) session.getAttribute("cart") : null;

        req.setAttribute("cartItems", cart);
        RequestDispatcher rd = req.getRequestDispatcher("cart.jsp");
        rd.forward(req, res);
    }


}
```
## LogoutServlet.java
```
package com.cdac;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if(session != null) session.invalidate();
        res.sendRedirect("login.jsp");
    }


}
```
## login.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: 'Segoe UI', sans-serif;
        background: #f4f6f9;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }

    .container {
        background: white;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 600px;
        text-align: center;
    }

    h2 {
        margin-bottom: 20px;
        color: #333;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
    }

    table th, table td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: center;
    }

    table th {
        background: #007BFF;
        color: white;
    }

    table tr:nth-child(even) {
        background: #f9f9f9;
    }

    .btn {
        display: inline-block;
        margin: 10px;
        padding: 10px 20px;
        background: linear-gradient(to right, #007BFF, #0056b3);
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background 0.3s ease;
    }

    .btn:hover {
        background: linear-gradient(to right, #0056b3, #003f7f);
    }

    input[type="text"], input[type="password"] {
        width: 80%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    input[type="submit"] {
        width: 85%;
        padding: 10px;
        background: #28a745;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background: #218838;
    }
</style>

</head>
<body>
	<form action="login" method="post">
	    Username: <input type="text" name="username"><br>
	    Password: <input type="password" name="password"><br>
	    <input type="submit" value="Login">
    </form>
</body>
</html>
```
## success.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        background: #f4f6f9;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }

    .container {
        background: white;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 600px;
        text-align: center;
    }

    h2 {
        margin-bottom: 20px;
        color: #333;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
    }

    table th, table td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: center;
    }

    table th {
        background: #007BFF;
        color: white;
    }

    table tr:nth-child(even) {
        background: #f9f9f9;
    }

    .btn {
        display: inline-block;
        margin: 10px;
        padding: 10px 20px;
        background: linear-gradient(to right, #007BFF, #0056b3);
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background 0.3s ease;
    }

    .btn:hover {
        background: linear-gradient(to right, #0056b3, #003f7f);
    }

    input[type="text"], input[type="password"] {
        width: 80%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    input[type="submit"] {
        width: 85%;
        padding: 10px;
        background: #28a745;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background: #218838;
    }
</style>

</head>
<body>
	 <div class="success-container">
        <h2>Action Completed Successfully!</h2>
        <p>Your request was processed without any issues.</p>

        <!-- Navigation options -->
        <a href="products" class="btn">View Products</a>
        <a href="viewCart" class="btn">View Cart</a>
        <a href="logout" class="btn">Logout</a>
    </div>
	
</body>
</html>
```
## error.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<style>
    body {
        margin: 0;
        padding: 0;
        font-family: 'Segoe UI', sans-serif;
        background: #f4f6f9;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }

    .container {
        background: white;
        padding: 30px 40px;
        border-radius: 10px;
        box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 600px;
        text-align: center;
    }

    h2 {
        margin-bottom: 20px;
        color: #333;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
    }

    table th, table td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: center;
    }

    table th {
        background: #007BFF;
        color: white;
    }

    table tr:nth-child(even) {
        background: #f9f9f9;
    }

    .btn {
        display: inline-block;
        margin: 10px;
        padding: 10px 20px;
        background: linear-gradient(to right, #007BFF, #0056b3);
        color: white;
        text-decoration: none;
        border-radius: 5px;
        transition: background 0.3s ease;
    }

    .btn:hover {
        background: linear-gradient(to right, #0056b3, #003f7f);
    }

    input[type="text"], input[type="password"] {
        width: 80%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    input[type="submit"] {
        width: 85%;
        padding: 10px;
        background: #28a745;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    input[type="submit"]:hover {
        background: #218838;
    }
</style>
		
</head>
<body>
	<div class="error-container">
        <h2>Login Failed</h2>
        <p>Invalid username or password. Please try again.</p>

        <a href="login.jsp" class="btn">Back to Login</a>
    </div>
	
</body>
</html>
```
## products.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cdac.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Products</title>
<style>
    body {
        font-family: 'Segoe UI', sans-serif;
        background: #f4f6f9;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }
    .container {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        width: 90%;
        max-width: 800px;
        text-align: center;
    }
    h2 {
        margin-bottom: 20px;
        color: #333;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 12px;
    }
    th {
        background-color: #007BFF;
        color: white;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    .btn {
        padding: 8px 16px;
        margin: 5px;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        display: inline-block;
    }
    .btn-cart {
        background-color: #f1c40f;
        color: black;
    }
    .btn-cart:hover {
        background-color: #d4ac0d;
    }
    .btn-view {
        background-color: #28a745;
    }
    .btn-view:hover {
        background-color: #218838;
    }
    .btn-logout {
        background-color: #dc3545;
    }
    .btn-logout:hover {
        background-color: #c82333;
    }
</style>
</head>
<body>
<div class="container">
    <h2>Product List</h2>
    <table>
        <tr><th>Name</th><th>Price</th><th>Action</th></tr>
        <%
            List<Product> list = (List<Product>) request.getAttribute("productList");
            for(Product p : list){
        %>
        <tr>
            <td><%= p.getName() %></td>
            <td><%= p.getPrice() %></td>
            <td><a href="addToCart?id=<%= p.getId() %>" class="btn btn-cart">Add to Cart</a></td>
        </tr>
        <% } %>
    </table>
    <a href="viewCart" class="btn btn-view">View Cart</a>
    <a href="logout" class="btn btn-logout">Logout</a>
</div>
</body>
</html>
```
## cart.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cdac.Product" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart</title>
<style>
    body {
        font-family: 'Segoe UI', sans-serif;
        background: #f4f6f9;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }
    .container {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 6px 12px rgba(0,0,0,0.1);
        width: 90%;
        max-width: 600px;
        text-align: center;
    }
    h2 {
        margin-bottom: 20px;
        color: #333;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 12px;
    }
    th {
        background-color: #007BFF;
        color: white;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    .btn {
        padding: 8px 16px;
        margin: 5px;
        background: #007BFF;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        display: inline-block;
    }
    .btn:hover {
        background: #0056b3;
    }
    
    .btn-products {
        background-color: #f1c40f;
        color: black;
    }
    .btn-products:hover {
        background-color: #d4ac0d;
    }
    .btn-logout {
        background-color: #dc3545;
    }
    .btn-logout:hover {
        background-color: #c82333;
    }
</style>
</head>
<body>
<div class="container">
    <h2>Your Cart</h2>
    <table>
        <tr><th>Name</th><th>Price</th></tr>
        <%
            List<Product> cart = (List<Product>) request.getAttribute("cartItems");
            if(cart != null){
                for(Product p : cart){
        %>
        <tr>
            <td><%= p.getName() %></td>
            <td><%= p.getPrice() %></td>
        </tr>
        <% }} %>
    </table>
    <a href="products" class="btn btn-products">Products</a>
    <a href="logout" class="btn btn-logout">Logout</a>
</div>
</body>
</html>
```
## hibernate.cfg.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>

        <!-- Database Connection Settings -->
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/shoppingdb</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">monu@123</property>

        <!-- JDBC Connection Pool (optional, basic) -->
        <property name="hibernate.connection.pool_size">10</property>

        <!-- SQL Dialect -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

        <!-- Schema Auto Update -->
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Show SQL in console -->
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>

        <!-- Entity Mappings -->
        <mapping class="com.cdac.User"/>
        <mapping class="com.cdac.Product"/>

    </session-factory>
</hibernate-configuration>
```
## web.xml
```xml
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="https://jakarta.ee/xml/ns/jakartaee" xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd" id="WebApp_ID" version="5.0">
	<display-name>Shopping Cart</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>login.jsp</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.jsp</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>register.html</welcome-file>
	</welcome-file-list>
	
	<!-- Welcome page -->
    <welcome-file-list>
        <welcome-file>login.jsp</welcome-file>
    </welcome-file-list>

    <!-- Login Servlet -->
    <servlet>
        <servlet-name>LoginServlet</servlet-name>
        <servlet-class>com.cdac.LoginServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>LoginServlet</servlet-name>
        <url-pattern>/login</url-pattern>
    </servlet-mapping>

    <!-- Product Servlet -->
    <servlet>
        <servlet-name>ProductServlet</servlet-name>
        <servlet-class>com.cdac.ProductServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ProductServlet</servlet-name>
        <url-pattern>/products</url-pattern>
    </servlet-mapping>

	<!-- Add To Cart Servlet -->
    <servlet>
        <servlet-name>AddToCartServlet</servlet-name>
        <servlet-class>com.cdac.AddToCartServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AddToCartServlet</servlet-name>
        <url-pattern>/addToCart</url-pattern>
    </servlet-mapping>

    <!-- View Cart Servlet -->
    <servlet>
        <servlet-name>ViewCartServlet</servlet-name>
        <servlet-class>com.cdac.ViewCartServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>ViewCartServlet</servlet-name>
        <url-pattern>/viewCart</url-pattern>
    </servlet-mapping>

    <!-- Logout Servlet -->
    <servlet>
        <servlet-name>LogoutServlet</servlet-name>
        <servlet-class>com.cdac.LogoutServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>LogoutServlet</servlet-name>
        <url-pattern>/logout</url-pattern>
    </servlet-mapping>

	
</web-app>
```
## pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.demo</groupId>
  <artifactId>ShoppingCart1</artifactId>
  <packaging>war</packaging>
  <version>0.0.1-SNAPSHOT</version>
  <name>ShoppingCart1 Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    
    <!-- https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core -->
	<dependency>
	    <groupId>org.hibernate.orm</groupId>
	    <artifactId>hibernate-core</artifactId>
	    <version>7.1.0.Final</version>
    </dependency>
    
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>8.0.33</version>
	</dependency>
	
	
	<!-- Servlet API -->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>

        <!-- JSP API -->
        <dependency>
            <groupId>jakarta.servlet.jsp</groupId>
            <artifactId>jakarta.servlet.jsp-api</artifactId>
            <version>3.1.1</version>
            <scope>provided</scope>
        </dependency>
  </dependencies>
  <build>
    <finalName>ShoppingCart1</finalName>
  </build>
</project>
```
