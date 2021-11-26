package com.example.book.service;

import com.example.book.model.User;

import java.util.List;

public interface UserService {

    List<User> selectUserInfo(String keyword,String pageSize,String pageNum);

    User getUserInfoById(String id);

    int deleteUser(String id);

    int totalUser(String keyword,String pageSize,String pageNum);

    User selectInfoByName(String userName);

    int addUser(User user);

    int editUser(User user);
}
