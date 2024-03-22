package org.example;

import java.util.Date;

public class Book {


    //attributes
    private int bookId;
    private String title;
    private String author;
    private boolean available;
    private int borrowedBy;
    private Date borrowedDate;

    //default constructor
    public Book () {

    }
    //constructor

    public Book(int bookId, String title, String author, boolean available, int borrowedBy, Date borrowedDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = available;
        this.borrowedBy = borrowedBy;
        this.borrowedDate = borrowedDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    @Override
    public String toString() {
        return title + " by " + author +(available ? "Available" : "Borrowed");
    }
}





























