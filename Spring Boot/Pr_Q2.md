## Student.java
```java
package com.example.demo;

public class Student {
	
	private int studentId;
	private String name;
	private String course;
	private String email;
	private String marks;
	
	Student() {}

	public Student(int studentId, String name, String course, String email, String marks) {
		this.studentId = studentId;
		this.name = name;
		this.course = course;
		this.email = email;
		this.marks = marks;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}
	

}
```

## StudentController.java
```java
package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	private List<Student> studList = new ArrayList<Student>();
	
	// ADD Student
	@PostMapping
	public String addCourse(@RequestBody Student student) {
		studList.add(student);
		return "Student added succesfully";
	}
	
	// VIEW ALL Student
	@GetMapping
	public List<Student> viewCourse(){
		return studList;
	}
	
	// SEARCH Student by ID
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable int id) {
		for(Student student : studList) {
			if(student.getStudentId() == id) {
				return student;
			}
		}
		return null;
	}
	
	// UPDATE Student
	@PutMapping("/{id}")
	public String updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
		for(Student student : studList) {
			if(student.getStudentId() == id) {
				student.setName(updatedStudent.getName());
				student.setCourse(updatedStudent.getCourse());
				student.setEmail(updatedStudent.getEmail());
				student.setMarks(updatedStudent.getMarks());
				return "Student updated successfully";
			}
		}
		return "Student not found";
	}
	
	// DELETE Student
	@DeleteMapping("/{id}")
	public String deleteCourse(@PathVariable int id) {
		
		Student studentToRemove = null;
		
		for(Student student : studList) {
			if(student.getStudentId() == id) {
				studentToRemove = student;
				break;
			}
		}
		
		if(studentToRemove != null) {
			studList.remove(studentToRemove);
			return "Student deleted succesfuly";
		}
		
		return "Student not found";
	}
	

}
```

## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>4.0.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>SRM-RestAPI</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>SRM-RestAPI</name>
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
