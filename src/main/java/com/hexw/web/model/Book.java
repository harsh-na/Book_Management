package com.hexw.web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@NotBlank(message = "ISBN must not be blank")
	@Pattern(regexp = "^[0-9]{10}|[0-9]{13}$", message = "ISBN must be 10 or 13 digits long")
	private String isbn;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password should be at least 8 characters long")
	private String password;

	@NotBlank(message = "Title must not be blank")
	@Size(max = 255, message = "Title must not exceed 255 characters")
	private String title;

	@NotBlank(message = "Author must not be blank")
	@Size(max = 100, message = "Author name must not exceed 100 characters")
	private String author;

	@NotNull(message = "Publication year must not be null")
	@Positive(message = "Publication year must be a positive number")
	private int publicationYear;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", password=" + password + ", title=" + title + ", author=" + author
				+ ", publicationYear=" + publicationYear + "]";
	}

	public Book(
			@NotBlank(message = "ISBN must not be blank") @Pattern(regexp = "^[0-9]{10}|[0-9]{13}$", message = "ISBN must be 10 or 13 digits long") String isbn,
			@NotBlank(message = "Password is required") @Size(min = 8, message = "Password should be at least 8 characters long") String password,
			@NotBlank(message = "Title must not be blank") @Size(max = 255, message = "Title must not exceed 255 characters") String title,
			@NotBlank(message = "Author must not be blank") @Size(max = 100, message = "Author name must not exceed 100 characters") String author,
			@NotNull(message = "Publication year must not be null") @Positive(message = "Publication year must be a positive number") int publicationYear) {
		super();
		this.isbn = isbn;
		this.password = password;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
	}

}
