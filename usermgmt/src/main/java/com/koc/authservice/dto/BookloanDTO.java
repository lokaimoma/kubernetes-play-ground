package com.koc.authservice.dto;

import com.koc.authservice.feignClients.BookMgmtService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class BookloanDTO {
    private String id;
    private Date checkoutDate;
    private Date returnDate;
    private BookMgmtService.BookResponse Book;
}
