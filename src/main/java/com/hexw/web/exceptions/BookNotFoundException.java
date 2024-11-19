package com.hexw.web.exceptions;

//BookNotFoundException
public class BookNotFoundException extends RuntimeException {
 public BookNotFoundException(String message) {
     super(message);
 }
}