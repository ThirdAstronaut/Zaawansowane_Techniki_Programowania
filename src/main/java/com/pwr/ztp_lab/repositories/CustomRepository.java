package com.pwr.ztp_lab.repositories;

import java.util.Optional;

public interface CustomRepository<T> {

    void save(T var1);

    Optional<T> findById(Long id);

    Iterable<T> findAll();


    void deleteById(Long id);


    void update(T t, Long id);
}