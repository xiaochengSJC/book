package com.example.book.dao;

import com.example.book.model.Book;
import com.example.book.model.BookType;
import com.example.book.model.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface BorrowMapper {

    //管理员查看所有的借书还书记录
    @Select("<script>" +
            "select a.id as id,a.user_id as userId,a.book_id as bookId,b.user_name as userName,c.card as card,c.book_name as bookName, c.auth_name as authName, c.type_id as typeId,a.borrow_time as borrowTime, a.back_time as backTime, a.real_time as realTime,a.status as status,a.state as state " +
            "from t_borrowrecord a left join t_user b on a.user_id = b.id " +
            "left join t_book c on a.book_id = c.id   " +
            "where 1 = 1" +
            "<if test='typeId != null and typeId != \"\"'> "+
            " and c.type_id =#{typeId}" +
            "</if>" +
            "<if test='bookName != null and bookName != \"\"'>"+
            " and c.book_name like concat('%',#{bookName},'%')" +
            "</if>" +
            "<if test='userName != null and userName != \"\"'>"+
            " and c.user_name like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test='borrowTime != null and borrowTime != \"\"'> "+
            " and a.borrow_time =#{borrowTime}" +
            "</if>" +
            "<if test='realTime != null and realTime != \"\"'> "+
            " and a.real_time =#{realTime}" +
            "</if>" +
            "</script>")
    List<Map<String,Object>> getBorrowRecord(@Param("typeId") String typeId, @Param("bookName")String bookName,@Param("borrowTime") String borrowTime, @Param("userName")String userName,@Param("realTime")String realTime);

    //管理员查看所有的借书还书记录
    @Select("<script>" +
            "select count(1) " +
            "where 1 = 1" +
            "<if test='typeId != null and typeId != \"\"'> "+
            " and c.type_id =#{typeId}" +
            "</if>" +
            "<if test='bookName != null and bookName != \"\"'>"+
            " and c.book_name like concat('%',#{bookName},'%')" +
            "</if>" +
            "<if test='userName != null and userName != \"\"'>"+
            " and c.user_name like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test='borrowTime != null and borrowTime != \"\"'> "+
            " and a.borrow_time =#{borrowTime}" +
            "</if>" +
            "<if test='realTime != null and realTime != \"\"'> "+
            " and a.real_time =#{realTime}" +
            "</if>" +
            "</script>")
    int getRecordCount(@Param("typeId") String typeId, @Param("bookName")String bookName,@Param("borrowTime") String borrowTime, @Param("userName")String userName,@Param("realTime")String realTime);

    //读者查看自己的借书记录
    @Select("<script>" +
            "select a.id as id,a.user_id as userId,a.book_id as bookId,b.user_name as userName,c.card as card,c.book_name as bookName, c.auth_name as authName, c.type_id as typeId,a.borrow_time as borrowTime, a.back_time as backTime, a.real_time as realTime,a.status as status,a.state as state " +
            "from t_borrowrecord a left join t_user b on a.user_id = b.id " +
            "left join t_book c on a.book_id = c.id   " +
            "where 1 = 1 and a.real_time is null and b.user_name =#{userName} " +
            "<if test='typeId != null and typeId != \"\"'> "+
            " and c.type_id =#{typeId}" +
            "</if>" +
            "<if test='bookName != null and bookName != \"\"'>"+
            " and c.book_name like concat('%',#{bookName},'%')" +
            "</if>" +
            "<if test='borrowTime != null and borrowTime != \"\"'> "+
            " and a.borrow_time =#{borrowTime}" +
            "</if>" +
            "</script>")
    List<Map<String,Object>> getBorrowList(@Param("typeId") String typeId, @Param("bookName")String bookName,@Param("borrowTime") String borrowTime, @Param("userName")String userName);

    //读者查看自己的借书记录
    @Select("<script>" +
            "select count(1) from t_borrowrecord" +
            "where 1 = 1 and a.real_time is null and c.user_name =#{userName} " +
            "<if test='typeId != null and typeId != \"\"'> "+
            " and c.type_id =#{typeId}" +
            "</if>" +
            "<if test='bookName != null and bookName != \"\"'>"+
            " and c.book_name like concat('%',#{bookName},'%')" +
            "</if>" +
            "<if test='borrowTime != null and borrowTime != \"\"'> "+
            " and a.borrow_time =#{borrowTime}" +
            "</if>" +
            "</script>")
    int getBorrowCount(@Param("typeId") String typeId, @Param("bookName")String bookName,@Param("borrowTime") String borrowTime, @Param("userName")String userName);


    //统计读者的借书数量
    @Select("select user_id as userId,count(1) as num from t_borrowrecord " +
            "where 1 = 1 and user_id=#{userId} group by user_id")
    List<Map<String,Object>> getBorrowNum(@Param("userId")String userId);

    //统计读者的借书数量
    @Select("select id as id,back_time as backTime,book_id as bookId,user_id as userId from t_borrowrecord " +
            "where id =#{id}")
    Borrow getBorrowById(@Param("id")String id);

    @Insert({"INSERT into t_borrowrecord (",
            "USER_ID,",
            "BOOK_ID,",
            "BORROW_TIME,",
            "BACK_TIME,",
            "STATUS )",
            "VALUES (",
            "#{userId},",
            "#{bookId},",
            "#{borrowTime},",
            "#{backTime},",
            "#{status})"
    })
    int addBorrowRecord(Borrow borrow);

    @Update({
            "UPDATE t_borrowrecord " ,
            "SET " +
            "REAL_TIME =#{realTime}," +
            "STATUS =#{status}" +
            " where id = #{id}"
    })
    int updateBorrowRecord(Borrow borrow);
}
