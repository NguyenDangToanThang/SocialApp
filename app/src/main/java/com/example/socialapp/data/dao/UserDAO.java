package com.example.socialapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.socialapp.models.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users WHERE id = :id")
    User getUserByUserId(int id);
    @Query("SELECT * FROM users WHERE email = :email")
    User getUserByUserEmail(String email);
    @Query("SELECT * FROM users WHERE fullname LIKE :fullname" )
    List<User> getUserByName(String fullname);
    @Insert
    void insertUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);
}
