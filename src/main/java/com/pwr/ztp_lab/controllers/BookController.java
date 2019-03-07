package com.pwr.ztp_lab.controllers;

import com.pwr.ztp_lab.repositories.CustomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class BookController {

    private final CustomRepository<com.pwr.ztp_lab.models.Book> bookRepository;

    @Autowired
    public BookController(CustomRepository<com.pwr.ztp_lab.models.Book> bookRepository) {
        this.bookRepository = bookRepository;
    }

    /* BOOKS RETRIVE */
    @GetMapping(value = "/books")
    public String allBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("book", new com.pwr.ztp_lab.models.Book());
        log.debug("Książka" + bookRepository.findAll());
        return "booksPage";
    }

    /* BOOKS CREATE */
    @PostMapping(value = "/books")
    public String addBook(@RequestBody com.pwr.ztp_lab.models.Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    /* BOOKS DELETE */
    @DeleteMapping(value = "/books/delete/{bookId}")
    public String deleteBook(@PathVariable(value = "bookId") Long bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:/books";
    }

    /* BOOKS UPDATE */
    @PutMapping(value = "/books/update/{bookId}")
    public String updateBook(@PathVariable(value = "bookId") Long bookId, @RequestBody com.pwr.ztp_lab.models.Book book) {
        bookRepository.update(book, bookId);
        return "redirect:/books";
    }
}
