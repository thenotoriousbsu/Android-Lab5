package com.example.lab5.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE first_name LIKE :lastFirstName || '%' OR last_name LIKE :lastFirstName || '%'")
    List<User> userSearch(String lastFirstName);

    @Query("SELECT count(*) FROM user")
    int allUsersNumber();

    @Query("SELECT count(*) FROM user WHERE first_name LIKE :number || '%' OR last_name LIKE :number || '%'")
    int usersCount(String number);

    @Insert
    Void insertUser(User... users);

    @Delete
    void delete(User user);
}
