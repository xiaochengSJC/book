package com.example.book.model;

import java.util.List;

public class Menu {

    private int id;//菜单id

    private String menuPath;//菜单名称

    private String menuName;//菜单名称

    private int parentId;//父级id

    private int roleId;//角色id

    private List list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuPath='" + menuPath + '\'' +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", roleId=" + roleId +
                ", list=" + list +
                '}';
    }
}
