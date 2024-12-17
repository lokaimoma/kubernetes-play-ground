package com.koc.authservice.dto;

import com.koc.authservice.feignClients.BookMgmtService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class BookloanDTO {
    private String id;
    private Date checkoutDate;
    private Date returnDate;
    private BookMgmtService.BookResponse Book;
}
