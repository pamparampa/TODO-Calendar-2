package com.example.radle.todo_calendar2.todoList.view.dto;

import com.example.radle.todo_calendar2.todoList.entity.Period;

public class EditableItemElement implements ToDoListElement {
    private String taskTitle;
    private Period period;

    public EditableItemElement(String taskTitle, Period period) {
        this.taskTitle = taskTitle;
        this.period = period;
    }

    @Override
    public int getViewType() {
        return ToDoListElement.EDITABLE_ITEM_VIEW_TYPE;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(CharSequence taskTitle) {
        this.taskTitle = taskTitle.toString();
    }
}
