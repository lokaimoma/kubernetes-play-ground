package com.koc.authservice;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.UUID;

@Entity(name = "bookloans")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String userId;
    private String bookId;
    @CreatedDate
    private Date checkoutDate;
    private Date returnDate;
}
