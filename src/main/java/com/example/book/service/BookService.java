package com.example.book.service;

import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

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

    //添加借书记录
    int addBorrowRecord(Borrow borrow);

    //查看读者借书记录
    List<Map<String,Object>> getBorrowNum(String userId);

    //查看借书记录
    List<Map<String,Object>> getRecord(String typeId, String card, String borrowTime, String bookName, String realTime);
}
