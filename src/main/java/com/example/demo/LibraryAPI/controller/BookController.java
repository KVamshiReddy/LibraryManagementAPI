package com.example.demo.LibraryAPI.controller;
import com.example.demo.LibraryAPI.models.Book;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/get/book")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public Response bookdesc(@PathVariable UUID id) {
        return new Response(bookService.bookdesc(id));
    }

    @PostMapping("/add/book")
    public String addBooks(@RequestBody Book book) {
        return bookService.addBoooks(book);
    }

    @DeleteMapping("/delete/{id}")
    public void delbookbyId(@PathVariable UUID id) {
        bookService.delbookbyId(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book updatedBook) {
        Book updated = bookService.updateBook(id, updatedBook);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

}
