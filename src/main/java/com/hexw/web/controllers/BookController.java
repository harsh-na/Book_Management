package com.hexw.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.hexw.web.model.Book;
import com.hexw.web.services.BookDetailsServiceImpl;
import com.hexw.web.services.BookService;
import com.hexw.web.utils.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private BookDetailsServiceImpl bookDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/getAll") // permit all
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}

	@GetMapping("/{isbn}") // permit all
	public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
		return bookService.getBookByIsbn(isbn).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/add") // permit all
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		return ResponseEntity.ok(bookService.addBook(book));
	}

	@PutMapping("/update/{isbn}") // authorized
	public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {
		Book updated = bookService.updateBook(isbn, updatedBook);
		return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{isbn}") // authorized
	public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
		return bookService.deleteBook(isbn) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	@PostMapping("/login") // anyone can try to login
	public ResponseEntity<String> login(@RequestBody Book book) {
		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(book.getIsbn(), book.getPassword()));

			UserDetails userDetails = bookDetailsService.loadUserByUsername(book.getIsbn());
			String jwt = jwtUtil.generateToken(userDetails.getUsername());

			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Login failed", HttpStatus.BAD_REQUEST);
		}
	}
}
