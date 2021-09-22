package io.okraskat.example.micronaut;

import io.okraskat.example.micronaut.domain.Book;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
