package com.example.radle.todo_calendar2.todoList.entity;


import java.util.Arrays;

public enum Period {
    TODAY("Today"),
    TOMORROW("Tomorrow"),
    THIS_WEEK("This week"),
    NEXT_WEEK("Next week"),
    THIS_MONTH("This month"),
    NEXT_MONTH("Next month"),
    THIS_WINTER("This winter"),
    THIS_SPRING("This spring"),
    THIS_SUMMER("This summer"),
    THIS_AUTUMN("This autumn"),
    THIS_YEAR("This year"),
    THIS_WINTER2("This winter"),
    NEXT_SPRING("Next spring"),
    NEXT_SUMMER("Next summer"),
    NEXT_AUTUMN("Next autumn"),
    NEXT_YEAR("Next year"),
    SOMETIME("Sometime");

    private final String name;

    Period(String name) {
        this.name = name;
    }

    public static String[] getNames() {
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }

    public String getName() {
        return this.name;
    }
}
