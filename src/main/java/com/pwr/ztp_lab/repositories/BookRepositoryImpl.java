package com.pwr.ztp_lab.repositories;

import com.pwr.ztp_lab.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Repository
@Transactional(readOnly = true)
public class BookRepositoryImpl implements BookRepository {

    private final EntityManager entityManager;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(com.pwr.ztp_lab.models.Book book) {
        entityManager.persist(book);
    }

    @Override
    public Optional<com.pwr.ztp_lab.models.Book> findById(Long id) {
        TypedQuery<com.pwr.ztp_lab.models.Book> q =
                entityManager.createQuery("SELECT b FROM com.pwr.ztp_lab.models.Book b where b.id = " + id, com.pwr.ztp_lab.models.Book.class);
        try{
            return Optional.ofNullable(q.getSingleResult());
        } catch(NoResultException e){
            return Optional.empty();
        }
  }

    @Override
    public Iterable<com.pwr.ztp_lab.models.Book> findAll() {
        TypedQuery<com.pwr.ztp_lab.models.Book> q =
                entityManager.createQuery("SELECT b FROM com.pwr.ztp_lab.models.Book b", com.pwr.ztp_lab.models.Book.class);

        List<com.pwr.ztp_lab.models.Book> results = q.getResultList();
        return results;

    }


    @Transactional
    @Override
    public void deleteById(Long id) {
        //   entityManager. createQuery("DELETE FROM com.pwr.ztp_lab.models.Book b WHERE b.id = "+ id);
        com.pwr.ztp_lab.models.Book b = entityManager.find(com.pwr.ztp_lab.models.Book.class, id);
        if (b != null)
            entityManager.remove(b);

        log.info("delete" + b);
    }

    public Optional<Book> findByIsbn(String isbn) {
        Optional<Book> book = Optional.empty();

            TypedQuery<com.pwr.ztp_lab.models.Book> q =
                    entityManager.createQuery("SELECT b FROM com.pwr.ztp_lab.models.Book b where b.isbn = " + isbn, com.pwr.ztp_lab.models.Book.class);

    return Optional.ofNullable(q.getSingleResult());
    }




    @Transactional
    @Override
    public void update(com.pwr.ztp_lab.models.Book book, Long id) {
        // entityManager.merge(book);
        log.info("update");
        com.pwr.ztp_lab.models.Book b = entityManager.find(com.pwr.ztp_lab.models.Book.class, id);

        if (b != null) {
            b.setAuthor(book.getAuthor());
            b.setIsbn(book.getIsbn());
            b.setTitle(book.getTitle());
            entityManager.persist(b);
        }
    }


    public List<Book> findPostsByTitleContaining(String title){
        String queryParam = "\'%" + title + "%\'";
         TypedQuery<com.pwr.ztp_lab.models.Book> q =
                entityManager.createQuery("SELECT b FROM com.pwr.ztp_lab.models.Book b WHERE UPPER(b.title) LIKE UPPER("+queryParam+")", com.pwr.ztp_lab.models.Book.class);

        List<com.pwr.ztp_lab.models.Book> results = q.getResultList();
        return results;

    }
}
