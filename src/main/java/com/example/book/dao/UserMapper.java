package com.example.book.dao;

import com.example.book.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Mapper
public interface UserMapper {


    @Select("<script>" +
            "select  id as id,user_name as userName,name as name ,password as password,email as email,phone as phone,lend_num as lendNum,max_num as maxNum from t_user " +
            "where 1=1 and role_id = '1'" +
            "<if test='keyword != null and keyword != \"\"'>"+
            " and ( user_name like concat('%',#{keyword},'%') or name like concat('%',#{keyword},'%'))"  +
            "</if>" +
            "</script>")
    List<User> selectUserInfo(@Param("keyword")String keyword,@Param("pageSize")int pageSize,@Param("pageNum")int pageNum);

    @Select("<script>" +
            "select count(1) from t_user " +
            "where 1=1 and role_id = '1'" +
            "<if test='keyword != null and keyword != \"\"'>"+
            " and (user_name like concat('%',#{keyword},'%') or name like concat('%',#{keyword},'%'))"  +
            "</if>" +
            "</script>")
    int totalUser(@Param("keyword")String keyword,@Param("pageSize")int pageSize,@Param("pageNum")int pageNum);

    @Select("select id as id,user_name as userName,name as name ,password as password,email as email,phone as phone,role_id as roleId,create_time as createTime from t_user where user_name=#{userName}")
    User selectInfoByName(@Param("userName")String userName);

    @Insert({"INSERT into t_user (",
               "USER_NAME,",
               "NAME,",
              "PASSWORD,",
              "EMAIL,",
              "ROLE_ID,",
              "CREATE_TIME,",
              "LEND_NUM,",
              "MAX_NUM,",
              "PHONE)",
              "VALUES(",
              "#{userName},",
              "#{name},",
              "#{password},",
              "#{email},",
             "#{roleId},",
             "#{createTime},",
             "#{lendNum},",
             "#{maxNum},",
              "#{phone})"
      })
    int addUser(User user);

    @Select("select id as id,user_name as userName,name as name ,password as password,email as email,phone as phone,lend_num as lendNum,max_num as maxNum from t_user where id=#{id}")
    User selectInfoById(@Param("id")String id);

    @Update({"UPDATE t_user SET ",
            "USER_NAME = #{userName},",
            "NAME = #{name},",
            "PASSWORD = #{password},",
            "EMAIL = #{email},",
            "PHONE = #{phone},",
            "LEND_NUM = #{lendNum},",
            "MAX_NUM = #{maxNum},",
            "CREATE_TIME = #{createTime}",
            "WHERE ID = #{id}"
    })
    int editUser(User user);

     @Delete("DELETE FROM T_USER WHERE ID = #{id}")
    int deleteUser(String id);
}
