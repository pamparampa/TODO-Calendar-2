package com.example.radle.todo_calendar2.todoList.entity;


import java.util.Arrays;

public enum Period {
    TODAY,
    YESTERDAY,
    THIS_WEEK,
    NEXT_WEEK,
    THIS_MONTH,
    NEXT_MONTH,
    THIS_WINTER,
    THIS_SPRING,
    THIS_SUMMER,
    THIS_AUTUMN,
    THIS_YER,
    NEXT_YEAR,
    SOMETIME;

    public static String[] getNames() {
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}
