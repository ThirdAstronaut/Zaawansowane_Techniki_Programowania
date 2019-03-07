package com.pwr.ztp_lab.repositories;


import com.pwr.ztp_lab.models.Order;

public interface OrderRepository extends CustomRepository<Order> {
    Iterable<Order> findByBookId(Long bookId);
}
