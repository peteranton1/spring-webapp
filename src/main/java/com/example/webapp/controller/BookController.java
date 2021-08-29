package com.example.webapp.controller;

import com.example.webapp.exception.BookMismatchException;
import com.example.webapp.exception.BookNotFoundException;
import com.example.webapp.model.Book;
import com.example.webapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @RequestMapping(value = "/title/{bookTitle}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<Book> findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(
                        String.format("Book Not found: %d", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(
                        String.format("Book Not found: %d", id)));
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book,
                           @PathVariable Long id) {
        if (book.getId() != id) {
            throw new BookMismatchException(
                    String.format("Book id: %d, url id: %d",
                            book.getId(), id));
        }
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(
                        String.format("Book Not found: %d", id)));
        return bookRepository.save(book);
    }
}
