package com.example.demo.LibraryAPI.controller;
import com.example.demo.LibraryAPI.models.Response;
import com.example.demo.LibraryAPI.models.UserUpdated;
import com.example.demo.LibraryAPI.repository.UserRepo;
import com.example.demo.LibraryAPI.services.UserService;
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

    @GetMapping("/get/user")
    public List<UserUpdated> getAllUsers() {
        return userService.getAllUsers();
    }


    @PostMapping("/add/user")
    public String addUser(@RequestBody UserUpdated user) throws Exception {
        return userService.addUser(user);
    }

    @PutMapping("/update/user/{id}")
    public ResponseEntity<UserUpdated> updateUserDetails(@PathVariable UUID id, @RequestBody UserUpdated updatedUser) {
        UserUpdated updated = userService.updateUserDetails(id, updatedUser);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/update/password/{id}")
    public Response updateUserPass(@PathVariable UUID id,@RequestParam("old pass") String oldpass, @RequestParam("new password")String newPass) {
        return new Response(userService.updateUserPass(id,oldpass,newPass));
    }

    @DeleteMapping("/delete/user/{id}")
    public void delUserbyId(@PathVariable UUID id) {
        userService.delUserbyId(id);
    }

    @DeleteMapping("/delete/all")
    public String delAllUsers() {
        return userService.delAllUsers();
    }

    @GetMapping("/name/{fname}")
    List<UserUpdated> findByName(@PathVariable String fname) {
        /*Query query = new Query();
        query.addCriteria(Criteria.where("fname").is(fname));
        return mongoTemplate.find(query, UserUpdated.class);*/
        return userRepo.findByName(fname);
    }

    @GetMapping("/sub/{subscription}")
    List<UserUpdated> findBySub(@PathVariable String subscription){
        /*Query query = new Query();
        query.addCriteria(Criteria.where("subscription").is(subscription));
        return mongoTemplate.find(query, UserUpdated.class);*/
        return userRepo.findBySub(subscription);
    }

    @GetMapping("/get/user/borrowed")
    public Response getBooksByUserId(@RequestParam UUID userId){
        return new Response(userService.getBooksByUserId(userId));
    }

}
