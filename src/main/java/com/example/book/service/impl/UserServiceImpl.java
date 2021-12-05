package com.example.book.service.impl;

import com.example.book.dao.UserMapper;
import com.example.book.model.User;
import com.example.book.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userDao;

    @Override
    public List<User> selectUserInfo(String keyword,String pagesize,String begNum) {
        int pageNum = Integer.parseInt(begNum);
        int pageSize = Integer.parseInt(pagesize);
        PageHelper.startPage(pageNum,pageSize);
        List<User> list = userDao.selectUserInfo(keyword);
        return list;
    }

    @Override
    public User getUserInfoById(String id) {
        return userDao.selectInfoById(id);
    }

    @Override
    public int deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    @Override
    public int totalUser(String keyword,String pagesize,String begNum) {
        int pageNum = Integer.parseInt(begNum);
        int pageSize = Integer.parseInt(pagesize);
        return userDao.totalUser(keyword);
    }

    @Override
    public User selectInfoByName(String userName) {
        return userDao.selectInfoByName(userName);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int editUser(User user) {
        return userDao.editUser(user);
    }

}
