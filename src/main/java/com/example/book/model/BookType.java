package com.example.book.model;

public class BookType {

    private int id ;//种类id
    private String typeName;//种类名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "BookType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
