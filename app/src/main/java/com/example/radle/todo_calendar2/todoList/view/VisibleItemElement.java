package com.example.radle.todo_calendar2.todoList.view;

public class VisibleItemElement implements ToDoListElement{
    private String taskTitle;
    private boolean isFinished;

    public VisibleItemElement(String taskTitle, boolean isFinished) {
        this.taskTitle = taskTitle;
        this.isFinished = isFinished;
    }

    @Override
    public int getViewType() {
        return ToDoListElement.VISIBLE_ITEM_VIEW_TYPE;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
