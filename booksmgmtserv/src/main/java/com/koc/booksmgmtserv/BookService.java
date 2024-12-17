package com.koc.booksmgmtserv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    public Page<Book> getBooks(PageRequest p) {
        return bookRepository.findAll(p);
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Iterable<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Book addBook(Book book) {
        book.setId(UUID.randomUUID());
        return bookRepository.save(book);
    }

    public Iterable<Book> addManyBooks(Iterable<Book> books) {
        return bookRepository.saveAll(books);
    }

    public Optional<Book> update(String id, Book book) {
        UUID bookID;
        try {
            bookID = UUID.fromString(id);
        }catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Optional<Book> optBook = bookRepository.findById(bookID);

        if (optBook.isEmpty()) {return optBook;}

        Book bk = optBook.get();
        bk.setTitle(book.getTitle());
        bk.setDescription(book.getDescription());
        bk.setAuthor(book.getAuthor());
        bk.setStock(book.getStock());
        bk.setAvailable(book.getAvailable());

        bookRepository.save(bk);
        return Optional.of(bookRepository.save(bk));
    }

    public enum BorrowBookResponse {
        AVAILABLE,
        NOT_AVAILABLE,
        INVALID_ID
    }

    public BorrowBookResponse borrowBook(String id) {
        UUID bookID;
        try {
            bookID = UUID.fromString(id);
        }catch (IllegalArgumentException e) {
            return BorrowBookResponse.INVALID_ID;
        }

        Optional<Book> optBook = bookRepository.findById(bookID);

        if (optBook.isEmpty()) {
            log.warn("Book not found");
            return BorrowBookResponse.NOT_AVAILABLE;
        }

        Book bk = optBook.get();
        if (bk.getAvailable() < 1) {
            return BorrowBookResponse.NOT_AVAILABLE;
        }

        bk.setAvailable(bk.getAvailable()-1);

        bookRepository.save(bk);

        return BorrowBookResponse.AVAILABLE;
    }

    public enum ReturnBookResponse {
        RETURNED,
        INVALID_ID,
        ALL_BOOKS_ALREADY_RETURNED
    }

    public ReturnBookResponse returnBook(String id) {
        UUID bookID;
        try {
            bookID = UUID.fromString(id);
        }catch (IllegalArgumentException e) {
            return ReturnBookResponse.INVALID_ID;
        }

        Optional<Book> optBook = bookRepository.findById(bookID);

        if (optBook.isEmpty()) {
            return ReturnBookResponse.INVALID_ID;
        }

        Book bk = optBook.get();
        if (bk.getAvailable() == bk.getStock()) {
            return ReturnBookResponse.ALL_BOOKS_ALREADY_RETURNED;
        }

        bk.setAvailable(bk.getAvailable()+1);

        bookRepository.save(bk);

        return ReturnBookResponse.RETURNED;
    }
}
