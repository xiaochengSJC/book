package com.example.book.model;

public class Book {

    private int id; //图书id

    private String typeId;//书籍种类id

    private String bookName;//书名

    private String card; //书号

    private String authName;//作者

    private String bookPic;//封面

    private String description;//描述

    private String press;//出版社

    private String status;//书的状态

    private String createTime;//添加时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", bookName='" + bookName + '\'' +
                ", card='" + card + '\'' +
                ", authName='" + authName + '\'' +
                ", bookPic='" + bookPic + '\'' +
                ", description='" + description + '\'' +
                ", press='" + press + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
