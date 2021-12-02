package com.example.book.service.impl;

import com.example.book.dao.BookMapper;
import com.example.book.dao.BookTypeMapper;
import com.example.book.dao.BorrowMapper;
import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookTypeMapper bookTypeMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public List<Book> getBookInfo(String typeId, String bookName) {
        return bookMapper.getBookInfo(typeId,bookName);
    }

    @Override
    public Book getBook(String id) {
        return bookMapper.getBook(id);
    }


    @Override
    public int addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public int deleteBook(String id) {
        return bookMapper.deleteBook(id);
    }

    @Override
    public List<BookType> getBookType(String typeName) {
        return bookTypeMapper.getBookType(typeName);
    }

    @Override
    public BookType getBookTypeById(String id) {
        return bookTypeMapper.getBookTypeById(id);
    }

    @Override
    public int addBookType(BookType bookType) {
        return bookTypeMapper.addBookType(bookType);
    }

    @Override
    public int updateBookType(BookType bookType) {
        return bookTypeMapper.updateBookType(bookType);
    }

    @Override
    public int deleteBookType(String id) {
        return bookTypeMapper.deleteBookType(id);
    }

    @Override
    public int addBorrowRecord(Borrow borrow) {
        return borrowMapper.addBorrowRecord(borrow);
    }

    @Override
    public List<Map<String, Object>> getBorrowNum(String userId) {
        return borrowMapper.getBorrowNum(userId);
    }

    @Override
    public List<Map<String, Object>> getRecord(String typeId, String bookName, String borrowTime, String userName, String realTime) {
        return borrowMapper.getBorrowRecord(typeId,bookName,borrowTime,userName,realTime);
    }


}
