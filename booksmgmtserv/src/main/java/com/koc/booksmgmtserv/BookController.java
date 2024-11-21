package com.koc.booksmgmtserv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Iterable<Book> getBooks(@RequestParam(name = "size", defaultValue = "20") int size,
                                   @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        return bookService.getBooks(PageRequest.of(pageNumber, size));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book bk = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(bk);
        }catch (Exception e) {
            log.error("Error inserting book: {}", book);
            log.error("Exception: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search/author")
    public Iterable<Book> getBooksByAuthorId(@RequestParam(name = "authorId", defaultValue = "") String authorId) {
        if (authorId.isBlank()) {return List.of();}
        return bookService.getBooksByAuthor(authorId);
    }

    @GetMapping("/search/title")
    public Optional<Book> getBooksByTitle(@RequestParam(name = "title", defaultValue = "") String title) {
        if (title.isBlank()) {return Optional.empty();}
        return bookService.getBookByTitle(title);
    }

}
