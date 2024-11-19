package com.hexw.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexw.web.dao.BookRepo;
import com.hexw.web.exceptions.BookAlreadyExistsException;
import com.hexw.web.exceptions.BookNotFoundException;
import com.hexw.web.exceptions.InvalidBookDetailsException;
import com.hexw.web.model.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	@Autowired
	private BookRepo bookRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public Optional<Book> getBookByIsbn(String isbn) {
		Optional<Book> book = bookRepository.findById(isbn);
		if (book.isEmpty()) {
			throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
		}
		return book;
	}

	public Book addBook(Book book) {

		if (bookRepository.existsById(book.getIsbn())) {
			throw new BookAlreadyExistsException("Book with ISBN " + book.getIsbn() + " already exists.");
		}
		try {

			book.setIsbn(book.getIsbn());

			String encryptedPassword = passwordEncoder.encode(book.getPassword());
			book.setPassword(encryptedPassword);

			book.setTitle(book.getTitle());
			book.setAuthor(book.getAuthor());
			book.setPublicationYear(book.getPublicationYear());

			Book savedUser = bookRepository.save(book);

			return savedUser;
		} catch (Exception e) {
			throw new InvalidBookDetailsException("Invalid book details provided.");
		}
	}

	public Book updateBook(String isbn, Book updatedBook) {
		if (!bookRepository.existsById(isbn)) {
			throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
		}
		updatedBook.setIsbn(isbn);
		return bookRepository.save(updatedBook);
	}

	public boolean deleteBook(String isbn) {
		if (!bookRepository.existsById(isbn)) {
			throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
		}
		bookRepository.deleteById(isbn);
		return true;
	}
}
