package com.example.book.service.impl;

import com.example.book.dao.BookMapper;
import com.example.book.dao.BookTypeMapper;
import com.example.book.dao.BorrowMapper;
import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import com.example.book.service.BookService;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.annotations.Param;
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
    public List<Book> getBookInfo(String typeId, String bookName, String pagesize, String begNum, String authName, String card, String press) {
        int pageNum = Integer.parseInt(begNum);
        int pageSize = Integer.parseInt(pagesize);
        PageHelper.startPage(pageNum,pageSize);
        return bookMapper.getBookInfo(typeId,bookName,authName,card,press);
    }

    @Override
    public int getBookCount(String typeId, String bookName, String authName, String card, String press) {
        return bookMapper.getBookCount(typeId,bookName,authName,card,press);
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
    public List<BookType> getBookType(String typeName,String pagesize,String begNum) {
        int pageNum = 1;
        int pageSize = 9999;
        if(!"".equals(begNum)){
            pageNum = Integer.parseInt(begNum);
        }
        if(!"".equals(pagesize)){
            pageSize = Integer.parseInt(pagesize);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<BookType> list = bookTypeMapper.getBookType(typeName);
        return list;
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
    public List<Map<String, Object>> getRecord(String typeId, String bookName, String borrowTime, String userName, String realTime,String pagesize,String begNum) {
        int pageNum = Integer.parseInt(begNum);
        int pageSize = Integer.parseInt(pagesize);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, Object>> list = borrowMapper.getBorrowRecord(typeId,bookName,borrowTime,userName,realTime);
        return list;
    }
    @Override
    public int getRecordCount(String typeId, String bookName, String borrowTime, String userName, String realTime) {
        return borrowMapper.getRecordCount(typeId,bookName,borrowTime,userName,realTime);
    }

    @Override
    public List<Map<String, Object>> getBorrowList(String typeId, String bookName, String borrowTime, String userName,String pagesize,String begNum) {
        int pageNum = Integer.parseInt(begNum);
        int pageSize = Integer.parseInt(pagesize);
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, Object>> list = borrowMapper.getBorrowList(typeId,bookName,borrowTime,userName);
        return list;
    }

    @Override
    public int getBorrowCount(String typeId, String bookName, String borrowTime, String userName) {
        return borrowMapper.getBorrowCount(typeId,bookName,borrowTime,userName);
    }

    @Override
    public Borrow getBorrowById(String id) {
        return borrowMapper.getBorrowById(id);
    }

    @Override
    public int updateBorrowRecord(Borrow borrow) {
        return borrowMapper.updateBorrowRecord(borrow);
    }


}
