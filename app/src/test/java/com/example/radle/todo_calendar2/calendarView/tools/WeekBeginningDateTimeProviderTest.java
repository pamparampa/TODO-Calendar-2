package com.example.radle.todo_calendar2.calendarView.tools;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class WeekBeginningDateTimeProviderTest {
    WeekBeginningDateTimeProvider subject = new WeekBeginningDateTimeProvider();

    @Test
    public void getWeekBeginningForDateTime_shouldReturnSameDateTime_whenWeekBeginingDateTimeIsPassed() {
        final LocalDateTime dateTime = LocalDateTime.of(2019, Month.AUGUST, 26, 0, 0);
        Assert.assertEquals(dateTime, this.subject.getWeekBeginning(dateTime));
    }

    @Test
    public void getWeekBeginning_shouldReturnWeekBeginningDateTime_shenSomeLaterTimeIsPassed() {
        final LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.AUGUST, 26, 0, 0);
        Assert.assertEquals(expectedDateTime,
                this.subject.getWeekBeginning(LocalDateTime.of(2019, Month.SEPTEMBER, 1,
                        13, 20)));
    }

    @Test
    public void getNextWeekBeginning_shouldReturnNextWeekBeginningDateTime_whenSomeDateTimePassed() {
        final LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.SEPTEMBER, 2, 0, 0);
        Assert.assertEquals(expectedDateTime,
                this.subject.getNextWeekBeginning(LocalDateTime.of(2019, Month.AUGUST, 27, 12, 3)));
    }

    @Test
    public void getNextNextWeekBeginning_shouldReturnNextWeekBeginningDateTime_whenSomeDateTimePassed() {
        final LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.SEPTEMBER, 9, 0, 0);
        Assert.assertEquals(expectedDateTime,
                this.subject
                        .getNextNextWeekBeginning(LocalDateTime.of(2019, Month.AUGUST, 27, 12, 3)));
    }

    @Test
    public void getPrevWeekBeginning_shouldReturnPrevWeekBeginningDateTime_whenSomeDateTimePassed() {
        final LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.AUGUST, 19, 0, 0);
        Assert.assertEquals(expectedDateTime,
                this.subject.getPrevWeekBeginning(LocalDateTime.of(2019, Month.AUGUST, 28, 0, 0)));
    }

    @Test
    public void getPrevPrevWeekBeginning_shouldReturnPrevPrevWeekBeginningDateTime_whenSomeDateTimePassed() {
        final LocalDateTime expectedDateTime = LocalDateTime.of(2019, Month.AUGUST, 12, 0, 0);
        Assert.assertEquals(expectedDateTime,
                this.subject
                        .getPrevPrevWeekBeginning(LocalDateTime.of(2019, Month.AUGUST, 28, 0, 0)));
    }

}