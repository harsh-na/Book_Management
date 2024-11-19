package com.hexw.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexw.web.model.Book;

public interface BookRepo extends JpaRepository<Book, String> {

	Book findByisbn(String isbn);
    
}