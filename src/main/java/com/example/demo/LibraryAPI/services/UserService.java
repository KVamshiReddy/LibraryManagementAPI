package com.example.demo.LibraryAPI.services;
import com.example.demo.LibraryAPI.models.Book;
import com.example.demo.LibraryAPI.models.BorrowedBook;
import com.example.demo.LibraryAPI.models.UserUpdated;
import com.example.demo.LibraryAPI.repository.BookRepo;
import com.example.demo.LibraryAPI.repository.BorrowedBookRepo;
import com.example.demo.LibraryAPI.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BorrowedBookRepo borrowedBookRepo;
    @Autowired
    private BookRepo bookRepo;

    public List<UserUpdated> getAllUsers() {
        return userRepo.findAll();
    }

    public String addUser(UserUpdated user) throws Exception {
        List<UserUpdated> existingUsers = userRepo.findByMobileAndEmail(user.getMobile(), user.getEmail());
        if (!existingUsers.isEmpty()) {
            throw new Exception("User Already Exists");
        }
        else {
            userRepo.save(user);
            return "User Created Successfully";
        }
    }

    public UserUpdated updateUserDetails(UUID id, UserUpdated updatedUser) {
        if (id == null || updatedUser == null){
            throw new IllegalArgumentException("Invalid input parameters");
        }
        return userRepo.findById(id)
                .map(existingUser -> {
                    existingUser.setFname(updatedUser.getFname());
                    existingUser.setLname(updatedUser.getLname());
                    existingUser.setMobile(updatedUser.getMobile());
                    existingUser.setSubscription(updatedUser.getSubscription());
                    existingUser.setEmail(updatedUser.getEmail());
                    return userRepo.save(existingUser);
                }).orElseThrow(()->new NoSuchElementException(id + "User Not Found"));
    }

    public String updateUserPass(UUID id,String oldpass, String newPass) {
        Optional<UserUpdated> optUser = userRepo.findById(id);
        if(optUser.isPresent()){
            UserUpdated user = optUser.get();
            if(user.getPassword().equals(oldpass)){
                user.setPassword(newPass);
                userRepo.save(user);
                return "Password Updated Successfully";
            }
            else {
                return "This is not your current password! PLEASE CHECK";
            }
        }
        else {
            return "!USER NOT FOUND!";
        }
    }

    public String delUserbyId(UUID id, String password) {
        Optional<UserUpdated> optUser = userRepo.findById(id);
        if (optUser.isPresent()){
            String pass = optUser.get().getPassword();
            if (pass.equals(password)){
                userRepo.deleteById(id);
                return "User Deleted Successfully";
            }
            else {
                return "Passwords Don't Match. PLEASE CHECK!!";
            }
        }
        else {
            return "User Not Found";
        }
    }

    public String delAllUsers() {
        userRepo.deleteAll();
        return "All users deleted succesfully";
    }

    public Map<String, List<String>> getBooksByUserId(UUID userId) {
        Map<String, List<String>> result = new HashMap<>();
        List<String> books = new ArrayList<>();
        Optional<UserUpdated> optUser = userRepo.findById(userId);
        if(optUser.isPresent()){
            UserUpdated user = optUser.get();
            List<BorrowedBook> borrowedBook = borrowedBookRepo.findByUserId(userId);
            for(BorrowedBook borrow : borrowedBook){
                Optional<Book> bookOptional = bookRepo.findById(borrow.getBookId());
                if (bookOptional.isPresent()){
                    books.add(bookOptional.get().getName());
                }
            }
            result.put(user.getFname(),books);
        }
        return result;
    }
}
