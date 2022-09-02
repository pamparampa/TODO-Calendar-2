package com.example.radle.todo_calendar2.todoList.view.dto;

public class HeaderElement implements ToDoListElement {
    private final String title;

    public HeaderElement(String title) {
        this.title = title;
    }

    @Override
    public int getViewType() {
        return ToDoListElement.HEADER_VIEW_TYPE;
    }

    public String getTitle() {
        return title;
    }
}
