package com.cdac;

public class Book {
	
	String title;
	String author;
	String genre;
	int year_published;
	
	public Book(String title, String author, String genre, int year_published) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.year_published = year_published;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear_published() {
		return year_published;
	}

	public void setYear_published(int year_published) {
		this.year_published = year_published;
	}

	
}
