package com.example.radle.todo_calendar2.todoList.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.radle.todo_calendar2.todoList.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    public List<Task> getAll();

    @Insert
    public void insertTask(Task task);
}
