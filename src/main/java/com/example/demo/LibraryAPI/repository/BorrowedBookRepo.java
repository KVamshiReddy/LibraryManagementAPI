package com.example.demo.LibraryAPI.repository;

import com.example.demo.LibraryAPI.models.BorrowedBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowedBookRepo extends MongoRepository<BorrowedBook, UUID> {

    @Query("{ 'userId' : ?0 }")
    List<BorrowedBook> findByUserId(UUID userId);

    @Query("{ 'bookId' : ?0 }")
    List<BorrowedBook> findByBookId(UUID bookId);

}
