package com.koc.authservice.dto;

import com.koc.authservice.feignClients.BookMgmtService;
import lombok.Getter;

import java.util.Date;

@Getter
public class BookloanAdminRecords extends BookloanDTO {
    String userEmail;

    public BookloanAdminRecords(String email, String id, Date checkoutDate, Date returnDate, BookMgmtService.BookResponse Book) {
        super(id, checkoutDate, returnDate, Book);
        userEmail = email;
    }
}
