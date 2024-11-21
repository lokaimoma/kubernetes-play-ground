package com.koc.booksmgmtserv;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Page<Book> getBooks(PageRequest p) {
        return bookRepository.findAll(p);
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
        bk.setCount(book.getCount());

        bookRepository.save(bk);
        return Optional.of(bookRepository.save(bk));
    }

    public void updateCount(String id, int toAdd) {
        UUID bookID;
        try {
            bookID = UUID.fromString(id);
        }catch (IllegalArgumentException e) {
            return;
        }

        Optional<Book> optBook = bookRepository.findById(bookID);

        if (optBook.isEmpty()) {return;}

        Book bk = optBook.get();
        bk.setCount(bk.getCount() + toAdd);
        bookRepository.save(bk);
    }
}
