package com.example.radle.todo_calendar2.todoList.view.dto;

import android.os.Parcelable;

import com.example.radle.todo_calendar2.todoList.entity.Period;

public interface ToDoListElement extends Parcelable {
    int HEADER_VIEW_TYPE = 0;
    int VISIBLE_ITEM_VIEW_TYPE = 1;
    int EDITABLE_ITEM_VIEW_TYPE = 2;
    int ENCOURAGE_ELEMENT = 3;

    int getViewType();

    Period getPeriod();
}
