package com.example.radle.todo_calendar2.todoList.view.dto;

public interface ToDoListElement {
    int HEADER_VIEW_TYPE = 0;
    int VISIBLE_ITEM_VIEW_TYPE = 1;
    int EDITABLE_ITEM_VIEW_TYPE = 2;

    int getViewType();
}
