package com.example.demo.LibraryAPI.controller;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.models.UserUpdated;
import com.example.demo.LibraryAPI.repository.UserRepo;
import com.example.demo.LibraryAPI.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Operation(summary = "Get list of all users")
    @GetMapping("/get/user")
    public List<UserUpdated> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Create a new user / User Registration")
    @PostMapping("/add/user")
    public String addUser(@RequestBody UserUpdated user) throws Exception {
        return userService.addUser(user);
    }

    @Operation(summary = "Update details of a user by ID")
    @PutMapping("/update/user/{id}")
    public Response updateUserDetails(@PathVariable UUID id, @RequestBody UserUpdated updatedUser) {
        return new Response(userService.updateUserDetails(id, updatedUser));
    }
    @Operation(summary = "Update a user's password only if their old password is correctly entered")
    @PutMapping("/update/password/{id}")
    public Response updateUserPass(@PathVariable UUID id,@RequestParam("old pass") String oldpass, @RequestParam("new password")String newPass) {
        return new Response(userService.updateUserPass(id,oldpass,newPass));
    }

    @Operation(summary = "Delete a user only if he enters his password")
    @DeleteMapping("/delete/user/{id}")
    public void delUserById(@PathVariable UUID id, @RequestParam("password") String password) {
        userService.delUserbyId(id, password);
    }

    @Operation(summary = "Deletes all users")
    @DeleteMapping("/delete/all")
    public String delAllUsers() {
        return userService.delAllUsers();
    }

    @Operation(summary = "Get user by his name")
    @GetMapping("/name/{fname}")
    List<UserUpdated> findByName(@PathVariable String fname) {
        /*Query query = new Query();
        query.addCriteria(Criteria.where("fname").is(fname));
        return mongoTemplate.find(query, UserUpdated.class);*/
        return userRepo.findByName(fname);
    }

    @Operation(summary = "Get users by their subscription")
    @GetMapping("/sub/{subscription}")
    List<UserUpdated> findBySub(@PathVariable String subscription){
        /*Query query = new Query();
        query.addCriteria(Criteria.where("subscription").is(subscription));
        return mongoTemplate.find(query, UserUpdated.class);*/
        return userRepo.findBySub(subscription);
    }

    @Operation(summary = "Get list of all books borrowed by the user")
    @GetMapping("/get/user/borrowed")
    public Response getBooksByUserId(@RequestParam UUID userId){
        return new Response(userService.getBooksByUserId(userId));
    }

}
