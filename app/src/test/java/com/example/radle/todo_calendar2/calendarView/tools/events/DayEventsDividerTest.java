package com.example.radle.todo_calendar2.calendarView.tools.events;


import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class DayEventsDividerTest {
    DayEventsDivider subject = new DayEventsDivider();

    @Test
    public void divide_shouldReturnEmptyMap_whenThereAreNoEvents() {
        Assert.assertEquals(Collections.emptyMap(), this.subject.divide(Collections.emptySet()));
    }

}