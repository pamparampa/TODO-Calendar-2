package com.example.radle.todo_calendar2.calendarView;

import com.example.radle.todo_calendar2.calendarView.dto.CalendarEvent;

import java.util.List;

public interface EventsDao {
    List<CalendarEvent> getEvents();
}
