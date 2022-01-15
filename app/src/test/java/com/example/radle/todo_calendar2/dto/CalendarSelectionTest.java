package com.example.radle.todo_calendar2.dto;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.easymock.PowerMock;

import java.time.LocalDateTime;
import java.time.Month;


@RunWith(PowerMockRunner.class)
@PrepareForTest({VisibleCalendarSelection.class, LocalDateTime.class})
public class CalendarSelectionTest extends TestCase {

    @Test
    public void halfHourLater_shouldReturnCalendarSelectionInNextHalfHour_whenCalledWithFirstHalfOfHour() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.SEPTEMBER, 25, 14, 10, 0);
        LocalDateTime expectedStartTime = LocalDateTime.of(2021, Month.SEPTEMBER, 25, 14, 30, 0);
        LocalDateTime expectedEndTime = LocalDateTime.of(2021, Month.SEPTEMBER, 25, 15, 0, 0);
        PowerMock.mockStatic(LocalDateTime.class);
        EasyMock.expect(LocalDateTime.now()).andReturn(localDateTime);
        PowerMock.replayAll();

        CalendarSelection expectedSelection = new CalendarSelection.Builder()
                .withStartTime(expectedStartTime)
                .withEndTime(expectedEndTime)
                .build();

        CalendarSelection actualSelection = VisibleCalendarSelection.halfHourLater();
        assertEquals(expectedSelection, actualSelection);
        PowerMock.verifyAll();
    }
    
    @Test
    public void halfHourLater_shouldReturnCalendarSelectionInNextHalfHour_whenCalledWithSecondHalfOfHour() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, Month.SEPTEMBER, 25, 19, 50, 0);
        LocalDateTime expectedStartTime = LocalDateTime.of(2021, Month.SEPTEMBER, 25, 20, 0, 0);
        LocalDateTime expectedEndTime = LocalDateTime.of(2021, Month.SEPTEMBER, 25, 20, 30, 0);
        PowerMock.mockStatic(LocalDateTime.class);
        EasyMock.expect(LocalDateTime.now()).andReturn(localDateTime);
        PowerMock.replayAll();

        CalendarSelection expectedSelection = new CalendarSelection.Builder()
                .withStartTime(expectedStartTime)
                .withEndTime(expectedEndTime)
                .build();

        CalendarSelection actualSelection = CalendarSelection.halfHourLater();
        assertEquals(expectedSelection, actualSelection);
        PowerMock.verifyAll();
    }
}