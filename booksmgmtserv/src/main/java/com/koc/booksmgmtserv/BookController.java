package com.koc.booksmgmtserv;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Iterable<Book> getBooks(@RequestParam(name = "size", defaultValue = "20") @Min(1) int size,
                                   @RequestParam(name = "pageNumber", defaultValue = "0") @Min(0) int pageNumber) {
        return bookService.getBooks(PageRequest.of(pageNumber, size));
    }

    @GetMapping("/{id}")
    public Optional<Book> getBook(@PathVariable("id") @NotNull UUID id) {
        return bookService.getBookById(id);
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody @Valid Book book) {
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

    @PostMapping("/borrow")
    public ResponseEntity<BookService.BorrowBookResponse> borrowBook(@RequestParam(name="bookId") @NotBlank String bookId) {
        BookService.BorrowBookResponse resp = bookService.borrowBook(bookId);
        return switch (resp) {
            case NOT_AVAILABLE, INVALID_ID -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            case AVAILABLE -> ResponseEntity.ok(resp);
        };
    }

    @PostMapping("/return")
    public ResponseEntity<BookService.ReturnBookResponse> returnBook(@RequestParam(name="bookId") @NotBlank String bookId) {
        BookService.ReturnBookResponse resp = bookService.returnBook(bookId);
        return switch (resp) {
            case ALL_BOOKS_ALREADY_RETURNED, INVALID_ID -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            case RETURNED -> ResponseEntity.ok(resp);
        };
    }
}
