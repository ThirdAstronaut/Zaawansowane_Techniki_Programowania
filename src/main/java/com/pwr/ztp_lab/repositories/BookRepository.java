package com.pwr.ztp_lab.repositories;

import com.pwr.ztp_lab.models.Book;
import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BookRepository extends CustomRepository<Book> {

    org.apache.log4j.Logger logger = Logger.getLogger(BookRepository.class.getSimpleName());

    default Set<String> getAllTitles() {
        var titles = new HashSet<String>(); //użycie var -> wnioskowanie typu
        for (Book book : findAll()) {
            titles.add(book.getTitle());
        }
        logDebug("Method called : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        return titles;
    }

    default Stream<Book> getBooksByAuthor(String author) {
        logDebug("Method called : " + Thread.currentThread().getStackTrace()[1].getMethodName());
        return StreamSupport.stream(findAll().spliterator(), false).filter(book -> book.getAuthor().equals(author));
    }


    private void logDebug(String message) {
        logger.debug(message);
    }
    Collection<Book> findPostsByTitleContaining(String title);

}
