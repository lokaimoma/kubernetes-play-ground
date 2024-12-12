package com.koc.authservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userEmail;
    private String bookId;
    @Builder.Default
    private Date checkoutDate = new Date();
    private Date returnDate;
}
