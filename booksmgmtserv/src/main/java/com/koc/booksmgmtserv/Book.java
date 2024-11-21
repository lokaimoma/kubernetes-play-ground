package com.koc.booksmgmtserv;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "books")
@Data
public class Book {
    @Id
    private UUID id;
    private String title;
    private String description;
    private String author;
    private int count;
}
