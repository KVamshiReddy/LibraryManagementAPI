package com.example.demo.LibraryAPI.services;

import com.example.demo.LibraryAPI.models.Book;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public String addBoooks(Book book) {
        bookRepo.save(book);
        return "Book Added Successfully";
    }

    public void delbookbyId(UUID id) {
        bookRepo.deleteById(id);
        System.out.println("Book Deleted Succesfully");
    }

    public Book updateBook(UUID id, Book updatedBook) {
        return bookRepo.findById(id)
                .map(existingBook -> {
                    existingBook.setName(updatedBook.getName());
                    existingBook.setAuthor(updatedBook.getAuthor());
                    existingBook.setCategory(updatedBook.getCategory());
                    existingBook.setDescription(updatedBook.getDescription());
                    return bookRepo.save(existingBook);
                }).orElse(null);
    }


    public Response bookdesc(UUID id) {
        return new Response(bookRepo.findById(id));
    }
}
