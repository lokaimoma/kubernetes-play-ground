package com.koc.booksmgmtserv;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends MongoRepository<Book, UUID> {
    Iterable<Book> findByAuthor(String author);
    Optional<Book> findByTitle(String title);
}
