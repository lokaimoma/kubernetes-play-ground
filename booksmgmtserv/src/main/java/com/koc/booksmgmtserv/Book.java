package com.koc.booksmgmtserv;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "books")
@Data
public class Book {
    @Id
    private UUID id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String author;
    @Min(1)
    private int stock;
    @Min(0)
    private int available;
}
