package com.example.radle.todo_calendar2.todoList.view.dto;

public class VisibleItemElement implements ToDoListElement {
    private String taskTitle;
    private boolean isDone;

    public VisibleItemElement(String taskTitle, boolean isDone) {
        this.taskTitle = taskTitle;
        this.isDone = isDone;
    }

    @Override
    public int getViewType() {
        return ToDoListElement.VISIBLE_ITEM_VIEW_TYPE;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}
