package com.hexw.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexw.web.dao.BookRepo;
import com.hexw.web.model.Book;

import java.util.Collections;

@Service
public class BookDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private BookRepo bookRepository;

	@Override
	public UserDetails loadUserByUsername(String isbn) throws UsernameNotFoundException {
		Book book = bookRepository.findByisbn(isbn);

		if (book != null) {

			return new org.springframework.security.core.userdetails.User(book.getIsbn(), book.getPassword(),
					Collections.emptyList());
		}

		throw new UsernameNotFoundException("User not found with isbn: " + isbn);
	}
}
