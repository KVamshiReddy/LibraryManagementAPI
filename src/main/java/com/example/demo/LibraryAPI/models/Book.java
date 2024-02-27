package com.example.demo.LibraryAPI.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@Data
@Document
public class Book {

    @Id
    private UUID id;
    private String name;
    private String author;
    private String category;
    private String description;
    private int copies;


    public Book() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

}