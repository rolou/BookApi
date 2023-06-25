package com.rvalencia.bookapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rvalencia.bookapi.models.Book;
import com.rvalencia.bookapi.services.BookServices;

import jakarta.validation.Valid;

@Controller
public class BookController {
	
	@Autowired
	private BookServices bookservices;
	
	public BookController(BookServices bookservices) {
        this.bookservices = bookservices;
    }

    @RequestMapping("/")
    public String home(){
        return "redirect:/books";
    }

    @RequestMapping("/books")
    public String index(Model model) {
        List<Book> books = bookservices.allBooks();
        model.addAttribute("books", books);
        return "/books/index.jsp";
    }

    @RequestMapping("/books/{id}")
    public String show(@PathVariable Long id, Model model) {
        Book book = bookservices.findBook(id);
        model.addAttribute("book", book);
        return "/books/show.jsp";
    }

    @RequestMapping("/books/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new.jsp";
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "/books/new.jsp";
        } else {
            bookservices.createBook(book);
            return "redirect:/books";
        }
    }

    @RequestMapping("/books/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Book book = bookservices.findBook(id);
        model.addAttribute("book", book);
        return "/books/edit.jsp";
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public String update(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "/books/edit.jsp";
        } else {
            bookservices.updateBook(book);
            return "redirect:/books";
        }
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public String destroy(@PathVariable("id") Long id) {
        bookservices.deleteBook(id);
        return "redirect:/books";
    }
}
