package com.pwr.ztp_lab.controllers;

import com.pwr.ztp_lab.models.Book;
import com.pwr.ztp_lab.repositories.CustomRepository;
import com.pwr.ztp_lab.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomRepository<Book> bookRepository;

    @Autowired
    public OrderController(@Qualifier("orderRepositoryImpl") OrderRepository orderRepository, CustomRepository<Book> bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    /* ORDERS RETRIVE */
    @GetMapping(value = "/orders")
    public String allOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "ordersPage";
    }

    /* BOOK AND ITS ORDERS RETRIVE */
    @GetMapping(value = "/books/{bookId}")
    public String oneBook(Model model, @PathVariable(value = "bookId") Long bookId) {
        Optional<com.pwr.ztp_lab.models.Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("orders", orderRepository.findByBookId(bookId)); // book.get().getOrders()
            return "oneBookPage";
        }
        return "redirect:/books";
    }

    /* ORDERS CREATE */
    @PostMapping(value = "/books/{bookId}/orders")
    public String addOrder(@PathVariable(value = "bookId") Long bookId, @RequestBody com.pwr.ztp_lab.models.Order order) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (b.isPresent()) {
            order.setBook(b.get());
            orderRepository.save(order);
            /*b.get().getOrders().add(order);
            bookRepository.save(b.get());
            */
        }
        return "redirect:/books/{bookId}";
    }

    /* ORDERS DELETE */
    @DeleteMapping(value = "/orders/delete/{orderId}")
    public String deleteOrder(@PathVariable(value = "orderId") Long orderId) {
        orderRepository.deleteById(orderId);
        return "redirect:/orders";
    }


    /* ORDERS UPDATE */
    @PutMapping(value = "/orders/update/{orderId}")
    public String updateOrder(@PathVariable(value = "orderId") Long orderId, @RequestBody com.pwr.ztp_lab.models.Order order) {
        orderRepository.update(order, orderId);
        return "redirect:/orders";
    }

}
