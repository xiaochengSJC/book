package com.example.book.service;

import com.example.book.model.Book;
import com.example.book.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getMenuByRoleId(String roleId);

    List<Menu> getMenuById(int id);
}
