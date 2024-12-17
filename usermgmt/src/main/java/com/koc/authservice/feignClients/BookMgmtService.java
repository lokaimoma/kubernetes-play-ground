package com.koc.authservice.feignClients;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "bookmgmtserv", url = "${services.bookmgmtserv}/api/books")
public interface BookMgmtService {
    public enum ReturnBookResponse {
        RETURNED,
        INVALID_ID,
        ALL_BOOKS_ALREADY_RETURNED
    }

    public enum BorrowBookResponse {
        AVAILABLE,
        NOT_AVAILABLE,
        INVALID_ID
    }

    @Data
    @Builder
    public class BookResponse {
        private String id;
        private String title;
        private String description;
        private String author;
        private int stock;
        private int available;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/borrow")
    BorrowBookResponse borrowBook(@RequestParam("bookId") String bookId);

    @RequestMapping(method = RequestMethod.POST, value = "/return")
    ReturnBookResponse returnBook(@RequestParam("bookId") String bookId);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    BookResponse getBook(@PathVariable("id") String id);

}
