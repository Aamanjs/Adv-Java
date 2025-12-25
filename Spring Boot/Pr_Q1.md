## Book.java
```java
package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {
	
	@Id
	private int book_id;
	private String book_name;
	private String author;
	private double price;
	
	Book() {}

	public Book(int book_id, String book_name, String author, double price) {
		this.book_id = book_id;
		this.book_name = book_name;
		this.author = author;
		this.price = price;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	

}
```
## BookRepository.java
```java
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>{

}
```

## BookController.java
```java
package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
	
	private final BookRepository bookRepository;
	
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("/register")
	public String showForm(Model model) {
		model.addAttribute("book", new Book());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerBook(@ModelAttribute Book book, Model model) {
		bookRepository.save(book);
		model.addAttribute("books", bookRepository.findAll());
		return "view";
	}
	
	@GetMapping("/view")
	public String viewBooks(Model model) {
		model.addAttribute("books", bookRepository.findAll());
		return "view";
	}
	
	@GetMapping("/search")
	public String searchBook(@RequestParam("book_id") int bookId, Model model) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isPresent()) {
			model.addAttribute("books",java.util.List.of(book.get()));
		} else {
			model.addAttribute("books", java.util.List.of());
			model.addAttribute("message","Book not found with ID: "+bookId);
		}
		return "view";
	}

	// Delete book by ID
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") int id, Model model) {
		bookRepository.deleteById(id);
		model.addAttribute("books", bookRepository.findAll());
		return "view";
	}
	
	// Show update form
	@GetMapping("update/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		Optional<Book> book = bookRepository.findById(id);
		if(book.isPresent()) {
			model.addAttribute("book", book.get());
			return "update";
		} else {
			model.addAttribute("books", bookRepository.findAll());
			model.addAttribute("message", "Book not found with ID: " + id);
			return "view";
		}
	}
	
	//Handle update
	@PostMapping("/update")
	public String updateBook(@ModelAttribute Book book, Model model) {
		bookRepository.save(book);
		model.addAttribute("books", bookRepository.findAll());
		return "view";
	}
	
	

}
```

## register.html
```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register Book</title>
</head>
<body>
 	<h1>Library Book Management</h1><hr><br><br>
	<h2>Book Registration</h2>
	<form th:action="@{/register}" th:object="${book}" method="post">
		Book Id: <input type="number" th:field="*{book_id}"><br><br>
		Book Name: <input type="text" th:field="*{book_name}"><br><br>
		Author: <input type="text" th:field="*{author}"><br><br>
		Price: <input type="number" th:field="*{price}"><br><br> 
		<button type="submit">Register</button>
	</form>
	
	<br><hr><br>
	<form th:action="@{/view}" method="get">
		<button type="submit">View All Books</button>
	</form>
	
	<br><hr><br>
	<form th:action="@{/search}" method="get">
		Enter Book Id: <input type="number" name="book_id">
		<button type="submit">Search Book by ID</button>
	</form>
</body>
</html>
```

## view.html
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Books</title>
</head>
<body>
	<h1>Library Book Management</h1><hr><br><br>
	<h2>Registered Books</h2>
	
	<p th:if="${message}" th:text="${message}" style="color:red;"></p>
	
	<table border="1">
		<tbody>
		<tr>
			<th>Book Id</th>
			<th>Book Name</th>
			<th>Author</th>
			<th>Price</th>
			<th>Actions</th>
		</tr>
		<tr th:each="b : ${books}">
			<td th:text="${b.book_id}"></td>
			<td th:text="${b.book_name}"></td>
			<td th:text="${b.author}"></td>
			<td th:text="${b.price}"></td>
			<td>
                <a th:href="@{/update/{id}(id=${b.book_id})}">Update</a> |
                <a th:href="@{/delete/{id}(id=${b.book_id})}">Delete</a>
            </td>
			
		</tr>
		</tbody>
	</table><br>
	<a th:href="@{/register}">Go to Registration Page</a>
</body>
</html>
```

## update.html
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<html>
<head>
<meta charset="UTF-8">
<title>Books</title>
</head>
<body>
	<h1>Library Book Management</h1><hr><br><br>
    <h2>Update Book</h2><br>

    <form th:action="@{/update}" th:object="${book}" method="post">
    		Book Id: <input type="number" th:field="*{book_id}" readonly><br><br>
    		Book Name: <input type="text" th:field="*{book_name}"><br><br>
        Author: <input type="text" th:field="*{author}"><br><br>
        Price: <input type="number" th:field="*{price}"><br><br>
        <button type="submit">Update</button>
    </form><br>
	<a th:href="@{/view}">Back to View Page</a>
</body>
</html>
```

## application.properties
```properties
spring.application.name=LBM-JPA
spring.datasource.url=jdbc:mysql://localhost:3306/lbmjpa
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
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>4.0.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.example</groupId>
	<artifactId>LBM-JPA</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>LBM-JPA</name>
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
