package com.example.demo.LibraryAPI.services;

import com.example.demo.LibraryAPI.models.Book;
import com.example.demo.LibraryAPI.models.BorrowedBook;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.models.UserUpdated;
import com.example.demo.LibraryAPI.repository.BookRepo;
import com.example.demo.LibraryAPI.repository.BorrowedBookRepo;
import com.example.demo.LibraryAPI.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Service
public class BorrowedBookService {

    @Autowired
    private BorrowedBookRepo borrowedBookRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookRepo bookRepo;

    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepo.findAll();
    }

    public String borrowBook(UUID userId, UUID bookId) {
        UserUpdated user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book Not Available"));
        BorrowedBook borrowedBook = new BorrowedBook();
        if (book.getCopies() > 0 ){
            borrowedBook.setUserId(userId);
            borrowedBook.setBookId(bookId);
            borrowedBook.setBorrowedDate(convertToIst(new Date()));
            Calendar returnCalendar = Calendar.getInstance();
            returnCalendar.setTime(convertToIst(borrowedBook.getBorrowedDate()));
            int borrowDays = user.getSubscription().getBorrowDays();
            returnCalendar.add(Calendar.DATE, borrowDays);
            borrowedBook.setReturnDate(returnCalendar.getTime());
            book.setCopies((book.getCopies())-1);
            borrowedBookRepo.save(borrowedBook);
            bookRepo.save(book);
            return "Book added to your borrow list";
        }
        else {
            return "SORRY! Book Is Not Available Currently";
        }
    }

    public Date convertToIst(Date localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(localDate);
        calendar.add(Calendar.HOUR_OF_DAY,5);
        calendar.add(Calendar.MINUTE, 30);
        return calendar.getTime();
    }

    public Response getBorrowedBookbyId(UUID id) {
        return new Response(borrowedBookRepo.findById(id));
    }


    public String   updateDate(UUID id) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Optional<BorrowedBook> optborrowedBook = borrowedBookRepo.findById(id);
        if (optborrowedBook.isPresent()){
            BorrowedBook borrowedBook = optborrowedBook.get();
            if(borrowedBook.getSubmissionDate() == null){
                borrowedBook.setSubmissionDate(convertToIst(new Date()));
                borrowedBookRepo.save(borrowedBook);
                Book book = bookRepo.findById(borrowedBook.getBookId()).orElseThrow(()->new RuntimeException("Book Doesn't Exist"));
                book.setCopies((book.getCopies())+1);
            }
            return "Submission Date Updated";
        }
        else {
            return "Book was already submitted.";
        }
    }

    public String fineAmount(UUID id) {
        BorrowedBook borrowedBook = borrowedBookRepo.findById(id).get();
        UserUpdated user = userRepo.findById(borrowedBook.getUserId()).get();
        long daysDifference = Duration.between(borrowedBook.getReturnDate().toInstant(), borrowedBook.getSubmissionDate().toInstant()).toDays();
        long fineAmt = 0;
        if (daysDifference > 0) {
            fineAmt = (user.getSubscription().getFineAmount()) * (daysDifference);
            user.setFine(fineAmt);
        }
        userRepo.save(user);
        return "Fine Amount is " + fineAmt;
    }

}
