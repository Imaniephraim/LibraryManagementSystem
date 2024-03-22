package org.example;

import java.util.Date;

public class BorrowingHistory {
    private int historyId;
    private int bookId;
    private int userId;
    private Date borrowedDate;
    private Date returnedDate;

    public BorrowingHistory() {

    }

    public BorrowingHistory(int historyId, int bookId, int userId, Date borrowedDate, Date returnedDate) {
        this.historyId = historyId;
        this.bookId = bookId;
        this.userId = userId;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
}



























