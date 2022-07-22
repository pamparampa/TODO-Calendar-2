package com.example.radle.todo_calendar2.todoList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskService extends JobIntentService {

    public void enqueue(final Context context, final Intent intent) {
        enqueueWork(context, TaskService.class, 1, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        ToDoDatabase toDoDatabase = ToDoDatabase.getDatabase();
        List<Task> tasks = toDoDatabase.taskDao().getAll();
        sendResult(intent.getParcelableExtra("receiver"), tasks);
    }

    private void sendResult(ResultReceiver resultReceiver, List<Task> tasks) {
        resultReceiver.send(333, prepareBundle(tasks));
    }

    private Bundle prepareBundle(List<Task> tasks) {
        final Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Tasks", new ArrayList(tasks));
        return bundle;
    }
}
