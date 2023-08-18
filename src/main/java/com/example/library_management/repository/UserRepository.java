package com.example.library_management.repository;

import com.example.library_management.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM users")
    public List<User> findAllUsers();

    @Select("SELECT * FROM users WHERE user_id = #{user_id}")
    public User findUserById();

    @Insert("""
        INSERT_INTO users (user_id, username, email, password, total_published_books, role)
        VALUES (#{user_id}, #{username}, #{email}, #{password}, #{total_published_books}, #{role})
    """)
    public int insertNewUser(User user);

    @Update(""" 
            UPDATE users
            SET username = #{username}, email = #{email}, password = #{password}, total_published_books = #{total_published_books}
            WHERE user_id = #{user_id}
            """)
    int updateUser (User user);

    @Delete("""
            DELETE FROM users
            WHERE user_id = #{user_id}
            """)
    int deleteUserByUserId(Integer user_id);


}
