package com.example.radle.todo_calendar2.todoList.view;

import com.example.radle.todo_calendar2.todoList.entity.Period;

public interface OnItemTextEditListener {
    public static int DO_NOTHING = 0;
    public static int EDIT_EXISTING_TASK = 1;
    public static int CREATE_NEW_TASK = 2;
    public static int CREATE_FIRST_TASK = 3;

    void onChangeState(int position, int mode, Period period);
}
