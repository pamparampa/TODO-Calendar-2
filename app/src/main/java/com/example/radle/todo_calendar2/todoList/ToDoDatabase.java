package com.example.radle.todo_calendar2.todoList;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.radle.todo_calendar2.todoList.dao.TaskDao;
import com.example.radle.todo_calendar2.todoList.entity.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    private static ToDoDatabase instance;

    public static void buildDatabase(Context context) {
        instance = Room.databaseBuilder(context, ToDoDatabase.class,
                "ToDoDatabase").build();
    }

    public static ToDoDatabase getDatabase() {
        if (instance == null) {
            throw new IllegalStateException("Database is not initialized");
        } else {
            return instance;
        }
    }

    public abstract TaskDao taskDao();
}
