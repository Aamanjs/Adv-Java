## Course.java
```java
package com.example.demo;

public class Course {
	
	private int courseId;
	private String courseName;
	private int duration;
	
	public Course() {}

	public Course(int courseId, String courseName, int duration) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.duration = duration;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
	

}
```

## CourseController
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
@RequestMapping("/courses")
public class CourseController {
	
	private List<Course> courseList = new ArrayList<Course>();
	
	// ADD Course
	@PostMapping
	public String addCourse(@RequestBody Course course) {
		courseList.add(course);
		return "Course added succesfully";
	}
	
	@PostMapping("/list")
	public String addCourse(@RequestBody List<Course> course) {
		courseList.addAll(course);
		return "Course added succesfully";
	}
	
	//  VIEW ALL Course
	@GetMapping
	public List<Course> viewCourse(){
		return courseList;
	}
	
	// SEARCH Course by ID
	@GetMapping("/{id}")
	public Course getCourseById(@PathVariable int id) {
		for(Course course : courseList) {
			if(course.getCourseId() == id) {
				return course;
			}
		}
		return null;
	}
	
	// UPDATE Course
	@PutMapping("/{id}")
	public String updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
		for(Course course : courseList) {
			if(course.getCourseId() == id) {
				course.setCourseName(updatedCourse.getCourseName());
				course.setDuration(updatedCourse.getDuration());
				return "Course updated successsfully";
			}
		}
		return "Course not found";
	}
	
	// DELETE Course
	@DeleteMapping("/{id}")
	public String deleteCourse(@PathVariable int id) {
		
		Course courseToRemove = null;
		
		for(Course course : courseList) {
			if(course.getCourseId() == id) {
				courseToRemove = course;
				break;
			}
		}
			
			if (courseToRemove != null) {
				courseList.remove(courseToRemove);
				return "Course deleted succesfully";
			}
			
			return "Course not found";
	}
	

}
```

## POM.xml
```java
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
	<artifactId>CourseAPI</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>CourseAPI</name>
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
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
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
