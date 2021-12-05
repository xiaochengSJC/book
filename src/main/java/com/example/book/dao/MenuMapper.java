package com.example.book.dao;

import com.example.book.model.Book;
import com.example.book.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface MenuMapper {

    @Select("<script>" +
            "select id as id,menu_name as menuName,role_id as roleId from t_menu " +
            "where 1 = 1 and parent_id = '0'" +
            "<if test='roleId != null and roleId != \"\"'>"+
            " and role_id =#{roleId}" +
            "</if>" +
            "order by id" +
            "</script>")
    List<Menu> getMenuByRoleId(@Param("roleId") String roleId);

    @Select("select id as id,menu_path as menuPath,menu_name as menuName,role_id as roleId from t_menu " +
            "where 1 = 1 and parent_id = #{id}")
    List<Menu> getMenuById(@Param("id") int id);
}
