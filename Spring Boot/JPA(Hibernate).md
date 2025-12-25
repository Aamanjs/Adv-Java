## Student.java
```java
package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
	
	@Id
	private int id;
	private String name;
	private String course;
	
	public Student() {}
	
	public Student(int id, String name, String course) {
		this.id = id;
		this.name = name;
		this.course = course;
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

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	

}
```

## StudentRepository.java
```java
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
}
```

## StudentController.java
```java
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Student;
import com.example.demo.StudentRepository;

@Controller
public class StudentController {
	
	private final StudentRepository studentRepository;
	
	public StudentController(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("student", new Student());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerStudent(@ModelAttribute Student student, Model model) {
		studentRepository.save(student);
		model.addAttribute("students", studentRepository.findAll());
		return "view";
	}
	
	
	

}
```

## register.html
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>register</title>
</head>
<body>
	<h2>Student Registration</h2>
	<form th:action="@{/register}" th:object="${student}" method="post">
		Student ID: <input type="number" th:field="*{id}"><br><br>
		Name: <input type="text" th:field="*{name}"><br><br>
		Course: <input type="text" th:field="*{course}"><br><br>
		<button type="submit">Register</button>
	</form>
</body>
</html>
```

## view.html
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Students</title>
</head>
<body>
	<h2>Registered Students</h2>
	<table border="1">
		<tbody>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Course</th>
		</tr>
		<tr th:each="s : ${students}">
			<td th:text="${s.id}"></td>
			<td th:text="${s.name}"></td>
			<td th:text="${s.course}"></td>
		</tr>
		</tbody>
	</table>
	<a th:href="@{/register}">Go to Registration Page</a>
</body>
</html>
```

## application.properties
```properties
spring.application.name=SpringBootMVC-Student-JPA
spring.datasource.url=jdbc:mysql://localhost:3306/sbhibstu
spring.datasource.username=root
spring.datasource.password=monu@123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.thymeleaf.cache=false
```

## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>4.0.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>SpringBootMVC-Student-JPA</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>SpringBootMVC-Student-JPA</name>
	<description>Demo project for Spring Boot</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
