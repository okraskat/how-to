package io.okraskat.example.micronaut;

import io.okraskat.example.micronaut.domain.Book;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest(environments = "test")
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Test
    public void should_use_database_from_container() {
        //given
        Book entity = new Book("isbn", "name");

        //when
        Book saved = bookRepository.save(entity);

        //then
        Assertions.assertNotNull(saved.getId());
    }
}
