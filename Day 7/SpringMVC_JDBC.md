### Student Registration

## Student.java
```java
package com.cdac;

public class Student {
	
	private int id;
	private String name;
	private String email;
	private String course;
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}

}
```
## StudentDAO.java
```java
package com.cdac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentDAO {
	
	@Autowired DataSource dataSource;
	public void createStudent(Student s) throws SQLException {
		Connection con = dataSource.getConnection();
		
		String q = "insert into Student(id, name, email, course) values(?, ?, ?, ?)";
		PreparedStatement pmt = con.prepareStatement(q);
		
		pmt.setInt(1, s.getId());
		pmt.setString(2, s.getName());
		pmt.setString(3, s.getEmail());
		pmt.setString(4, s.getCourse());
		
		
		pmt.executeUpdate();
	}
	
	public ArrayList<Student> viewStudent() throws SQLException{
	
		ArrayList<Student> list = new ArrayList<Student>();
		
		Connection con = dataSource.getConnection();
		
		String q = "select * from Student";
		PreparedStatement pmt = con.prepareStatement(q);
		
		ResultSet rs = pmt.executeQuery();
		
		while(rs.next()) {
			Student s = new Student();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setEmail(rs.getString("email"));
			s.setCourse(rs.getString("course"));
			
			list.add(s);
		}
		
		return list;
	}
	
}
```

## StudentController.java
```java
package com.cdac;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {
	
	@Autowired StudentDAO dao;
	@GetMapping("/register")
	public String showRegister(){
		return "register";
	}
	
	@PostMapping("/register")
	public String doRegister( @ModelAttribute Student s) throws SQLException {
		dao.createStudent(s);
		return "redirect:/view";
	}
	
	@GetMapping("/view")
	public String viewStudent(Model model) throws SQLException {
		ArrayList<Student> list = dao.viewStudent();
		model.addAttribute("Slist",list);
		return "view"; 
	}

}
```

## register.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
</head>
<body style="text-align: center;">
<h2>Student Registration</h2>
	<form action="register" method="post">
		Student Id: <input type="number" name="id" placeholder="Enter Student Id"><br>
		Name: <input type="text" name="name" placeholder="Enter Student Name"><br>
		Email <input type="email" name="email" placeholder="Enter Mail id"><br>
		Course: <input type="text" name="course" placeholder="Enter Course"><br>
		<input type="submit" value="Register" style="background-color: blue; color: white; border-radius: 15px;"> 
	</form>
</body>
</html>
```
## view.jsp
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View</title>
</head>
<body>
	<c:forEach var="s" items="${Slist}">
	<h2>Id: ${s.getId()}</h2>
	<h2>Name: ${s.getName()}</h2>
	<h2>Email: ${s.getEmail()}</h2>
	<h2>Course: ${s.getCourse()}</h2>
	<hr/>
	</c:forEach>
	<a href="register">Back To Register</a>
</body>
</html>
```

## dispatcher-servlet.xml
```xml
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:context="http://www.springframework.org/schema/context" xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation=" http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
<mvc:annotation-driven/>
<context:component-scan base-package="com.cdac"/>
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
<property name="prefix" value="/WEB-INF/views/"/>
<property name="suffix" value=".jsp"/>
</bean>
</beans>
```

## web.xml
```xml
<web-app>
<servlet>
<servlet-name>dispatcher</servlet-name>
<servlet-class> org.springframework.web.servlet.DispatcherServlet </servlet-class>
<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
<servlet-name>dispatcher</servlet-name>
<url-pattern>/</url-pattern>
</servlet-mapping>

<listener>
    	<listener-class>
        	org.springframework.web.context.ContextLoaderListener
    	</listener-class>
</listener>

<context-param>
    	<param-name>contextConfigLocation</param-name>
    	<param-value>
	        classpath:applicationContext.xml
	    </param-value>
</context-param>
</web-app>
```

## applicationContext.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd">

    <context:component-scan base-package="com.cdac"/>

    <!-- DataSource -->
    <bean id="dataSource"
          class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/springDB"/>
        <property name="username" value="root"/>
        <property name="password" value="monu@123"/>
    </bean>

    <!-- Transaction Manager -->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <tx:annotation-driven/>
</beans>
```

## pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.spring</groupId>
  <artifactId>SpringMVCJDBC</artifactId>
  <packaging>war</packaging>
  <version>0.0.1-SNAPSHOT</version>
  <name>SpringMVCJDBC Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <properties>
        <java.version>17</java.version>
        <spring.version>6.1.3</spring.version>
    </properties>
  
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    
    <!-- Spring Core -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-core</artifactId>
	    <version>${spring.version}</version>
	</dependency>
	
	<!-- Spring Beans -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-beans</artifactId>
	    <version>${spring.version}</version>
	</dependency>
    
    <!-- Spring MVC -->
    <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring Context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        
        <!-- Spring JDBC -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-jdbc</artifactId>
		    <version>${spring.version}</version>
		</dependency>
		
		<!-- Spring TX (Transaction Management - REQUIRED with JDBC) -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-tx</artifactId>
		    <version>${spring.version}</version>
		</dependency>

        <!-- Jakarta Servlet API (Tomcat 10) -->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>

        <!-- JSP Support -->
        <dependency>
		    <groupId>org.apache.tomcat.embed</groupId>
		    <artifactId>tomcat-embed-jasper</artifactId>
		    <version>10.1.20</version>
		    <scope>provided</scope>
		</dependency>
		
		<!-- MySQL Driver -->
		<dependency>
		    <groupId>com.mysql</groupId>
		    <artifactId>mysql-connector-j</artifactId>
		    <version>8.3.0</version>
		</dependency>


        <!-- JSTL (Jakarta version) -->
        <dependency>
            <groupId>jakarta.servlet.jsp.jstl</groupId>
            <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
            <version>3.0.0</version>
        </dependency>

        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>3.0.1</version>
        </dependency>

        <!-- Logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.9</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>2.0.9</version>
        </dependency>
  </dependencies>
  <build>
    <finalName>SpringMVCJDBC</finalName>
    <plugins>
    	<!-- Java Compiler -->
    	<plugin>
        	<groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
            	<source>${java.version}</source>
                <target>${java.version}</target>
            </configuration>
        </plugin>

        <!-- WAR Plugin -->
        <plugin>
        	<groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>3.4.0</version>
            <configuration>
            	<failOnMissingWebXml>false</failOnMissingWebXml>
            </configuration>
         </plugin>
    </plugins>
  </build>
</project>
```



