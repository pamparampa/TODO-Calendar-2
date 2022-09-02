package com.example.radle.todo_calendar2.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.example.radle.todo_calendar2.todoList.entity.Task;
import java.util.List;
import java.util.function.Consumer;

public class TasksReceiver extends ResultReceiver {
    private final Consumer<List<Task>> consumer;

    public TasksReceiver(Handler handler, Consumer<List<Task>> consumer) {
        super(handler);
        this.consumer = consumer;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        /*if (resultCode == 333) {
            final ArrayList<Task> tasks =
                    resultData.getParcelableArrayList("Tasks");
            this.consumer.accept(tasks);
        }*/
    }
}
