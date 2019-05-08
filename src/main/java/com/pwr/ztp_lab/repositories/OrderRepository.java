package com.pwr.ztp_lab.repositories;


import com.pwr.ztp_lab.models.Order;
import org.aspectj.weaver.ast.Or;

import java.util.Optional;

public interface OrderRepository extends CustomRepository<Order> {
    Iterable<Order> findByBookId(Long bookId);

}
