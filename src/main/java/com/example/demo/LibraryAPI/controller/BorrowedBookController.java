package com.example.demo.LibraryAPI.controller;

import com.example.demo.LibraryAPI.models.BorrowedBook;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.services.BorrowedBookService;
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

    @GetMapping("/books")
    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookService.getAllBorrowedBooks();
    }

    @GetMapping("/book/{id}")
    public Response getBorrowedBookbyId(@PathVariable UUID id) {
        return borrowedBookService.getBorrowedBookbyId(id);
    }

    @PostMapping("/")
    public String borrowBook(@RequestParam UUID userId, @RequestParam UUID bookId) {
        return borrowedBookService.borrowBook(userId, bookId);
    }

    @PutMapping("/update")
    public Response updateDate(@RequestParam UUID id) throws ParseException {
        return new Response(borrowedBookService.updateDate(id));
    }

    @PutMapping("/fine/amount")
    public Response fineAmount(@RequestParam UUID id){
        return new Response(borrowedBookService.fineAmount(id));
    }



}
