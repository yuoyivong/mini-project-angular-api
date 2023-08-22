package com.example.library_management.repository;

import com.example.library_management.model.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Mapper
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Select("SELECT * FROM users")
//    public List<User> findAllUsers();
//
//    @Select("SELECT * FROM users WHERE user_id = #{user_id}")
//    public User findUserById(Integer user_id);
//
//    @Insert("""
//        INSERT_INTO users (user_id, username, email, password, total_published_books, role)
//        VALUES (#{user_id}, #{username}, #{email}, #{password}, #{total_published_books}, #{role})
//    """)
//    public int insertNewUser(User user);
//
//    @Update("""
//            UPDATE users
//            SET username = #{username}, email = #{email}, password = #{password}, total_published_books = #{total_published_books}
//            WHERE user_id = #{user_id}
//            """)
//    int updateUser (User user);
//
//    @Delete("""
//            DELETE FROM users
//            WHERE user_id = #{user_id}
//            """)
//    int deleteUserByUserId(Integer user_id);

    Optional<User> findUserByEmailEqualsIgnoreCase(String email);
    Optional<User> findUserByEmail(String email);
}

