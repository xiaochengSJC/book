package com.example.book.service;

import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book> getBookInfo(String typeId, String bookName,String pagesize,String begNum,String authName,String card,String press);

    int getBookCount(String typeId, String bookName,String authName,String card,String press);

    Book getBook(String id );

    int addBook(Book book);

    int updateBook(Book book);

    int deleteBook(String id);


    List<BookType> getBookType(String typeName,String pagesize,String begNum);

    BookType getBookTypeById(String id );

    int addBookType(BookType bookType);

    int updateBookType(BookType bookType);

    int deleteBookType(String id);

    //添加借书记录
    int addBorrowRecord(Borrow borrow);

    //查看读者借书记录
    List<Map<String,Object>> getBorrowNum(String userId);

    //管理员查看借书还书记录
    List<Map<String,Object>> getRecord(String typeId, String card, String borrowTime, String bookName, String realTime,String pagesize,String begNum);

    //读者查看自己的借书记录
    List<Map<String,Object>> getBorrowList(String typeId, String bookName, String borrowTime,String userName,String pagesize,String begNum);

    int getRecordCount(String typeId, String bookName, String borrowTime, String userName, String realTime);

    int getBorrowCount(String typeId, String bookName, String borrowTime,String userName);

    Borrow getBorrowById(@Param("id")String id);

    int updateBorrowRecord(Borrow borrow);
}
