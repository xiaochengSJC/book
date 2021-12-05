package com.example.book.model;

public class Borrow {

    private int id; //图书id

    private String bookId;//书籍id

    private String userId;//读者id

    private String borrowTime; //借书日期

    private String backTime;//计划还书日期

    private String state;//是否归还

    private String realTime;//还书日期

    private String status;//是否超期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", userId='" + userId + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                ", backTime='" + backTime + '\'' +
                ", state='" + state + '\'' +
                ", realTime='" + realTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
