package com.pwr.ztp_lab.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@ToString
@Table(name = "order_table")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Europe/Warsaw")
    @Column
    private Date orderDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "book_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    public Order() { }

    public Order(String name, Date orderDate, Book book) {
        this.name = name;
        this.orderDate = orderDate;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return orderDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
