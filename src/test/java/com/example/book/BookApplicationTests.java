package com.example.book;

import com.example.book.dao.BookMapper;
import com.example.book.dao.UserMapper;
import com.example.book.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
class BookApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void getUserById(){

        System.out.println(userMapper.selectInfoByName("shang"));
    }

    @Test
    public void getBookById(){

        System.out.println(bookMapper.getBookInfo("1",""));
    }

    @Test
    public void addBook(){

    }

}
