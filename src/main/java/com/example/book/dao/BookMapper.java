package com.example.book.dao;

import com.example.book.model.Book;
import com.example.book.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("<script>" +
            "select id as id,card as card, auth_name as authName, type_id as typeId,book_name as bookName,book_pic as bookPic, description as  description,press as press,status as status from t_book " +
            "where 1 = 1" +
            "<if test='typeId != null and typeId != \"\"'> "+
            " and type_id =#{typeId}" +
            "</if>" +
            "<if test='bookName != null and bookName != \"\"'>"+
            " and book_name like concat('%',#{bookName},'%')" +
            "</if>" +
            "<if test='card != null and card != \"\"'>"+
            " and card like concat('%',#{card},'%')" +
            "</if>" +
            "<if test='authName != null and authName != \"\"'>"+
            " and auth_name like concat('%',#{authName},'%')" +
            "</if>" +
            "<if test='press != null and press != \"\"'>"+
            " and press like concat('%',#{press},'%')" +
            "</if>" +
            "</script>")
    List<Book> getBookInfo(@Param("typeId") String typeId, @Param("bookName")String bookName,@Param("authName")String authName,@Param("card")String card,@Param("press")String press);

    @Select("<script>" +
            "select count(1) from t_book " +
            "where 1 = 1" +
            "<if test='typeId != null and typeId != \"\"'> "+
            " and type_id =#{typeId}" +
            "</if>" +
            "<if test='bookName != null and bookName != \"\"'>"+
            " and book_name like concat('%',#{bookName},'%')" +
            "</if>" +
            "<if test='card != null and card != \"\"'>"+
            " and card like concat('%',#{card},'%')" +
            "</if>" +
            "<if test='authName != null and authName != \"\"'>"+
            " and auth_name like concat('%',#{authName},'%')" +
            "</if>" +
            "<if test='press != null and press != \"\"'>"+
            " and press like concat('%',#{press},'%')" +
            "</if>" +
            "</script>")
    int getBookCount(@Param("typeId") String typeId, @Param("bookName")String bookName,@Param("authName")String authName,@Param("card")String card,@Param("press")String press);

    @Select("select id as id,card as card, auth_name as authName, type_id as typeId,book_name as bookName,book_pic as bookPic, description as  description,press as press,status as status from t_book " +
            "where 1 = 1 and id=#{id}")
    Book getBook(@Param("id")String id);


    @Insert({"INSERT into t_book (",
            "CARD,",
            "AUTH_NAME,",
            "TYPE_ID,",
            "BOOK_NAME,",
            "BOOK_PIC,",
            "DESCRIPTION,",
            "PRESS,",
            "STATUS,",
            "CREATE_TIME )",
            "VALUES (",
            "#{card},",
            "#{authName},",
            "#{typeId},",
            "#{bookName},",
            "#{bookPic},",
            "#{description},",
            "#{press},",
            "#{status},",
            "#{createTime})"
    })
    int addBook(Book book);

    @Update({
        "UPDATE T_BOOK " ,
        "SET" ,
        "CARD =#{card},",
        "AUTH_NAME =#{authName},",
        "TYPE_ID =#{typeId},",
        "BOOK_NAME =#{bookName},",
        "BOOK_PIC =#{bookPic},",
        "DESCRIPTION =#{description},",
        "PRESS =#{press},",
        "STATUS =#{status},",
        "CREATE_TIME =#{createTime} " +
        "where id = #{id}"
    })
    int updateBook(Book book);

    @Delete("DELETE FROM T_BOOK WHERE ID = #{id}")
    int deleteBook(@Param("id")String id);

}
