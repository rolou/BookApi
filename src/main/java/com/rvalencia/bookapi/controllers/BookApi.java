package com.rvalencia.bookapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rvalencia.bookapi.models.Book;
import com.rvalencia.bookapi.services.BookServices;

@RestController
public class BookApi {

	@Autowired
	private BookServices bookservices;
	
	public BookApi(BookServices bookservices) {
        this.bookservices = bookservices;
    }

    @RequestMapping("/api/books")
    public List<Book> index() {
        return bookservices.allBooks();
    }

    @RequestMapping(value = "/api/books", method = RequestMethod.POST)
    public Book create(@RequestParam(value = "title") String title, @RequestParam(value = "description") String desc, @RequestParam(value = "language") String lang, @RequestParam(value = "pages") Integer numOfPages) {
        Book book = new Book(title, desc, lang, numOfPages);
        return bookservices.createBook(book);
    }

    @RequestMapping("/api/books/{id}")
    public Book show(@PathVariable("id") Long id) {
        Book book = bookservices.findBook(id);
        return book;
    }

    //Otros métodos han sido removidos para resumir.
    @RequestMapping(value = "/api/books/{id}", method = RequestMethod.PUT)
    public Book update(@PathVariable("id") Long id, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "description", required = false) String desc, @RequestParam(value = "language", required = false) String lang, @RequestParam(value = "pages", required = false) Integer numOfPages) {
        Book book = bookservices.updateBook(id, title, desc, lang, 100);
        return book;
    }

    @RequestMapping(value = "/api/books/{id}", method = RequestMethod.DELETE)
    public void destroy(@PathVariable("id") Long id) {
        bookservices.deleteBook(id);
    }
	
}

