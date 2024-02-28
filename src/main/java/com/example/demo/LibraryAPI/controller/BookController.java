package com.example.demo.LibraryAPI.controller;
import com.example.demo.LibraryAPI.models.Book;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Get list of all books")
    @GetMapping("/get/book")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Operation(summary = "Get a book by it's ID")
    @GetMapping("/book/{id}")
    public Response bookdesc(@PathVariable UUID id) {
        return new Response(bookService.bookdesc(id));
    }

    @Operation(summary = "Create a new book")
    @PostMapping("/add/book")
    public String addBooks(@RequestBody Book book) {
        return bookService.addBoooks(book);
    }

    @Operation(summary = "Delete a book by it's ID")
    @DeleteMapping("/delete/{id}")
    public void delbookbyId(@PathVariable UUID id) {
        bookService.delbookbyId(id);
    }

    @Operation(summary = "Update details of a book based on it's ID")
    @PutMapping("/update/{id}")
    public Response updateBook(@PathVariable UUID id, @RequestBody Book updatedBook) {
        return new Response( bookService.updateBook(id, updatedBook));
    }

}
