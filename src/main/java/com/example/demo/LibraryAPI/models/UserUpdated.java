package com.example.demo.LibraryAPI.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document
public class UserUpdated{
    @Id
    private UUID id;
    private String fname;
    private String lname;
    private String mobile;
    private String email;
    private String password;
    private Subscription subscription;
    private long fine;

    public UserUpdated() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public enum Subscription {
        BRONZE(3, 3), SILVER(5,2), GOLD(7,1);

        private int borrowDays;
        private int fineAmount;

        Subscription(int borrowDays, int fineAmount) {
            this.borrowDays = borrowDays;
            this.fineAmount = fineAmount;
        }

    public int getBorrowDays() {
            return borrowDays;
        }
    public int getFineAmount() {
            return fineAmount;
    }

    }
    public LocalDate returnDate() {
        return LocalDate.now().plusDays(subscription.getBorrowDays());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public long getFine() {
        return fine;
    }

    public void setFine(long fine) {
        this.fine = fine;
    }
}


