package com.example.radle.todo_calendar2.calendarView.tools.events;

import android.graphics.Rect;


import com.example.radle.todo_calendar2.calendarView.CalendarEventPartWithBounds;
import com.example.radle.todo_calendar2.dto.CalendarEvent;
import com.example.radle.todo_calendar2.dto.VisibleCalendarSelection;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CalendarEventSearcherTest {

    private CalendarEventSearcher eventSearcher;

    @Before
    public void init() {
        this.eventSearcher = new CalendarEventSearcher();
    }

    @Test
    public void getEvent_shouldReturnEmpty_whenThereAreNoEvents() {
        Optional<CalendarEvent> event = eventSearcher.getEvent(
                new VisibleCalendarSelection.Builder().withX(0).withY(0).build());
        assertFalse(event.isPresent());
    }

    @Test
    public void getEvent_shouldReturnEmpty_whenEventIsOnOtherPosition() {
        CalendarEventPartWithBounds event = eventWithBounds(50, 50, 60, 60);
        eventSearcher.putEvents(ImmutableSet.of(event));
        Optional<CalendarEvent> foundEvent = eventSearcher.getEvent(
                new VisibleCalendarSelection.Builder().withX(10).withY(10).build());
        assertFalse(foundEvent.isPresent());
    }

    @Test
    public void getEvent_shouldReturnSelectedEvent_whenThereAreFewEvents() {
        CalendarEventPartWithBounds event1 = eventWithBounds(0, 5, 9, 15);
        CalendarEventPartWithBounds event2 = eventWithBounds(10, 5, 20, 15);
        CalendarEventPartWithBounds event3 = eventWithBounds(50, 50, 60, 60);
        eventSearcher.putEvents(ImmutableSet.of(event1, event2, event3));
        Optional<CalendarEvent> foundEvent = eventSearcher.getEvent(
                new VisibleCalendarSelection.Builder().withX(10).withY(10).build());
        assertEquals(event2, foundEvent.get());
    }

    private CalendarEventPartWithBounds eventWithBounds(int left, int top, int right, int bottom) {
        Rect rect = new Rect();
        rect.left = left;
        rect.top = top;
        rect.right = right;
        rect.bottom = bottom;
        return new CalendarEventPartWithBounds("event", LocalDateTime.now(), LocalDateTime.now(),
                new CalendarEvent("1", "event", LocalDateTime.now(), LocalDateTime.now()),
                rect);
    }

}