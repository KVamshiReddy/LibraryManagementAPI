package com.example.demo.LibraryAPI.controller;

import com.example.demo.LibraryAPI.models.BorrowedBook;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.services.BorrowedBookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/borrowed")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService borrowedBookService;

    @Operation(summary = "Get list of all borrowed books")
    @GetMapping("/books")
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookService.getAllBorrowedBooks();
    }

    @Operation(summary = "Get borrowed book by it's ID")
    @GetMapping("/book/{id}")
    public Response getBorrowedBookbyId(@PathVariable UUID id) {
        return borrowedBookService.getBorrowedBookbyId(id);
    }

    @Operation(summary = "Borrow a new book")
    @PostMapping("/")
    public String borrowBook(@RequestParam UUID userId, @RequestParam UUID bookId) {
        return borrowedBookService.borrowBook(userId, bookId);
    }

    @Operation(summary = "Submitting a borrowed book")
    @PutMapping("/update")
    public Response updateDate(@RequestParam UUID id) throws ParseException {
        return new Response(borrowedBookService.updateDate(id));
    }

    @Operation(summary = "Updating the fine amount of a user")
    @PutMapping("/fine/amount")
    public Response fineAmount(@RequestParam UUID id){
        return new Response(borrowedBookService.fineAmount(id));
    }

}
