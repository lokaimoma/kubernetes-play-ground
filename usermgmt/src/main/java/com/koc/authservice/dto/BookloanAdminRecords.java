package com.koc.authservice.dto;

import com.koc.authservice.feignClients.BookMgmtService;

import java.util.Date;


public class BookloanAdminRecords extends BookloanDTO {
    String userEmail;

    public BookloanAdminRecords(String email, String id, Date checkoutDate, Date returnDate, BookMgmtService.BookResponse Book) {
        super(id, checkoutDate, returnDate, Book);
        userEmail = email;
    }
}
