package com.pwr.ztp_lab.controllers;

import com.pwr.ztp_lab.DTO.BookDto;
import com.pwr.ztp_lab.models.Book;
import com.pwr.ztp_lab.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Controller
public class BookController {

    private final BookRepository bookRepository;


    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /* BOOKS RETRIVE */
    @GetMapping(value = "/books")
    public String allBooks(Model model) {
        if (!model.containsAttribute("bookToSearch"))
            model.addAttribute("bookToSearch", new BookDto());


        if (!model.containsAttribute("books"))
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

    @ResponseBody
    @GetMapping(value = "/titles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> getTitles() {
        return bookRepository.getAllTitles();
    }

    @ResponseBody
    @GetMapping(value = "/author/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stream<Book> getBooksByAuthor(@PathVariable(value = "author") String author) {
        return bookRepository.getBooksByAuthor(author);
    }


    @GetMapping("/find_book")
    public String processFindForm(BookDto bookDto, Model model, RedirectAttributes redirectAttributes) {

        if (bookDto.getTitle() == null) {
            bookDto.setTitle("");
        }
        Collection<Book> results = this.bookRepository.findPostsByTitleContaining(bookDto.getTitle());

        model.addAttribute("books", results);
        redirectAttributes.addFlashAttribute("books", results);

        return "redirect:/books";
    }


}
