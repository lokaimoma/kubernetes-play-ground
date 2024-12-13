package com.koc.authservice.feignClients;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(method = RequestMethod.POST, value = "/borrow")
    BorrowBookResponse borrowBook(@QueryParam("bookId") String bookId);

    @RequestMapping(method = RequestMethod.POST, value = "/return")
    ReturnBookResponse returnBook(@QueryParam("bookId") String bookId);
}
