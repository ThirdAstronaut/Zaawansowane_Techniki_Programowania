package com.pwr.ztp_lab.repositories;

import com.pwr.ztp_lab.models.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        TypedQuery<com.pwr.ztp_lab.models.Order> q =
                entityManager.createQuery("SELECT o FROM com.pwr.ztp_lab.models.Order o where o.id = " + id, com.pwr.ztp_lab.models.Order.class);
        try {
            return Optional.ofNullable(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Iterable<Order> findByBookId(Long bookId) {
        TypedQuery<Order> q =
                entityManager.createQuery("SELECT o FROM com.pwr.ztp_lab.models.Order o  where o.book = " + bookId, com.pwr.ztp_lab.models.Order.class);

        return q.getResultList();
    }

    @Override
    public Iterable<Order> findAll() {
        TypedQuery<Order> q =
                entityManager.createQuery("SELECT o FROM com.pwr.ztp_lab.models.Order o", com.pwr.ztp_lab.models.Order.class);

        return q.getResultList();

    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        //   entityManager. createQuery("DELETE FROM com.pwr.ztp_lab.models.Order o WHERE o.id = "+ id);
        com.pwr.ztp_lab.models.Order o = entityManager.find(com.pwr.ztp_lab.models.Order.class, id);
        if (o != null)
            entityManager.remove(o);

        log.info("delete" + o);
    }

    @Transactional
    @Override
    public void update(Order order, Long id) {
        // entityManager.merge(book);
        log.info("update");
        com.pwr.ztp_lab.models.Order o = entityManager.find(com.pwr.ztp_lab.models.Order.class, id);

        if (o != null) {
            o.setName(order.getName());
            o.setOrderDate(order.getDate());
            //           o.setBook(order.getBook());
            entityManager.persist(o);
        }
    }
}
