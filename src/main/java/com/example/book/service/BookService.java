package com.example.book.service;

import com.example.book.model.Book;
import com.example.book.model.BookType;


import java.util.List;

public interface BookService {

    List<Book> getBookInfo(String typeId, String bookName);

    Book getBook(String id );

    int addBook(Book book);

    int updateBook(Book book);

    int deleteBook(String id);


    List<BookType> getBookType(String typeName);

    BookType getBookTypeById(String id );

    int addBookType(BookType bookType);

    int updateBookType(BookType bookType);

    int deleteBookType(String id);
}
