package com.example.book.service.impl;

import com.example.book.dao.MenuMapper;
import com.example.book.model.Menu;
import com.example.book.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuByRoleId(String roleId) {
        return menuMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> getMenuById(int id) {
        return menuMapper.getMenuById(id);
    }
}
