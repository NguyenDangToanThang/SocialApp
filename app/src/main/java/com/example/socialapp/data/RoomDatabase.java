package com.example.socialapp.data;

import static com.example.socialapp.data.RoomDatabase.DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.socialapp.data.dao.UserDAO;
import com.example.socialapp.models.User;

@Database(entities = {User.class}, version = DATABASE_VERSION)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static volatile RoomDatabase roomDatabase;
    public abstract UserDAO getUserDAO();
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "social-database";
    public static RoomDatabase getInstance(Context context) {
        if (roomDatabase == null) synchronized (RoomDatabase.class){
            roomDatabase = Room.databaseBuilder(context, RoomDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return roomDatabase;
    }

}
