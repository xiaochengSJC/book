package com.example.book.dao;


import com.example.book.model.Book;
import com.example.book.model.BookType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookTypeMapper {

    @Select("<script>" +
            "select id as id,type_name as typeName from t_bookType " +
            "where 1 = 1" +
            "<if test='typeName != null and typeName != \"\"'> "+
            " and type_name like concat ('%',#{typeName},'%')" +
            "</if>" +
            "</script>")
    List<BookType> getBookType(@Param("typeName") String typeName);

    @Select("select id as id,type_name as typeName from t_bookType " +
            "where 1 = 1 and id=#{id}")
    BookType getBookTypeById(@Param("id")String id);


    @Insert({"INSERT into t_bookType (",
            "TYPE_NAME )",
            "VALUES (",
            "#{typeName})"
    })
    int addBookType(BookType bookType);

    @Update({
            "UPDATE t_bookType " ,
            "SET TYPE_NAME =#{typeName} where id = #{id}"
    })
    int updateBookType(BookType bookType);

    @Delete("DELETE FROM t_BookType WHERE ID = #{id}")
    int deleteBookType(@Param("id")String id);
}
